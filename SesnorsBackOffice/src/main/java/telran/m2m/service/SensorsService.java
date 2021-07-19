package telran.m2m.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import telran.m2m.dto.AverageSensorData;
import telran.m2m.dto.MinMaxAvg;
import telran.m2m.interfaces.ISensors;
import telran.m2m.repository.SensorsRepository;
import telran.util.InvalidInputException;


@Service
public class SensorsService implements ISensors {
    @Autowired
    SensorsRepository sensorsRepository;


    public SensorsService() {
    }

    public List<AverageSensorData> getSensorData(int sensorId, LocalDateTime from, LocalDateTime to) {
        if(from.isAfter(to)){
            return null;
        }
        return this.sensorsRepository.getSensorDataDateTimeRange(sensorId, from, to);
    }

    public List<LocalDateTime> getDatesSensorGreaterValue(int sensorId, int value) {

        return this.sensorsRepository.getDatesSensorGreaterValueThen( sensorId,  value).stream().map(AverageSensorData::getDateTime)
                .collect(Collectors.toList());
    }

    public List<LocalDateTime> getDatesSensorLessValue(int sensorId, int value) {

        return this.sensorsRepository.getDatesSensorLessValueThen(sensorId,value).stream().map(AverageSensorData::getDateTime)
                .collect(Collectors.toList());
    }

    public List<MinMaxAvg> getMinMaxAvg() {
        return  this.sensorsRepository.getMinMaxAvg() ;
    }


    public MinMaxAvg getMinMaxAvgSensor(int sensorId, LocalDateTime from, LocalDateTime to) {
        return this.sensorsRepository.getMinMaxAvgSensor(sensorId, from, to);
    }
}
