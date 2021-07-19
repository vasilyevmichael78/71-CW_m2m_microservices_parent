package telran.m2m.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import telran.m2m.dto.AverageSensorData;
import telran.m2m.dto.MinMaxAvg;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorsRepository extends MongoRepository<AverageSensorData,Integer>, SensorsStatisticQueries {
    @Query("{'sensorId':{$eq: ?0},'dateTime': {$gte: ?1, $lte: ?2}}")
        List<AverageSensorData> getSensorDataDateTimeRange(int sensorId, LocalDateTime from, LocalDateTime to);
    @Query("{'sensorId':{$eq: ?0}, 'value':{$gte: ?1}}")
    List<AverageSensorData> getDatesSensorGreaterValueThen(int sensorId, int value);
    @Query("{'sensorId':{$eq: ?0}, 'value':{$lte: ?1}}")
    List<AverageSensorData> getDatesSensorLessValueThen(int sensorId, int value);

}
