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

public class QueryThreeDAO {
	// MongoDB collection names
	private static final String COLLECTION_NAME = "EduCostStat";
	private static final String QUERY_COLLECTION_NAME = "EduCostStatQueryThree";

	// Number of results to return
	private static final int TOP_N = 5;

	@SuppressWarnings("unchecked")
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
			return (List<Document>) result.get("result");
		}

		// Query the top 5 most economic states (with overall expense) given a year,
		// type, length
		List<Bson> pipeline = Arrays.asList(
				Aggregates.match(
						Filters.and(Filters.eq("year", year), Filters.eq("type", type), Filters.eq("length", length))),
				Aggregates.group("$state", Accumulators.sum("total", "$value")),
				Aggregates.sort(Sorts.ascending("total")), Aggregates.limit(TOP_N));
		List<Document> documents = collection.aggregate(pipeline).into(new ArrayList<>());

		// Save the query as a document in the collection if it is not empty
		if (!documents.isEmpty()) {
			Document queryDocument = new Document().append("year", year).append("type", type).append("length", length)
					.append("result", documents);
			MongoDBUtil.getCollection(QUERY_COLLECTION_NAME).insertOne(queryDocument);
			System.out.println("Query result saved in the collection.");
		} else {
			System.out.println("No data found for the given query.");
		}
		return documents;
	}

	public static void main(String[] args) {
		// Specify the query parameters
		int year = 2017;
		String type = "Public In-State";
		String length = "2-year";

		// Execute the query and print the results
		List<Document> documents = QueryThreeDAO.query(year, type, length);
		if (!documents.isEmpty()) {
			System.out.println("Top " + TOP_N + " most economic states (with overall expense):");
			for (Document document : documents) {
				String state = document.getString("_id");
				if (state == null) {
					continue;
				}
				Integer total = document.getInteger("total");
				if (total == null) {
					continue;
				}
				System.out.println(state + ": " + (double) total);
			}
		}
	}

}