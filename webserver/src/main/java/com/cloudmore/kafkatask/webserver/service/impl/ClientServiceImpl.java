package com.cloudmore.kafkatask.webserver.service.impl;

import com.cloudmore.kafkatask.commons.avro.schema.Client;
import com.cloudmore.kafkatask.commons.dto.ClientDto;
import com.cloudmore.kafkatask.webserver.mapper.ClientMapper;
import com.cloudmore.kafkatask.webserver.service.ClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientMapper clientMapper;
	private final KafkaTemplate<UUID, Client> template;
	private final String topic;

	public ClientServiceImpl(final ClientMapper clientMapper,
							 final KafkaTemplate<UUID, Client> template,
							 @Value("${app.kafka.topic}") final String topic) {
		this.clientMapper = clientMapper;
		this.template = template;
		this.topic = topic;
	}

	@Override
	public UUID produce(final ClientDto clientDto) throws InterruptedException, ExecutionException {
		final Client client = clientMapper.toSchema(clientDto);
		return template.send(topic, UUID.randomUUID(), client)
			.get()
			.getProducerRecord()
			.key();
	}
}
