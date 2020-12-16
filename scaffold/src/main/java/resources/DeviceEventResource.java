//package com.example.ingestbattevents;
package com.example.ingestbattevents.resources;

import com.codahale.metrics.annotation.Timed;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;
import java.util.Properties;
import java.util.Date;

import com.example.ingestbattevents.api.DeviceEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

//import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.common.errors.SerializationException;

//import com.example.ingestbattevents.DeviceEvent;
import com.example.ingestbattevents.avro.DeviceEventAvro;

@Consumes(MediaType.APPLICATION_JSON)
@Path("/device-events")
public class DeviceEventResource {

    private static final String TOPIC = "device-events";
    private Properties props;

    public DeviceEventResource() {
        props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        //props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8090");        
        props.put("schema.registry.url", "http://localhost:8090");        
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    }

    @POST
    @Path("/{devid}")
    //@Timed
    public void processDeviceEvent(@PathParam("devid") String devId,
                                  /*@NotNull @Valid*/ ArrayList<DeviceEvent> deviceevents) {
        System.out.println("Charging source = " + deviceevents.get(0).getCharging_source()
        + ", charging = " + deviceevents.get(0). getCharging()
        + ", SoC-Regulator = " + deviceevents.get(0). getSoc_regulator()
        );

        for (DeviceEvent deviceevent : deviceevents) {
            sendDeviceEventToKafka(deviceevent);
        }
    }

    private void sendDeviceEventToKafka(DeviceEvent deviceevent) {
        KafkaProducer<String, DeviceEventAvro> producer = new KafkaProducer<String, DeviceEventAvro>(props);

        DeviceEventAvro.Builder avrobuilder = DeviceEventAvro.newBuilder();
        DeviceEventAvro eventavro = 
            avrobuilder.setDeviceId       (deviceevent.getDevice_id())
                       .setCharging       (deviceevent.getCharging())
                       .setChargingSource (deviceevent.getCharging_source())
                       .setCurrentCapacity(deviceevent.getCurrent_capacity())
	                   .setModuleLTemp    (deviceevent.getModuleL_temp())
      	               .setModuleRTemp    (deviceevent.getModuleR_temp())
	                   .setProcessor1Temp (deviceevent.getProcessor1_temp())
	                   .setProcessor2Temp (deviceevent.getProcessor2_temp())
	                   .setProcessor3Temp (deviceevent.getProcessor3_temp())
    	               .setProcessor4Temp (deviceevent.getProcessor4_temp())
    	               .setInverterState  (deviceevent.getInverter_state())
    	               .setSocRegulator   (deviceevent.getSoc_regulator())
	                   .setReceivedWhen   ((new Date()).getTime())
                       .build();

        final ProducerRecord<String, DeviceEventAvro> record = new ProducerRecord<String, DeviceEventAvro>(TOPIC, deviceevent.getDevice_id(), eventavro);
        producer.send(record);
        producer.close();
    }
        
}
