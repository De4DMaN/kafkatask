package com.cloudmore.kafkatask.consumer.mapper;

import com.cloudmore.kafkatask.commons.avro.schema.Client;
import com.cloudmore.kafkatask.consumer.model.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class ClientMapper {

	@Value("${app.misc.tax-percent}")
	private double taxPercent;

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "wage", target = "wage", qualifiedByName = "wageModifier")
	public abstract ClientEntity toEntity(Client client);

	@Named("wageModifier")
	protected final Double wageModifier(final Double wage) {
		return Optional.ofNullable(wage)
			.map(value -> (value * (taxPercent / 100D)) + value)
			.orElse(null);
	}
}
