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

import java.util.{ Collections, Properties }

import org.apache.kafka.clients.consumer.{ ConsumerConfig, KafkaConsumer }

import scala.collection.JavaConverters._

object Consumer extends App {

  run("test.dans.knaw.nl:9092")

  def run(server: String): Unit = {
    val topic = "test"
    val group = "group1"

    val props = new Properties() {
      put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server)
      put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
      put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
      put(ConsumerConfig.GROUP_ID_CONFIG, group)
    }

    val consumer = new KafkaConsumer[String, String](props)

    consumer.subscribe(Collections.singletonList(topic))

    println(s"subscribed to $topic")

    val currentThread = Thread.currentThread()
    Runtime.getRuntime.addShutdownHook(new Thread() {
      override def run() {
        println("closing consumer")
        consumer.close()
        currentThread.join()
      }
    })

    while (true) {
      val records = consumer.poll(100)
      for (record <- records.asScala) {
        println(record.value())
      }
    }
  }
}
