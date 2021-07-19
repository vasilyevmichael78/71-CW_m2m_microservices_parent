package telran.m2m.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import telran.m2m.dto.AverageSensorData;
import telran.m2m.dto.MinMaxAvg;
import telran.m2m.service.SensorsService;
import telran.util.InvalidInputException;
import telran.util.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class SensorsController {
    static final Logger LOG = LoggerFactory.getLogger(SensorsController.class);
    @Autowired
    SensorsService sensorsService;
    @GetMapping("sensors/range")
    List<AverageSensorData> getSensorData(@RequestParam(name = "sensorId", defaultValue = "1") int sensorId,
                                          @RequestParam(name = "from", defaultValue = "2017-01-13T17:09:42.411")String from,
                                          @RequestParam(name = "to", defaultValue = "2023-01-13T17:09:42.411")String to) {



            List<AverageSensorData> res = sensorsService.getSensorData(sensorId, LocalDateTime.parse(from), LocalDateTime.parse(to));
            try {
                LOG.debug("list of sensors:{}", res);
                return res;
            } catch (Exception e) {
                throw new InvalidInputException(e.getMessage());
            }


    }
    @GetMapping("sensors/greater")
    List<LocalDateTime> getSensorGreaterValuesThan(@RequestParam(name = "sensorId", defaultValue = "1") int sensorId,
                                          @RequestParam(name = "value", defaultValue = "1000")int value)
                                          {
        List<LocalDateTime> res = sensorsService.getDatesSensorGreaterValue(sensorId,value);
        LOG.debug("list of sensor's dates:{}", res);
        return res;
    }
    @GetMapping("sensors/less")
    List<LocalDateTime> getSensorLessValuesThan(@RequestParam(name = "sensorId", defaultValue = "1") int sensorId,
                                      @RequestParam(name = "value", defaultValue = "0")int value)
    {
        List<LocalDateTime> res = sensorsService.getDatesSensorLessValue(sensorId,value);
        LOG.debug("list of sensor's dates:{}", res);
        return res;
    }
    @GetMapping("sensors/statistics")
    List<MinMaxAvg> getMinMaxAvg()
    {
       List<MinMaxAvg> res = sensorsService.getMinMaxAvg();
        LOG.debug("list of stats:{}", res);
        return res;
    }
    @GetMapping("sensors/statistics/sensor")
     MinMaxAvg getMinMaxAvgSensorData(@RequestParam(name = "sensorId", defaultValue = "1") int sensorId,
                                          @RequestParam(name = "from", defaultValue = "2017-01-13T17:09:42.411")String from,
                                          @RequestParam(name = "to", defaultValue = "2023-01-13T17:09:42.411")String to){
        MinMaxAvg res = sensorsService.getMinMaxAvgSensor(sensorId,LocalDateTime.parse(from),LocalDateTime.parse(to));
        LOG.debug("list of sensors:{}", res);
        return res;
    }
}
