package streams;
import com.example.ingestbattevents.avro.DeviceEventAvro;
//import com.example.ingestbattevents.api.DeviceEvent;
//import com.example.ingestbattevents.dbi.DeviceEventDao;
import dbi.ChargingStateStore;
import api.ChargingState;

import streams.StreamsConfiguration;
//import org.jdbi.v3.core.Jdbi;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.common.serialization.Serdes.StringSerde;

import io.dropwizard.lifecycle.Managed;

import java.util.Map;

public class DeviceEventProcessing implements Managed {
    // See https://www.dropwizard.io/en/latest/manual/core.html#managed-objects

    static final String DEVICE_EVENTS = "device-events";

    final KafkaStreams streams;
//    private Jdbi jdbi;
    private ChargingStateStore chargingStateStore;

    private Topology buildTopology() {
        final Map<String, String> serdeConfig = StreamsConfiguration.schemaRegistry();
        final StringSerde deviceIDSerde = new StringSerde();

        // create and configure the SpecificAvroSerde
        final SpecificAvroSerde<DeviceEventAvro> deviceEventSerde = new SpecificAvroSerde<>();
        deviceEventSerde.configure(serdeConfig, false);
    
        // get a stream of device events
        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, DeviceEventAvro> deviceEvents = builder.stream(
            DEVICE_EVENTS,
            Consumed.with(deviceIDSerde, deviceEventSerde));
        deviceEvents.foreach((key, value) -> {
            System.out.println(key + " => " + value.getCharging());
            StoreEventInChargeStore(value);
            //LATER: StoreEventInDb(value);
        }
        );
        return builder.build();
    }

    // private void StoreEventInDb(DeviceEventAvro deAvro) {
    //     final DeviceEventDao dao = jdbi.onDemand(DeviceEventDao.class);
    //     dao.insertevent(deAvro.getCharging(), deAvro.getChargingSource().toString(), deAvro.getCurrentCapacity());
    // }

    private void StoreEventInChargeStore(DeviceEventAvro deAvro) {
        chargingStateStore.putChargingState(
                              deAvro.getDeviceId(), 
                                     new ChargingState(
                                         deAvro.getCharging(),
                                         deAvro.getChargingSource().toString(),
                                         deAvro.getCurrentCapacity()));
    }
      
    public DeviceEventProcessing(/*Jdbi jdbi, */ ChargingStateStore store) {
        //this.jdbi               = jdbi;
        this.chargingStateStore = store;

        streams = new KafkaStreams(buildTopology(),
                                   StreamsConfiguration.streamsConfiguration());
    }

    @Override
    public void start() throws Exception {

        // Always (and unconditionally) clean local state prior to starting the processing topology.
        // We opt for this unconditional call here because this will make it easier for you to play around with the example
        // when resetting the application for doing a re-run (via the Application Reset Tool,
        // http://docs.confluent.io/current/streams/developer-guide.html#application-reset-tool).
        //
        // The drawback of cleaning up local state prior is that your app must rebuilt its local state from scratch, which
        // will take time and will require reading all the state-relevant data from the Kafka cluster over the network.
        // Thus in a production scenario you typically do not want to clean up always as we do here but rather only when it
        // is truly needed, i.e., only under certain conditions (e.g., the presence of a command line flag for your app).
        // See `ApplicationResetExample.java` for a production-like example.
        streams.cleanUp();

        // Now that we have finished the definition of the processing topology we can actually run
        // it via `start()`.  The Streams application as a whole can be launched just like any
        // normal Java application that has a `main()` method.
        streams.start();

        System.out.println("Device Events stream started");
    }

    @Override
    public void stop() throws Exception {
        try {
            streams.close();
            System.out.println("Device Events stream stopped");
        } catch (final Exception e) {
            // ignored
          }
    }
}
