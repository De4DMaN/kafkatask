package com.cloudmore.kafkatask.webserver.mapper;

import com.cloudmore.kafkatask.commons.avro.schema.Client;
import com.cloudmore.kafkatask.commons.dto.ClientDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ClientMapperImpl.class)
class ClientMapperTest {

	private static final ClientDto CLIENT_DTO = new ClientDto();

	static {
		CLIENT_DTO.setName("Name");
		CLIENT_DTO.setSurname("Surname");
		CLIENT_DTO.setWage(5000D);
		CLIENT_DTO.setEventTime(Instant.now().truncatedTo(ChronoUnit.MILLIS));
	}

	@Autowired
	private ClientMapper clientMapper;

	@Test
	void verifyCorrectMapping() {
		assertThat(clientMapper.toSchema(CLIENT_DTO))
			.extracting(Client::getName,
						Client::getSurname,
						Client::getWage,
						Client::getEventTime)
			.containsExactly(CLIENT_DTO.getName(),
							 CLIENT_DTO.getSurname(),
							 CLIENT_DTO.getWage(),
							 CLIENT_DTO.getEventTime());
	}
}
