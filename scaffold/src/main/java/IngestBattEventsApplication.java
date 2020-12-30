package com.example.ingestbattevents;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;
// import io.dropwizard.setup.Environment.ExecutorService;
// import io.dropwizard.setup.Environment;
// import com.example.ingestbattevents.resources.HelloWorldResource;
import com.example.ingestbattevents.IngestBattEventsConfiguration;

import resources.ChargingStateResource;
import resources.DeviceEventResource;
import health.TemplateHealthCheck;
import api.ChargingState;
import dbi.ChargingStateStore;
import streams.DeviceEventProcessing;

public class IngestBattEventsApplication extends Application<IngestBattEventsConfiguration> {

    private ChargingStateStore chargingStateStore;

    public IngestBattEventsApplication() {
        this.chargingStateStore = new ChargingStateStore();
    }

    public static void main(String[] args) throws Exception {
        new IngestBattEventsApplication().run(args);
    }

    // @Override
    // public String getName() {
    //     return "hello-world";
    // }

    @Override
    public void initialize(Bootstrap<IngestBattEventsConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(IngestBattEventsConfiguration configuration,
                    Environment environment) {

        // final JdbiFactory jdbiFactory = new JdbiFactory();
        // final Jdbi jdbi = jdbiFactory.build(environment, configuration.getDataSourceFactory(), "database");

        final TemplateHealthCheck healthCheck =
            new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        // ExecutorService executorService = environment.lifecycle()
        //     .executorService(nameFormat)
        //     .maxThreads(maxThreads)
        //     .build();

        // ScheduledExecutorService scheduledExecutorService = environment.lifecycle()
        //     .scheduledExecutorService(nameFormat)
        //     .build();

        DeviceEventProcessing deviceEvtProcessing = new DeviceEventProcessing(/*jdbi, */ chargingStateStore);
        environment.lifecycle().manage(deviceEvtProcessing);

        final DeviceEventResource devresource = new DeviceEventResource();
        environment.jersey().register(devresource);

        final ChargingStateResource csresource = new ChargingStateResource(chargingStateStore);
        environment.jersey().register(csresource);
    }    
    
}
