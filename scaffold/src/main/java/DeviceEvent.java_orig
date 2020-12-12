package com.example.ingestbattevents;
//package com.example.ingestbattevents.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceEvent {

    private String device_id;
    private int charging;
    private String charging_source;
    private int current_capacity;

    public DeviceEvent() {
        // Jackson deserialization
    }

    // public DeviceEvent(String device_id, int charging, String charging_source, int current_capacity) {
    //     this.device_id        = device_id;
    //     this.charging         = charging;
    //     this.charging_source  = charging_source;
    //     this.current_capacity = current_capacity;
    // }

    @JsonProperty
    public String getDevice_id() {
        return device_id;
    }
    

    @JsonProperty
    public int getCharging() {
        return charging;
    }

    // @JsonProperty
    // public void setCharging(int charging) {
    //     this.charging = charging;
    // }    

    @JsonProperty
    public String getCharging_source() {
        return charging_source;
    }

    // @JsonProperty
    // public void setCharging_source(String charging_source) {
    //     this.charging_source = charging_source;
    // }    

    @JsonProperty
    public int getCurrent_capacity() {
        return current_capacity;
    }

    // @JsonProperty
    // public void setCurrent_capacity(int current_capacity) {
    //     this.current_capacity = current_capacity;
    // }    
}
