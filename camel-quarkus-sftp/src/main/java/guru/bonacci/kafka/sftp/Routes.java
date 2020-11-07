package guru.bonacci.kafka.sftp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class Routes extends EndpointRouteBuilder {

	private final BindyCsvDataFormat bindy = new BindyCsvDataFormat(Sample.class);
	
	@Override
    public void configure() throws Exception {
    	final Properties props = loadConfig("C:\\configs\\confluent\\cloud-test.properties");
    	final String topic = "jeff-json";
    			
        from(platformHttp("/fruits").httpMethodRestrict("POST"))
        		.setBody().constant("aa,bb,cc,dd\nff,gg,hh,ii")
        		.split(bodyAs(String.class).tokenize("\n"))
        		.unmarshal(bindy)
    		    .marshal()
    		    .json(JsonLibrary.Jackson)
    		    .to(kafka(topic)
                   .brokers(props.getProperty("bootstrap.servers"))
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
