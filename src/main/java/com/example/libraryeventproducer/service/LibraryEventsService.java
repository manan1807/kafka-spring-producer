package com.example.libraryeventproducer.service;

import java.util.List;
//import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.example.libraryeventproducer.config.ProducerConfiguration;
import com.example.libraryeventproducer.domain.LibraryEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LibraryEventsService {

	@Value("${spring.kafka.topic}")
	private String topic;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

//	public CompletableFuture<SendResult<String, String>> sendLibraryEvent(List<LibraryEvent> records)
//			throws JsonProcessingException {
//
//		for(LibraryEvent event: records) {
//		switch (event.getEventType()) {
//		case NEW:
//			generateRandomLibraryEventId(event);
//		default:
//			break;
//		}
//		var key = event.getLibraryEventId();
//		var value = event;
//		var listenableFuture = kafkaTemplate.send(topic, key, mapper.writeValueAsString(value));
//
//		return listenableFuture.completable().whenComplete((sendResult, throwable) -> {
//			if (throwable != null) {
//				handleError(key, value, throwable);
//			} else {
//				handleSuccess(key, value, sendResult);
//			}
//		});
//		}
//	}

	public void sendLibraryEventViaProducerRecord(List<LibraryEvent> libraryEventRecords)
			throws JsonProcessingException {

		KafkaProducer<String, String> kafkaProducer = ProducerConfiguration.getProducerProps();

		try {
			for (LibraryEvent event : libraryEventRecords) {

				switch (event.getEventType()) {
				case NEW:
					generateRandomLibraryEventId(event);
				default:
					break;
				}

				var key = event.getLibraryEventId();
				var value = event;

				ProducerRecord<String, String> producerRecord = buildProducerRecord(key, value);

				kafkaProducer.send(producerRecord, new Callback() {
					
					@Override
					public void onCompletion(RecordMetadata metadata, Exception exception) {
						if (exception != null) {
							handleError(key, value, exception);
						} else {
							log.debug("Record processed: {} "+key);
							handleSuccess(key, value, metadata);
						}
					}
				});

//		var listenableFuture = kafkaTemplate.send(producerRecord);

//		return listenableFuture.completable().whenComplete((sendResult, throwable) -> {
//			if (throwable != null) {
//				handleError(key, value, throwable);
//			} else {
//				handleSuccess(key, value, sendResult);
//			}
//		});
			}
		} finally {
			kafkaProducer.flush();
			kafkaProducer.close();
		}
	}

	private void generateRandomLibraryEventId(LibraryEvent event) {

		if (event.getLibraryEventId() == null) {
//			event.setLibraryEventId(new Random().nextInt(Integer.SIZE - 1));
			event.setLibraryEventId(UUID.randomUUID().toString());
			log.debug("*****Random UUID created for event: {}"+event.getLibraryEventId());
		}
	}

	private ProducerRecord<String, String> buildProducerRecord(String key, LibraryEvent value)
			throws JsonProcessingException {
		return new ProducerRecord<String, String>(topic, key, mapper.writeValueAsString(value));
	}

	private void handleSuccess(String key, LibraryEvent value, RecordMetadata metadata) {
		log.debug("****sent record(key={} value={}) " + "meta(partition={}, offset={})", key, value,
				metadata.partition(), metadata.offset());

	}

	private void handleError(String key, LibraryEvent value, Throwable throwable) {

		System.out.println("###Error producing the message to the topic: " + throwable);

	}

}
