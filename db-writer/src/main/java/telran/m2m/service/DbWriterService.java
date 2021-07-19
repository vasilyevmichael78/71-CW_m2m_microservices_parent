package telran.m2m.service;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import telran.m2m.dto.AverageSensorData;
import telran.m2m.repository.SensorsRepository;


import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@EnableBinding(Sink.class)
public class DbWriterService {
    static Logger LOG = LoggerFactory.getLogger(DbWriterService.class);
    ObjectMapper mapper = new ObjectMapper();
   Map<Integer, Double> sensorsMap;
    @Autowired
    SensorsRepository sensorsRepository;

    @StreamListener(Sink.INPUT)
    void takeSensorsMapAndSaveToDB(String sensorsMapJson) throws IOException {
         LOG.info(sensorsMapJson);
        TypeReference<Map<Integer, Double>> typeRef
                = new TypeReference<Map<Integer, Double>>() {};
         sensorsMap =  mapper.readValue(sensorsMapJson, typeRef);
        LOG.info("getting map from reducer: {}", sensorsMap);
        List<AverageSensorData> sensors = getListToDb(sensorsMap);
        sensorsRepository.saveAll(sensors);
        sensorsMap.clear();


    }

    private  static List<AverageSensorData> getListToDb(Map<Integer, Double> sensorsMap) {
        List<AverageSensorData> list = new ArrayList<>();
        sensorsMap.forEach((k,v)->{{
            AverageSensorData obj = new AverageSensorData(k,LocalDateTime.now(),v);
            list.add(obj);
            System.out.println(list.toString());
        }
        });
return list;

        /*return sensorsMap.entrySet()
                .stream().collect(Collectors.toCollection(ArrayList::new))
                .stream().map(DbWriterService::getAverageSensorData )
                .collect(Collectors.toList());*/

    }
    private static AverageSensorData getAverageSensorData(Map.Entry<Integer,Double> entry){
        return new AverageSensorData(Integer.getInteger(entry.getKey().toString()), LocalDateTime.now(),Double.parseDouble( entry.getValue().toString())) ;
    }
}
