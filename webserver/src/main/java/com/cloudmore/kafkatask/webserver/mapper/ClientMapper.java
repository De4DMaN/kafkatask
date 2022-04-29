package com.cloudmore.kafkatask.webserver.mapper;

import com.cloudmore.kafkatask.commons.avro.schema.Client;
import com.cloudmore.kafkatask.commons.dto.ClientDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

	Client toSchema(ClientDto dto);
}
