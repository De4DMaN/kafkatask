package com.cloudmore.kafkatask.webserver.rest.controller;

import com.cloudmore.kafkatask.commons.avro.schema.Client;
import com.cloudmore.kafkatask.commons.dto.ClientDto;
import com.cloudmore.kafkatask.webserver.mapper.ClientMapperImpl;
import com.cloudmore.kafkatask.webserver.service.ClientService;
import com.cloudmore.kafkatask.webserver.service.impl.ClientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ClientMapperImpl.class, ClientServiceImpl.class})
class ClientControllerIntegrationTest {

	private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

	private MockMvc mvc;
	private ClientDto clientDto;

	@Autowired
	private ClientService clientService;

	@MockBean
	private KafkaTemplate<UUID, Client> template;

	private static SendResult<UUID, Client> constructResult(final ClientDto clientDto) {
		final Client client = new Client(clientDto.getName(),
										 clientDto.getSurname(),
										 clientDto.getWage(),
										 clientDto.getEventTime());
		final ProducerRecord<UUID, Client> producerRecord = new ProducerRecord<>("topic", UUID.randomUUID(), client);
		return new SendResult<>(producerRecord, null);
	}

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.standaloneSetup(new ClientController(clientService))
			.setControllerAdvice(ExceptionHandlerController.class)
			.build();

		clientDto = new ClientDto();
		clientDto.setName("Name");
		clientDto.setSurname("Surname");
		clientDto.setWage(10D);
		clientDto.setEventTime(Instant.now());
	}

	@Test
	void verifySuccessfulProduction() throws Exception {
		final SendResult<UUID, Client> sendResult = constructResult(clientDto);
		final SettableListenableFuture<SendResult<UUID, Client>> listenableFuture = new SettableListenableFuture<>();
		listenableFuture.set(sendResult);

		when(template.send(any(), any(UUID.class), any(Client.class))).thenReturn(listenableFuture);

		mvc.perform(post("/api/v1/client/produce").contentType(MediaType.APPLICATION_JSON)
						.characterEncoding(StandardCharsets.UTF_8.name())
						.content(MAPPER.writeValueAsString(clientDto)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(sendResult.getProducerRecord().key().toString()));
	}

	@Test
	void verifyInvalidInput() throws Exception {
		clientDto.setName(null);

		mvc.perform(post("/api/v1/client/produce").contentType(MediaType.APPLICATION_JSON)
						.characterEncoding(StandardCharsets.UTF_8.name())
						.content(MAPPER.writeValueAsString(clientDto)))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	void verifyErrorResponse() throws Exception {
		final String message = "Failed to send";
		when(template.send(any(), any(UUID.class), any(Client.class))).thenThrow(new RuntimeException(message));

		mvc.perform(post("/api/v1/client/produce").contentType(MediaType.APPLICATION_JSON)
						.characterEncoding(StandardCharsets.UTF_8.name())
						.content(MAPPER.writeValueAsString(clientDto)))
			.andDo(print())
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.message").value(message));
	}
}
