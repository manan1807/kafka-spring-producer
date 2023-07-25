package com.example.libraryeventproducer.config;


import java.util.Properties;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class ProducerConfiguration {
	
	@Value("${spring.kafka.topic}")
	public String topic;
	
	@Bean
	public NewTopic libraryEvents() {
		return TopicBuilder
				.name(topic)
				.partitions(3)
				.replicas(1)
				.build();
	}
	//,localhost:9093,localhost:9094
	public static KafkaProducer<String, String> getProducerProps() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		properties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "LibraryEventProduer");
		return new KafkaProducer<>(properties);
	}
	
}
