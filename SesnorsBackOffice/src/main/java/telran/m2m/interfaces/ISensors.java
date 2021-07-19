package telran.m2m.interfaces;

import telran.m2m.dto.AverageSensorData;
import telran.m2m.dto.MinMaxAvg;

import java.time.LocalDateTime;
import java.util.List;

public interface ISensors {
    List<AverageSensorData> getSensorData(int sensorId, LocalDateTime from, LocalDateTime to);
    List<LocalDateTime> getDatesSensorGreaterValue(int sensorId, int value);
    List<LocalDateTime> getDatesSensorLessValue(int sensorId, int value);
    List<MinMaxAvg> getMinMaxAvg();
    MinMaxAvg getMinMaxAvgSensor(int sensorId, LocalDateTime from, LocalDateTime to);
}
