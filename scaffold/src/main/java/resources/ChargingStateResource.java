package resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;
import java.util.Properties;
import java.util.Date;

import api.ChargingState;
import dbi.ChargingStateStore;
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

@Produces(MediaType.APPLICATION_JSON)
@Path("/charging-state")
public class ChargingStateResource {

    private ChargingStateStore chargingStateStore;

    public ChargingStateResource(ChargingStateStore store) {
        this.chargingStateStore = store;
    }

    @GET
    @Path("/{devid}")
    //@Timed
    public Response processChargingState(@PathParam("devid") String devId) {
        //System.out.println(">>>>>>>> GET " + devId);
        if (chargingStateStore.hasState(devId)) {
            return Response.ok(chargingStateStore.getChargingState(devId)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
       
}
