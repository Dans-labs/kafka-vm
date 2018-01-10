/**
 * Copyright (C) ${project.inceptionYear} DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{ KafkaProducer, ProducerConfig, ProducerRecord }

object Producer extends App {

  run("test.dans.knaw.nl:9092")

  def run(server: String): Unit = {
    val topic = "test"

    val props = new Properties() {
      put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server)
      put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
      put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    }

    val producer = new KafkaProducer[String, String](props)

    println(s"producer: $producer")

    for (i <- 1 to 50) {
      val record = new ProducerRecord(topic, "key", s"hello $i")
      println(s"send record: $record")
      producer.send(record)
      Thread.sleep(200)
    }

    val record = new ProducerRecord(topic, "key", "the end " + new java.util.Date)
    println(s"send last record: $record")
    producer.send(record)

    producer.close()
  }
}
