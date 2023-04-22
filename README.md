# Edu-Analytics
A Distributed System with gRPC, MongoDB NoSQL Database, RabbitMQ, and Message-Driven Data Processing


A distributed system is a software architecture that allows multiple independent computers to work together as a single system. One way to build such a system is by using gRPC, a high-performance remote procedure call (RPC) framework, and NoSQL MongoDB, a popular document-oriented database.

gRPC enables communication between different services in a distributed system by allowing them to call each other's functions as if they were local. This makes it easier to build scalable and fault-tolerant systems, as services can be added or removed without affecting the overall architecture.

MongoDB is a NoSQL database that stores data in flexible, JSON-like documents. It is a popular choice for building distributed systems because it can handle large amounts of data and scale horizontally across multiple servers.

To process data in a message-driven manner, a message broker like RabbitMQ can be used. RabbitMQ is an open-source message broker that enables communication between different services by allowing them to publish and subscribe to messages. This allows for asynchronous communication between services, which can improve performance and scalability.

When combined with MongoDB, RabbitMQ can be used to implement a message-driven data processing architecture. In this architecture, messages are published to RabbitMQ and then consumed by a service that processes the data and stores it in MongoDB.
