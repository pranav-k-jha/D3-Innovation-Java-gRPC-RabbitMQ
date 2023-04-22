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

public class QueryTwoDAO {
	private static final String COLLECTION_NAME = "EduCostStat";
	private static final String QUERY_COLLECTION_NAME = "EduCostStatQueryTwo";
	private static final int TOP_N = 5;

	public static List<Document> query(int year, String type, String length) {
		// Get the collection from MongoDB
		MongoCollection<Document> collection = MongoDBUtil.getCollection(COLLECTION_NAME);

		// Create the query for checking if the result already exists in the collection
		Document query = new Document().append("year", year).append("type", type).append("length", length);

		// Check if the query already exists in the collection
		Document result = MongoDBUtil.getCollection(QUERY_COLLECTION_NAME).find(query).first();
		if (result != null) {
			// Query already exists in the collection
			System.out.println("Query already exists in the collection.");
			List<Document> documents = result.getList("result", Document.class);
			printTopExpensiveStates(documents);
			return documents;
		}

		// Query the top 5 most expensive states (with overall expense) given a year,
		// type, length
		List<Bson> pipeline = Arrays.asList(
				Aggregates.match(
						Filters.and(Filters.eq("year", year), Filters.eq("type", type), Filters.eq("length", length))),
				Aggregates.group("$state", Accumulators.sum("total", "$value")),
				Aggregates.sort(Sorts.descending("total")), Aggregates.limit(TOP_N));
		List<Document> documents = collection.aggregate(pipeline).into(new ArrayList<>());

		// Save the query as a document in the collection if the query is not empty
		if (!documents.isEmpty()) {
			Document queryDocument = new Document().append("year", year).append("type", type).append("length", length)
					.append("result", documents);
			MongoDBUtil.getCollection(QUERY_COLLECTION_NAME).insertOne(queryDocument);
		}

		// Print the top N most expensive states in descending order
		printTopExpensiveStates(documents);
		return documents;
	}

	/**
	 * This method prints the top N most expensive states with their total expenses.
	 * 
	 * @param documents a list of documents containing the top N most expensive
	 *                  states with their total expenses
	 */
	private static void printTopExpensiveStates(List<Document> documents) {
		if (documents.isEmpty()) {
			System.out.println("No data found for the given query.");
		} else {
			System.out.println("Top " + TOP_N + " most expensive states:");
			for (Document doc : documents) {
				String state = doc.getString("_id");
				double total = Double.valueOf(doc.getInteger("total"));
				System.out.println(state + ": " + total);
			}
		}
	}

	public static void main(String[] args) {
		int year = 2013;
		String type = "Private";
		String length = "4-year";
		QueryTwoDAO.query(year, type, length);

		year = 2022;
		type = "Private";
		length = "4-year";
		QueryTwoDAO.query(year, type, length);

	}
}
