# Experimenting with Kafka

## Installation
* To install Kafka, we require to download and unpack a tarball; I haven't found a corresponding
yum repository yet.
* Kafka runs on Zookeeper, so you need to install that first
* The Kafka configuration requires `listeners` to be defined, which are the addresses the socket
server is listening on. In all examples `localhost:9092` is used; however, to access Kafka from
the outside, we need to set this to the machine's IP (`192.168.33.32:9092`).
* Since Kafka uses TCP, we might need to open some ports in the firewall on certain servers. However,
I'm not sure about this, since this VM has no firewall at all!

## Consumers
* Consumers can appear either in the same group, or in different groups.
* Consumers subscribe to topics; producers publish to these topics.
* If multiple subscribed consumers are in the same group, the received data will be split among them.
The number of subscribed consumers in the same group which will receive data is however dependend
on the number of partitions the topic has. The topic needs at least the same number of partitions as
the number of subscribed consumers for all consumers to receive data. [A single partition will never
be shared across several consumers within the same group unless a consumer goes offline.](https://stackoverflow.com/a/24415632/2389405)
* If all consumers are in different groups, but subscribed to the same topic, the data will be
replicated across all consumers.
* See also [How to choose the number of partitions](https://www.confluent.io/blog/how-to-choose-the-number-of-topicspartitions-in-a-kafka-cluster/)

## Other resources to watch:
* https://www.youtube.com/playlist?list=PLkz1SCf5iB4enAR00Z46JwY9GGkaS2NON
* https://youtu.be/SspgvvQ13DU

## Demo 1
- Start the VM (`vagrant up`)
- Use one terminal window/tab to monitor the Kafka log (`vagrant ssh` and `tail -F /var/log/kafka/server.log`)
- Use another terminal to act as a producer (`vagrant ssh` and `kafka-console-producer.sh --broker-list localhost:9093 --topic test`)
- Start two instances of the Consumer application in Scala (using IntelliJ), one with `val group = "group1"` and one with `val group = "group2"`
- Use the producer to send data into Kafka
    - this should be received by both consumers

## Demo 2
- Start the VM (`vagrant up`)
- Use one terminal window/tab to monitor the Kafka log (`vagrant ssh` and `tail -F /var/log/kafka/server.log`)
- Use another terminal to create a new topic (`kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic demo`)
- You can use this same terminal to act as a producer (`vagrant ssh` and `kafka-console-producer.sh --broker-list localhost:9093 --topic demo`)
- Start four instances of the Consumer application in Scala (using IntelliJ), three with `val group = "group1"` and one with `val group = "group2"`
- Use the producer to send data into Kafka
    - notice that consumer4 (`group2`) receives all messages
    - notice that for `group1` the messages are distributed between the three consumers;
      they do not get all the messages individually! This is because of the partition setting on
      this topic, as described in the [Consumer](#Consumers) section.
    - notice that if you run yet another consumer in `group1`, that it will never receive any data,
      as the number of consumers now exceeds the number of partitions in the topic
