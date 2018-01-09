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
