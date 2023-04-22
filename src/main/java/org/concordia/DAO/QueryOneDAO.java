package org.concordia.DAO;

import java.util.Arrays;

import org.bson.Document;
import org.concordia.MongoDB.MongoDBUtil;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class QueryOneDAO {

	private static final String COLLECTION_NAME = "EduCostStat";
	private static final String QUERY_COLLECTION_NAME = "EduCostStatQueryOne";

	public static int query(int year, String state, String type, String length, String expense) {

		// Get the EduCostStat and query collection instances
		MongoCollection<Document> collection = MongoDBUtil.getCollection(COLLECTION_NAME);
		MongoCollection<Document> queryCollection = MongoDBUtil.getCollection(QUERY_COLLECTION_NAME);

		// Create a query document based on the input parameters
		Document query = new Document().append("year", year).append("state", state).append("type", type)
				.append("length", length).append("expense", expense);

		// Check if the query result is already in the query collection
		Document result = queryCollection.find(new Document("query", Arrays.asList(year, state, type, length, expense)))
				.first();
		if (result != null) {
			// Query already exists in the collection
			System.out.println("Query already exists in the collection.");
			int total = result.getInteger("result");
			System.out.println("Total expense for query: year=" + year + ", state=" + state + ", type=" + type
					+ ", length=" + length + ", expense=" + expense + " is $" + total);
			return total;
		}

		// Execute the query on the EduCostStat collection
		FindIterable<Document> findResult = collection.find(query);
		int total = 0;
		for (Document doc : findResult) {
			total += doc.getInteger("value");
		}

		if (total == 0) {
			System.out.println("No results for query " + query);
			return 0;
		}

		// Save the query result to the query collection
		System.out.println("Total expense for query: year=" + year + ", state=" + state + ", type=" + type + ", length="
				+ length + ", expense=" + expense + " is $" + total);
		Document queryDocument = new Document().append("query", Arrays.asList(year, state, type, length, expense))
				.append("result", total);
		queryCollection.insertOne(queryDocument);
		System.out.println("Query result saved in the collection:");
		System.out.println(queryDocument.toJson());
		return total;
	}

	public static void main(String[] args) {
		// Test QueryOneDAO for existing data
		int year = 2015;
		String state = "Ohio";
		String type = "Public In-State";
		String length = "4-year";
		String expense = "Room/Board";
		QueryOneDAO.query(year, state, type, length, expense);

		year = 2016;
		state = "Louisiana";
		type = "Private";
		length = "4-year";
		expense = "Fees/Tuition";
		QueryOneDAO.query(year, state, type, length, expense);

		// Test QueryOneDAO for non-existing data
		year = 2019;
		state = "Louisiana";
		type = "Private";
		length = "2-year";
		expense = "Fees/Tuition";
		QueryOneDAO.query(year, state, type, length, expense);
	}
}
