package dbi;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;
import api.ChargingState;

public class ChargingStateStore {
    
    private ConcurrentHashMap<String, ChargingState> store = new ConcurrentHashMap<String, ChargingState>();

    public void putChargingState(CharSequence devid, ChargingState state) {
        store.put(devid.toString(), state);
    }

    public Optional<ChargingState> getChargingState(String devid) {
        ChargingState cs = store.get(devid);
        if (cs == null)
            return Optional.empty();
        return Optional.of(cs);
    }
}
