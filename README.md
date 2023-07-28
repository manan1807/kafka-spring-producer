# kafka-spring-producer
 An application that is the source of the data stream is what we call a producer. In order to generate tokens or messages and further publish it to one or more topics in the Kafka Cluster, we use Apache Kafka Producer.
Also the Producer API from Kafka helps to pack the message and deliver it to Kafka Server.

In order to send messages to the Kafka Cluster there is very much needed two servers

1). Zookeeper

2). Kafka Cluster/Server

### Zookeeper 
Zookeeper co-ordinates and manages the **Kafka Clusters** and keep track of clusters heartbeats, so when a cluster stop sending heartbeats to it, zookeeper rebalances the load to the other kafka clusters.

Command to run the zookeeper server : `zookeeper-server-start.bat zookeeper.properties`

### Kafka Broker
Kafka Broker is a structured as a KafkaServer, that is hosting various topics. There can be more than one broker resides in the kafka cluster, these brokers send and receive messages/record to the Kafka Producer and Kafka Consumer.

Command to run the kafka broker: `kafka-server-start.bat server.properties` 
By, defualt the Kafka Broker runs on 9092 port, though it can be customize to a different port and many other Kafka Brokers can be up using different ports.

**Command to- kafka topic create**

`kafka-topics.bat --bootstrap-server localhost:9092 --replication-factor 3  --partitions 3 --create --topic test-topic`

**Command to- kafka list topics**

`kafka-topics.bat --list --bootstrap-server localhost:9092`

**Command to- kafka describe topic**

`kafka-topics.bat --describe --topic first-topic --bootstrap-server localhost:9092`

**Command to- kafka command to consume data from topics**

`kafka-console-consumer --bootstrap-server localhost:9092 --topic <topicName> --from-beginning`

**Command to- produce message to multiple broker**

`kafka-console-producer --bootstrap-server localhost:9092,localhost:9093,localhost:9094 --topic test-topic`

**Command to- kafka command to produce data to topic**

`kafka-console-producer --bootstrap-server localhost:9092 --topic <topicName>`

**Command to- find consumer-groups**

`kafka-consumer-groups --bootstrap-server localhost:9092 --list`
