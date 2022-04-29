package com.cloudmore.kafkatask.webserver.config;

import com.cloudmore.kafkatask.webserver.rest.controller.ExceptionHandlerController.ErrorResponse;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableWebMvc
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Bean
	public Docket swaggerApi(final TypeResolver typeResolver) {
		return new Docket(DocumentationType.OAS_30)
			.additionalModels(typeResolver.resolve(ErrorResponse.class))
			.useDefaultResponseMessages(false)
			.select()
			.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
			.paths(PathSelectors.any())
			.build();
	}
}
