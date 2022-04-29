import com.cloudmore.kafkatask.commons.dto.ClientDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.time.Instant;

/**
 * TODO: Research more on this topic.
 *  This seems like a very complicated task using pure Java and containers. I would rather create a separate
 *  temporary containers with Kafka, MySQL, Consumer and Webservice and throw Selenium or Cucumber at them, but it is
 *  more of a QA task rather than a developer task.
 *  Alternatively I would rather talk with a person who has done this already and learn from him, instead of creating
 *  something stupid.
 */
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = RestTemplateAutoConfiguration.class)
//@Testcontainers
class EndToEndTest {

//	@Container
//	private static final KafkaContainer KAFKA =
//		new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka").withTag("7.1.1")).withEmbeddedZookeeper();
//
//	@Container
//	private static final MySQLContainer<?> mySQLContainer =
//		new MySQLContainer<>(DockerImageName.parse("mysql").withTag("8"))
//			.withPassword("root")
//			.withUsername("pa")
//			.withDatabaseName("kafka")
//			.dependsOn(KAFKA);

//	@Container
//	private static final GenericContainer<?> webServerCompose =
//		new GenericContainer<>(DockerImageName.parse("kafka-webserver").withTag("latest"));

//	@Container
//	private static final GenericContainer<?> consumerCompose =
//		new GenericContainer<>(DockerImageName.parse("kafka-consumer").withTag("latest"));

//	@Container
//	private static final DockerComposeContainer<?> WEB_CO = new DockerComposeContainer<>(WEBSERVER_COMPOSE)
//		.withExposedService("kafka-webserver", 8080);


//	private static final File WEBSERVER_COMPOSE =
//		Paths.get(new File(System.getProperty("user.dir")).getParentFile().toString(), "webserver/docker-compose.yml").toFile();
//	private static final File CONSUMER_COMPOSE =
//		Paths.get(new File(System.getProperty("user.dir")).getParentFile().toString(), "consumer/docker-compose.yml").toFile();
//	private static final File CONFLUENT_COMPOSE =
//		Paths.get(new File(System.getProperty("user.dir")).getParentFile().toString(), "docker-dev/confluent/docker-compose.yml").toFile();
//	private static final File MYSQL_COMPOSE =
//		Paths.get(new File(System.getProperty("user.dir")).getParentFile().toString(), "docker-dev/mysql/docker-compose.yml").toFile();
//
//	private static DockerComposeContainer<?> webserver;
//	private static DockerComposeContainer<?> consumer;
//	private static DockerComposeContainer<?> mysql;
//	private static DockerComposeContainer<?> confluent;
//
//	@BeforeAll
//	static void prepare() throws InterruptedException {
//		confluent = new DockerComposeContainer<>(CONFLUENT_COMPOSE);
//		confluent.start();
//
//		Thread.sleep(120000);
//
//		webserver = new DockerComposeContainer<>(WEBSERVER_COMPOSE);
//		webserver.start();
//	}
//
//	@AfterAll
//	static void cleanup() {
//		webserver.stop();
//		confluent.stop();
//	}
//
//	@Test
//	void verifyStuff() {
//		final ClientDto clientDto = new ClientDto();
//		clientDto.setName("Name");
//		clientDto.setSurname("Surname");
//		clientDto.setWage(10D);
//		clientDto.setEventTime(Instant.now());
//
//		new RestTemplate().postForLocation(URI.create("http://localhost:8080/api/v1/client/produce"), clientDto);
//		return;
//	}

	@Test
	void testSub() {

	}
}
