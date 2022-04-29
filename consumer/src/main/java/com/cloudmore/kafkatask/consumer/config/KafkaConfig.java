package com.cloudmore.kafkatask.consumer.config;

import org.apache.avro.specific.SpecificRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;

import java.util.UUID;

@Configuration
public class KafkaConfig {

	public static final String DEFAULT_LISTENER_FACTORY = "defaultListenerFactory";

	@Bean(name = DEFAULT_LISTENER_FACTORY)
	public ConcurrentKafkaListenerContainerFactory<UUID, SpecificRecord> defaultListenerFactory(final ConsumerFactory<UUID, SpecificRecord> consumerFactory) {
		final ConcurrentKafkaListenerContainerFactory<UUID, SpecificRecord> factory =
			new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		factory.setBatchListener(true);
		factory.setCommonErrorHandler(new DefaultErrorHandler());
		factory.getContainerProperties()
			.setAckMode(ContainerProperties.AckMode.BATCH);
		return factory;
	}
}
