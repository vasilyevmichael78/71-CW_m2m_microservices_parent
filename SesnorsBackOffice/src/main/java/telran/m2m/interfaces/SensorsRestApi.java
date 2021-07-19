package telran.m2m.interfaces;

import telran.m2m.dto.SensorsResponseCode;

import java.time.LocalDateTime;

public interface SensorsRestApi {
    SensorsResponseCode getSensorData(int sensorId, LocalDateTime from, LocalDateTime to);
}
