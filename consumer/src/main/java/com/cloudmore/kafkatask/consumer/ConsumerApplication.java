package com.cloudmore.kafkatask.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableKafka
@EnableJpaRepositories("com.cloudmore.kafkatask.consumer.repository")
@EnableTransactionManagement
@SpringBootApplication
public class ConsumerApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
