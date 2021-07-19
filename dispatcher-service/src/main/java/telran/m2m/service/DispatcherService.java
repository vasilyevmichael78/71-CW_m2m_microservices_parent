package telran.m2m.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.m2m.dto.Configuration;
import telran.m2m.dto.Sensor;

@EnableBinding(IDispatcher.class)
public class DispatcherService {
	static Logger LOG = LoggerFactory.getLogger(DispatcherService.class);
	ObjectMapper mapper = new ObjectMapper();
	int minValue;
	int maxValue;
	@Autowired
	IDispatcher dispatcher;
	@StreamListener(IDispatcher.CONFIG)
	void takeConfiguration(String configJson) throws JsonParseException, JsonMappingException, IOException {
		LOG.info("Configuration {}", configJson);
		Configuration config = mapper.readValue(configJson, Configuration.class);
		minValue = config.minValue;
		maxValue = config.maxValue;
		System.out.println("minValue " + minValue+" maxValue "+maxValue);
	}
	@StreamListener(IDispatcher.SENSORS)
	void takeSensor(String sensorJson) throws JsonParseException, JsonMappingException, IOException {
		LOG.info("Sensor data {}", sensorJson);

		Sensor sensor = mapper.readValue(sensorJson, Sensor.class);
		System.out.println( sensor.value);
		dispatcher.reducer().send(MessageBuilder.withPayload(sensorJson).build());
		if (sensor.value < minValue || sensor.value > maxValue) {
			dispatcher.abnormal().send(MessageBuilder.withPayload(sensorJson).build());
		}
	}
}