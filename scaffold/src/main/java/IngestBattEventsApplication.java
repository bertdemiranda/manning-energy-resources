package com.example.ingestbattevents;

import streams.DeviceEventProcessing;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
// import io.dropwizard.setup.Environment.ExecutorService;
// import io.dropwizard.setup.Environment;
// import com.example.ingestbattevents.resources.HelloWorldResource;
import com.example.ingestbattevents.resources.DeviceEventResource;
import com.example.ingestbattevents.health.TemplateHealthCheck;

public class IngestBattEventsApplication extends Application<IngestBattEventsConfiguration> {
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

        DeviceEventProcessing deviceEvtProcessing = new DeviceEventProcessing();
        environment.lifecycle().manage(deviceEvtProcessing);

        final DeviceEventResource devresource = new DeviceEventResource(
        );
        environment.jersey().register(devresource);
    }    
    
}
