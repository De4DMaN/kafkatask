package com.cloudmore.kafkatask.consumer;

import com.cloudmore.kafkatask.commons.avro.schema.Client;
import com.cloudmore.kafkatask.consumer.model.ClientEntity;
import com.cloudmore.kafkatask.consumer.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1,
	brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"},
	topics = "${app.kafka.topic}")
@ActiveProfiles("test")
class ConsumerApplicationIntegrationTest {

	private static final List<Client> CLIENTS = List.of(
		new Client("Client 1", "4", 5000D, Instant.now().plus(1L, ChronoUnit.DAYS)),
		new Client("Client 4", "3", 2500D, Instant.now().minus(2L, ChronoUnit.DAYS)),
		new Client("Client 3", "2", 0D, Instant.now().plus(14L, ChronoUnit.SECONDS)),
		new Client("Client 2", "1", 1888.69D, Instant.now())
	);

	@Autowired
	private KafkaTemplate<UUID, Client> kafkaTemplate;
	@SpyBean
	private ClientRepository clientRepository;

	@Value("${app.kafka.topic}")
	private String topic;

	@Test
	void verifyCorrectProcessing() {
		CLIENTS.forEach(client -> kafkaTemplate.send(topic, UUID.randomUUID(), client));

		verify(clientRepository, timeout(10000L).times(1))
			.saveAll(any());

		assertThat(clientRepository.findAll()).hasSize(4)
			.extracting(ClientEntity::getName)
			.containsExactlyInAnyOrderElementsOf(CLIENTS.stream()
													 .map(Client::getName)
													 .collect(Collectors.toList()));
	}
}
