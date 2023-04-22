# Edu-Analytics
A Distributed System with gRPC, MongoDB NoSQL Database, RabbitMQ, and Message-Driven Data Processing


The goal of this project is to develop a distributed data processing system that utilizes a microservice architecture, message queuing with RabbitMQ, and MongoDB NoSQL database to analyze educational cost and economic data in the United States. The system is developed using Java and leverages the RabbitMQ Java client library and the MongoDB Java driver to connect to a RabbitMQ message broker and MongoDB database, respectively.

The system is composed of a producer and a consumer, which communicate through a RabbitMQ message broker to enable asynchronous communication and decouple the two components. The producer generates messages in response to user queries, and the consumer processes these messages and produces responses.

The system supports five different queries, each with its own message format and response format, designed to retrieve data on educational cost, economic indicators, and growth rates to provide insights into trends and patterns in the data. These queries are implemented using a gRPC client that connects to a gRPC gateway, which in turn connects to five different DAO QueryTypes, each representing a specific query to the data stored in the EduCostStat collection.

The project is divided into two main tasks. The first task involves setting up a MongoDB database, populating it with data samples, and developing Java DAO classes to represent different queries to the data stored in the EduCostStat collection. The second task involves creating a Protocol Buff definition file to represent the request, response, and service for each query defined in Task 1, developing a Java program for each service defined in Task 2.1 as gRPC services, and finally, developing a gRPC client and server (or gateway) code to communicate as RPC calls for the five queries defined in Task 1.
