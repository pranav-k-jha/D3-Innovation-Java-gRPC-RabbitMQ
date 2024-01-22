# Edu-Analytics
A DISTRIBUTED SYSTEM BASED ON A MICROSERVICE ARCHITECTURE UTILIZES GRPC AND EMPLOYS MESSAGE-DRIVEN DATA PROCESSING.

Brief Description of the Project  This project has the purpose of practicing (1) RPC communication for resources and services; (2) data operations on MongoDB, a NoSQL database; (3) implementation of aggregation pipelines for data processing. A public data source is available on Kaggle. It is compiled from the National Center of Education Statistics Annual Digest, USA. The data contains the statistics of average undergraduate tuition and fees and room and board rates charged for full-time students in degree-granting postsecondary institutions. 

https://www.kaggle.com/datasets/kfoster150/avg-cost-of-undergrad-college-bystate/versions/10?resource=download

Task 1. MongoDB Data Storage and Operation 

Task 1.1 is to create a MongoDB collection named EduCostStat to store the data in the MongoDB instance running on the MongoDB online cluster. No local database is accepted. The dataset can be downloaded as Excel sheets or a .csv file. Please write a program to create a collection in MongoDB by reading the data samples from the file.

Task 1.2 is to develop data access objects in Java that represent different queries to the data. Please be aware that duplicated queries should not be inserted as a new document in a collection. 
1) Query the cost given specific year, state, type, length, and expense; and save the query as a document in a collection named EduCostStatQueryOne. 
2) Query the top 5 most expensive states (with overall expense) given a year, type, and length; and save the query as a document in a collection named EduCostStatQueryTwo. 
3) Query the top 5 most economic states (with the overall expense) given a year, type, and length; and save the query as a document in a collection named EduCostStatQueryThree. 
4) Query the top 5 states with the highest growth rate of overall expense given a range of past years, one year, three years, and five years (using the latest year as the base) , type and length; and save the query as a document in a collection named EduCostStatQueryFour. 

5) Aggregate the region‘s average overall expense for a given year, type, and length; and save the query as a document in a collection named EduCostStatQueryFive. The region’s map can be found here. https://education.nationalgeographic.org/resource/united-states-regions/ (you can search the keywords “Five US Region Map” to find our text-based mapping) (10 marks) 

Task 2. Data Communication Interface Definition and Service Implementation 

Task 2.1 Define a ProtocolBuff definition file to represent the request, response, and service for reach query in Task 1. You may use the conversion tool from JSON to ProtocolBuff. https://www.site24x7.com/tools/json-to-protobuf.html 

Task 2.2 Develop the Java program for reach service defined in Task 2.1 as gRPC services. Each service invokes the corresponding data access object classes developed in Task 1.  
Task 2.3 Develop the gRPC client and server (or gateway) code to communicate as RPC calls for the five queries defined in Task 1.

1. The producer retrieves the datasets from each collection from the MongoDB cloud service for each topic described in Task 1.1. The parameters to customize each topic is set in a configuration file. 
2. The producer publishes the data to the exchange topics with a routing key that matches to the topic for each queue. 
3. The consumer receives the data from the queue based on the topic a consumer subscribed. 
Task 1: Install rabbitmq server on the local computer or on the oracle cloud. 
Task 2: Program the producer and consumer using rabbitmq exchange topic libraries. The producer and consumer can be running on the same node but cannot using multi-threading within the same application.

----------------------------------------------------------------------------------------------------------------------------------

The goal of this project is to develop a distributed data processing system that utilizes a microservice architecture, message queuing with RabbitMQ, and MongoDB NoSQL database to analyze educational cost and economic data in the United States. The system is developed using Java and leverages the RabbitMQ Java client library and the MongoDB Java driver to connect to a RabbitMQ message broker and MongoDB database, respectively.

The system is composed of a producer and a consumer, which communicate through a RabbitMQ message broker to enable asynchronous communication and decouple the two components. The producer generates messages in response to user queries, and the consumer processes these messages and produces responses.

The system supports five different queries, each with its own message format and response format, designed to retrieve data on educational cost, economic indicators, and growth rates to provide insights into trends and patterns in the data. These queries are implemented using a gRPC client that connects to a gRPC gateway, which in turn connects to five different DAO QueryTypes, each representing a specific query to the data stored in the EduCostStat collection.

The project is divided into two main tasks. The first task involves setting up a MongoDB database, populating it with data samples, and developing Java DAO classes to represent different queries to the data stored in the EduCostStat collection. The second task involves creating a Protocol Buff definition file to represent the request, response, and service for each query defined in Task 1, developing a Java program for each service defined in Task 2.1 as gRPC services, and finally, developing a gRPC client and server (or gateway) code to communicate as RPC calls for the five queries defined in Task 1.
