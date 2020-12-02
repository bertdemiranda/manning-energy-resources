package streams;

import org.apache.kafka.streams.StreamsConfig;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import java.util.Properties;
import java.util.Map;
import static java.util.Collections.singletonMap;

public class StreamsConfiguration {
    public static Properties streamsConfiguration() {
        final Properties streamsConfiguration = new Properties();
        // Give the Streams application a unique name.  The name must be unique in the Kafka cluster
        // against which the application is run.
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "device-events-processing");
        // Where to find Kafka broker(s).
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        return streamsConfiguration;
    }
    
    public static Map<String, String> schemaRegistry() {
        return singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8090");
    }
    
}
