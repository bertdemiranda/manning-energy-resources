package com.example.ingestbattevents.dbi;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

// ============================
// DeviceEvent Db access object
// ============================

/*
CREATE TABLE device_events (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY
 ,charging INT
 ,charging_source VARCHAR(50)
 ,current_capacity INT
);
*/

public interface DeviceEventDao {
    // @SqlUpdate("INSERT INTO device_events (charging, charging_source, current_capacity) VALUES (:charging, :charging_source, :current_capacity)")
    // void insertevent(
    //     @Bind("charging")         int    charging
    //    ,@Bind("charging_source")  String charging_source
    //    ,@Bind("current_capacity") int    current_capacity
    //    );
}
