package guru.bonacci.kafka.sftp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;

import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

@ApplicationScoped
@AllArgsConstructor
public class Routes extends EndpointRouteBuilder {

	private final BindyCsvDataFormat bindy = new BindyCsvDataFormat(Foo.class);

	@Override
    public void configure() throws Exception {
    	final Properties props = loadConfig("C:\\configs\\confluent\\cloud-test-sr.properties");
    	final String topic = "some-jsonschema-topic";

		Map<String, Object> addProps = new HashMap<>();
    	addProps.put("schema.registry.url", props.get("schema.registry.url"));
    	addProps.put("basic.auth.credentials.source", props.get("basic.auth.credentials.source"));
		addProps.put("basic.auth.user.info", String.format("RAW{%s}", props.get("basic.auth.user.info"))); // RAW - very important detail here!
		addProps.put("auto.register.schemas", false);
		
		from(sftp("127.0.0.1:9922/foo-home/in")
			.username("foo")
			.password("pass")
			.initialDelay(10)
			.delay(50)
		.delete(true))
		.split(bodyAs(String.class).tokenize("\n"))
		.unmarshal(bindy)
		.log("!!! ${body}")
		.to(kafka(topic)
          .brokers(props.getProperty("bootstrap.servers"))
          .serializerClass(KafkaJsonSchemaSerializer.class.getName())
          .additionalProperties(addProps)
          .saslMechanism(props.getProperty("sasl.mechanism")) 
          .securityProtocol(props.getProperty("security.protocol"))
          .sslEndpointAlgorithm(props.getProperty("ssl.endpoint.identification.algorithm"))
          .saslJaasConfig(props.getProperty("sasl.jaas.config")));
    }

	//TODO load external configs in Quarkus?
    public static Properties loadConfig(final String configFile) throws IOException {
		if (!Files.exists(Paths.get(configFile))) {
			throw new IOException(configFile + " not found.");
		}
		final Properties cfg = new Properties();
		try (InputStream inputStream = new FileInputStream(configFile)) {
			cfg.load(inputStream);
		}
		return cfg;
	}
}
