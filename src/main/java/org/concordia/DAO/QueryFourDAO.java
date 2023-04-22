package org.concordia.DAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.concordia.MongoDB.MongoDBUtil;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class QueryFourDAO {
	private static final String QUERY_COLLECTION_NAME = "EduCostStatQueryFour";
	private static final String COLLECTION_NAME = "EduCostStat";

	public static List<Document> query(int baseYear, int yearsAgo, String type, String length) {
		// Get collections
		MongoCollection<Document> collection = MongoDBUtil.getCollection(COLLECTION_NAME);
		MongoCollection<Document> queryCollection = MongoDBUtil.getCollection(QUERY_COLLECTION_NAME);

		// Check if the query result is already in the query collection
		List<Document> existingResult = queryCollection
				.find(Filters.eq("query", Arrays.asList(baseYear, yearsAgo, type, length))).into(new ArrayList<>());

		if (!existingResult.isEmpty()) {
			System.out.println("Query result already exists in the collection.");
			for (Document document : existingResult) {
				List<Document> results = (List<Document>) document.get("result");
				for (Document result : results) {
					System.out.println(result.getString("state") + ": " + result.getDouble("growthRate"));
				}
			}
			return existingResult;
		}

		// Set start year
		int startYear = baseYear - yearsAgo;

		// Build the aggregation pipeline
		List<Bson> pipeline = new ArrayList<>();
		pipeline.add(Aggregates.match(Filters.and(Filters.gte("year", startYear), Filters.lte("year", baseYear),
				Filters.eq("type", type), Filters.eq("length", length))));
		pipeline.add(Aggregates.group(new Document().append("state", "$state").append("year", "$year"),
				Accumulators.sum("totalExpense", "$value")));
		pipeline.add(Aggregates.sort(Sorts.ascending("_id.state", "_id.year")));

		// Execute the aggregation pipeline
		List<Document> stateYearExpenses = collection.aggregate(pipeline).into(new ArrayList<>());

		// Check if there is any result
		if (stateYearExpenses.isEmpty()) {
			System.out.println("No data found.");
			return stateYearExpenses;
		}

		// Calculate growth rates for each state and add to list
		List<Document> growthRates = new ArrayList<>();
		String currentState = "";
		double startExpense = 0;
		double endExpense = 0;

		for (Document stateYearExpense : stateYearExpenses) {
			String state = stateYearExpense.get("_id", Document.class).getString("state");
			int year = stateYearExpense.get("_id", Document.class).getInteger("year");

			if (!state.equals(currentState)) {
				// If it is a new state, reset the start expense and end expense
				currentState = state;
				startExpense = ((Number) stateYearExpense.get("totalExpense")).doubleValue();
				endExpense = 0.0;
			} else if (year == baseYear) {
				// If it is the base year, calculate the growth rate and add to list
				endExpense = ((Number) stateYearExpense.get("totalExpense")).doubleValue();

				double growthRate = ((double) (endExpense - startExpense)) / (startExpense * yearsAgo) * 100;

				growthRates.add(new Document("state", state).append("growthRate", growthRate));
			}
		}
		// Sort the growth rates list in descending order and get the top 5
		growthRates.sort((doc1, doc2) -> Double.compare(doc2.getDouble("growthRate"), doc1.getDouble("growthRate")));
		List<Document> topFiveGrowthRates = growthRates.subList(0, Math.min(5, growthRates.size()));

		Document queryResult = new Document();
		queryResult.append("query", Arrays.asList(baseYear, yearsAgo, type, length));
		queryResult.append("result", topFiveGrowthRates);
		queryCollection.insertOne(queryResult);
		System.out.println("Query result saved in the collection.");

		System.out.println("Top 5 states with highest growth rate:");
		for (Document result : topFiveGrowthRates) {
			System.out.printf("%-20s %s%n", result.getString("state"), result.getDouble("growthRate"));
		}
		return topFiveGrowthRates;

	}

	public static void main(String[] args) {
		int baseYear = 2020;
		int yearsAgo = 3; // 1, 3, or 5
		String type = "Public In-State";
		String length = "4-year";
		query(baseYear, yearsAgo, type, length);
	}
}
