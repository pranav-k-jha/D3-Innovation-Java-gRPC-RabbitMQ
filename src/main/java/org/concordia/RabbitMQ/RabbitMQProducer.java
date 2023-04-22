package org.concordia.RabbitMQ;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.bson.Document;
import org.concordia.MongoDB.MongoDBUtil;

import proto.EduCostStat.QueryFiveRequest;
import proto.EduCostStat.QueryFourRequest;
import proto.EduCostStat.QueryOneRequest;
import proto.EduCostStat.QueryThreeRequest;
import proto.EduCostStat.QueryTwoRequest;
import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class RabbitMQProducer {

	private static final String HOST = "localhost";
	private static final int PORT = 5672;
	private static final String EXCHANGE_NAME = "EduCostStat";
	private static final String ROUTING_KEY = "EduCostStatQueryOne.%d.%s.%s.%s.%s";

	public static void main(String[] args) throws IOException, TimeoutException {
		// Load the configuration properties file
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
			props.load(fis);
		} catch (IOException e) {
			// Handle errors when loading the properties file
			e.printStackTrace();
			System.exit(1);
		}

		// Before connecting to RabbitMQ
		System.out.println("Connecting to RabbitMQ...");

		// Establish a connection with the RabbitMQ server
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		factory.setPort(PORT);
		factory.setUsername("guest");
		factory.setPassword("guest");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// After connecting to RabbitMQ
		System.out.println("Connected to RabbitMQ. Publishing messages...");

		// Declare the exchange
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		
		// Query One
		// Create a QueryOneRequest object with the specified properties from the configuration file
		QueryOneRequest queryOneRequest = QueryOneRequest.newBuilder()
				.setYear(Integer.parseInt(props.getProperty("EduCostStat_query_one.year")))
				.setState(props.getProperty("EduCostStat_query_one.state"))
				.setType(props.getProperty("EduCostStat_query_one.type"))
				.setLength(props.getProperty("EduCostStat_query_one.length"))
				.setExpense(props.getProperty("EduCostStat_query_one.expense")).build();

		// Serialize the message to a byte array
		byte[] messageBytes = queryOneRequest.toByteArray();

		// Set the routing key
		String routingKey = String.format(ROUTING_KEY, queryOneRequest.getYear(), queryOneRequest.getState(),
				queryOneRequest.getType(), queryOneRequest.getLength(), queryOneRequest.getExpense());

		// Publish the message to the exchange with the appropriate routing key
		channel.basicPublish(EXCHANGE_NAME, routingKey, null, messageBytes);
		System.out.println("Sent message for query one: " + queryOneRequest);

		// Get the collection from MongoDB
		MongoCollection<Document> collection = MongoDBUtil.getCollection("EduCostStatQueryOne");

		// Retrieve the query result from MongoDB
		List<Object> queryArray = Arrays.asList(queryOneRequest.getYear(), queryOneRequest.getState(),
				queryOneRequest.getType(), queryOneRequest.getLength(), queryOneRequest.getExpense());
		Document result = collection.find(Filters.eq("query", queryArray)).first();

		// Print the query result to the console
		if (result != null) {
			Integer expense = result.getInteger("result");
			System.out.println("Query One result: " + expense);
		} else {
			System.out.println("Query result is not found in the collection.");
		}
		// Query Two
		// Construct QueryTwoRequest object with the required parameters
		QueryTwoRequest queryTwoRequest = QueryTwoRequest.newBuilder()
				.setYear(Integer.parseInt(props.getProperty("EduCostStat_query_two.year")))
				.setType(props.getProperty("EduCostStat_query_two.type"))
				.setLength(props.getProperty("EduCostStat_query_two.length")).build();

		// Serialize QueryTwoRequest object to a byte array
		byte[] messageBytesQueryTwo = queryTwoRequest.toByteArray();

		// Set the routing key for QueryTwoRequest
		String routingKeyQueryTwo = String.format("EduCostStatQueryTwo.%d.%s.%s", queryTwoRequest.getYear(),
				queryTwoRequest.getType(), queryTwoRequest.getLength());

		// Publish QueryTwoRequest message to the exchange with the appropriate routing
		// key
		channel.basicPublish(EXCHANGE_NAME, routingKeyQueryTwo, null, messageBytesQueryTwo);
		System.out.println("Sent message for query two: " + queryTwoRequest);

		// Retrieve data from MongoDB
		MongoCollection<Document> collectionQueryTwo = MongoDBUtil.getCollection("EduCostStatQueryTwo");

		// Create a query document with the required parameters
		Document queryTwo = new Document("year", queryTwoRequest.getYear()).append("type", queryTwoRequest.getType())
				.append("length", queryTwoRequest.getLength());

		// Execute the query
		Document resultQueryTwo = collectionQueryTwo.find(queryTwo).first();

		// Process and print the query result to the console
		if (resultQueryTwo != null) {
			@SuppressWarnings("unchecked")
			List<Document> resultList = (List<Document>) resultQueryTwo.get("result");

			System.out.println("Query Two result:");
			System.out.println("Top 5 most expensive states are:");

			for (Document resultItem : resultList) {
				String state = resultItem.getString("_id");
				int total = resultItem.getInteger("total");
				System.out.println(String.format("%s: %d", state, total));
			}
		} else {
			System.out.println("No data found for the given query in the collection.");
		}
		// Create a QueryThreeRequest object with the required parameters
		QueryThreeRequest queryThreeRequest = QueryThreeRequest.newBuilder()
				.setYear(Integer.parseInt(props.getProperty("EduCostStat_query_three.year")))
				.setType(props.getProperty("EduCostStat_query_three.type"))
				.setLength(props.getProperty("EduCostStat_query_three.length")).build();

		// Serialize the message to a byte array
		byte[] messageBytesQueryThree = queryThreeRequest.toByteArray();

		// Set the routing key for QueryThreeRequest
		String routingKeyQueryThree = String.format("EduCostStatQueryThree.%d.%s.%s", queryThreeRequest.getYear(),
				queryThreeRequest.getType(), queryThreeRequest.getLength());

		// Publish the message to the exchange with the appropriate routing key
		channel.basicPublish(EXCHANGE_NAME, routingKeyQueryThree, null, messageBytesQueryThree);
		System.out.println("Sent message for query three: " + queryThreeRequest);

		// Retrieve data from MongoDB
		MongoCollection<Document> collectionQueryThree = MongoDBUtil.getCollection("EduCostStatQueryThree");

		// Create a query document with the required parameters
		Document queryThree = new Document("year", queryThreeRequest.getYear())
				.append("type", queryThreeRequest.getType()).append("length", queryThreeRequest.getLength());

		// Execute the query
		List<Document> resultListQueryThree = collectionQueryThree.find(queryThree).into(new ArrayList<>());

		if (!resultListQueryThree.isEmpty()) {
			Document resultItem = resultListQueryThree.get(0);
			@SuppressWarnings("unchecked")
			List<Document> results = (List<Document>) resultItem.get("result");

			if (results != null) {
				System.out.println("Query Three result:");
				System.out.println("Top 5 most economic states are:");
				for (Document doc : results) {
					String state = doc.getString("_id");
					Integer total = doc.getInteger("total");
					if (total != null) {
						System.out.println(String.format("%s: %d", state, total.intValue()));
					} else {
						System.out.println(String.format("%s: null", state));
					}
				}
			} else {
				System.out.println("No data found for the given query in the collection.");
			}
		}

		// Creating a QueryFourRequest object with the required parameters,
		// serialize it to a byte array, and set the routing key for the message based
		// on the parameters.

		QueryFourRequest queryFourRequest = QueryFourRequest.newBuilder()
				.setBaseYear(Integer.parseInt(props.getProperty("EduCostStat_query_four.baseYear")))
				.setYearsAgo(Integer.parseInt(props.getProperty("EduCostStat_query_four.yearsAgo")))
				.setType(props.getProperty("EduCostStat_query_four.type"))
				.setLength(props.getProperty("EduCostStat_query_four.length")).build();

		byte[] messageBytesQueryFour = queryFourRequest.toByteArray();
		String routingKeyQueryFour = String.format("EduCostStatQueryFour.%d.%d.%s.%s", queryFourRequest.getBaseYear(),
				queryFourRequest.getYearsAgo(), queryFourRequest.getType(), queryFourRequest.getLength());

		// Then, we publish the message to the RabbitMQ exchange.

		channel.basicPublish(EXCHANGE_NAME, routingKeyQueryFour, null, messageBytesQueryFour);
		System.out.println("Sent message for query four: " + queryFourRequest);

		// Next, we retrieve a MongoDB collection, create a query document with the
		// required parameters, and execute the query.

		MongoCollection<Document> collectionQueryFour = MongoDBUtil.getCollection("EduCostStatQueryFour");

		Document queryFour = new Document("query", Arrays.asList(queryFourRequest.getBaseYear(),
				queryFourRequest.getYearsAgo(), queryFourRequest.getType(), queryFourRequest.getLength()));

		FindIterable<Document> iterable = collectionQueryFour.find(queryFour);

		// Finally, we iterate over the query results and retrieve the top 5 states with
		// the highest growth rate.

		System.out.println("Query Four result:");
		for (Document doc : iterable) {
			@SuppressWarnings("unchecked")
			List<Document> results = (List<Document>) doc.get("result");
			System.out.println("Top 5 states of highest growth rate are:");
			if (results != null) {
				for (Document result1 : results) {
					String state = result1.getString("state");
					Double growthRate = result1.getDouble("growthRate");
					System.out.println(String.format("%s: %.2f", state, growthRate));
				}
			}
		}
		//Now, we create a QueryFiveRequest object with the required parameters,
		// serialize it to a byte array, and set the routing key for the message based
		// on the parameters.

		QueryFiveRequest queryFiveRequest = QueryFiveRequest.newBuilder()
				.setYear(Integer.parseInt(props.getProperty("EduCostStat_query_five.year")))
				.setType(props.getProperty("EduCostStat_query_five.type"))
				.setLength(props.getProperty("EduCostStat_query_five.length")).build();

		byte[] messageBytesQueryFive = queryFiveRequest.toByteArray();
		String routingKeyQueryFive = String.format("EduCostStatQueryFive.%d.%s.%s", queryFiveRequest.getYear(),
				queryFiveRequest.getType(), queryFiveRequest.getLength());

		// Next, we retrieve a MongoDB collection, create a query document with the
		// required parameters, and execute the query.

		MongoCollection<Document> collectionQueryFive = MongoDBUtil.getCollection("EduCostStatQueryFive");

		Document queryFive = new Document("year", queryFiveRequest.getYear()).append("type", queryFiveRequest.getType())
				.append("length", queryFiveRequest.getLength());

		Document resultItem = collectionQueryFive.find(queryFive).first();
		
		// Then, we publish the message to the RabbitMQ exchange.

		channel.basicPublish(EXCHANGE_NAME, routingKeyQueryFive, null, messageBytesQueryFive);
		System.out.println("Sent message for query five: " + queryFiveRequest);
		// Finally, we retrieve the query results and print them to the console.

		if (resultItem != null) {
			Object result1 = resultItem.get("result");
			if (result1 instanceof Document) {
				Document resultDoc = (Document) result1;
				System.out.println("Query Five result:");
				for (String region : resultDoc.keySet()) {
					Double cost = resultDoc.getDouble(region);
					if (cost != null) {
						System.out.println(String.format("%s: %.2f", region, cost.doubleValue()));
					} else {
						System.out.println(String.format("%s: null", region));
					}
				}
			} else {
				System.out.println("No data found for the given query in the collection.");
			}
		}

		// These lines of code are used to close the RabbitMQ channel and connection
		// after the program is finished using them.
		

		// The channel.close() method closes the channel.

		channel.close();

		// The connection.close() method closes the connection to the RabbitMQ broker.

		connection.close();
	}
}
