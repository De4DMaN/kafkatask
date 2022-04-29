package com.cloudmore.kafkatask.consumer.service;

import com.cloudmore.kafkatask.commons.avro.schema.Client;
import com.cloudmore.kafkatask.consumer.config.KafkaConfig;
import com.cloudmore.kafkatask.consumer.mapper.ClientMapper;
import com.cloudmore.kafkatask.consumer.model.ClientEntity;
import com.cloudmore.kafkatask.consumer.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class ClientRecordProcessor {

	private final ClientRepository clientRepository;
	private final ClientMapper clientMapper;

	@KafkaListener(topics = "${app.kafka.topic}", containerFactory = KafkaConfig.DEFAULT_LISTENER_FACTORY)
	public void processRecords(final List<ConsumerRecord<UUID, Client>> consumerRecords) {
		final List<ClientEntity> clients = consumerRecords.stream()
			.map(ConsumerRecord::value)
			.map(clientMapper::toEntity)
			.collect(Collectors.toList());
		clientRepository.saveAll(clients);
	}
}
