package com.cloudmore.kafkatask.consumer.mapper;

import com.cloudmore.kafkatask.commons.avro.schema.Client;
import com.cloudmore.kafkatask.consumer.model.ClientEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ClientMapperImpl.class)
@SpringBootTest
@ActiveProfiles("test")
class ClientMapperTest {

	private static final Client CLIENT = new Client("Name", "Surname", 5000D, Instant.now());

	@Value("${app.misc.tax-percent}")
	private double taxPercent;

	@Autowired
	private ClientMapper clientMapper;

	@Test
	void verifyCorrectMapping() {
		assertThat(clientMapper.toEntity(CLIENT))
			.extracting(ClientEntity::getName,
						ClientEntity::getSurname,
						ClientEntity::getWage,
						ClientEntity::getEventTime)
			.containsExactly(CLIENT.getName(),
							 CLIENT.getSurname(),
							 CLIENT.getWage() + (CLIENT.getWage() * (taxPercent / 100D)),
							 CLIENT.getEventTime());
	}
}
