package api;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChargingState {

    public ChargingState (int charging, CharSequence charging_source, int current_capacity) {
        this.charging = charging;
        this.charging_source = charging_source.toString();
        this.current_capacity = current_capacity;
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
