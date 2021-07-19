package telran.m2m.service;

import java.io.IOException;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.m2m.dto.Configuration;
import telran.m2m.dto.Sensor;
import org.springframework.messaging.support.MessageBuilder;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.groupingBy;

@EnableBinding(IReducer.class)
public class AverageReducerService {


	ObjectMapper mapper = new ObjectMapper();
	long averagePeriod=0;
	long temp;
	Logger LOG = LoggerFactory.getLogger(AverageReducerService.class);
	@Autowired
	IReducer reducer;
	Map<Integer, Double> sensorsMap;
	List<Sensor> sensors=new ArrayList<Sensor>();
	
@StreamListener(IReducer.CONFIG)
void takeConfiguration(String configJson) throws JsonParseException, JsonMappingException, IOException {
	LOG.info("Configuration {}", configJson);
	Configuration config = mapper.readValue(configJson, Configuration.class);
	averagePeriod=config.averagePeriod;
	temp=config.averagePeriod;
}

int count =1;


@StreamListener(IReducer.AVGREDUCER)
void getNormalSensorValues(String sensorJson) throws Exception {


		Sensor sensor = mapper.readValue(sensorJson, Sensor.class);
		sensors.add(sensor);

	sensorsMap = sensors.stream().collect(groupingBy(Sensor::getId, averagingInt(Sensor::getValue)));
	if(count>averagePeriod-1) {

String data=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sensorsMap);
LOG.info(data);
		reducer.sensorsDataBase().send(MessageBuilder.withPayload(data).build());

		sensorsMap.clear();
		sensors.clear();
		count = 1;
	}

	count++;


}
}