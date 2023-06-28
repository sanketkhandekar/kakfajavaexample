package com.learning.kafka.examples;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
public class ProducerDemo {


    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {

        log.info("I am a Kafka Producer!");

        // create Producer Properties
        Properties properties = new Properties();

        // connect to Localhost
      properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // connect to Conduktor Playground


        // set producer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

       // properties.setProperty("partitioner.class", RoundRobinPartitioner.class.getName());

        // create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // create a Producer Record

        for (int j = 0 ; j < 2; j ++){
        for (int i = 0; i < 1000; i++) {
            String key = "id_" + i;
            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<>("stock-ticks", key, "hello world Sanket " + i);
            producer.send(producerRecord, (metadata, exception) -> {

                   /* System.out.println("Message sent successfully to Kafka topic: " + metadata.topic());
                    System.out.println("Offset: " + metadata.offset());
                    System.out.println("Partition: " + metadata.partition());
                    */
                log.info("Key "+key +"  | patition  "+metadata.partition());
            });

        }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // tell the producer to send all data and block until done -- synchronous
        producer.flush();

        // flush and close the producer
        producer.close();
    }
}
