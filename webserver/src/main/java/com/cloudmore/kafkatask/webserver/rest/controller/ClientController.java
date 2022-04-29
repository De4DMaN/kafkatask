package com.cloudmore.kafkatask.webserver.rest.controller;

import com.cloudmore.kafkatask.commons.dto.ClientDto;
import com.cloudmore.kafkatask.webserver.dto.ClientResponseDto;
import com.cloudmore.kafkatask.webserver.rest.controller.ExceptionHandlerController.ErrorResponse;
import com.cloudmore.kafkatask.webserver.service.ClientService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@RestController
@RequestMapping(value = "api/v1/client")
public class ClientController {

	private final ClientService clientService;

	@SuppressWarnings("squid:S1130")
	@ApiResponse(responseCode = "200",
		description = "OK",
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ClientResponseDto.class)))
	@ApiResponse(responseCode = "400",
		description = "Bad Request",
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "500",
		description = "Internal Server Error",
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
	@PostMapping("/produce")
	public ClientResponseDto produce(@RequestBody @Valid final ClientDto client) throws InterruptedException, ExecutionException {
		final UUID uuid = clientService.produce(client);
		return new ClientResponseDto(uuid);
	}
}
