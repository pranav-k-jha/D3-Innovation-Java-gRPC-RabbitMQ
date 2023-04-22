package org.concordia.MongoDB;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//This class provides utility methods for connecting to a MongoDB database and manipulating data
public class MongoDBUtil {
	// The MongoDB client object used to connect to the database
	private static MongoClient mongoClient;

	// Database connection parameters
	private static final String DATABASE_NAME = "EduCostStatDB";
	private static final String USERNAME = "pranavjha";
	private static final String PASSWORD = "24544";
	private static final String CLUSTER_NAME = "cluster0.tciw1xy.mongodb.net";
	private static final String CONNECTION_STRING = "mongodb+srv://" + USERNAME + ":" + PASSWORD + "@" + CLUSTER_NAME
			+ "/" + DATABASE_NAME + "?retryWrites=true&w=majority";

	// This method returns a MongoCollection object for the specified collection
	// name.
	// If the collection does not exist, it is created.
	public static MongoCollection<Document> getCollection(String collectionName) {
		// Check if a MongoClient object has been created, create one if not
		if (mongoClient == null) {
			ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
			mongoClient = MongoClients.create(connectionString);
		}

		// Get the database object
		MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

		// Check if the collection already exists
		if (database.listCollectionNames().into(new ArrayList<>()).contains(collectionName)) {
			// Collection exists, return it
			return database.getCollection(collectionName);
		}

		// Collection does not exist, create it and return it
		database.createCollection(collectionName);
		return database.getCollection(collectionName);
	}

	// This method reads data from a CSV file and inserts it into a MongoDB
	// collection
	public static void createEduCostStatCollection(String filePath) {
		MongoCollection<Document> eduCostStatCollection = getCollection("EduCostStat");

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			// Skip the first line (header row)
			reader.readLine();

			String line;
			while ((line = reader.readLine()) != null) {
				// Split the line into fields using commas as the delimiter
				String[] fields = line.split(",");

				// Skip any lines with empty fields or an incorrect number of fields
				if (fields.length != 6 || Arrays.stream(fields).limit(5).anyMatch(String::isEmpty)) {
					continue;
				}

				// Extract the values from the fields
				int currentYear = Integer.parseInt(fields[0]);
				String currentState = fields[1];
				String currentType = fields[2];
				String currentLength = fields[3];
				String currentExpense = fields[4];
				int currentValue = Integer.parseInt(fields[5]);

				// Create a new document and insert it into the collection
				Document document = new Document().append("year", currentYear).append("state", currentState)
						.append("type", currentType).append("length", currentLength).append("expense", currentExpense)
						.append("value", currentValue);
				eduCostStatCollection.insertOne(document);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Error inserting data into collection: " + e.getMessage());
		}
	}

	// This method is the entry point for the program and reads data from a CSV file
	// and
	// inserts it into a MongoDB collection.
	public static void main(String[] args) {
		String filePath = "src/main/resources/avg-cost-undergrad-by-states.csv";
		MongoDBUtil.createEduCostStatCollection(filePath);
		System.out.println("Data imported successfully!");
	}
}
