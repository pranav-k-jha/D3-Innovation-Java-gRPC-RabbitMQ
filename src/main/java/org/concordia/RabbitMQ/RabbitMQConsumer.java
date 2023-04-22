package org.concordia.RabbitMQ;

import com.google.protobuf.InvalidProtocolBufferException;
import com.mongodb.client.MongoCollection;
import com.rabbitmq.client.*;

import org.bson.Document;
import org.concordia.DAO.QueryFiveDAO;
import org.concordia.DAO.QueryFourDAO;
import org.concordia.DAO.QueryThreeDAO;
import org.concordia.DAO.QueryTwoDAO;
import org.concordia.MongoDB.MongoDBUtil;

import proto.EduCostStat.*;
import proto.EduCostStat.QueryFiveResponse.RegionCost;
import proto.EduCostStat.QueryFourResponse.StateGrowthRate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


public class RabbitMQConsumer {

	// Define constants for RabbitMQ server connection details and exchange name
	private static final String HOST = "localhost";
	private static final int PORT = 5672;
	private static final String EXCHANGE_NAME = "EduCostStat";

	// Method to bind a queue to a specific routing key on a channel
	private static void bindQueueToTopic(Channel channel, String queueName, String routingKey) throws IOException {
		channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
	}

	// Main method that sets up the connection to RabbitMQ and creates and binds
	// queues to specific routing keys
	public static void main(String[] args) throws Exception {

		// Set up connection factory with server details
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		factory.setPort(PORT);

		// Create a connection and channel to interact with RabbitMQ server
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

			// Declare exchange type as TOPIC
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

			// Create and bind queue for Query One messages
			String queueNameQueryOne = channel.queueDeclare("EduCostStat_query_one", true, false, false, null)
					.getQueue();
			bindQueueToTopic(channel, queueNameQueryOne, "EduCostStatQueryOne.#");

			// Create and bind queue for Query Two messages
			String queueNameQueryTwo = channel.queueDeclare("EduCostStat_query_two", true, false, false, null)
					.getQueue();
			bindQueueToTopic(channel, queueNameQueryTwo, "EduCostStatQueryTwo.#");

			// Create and bind queue for Query Three messages
			String queueNameQueryThree = channel.queueDeclare("EduCostStat_query_three", true, false, false, null)
					.getQueue();
			bindQueueToTopic(channel, queueNameQueryThree, "EduCostStatQueryThree.#");

			// Create and bind queue for Query Four messages
			String queueNameQueryFour = channel.queueDeclare("EduCostStat_query_four", true, false, false, null)
					.getQueue();
			bindQueueToTopic(channel, queueNameQueryFour, "EduCostStatQueryFour.#");

			// Create and bind queue for Query Five messages
			String queueNameQueryFive = channel.queueDeclare("EduCostStat_query_five", true, false, false, null)
					.getQueue();
			bindQueueToTopic(channel, queueNameQueryFive, "EduCostStatQueryFive.#");

			// Print a message indicating that the consumer is waiting for messages
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

			// Create a new latch with an initial count of 1
			CountDownLatch latch = new CountDownLatch(1);

			// Define callback function for handling messages for Query One
			DeliverCallback deliverCallbackQueryOne = (consumerTag, delivery) -> {
				try {
					// Parse the message body into a QueryOneRequest object
					QueryOneRequest request = QueryOneRequest.parseFrom(delivery.getBody());
					System.out.println("Received query one request: " + request);

					// Retrieve data from MongoDB
					MongoCollection<Document> collection = MongoDBUtil.getCollection("EduCostStatQueryOne");
					Document query = new Document("query", Arrays.asList(request.getYear(), request.getState(),
							request.getType(), request.getLength(), request.getExpense()));
					System.out.println("Executing query: " + query.toString());
					Document result = collection.find(query).first();

					// If query result exists, generate a response and publish it to the exchange
					// with appropriate routing key
					if (result != null) {
						int expense = result.getInteger("result");
						System.out.println("Query One result: " + expense);

						// Generate the response
						QueryOneResponse response = QueryOneResponse.newBuilder().setQueryId("1")
								.setTotalExpense(expense).build();

						// Publish the response message to the exchange with the appropriate routing key
						String routingKey = String.format("QueryOneResponse.%d.%s.%s.%s.%s", request.getYear(),
								request.getState(), request.getType(), request.getLength(), request.getExpense());
						channel.basicPublish(EXCHANGE_NAME, routingKey, null, response.toByteArray());
						System.out.println("Sent query one response: " + response);
					} else {
						System.out.println("Query result not found in the collection.");
					}

				} catch (InvalidProtocolBufferException e) {
					// Handle errors when parsing the message body
					System.out.println("Invalid message received for query one: " + e.getMessage());
				}
			};
			// Define callback function for handling messages for Query Two
			DeliverCallback deliverCallbackQueryTwo = (consumerTag, delivery) -> {
				try {
					// Parse the message body into a QueryTwoRequest object
					QueryTwoRequest request = QueryTwoRequest.parseFrom(delivery.getBody());
					System.out.println("Received query two request: " + request);

					// Retrieve data from MongoDB
					List<Document> expensiveStates = QueryTwoDAO.query(request.getYear(), request.getType(),
							request.getLength());

					// Build the response using the list of documents
					QueryTwoResponse.Builder responseBuilder = QueryTwoResponse.newBuilder();
					for (Document doc : expensiveStates) {
						String state = doc.getString("_id");
						int total = doc.getInteger("total");
						ExpensiveState expensiveState = ExpensiveState.newBuilder().setState(state).setTotal(total)
								.build();
						responseBuilder.addExpensiveStates(expensiveState);
					}
					QueryTwoResponse response = responseBuilder.build();

					// Publish the response message to the exchange with the appropriate routing key
					String routingKey = String.format("QueryTwoResponse.%d.%s.%s", request.getYear(), request.getType(),
							request.getLength());
					channel.basicPublish(EXCHANGE_NAME, routingKey, null, response.toByteArray());
					System.out.println("Sent query two response: " + response);

				} catch (InvalidProtocolBufferException e) {
					// Handle errors when parsing the message body
					System.out.println("Invalid message received for query two: " + e.getMessage());
				}
			};
			// Define callback function for handling messages for Query Three
			DeliverCallback deliverCallbackQueryThree = (consumerTag, delivery) -> {
				try {
					// Parse the message body into a QueryThreeRequest object
					QueryThreeRequest request = QueryThreeRequest.parseFrom(delivery.getBody());
					System.out.println("Received query three request: " + request);

					// Query the database for the economic states
					List<Document> economicStates = QueryThreeDAO.query(request.getYear(), request.getType(),
							request.getLength());

					// Build the response using the list of documents
					QueryThreeResponse.Builder responseBuilder = QueryThreeResponse.newBuilder();
					for (Document doc : economicStates) {
						String state = doc.getString("_id");
						int total = doc.getInteger("total");
						EconomicState economicState = EconomicState.newBuilder().setState(state).setTotal(total)
								.build();
						responseBuilder.addEconomicStates(economicState);
					}
					QueryThreeResponse response = responseBuilder.build();

					// Publish the response message to the exchange with the appropriate routing key
					String routingKeyResponseQueryThree = String.format("QueryThreeResponse.%d.%s.%s",
							request.getYear(), request.getType(), request.getLength());
					channel.basicPublish(EXCHANGE_NAME, routingKeyResponseQueryThree, null, response.toByteArray());
					System.out.println("Sent query three response: " + response);

				} catch (InvalidProtocolBufferException e) {
					// Handle errors when parsing the message body
					System.out.println("Invalid message received for query three: " + e.getMessage());
				}
			};

			// Define callback function for handling messages for Query Four
			DeliverCallback deliverCallbackQueryFour = (consumerTag, delivery) -> {
				try {
					// Parse the message body into a QueryFourRequest object
					QueryFourRequest request = QueryFourRequest.parseFrom(delivery.getBody());
					System.out.println("Received query four request: " + request);

					// Query the database for the growth rates
					List<Document> growthRates = QueryFourDAO.query(request.getBaseYear(), request.getYearsAgo(),
							request.getType(), request.getLength());

					// Build the response using the list of documents
					QueryFourResponse.Builder responseBuilder = QueryFourResponse.newBuilder();
					for (Document doc : growthRates) {
						String state = doc.getString("state");
						double growthRate = doc.getDouble("growthRate");
						StateGrowthRate growthRateObj = StateGrowthRate.newBuilder().setState(state)
								.setGrowthRate(growthRate).build();
						responseBuilder.addStateGrowthRates(growthRateObj);
					}
					QueryFourResponse response = responseBuilder.build();

					// Publish the response message to the exchange with the appropriate routing key
					String routingKeyResponseQueryFour = String.format("QueryFourResponse.%d.%d.%s.%s",
							request.getBaseYear(), request.getYearsAgo(), request.getType(), request.getLength());
					channel.basicPublish(EXCHANGE_NAME, routingKeyResponseQueryFour, null, response.toByteArray());
					System.out.println("Sent query four response: " + response);

				} catch (InvalidProtocolBufferException e) {
					// Handle errors when parsing the message body
					System.out.println("Invalid message received query four: " + e.getMessage());
				}
			};
			// Define callback function for handling messages for Query Five
			DeliverCallback deliverCallbackQueryFive = (consumerTag, delivery) -> {
				try {
					// Parse the message body into a QueryFiveRequest object
					QueryFiveRequest request = QueryFiveRequest.parseFrom(delivery.getBody());
					System.out.println("Received query five request: " + request);

					// Query the database for region costs
					Map<String, Double> regionCostMap = QueryFiveDAO.query(request.getYear(), request.getType(),
							request.getLength());

					// Build the response using the map of region costs
					QueryFiveResponse.Builder responseBuilder = QueryFiveResponse.newBuilder();
					for (Map.Entry<String, Double> entry : regionCostMap.entrySet()) {
						RegionCost.Builder regionCostBuilder = RegionCost.newBuilder().setRegion(entry.getKey());
						Double cost = entry.getValue();
						if (cost != null) {
							regionCostBuilder.setCost(cost);
						}
						responseBuilder.addRegionCosts(regionCostBuilder.build());
					}
					QueryFiveResponse response = responseBuilder.build();

					// Publish the response message to the exchange with the appropriate routing key
					String routingKeyResponseQueryFive = String.format("QueryFiveResponse.%d.%s.%s", request.getYear(),
							request.getType(), request.getLength());
					channel.basicPublish(EXCHANGE_NAME, routingKeyResponseQueryFive, null, response.toByteArray());
					System.out.println("Sent query five response: " + response);

				} catch (InvalidProtocolBufferException e) {
					// Handle errors when parsing the message body
					System.out.println("Invalid message received for query five: " + e.getMessage());
				}
			};
			// Define the cancelCallback to be executed when the consumer is canceled
			CancelCallback cancelCallback = consumerTag -> {
				// Decrement the latch to signal that the consumer has been canceled
				latch.countDown();
			};

			// Start consuming messages from each queue, using the appropriate callback
			// function
			channel.basicConsume(queueNameQueryOne, true, deliverCallbackQueryOne, cancelCallback);
			channel.basicConsume(queueNameQueryTwo, true, deliverCallbackQueryTwo, cancelCallback);
			channel.basicConsume(queueNameQueryThree, true, deliverCallbackQueryThree, consumerTag -> {
			});
			channel.basicConsume(queueNameQueryFour, true, deliverCallbackQueryFour, consumerTag -> {
			});
			channel.basicConsume(queueNameQueryFive, true, deliverCallbackQueryFive, consumerTag -> {
			});

			// Wait indefinitely until the consumer is canceled
			latch.await();

		}
	}
}
