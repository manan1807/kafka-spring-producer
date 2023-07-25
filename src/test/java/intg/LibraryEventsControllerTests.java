package intg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import com.example.libraryeventproducer.LibraryEventProducerApplication;
import com.example.libraryeventproducer.domain.Book;
import com.example.libraryeventproducer.domain.LibraryEvent;

@SpringBootTest(classes = LibraryEventProducerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = "library-events")
@TestPropertySource(properties = { "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
		"spring.kafka.admin.properties.bootstrap-servers=${spring.embedded.kafka.brokers}", })
public class LibraryEventsControllerTests {

	@Autowired
	TestRestTemplate restTemplate;

	// testing kafka consumer

	@Autowired
	EmbeddedKafkaBroker broker;

	private Consumer<Integer, String> consumer;

	@BeforeEach
	void setUp() {
		var consumerConfig = new HashMap<>(KafkaTestUtils.consumerProps("group1", "true", broker));
		consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		consumer = new DefaultKafkaConsumerFactory<>(consumerConfig, new IntegerDeserializer(),
				new StringDeserializer()).createConsumer();
		broker.consumeFromAllEmbeddedTopics(consumer);
	}
	
	@AfterEach
	void tearDown() {
		consumer.close();
	}

	@Test
	public void postLibraryEvent() {

		var httpEntity = new HttpEntity<>(new LibraryEvent(null, null, new Book(101, "AC", "AD")));

		var responseEntity = restTemplate.exchange("/v1/libraryEvents", HttpMethod.POST, httpEntity,
				LibraryEvent.class);
		System.out.println("*****Message is sent");
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()); 
		
		ConsumerRecords<Integer, String> consumerRecords =  KafkaTestUtils.getRecords(consumer);
		System.out.println("*****Message is consumed");
		assert consumerRecords.count() == 1;

	}

}
