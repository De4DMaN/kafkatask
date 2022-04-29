package com.cloudmore.kafkatask.webserver.service;

import com.cloudmore.kafkatask.commons.dto.ClientDto;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface ClientService {

	/**
	 * Method to parse the incoming Client DTO and submit it to Kafka
	 *
	 * @param client DTO to submit to the Kafka stream
	 *
	 * @return UUID of the corresponding message send to Kafka stream
	 *
	 * @throws InterruptedException can be thrown by underlying Kafka message producer
	 * @throws ExecutionException   can be thrown by underlying Kafka message producer
	 */
	UUID produce(ClientDto client) throws InterruptedException, ExecutionException;
}
