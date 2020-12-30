package dbi;

import java.util.concurrent.ConcurrentHashMap;
import api.ChargingState;

public class ChargingStateStore {
    
    private ConcurrentHashMap<String, ChargingState> store = new ConcurrentHashMap<String, ChargingState>();

    public void putChargingState(CharSequence devid, ChargingState state) {
        store.put(devid.toString(), state);
    }

    public ChargingState getChargingState(String devid) {
        System.out.println("get(" + devid + ")");
        return store.get(devid);
    }

    public boolean hasState(String devid) {
        System.out.println("hasState(" + devid + ")");
                           //+ store.containsKey(devid).toString());
        System.out.println("id's in here: "
                           + store.elements().toString());

        return store.containsKey(devid);
    }
}
