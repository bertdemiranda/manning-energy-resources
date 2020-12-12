package com.example.ingestbattevents.api;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceEvent {

    public DeviceEvent() {
        // Jackson deserialization
    }

    private String device_id;
    @JsonProperty
    public String getDevice_id() {
        return device_id;
    }

    private int charging;
    @JsonProperty
    public int getCharging() {
        return charging;
    }

    private String charging_source;
    @JsonProperty
    public String getCharging_source() {
        return charging_source;
    }

    private int current_capacity;
    @JsonProperty
    public int getCurrent_capacity() {
        return current_capacity;
    }


}
