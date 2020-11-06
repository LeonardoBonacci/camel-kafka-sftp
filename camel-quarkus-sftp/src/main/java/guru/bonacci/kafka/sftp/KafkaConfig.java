package guru.bonacci.kafka.sftp;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.apache.camel.component.kafka.KafkaConfiguration;
import org.apache.camel.component.kafka.KafkaEndpoint;

@ApplicationScoped
public class KafkaConfig {

	@Produces @ApplicationScoped
	public KafkaEndpoint kafkaEndpoint() {
		KafkaEndpoint endpoint = new KafkaEndpoint();
		KafkaConfiguration config = new KafkaConfiguration();
		config.setBrokers("localhost:9092");
		config.setTopic("jeff-json");
		endpoint.setConfiguration(config);
		return endpoint;
	}
}