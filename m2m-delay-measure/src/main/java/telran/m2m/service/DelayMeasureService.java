package telran.m2m.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.m2m.dto.Sensor;

@EnableBinding(Sink.class)
public class DelayMeasureService {
private static final long PROCESS_TIME = 3000;
ObjectMapper mapper = new ObjectMapper();
@StreamListener(Sink.INPUT)
void takeSensorData(String sensorJson) throws Exception {
	Sensor sensor = mapper.readValue(sensorJson, Sensor.class);
	System.out.printf("seqNumber: %d; sensor id: %d, delay: %d\n",
			sensor.sequenceNumber, sensor.id, System.currentTimeMillis() - sensor.timestamp);
	Thread.sleep(PROCESS_TIME);
}
}
