package telran.m2m.repository;



import telran.m2m.dto.MinMaxAvg;


import java.time.LocalDateTime;
import java.util.List;


public interface SensorsStatisticQueries  {
MinMaxAvg getMinMaxAvgSensor(int sensorId, LocalDateTime from, LocalDateTime to);
    List<MinMaxAvg >getMinMaxAvg();

}
