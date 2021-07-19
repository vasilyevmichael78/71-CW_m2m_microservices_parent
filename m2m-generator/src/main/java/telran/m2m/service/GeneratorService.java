package telran.m2m.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.m2m.dto.Configuration;
import telran.m2m.dto.Sensor;

@EnableBinding(Processor.class)
public class GeneratorService {
private static final double WIDE_PARAMETR = 0.2;
private static final int MIN_ID = 1;
private static final int MAX_ID = 10;
int maxValue;
int minValue;
ObjectMapper mapper = new ObjectMapper();
static final Logger LOG = LoggerFactory.getLogger(GeneratorService.class);
int seqNumber=-1 ;
@StreamListener(Processor.INPUT)
void takeConfiguration(String configJson) throws JsonParseException, JsonMappingException, IOException {
	LOG.info("Configuration {}", configJson);
	
	Configuration config = mapper.readValue(configJson, Configuration.class);
	minValue = (int) (config.minValue + config.minValue*WIDE_PARAMETR);
	maxValue = (int) (config.maxValue + config.maxValue*WIDE_PARAMETR);
	System.out.printf("maxValue=%d, minValue=%d\n",maxValue,minValue);
}
@InboundChannelAdapter(value=Processor.OUTPUT,
poller=@Poller(fixedDelay="${fixedDelay:1000}",maxMessagesPerPoll="${nMessages:1}"))
String sendRandomSensor() throws Exception{
	
	int id = (int) (MIN_ID + Math.random() * (MAX_ID - MIN_ID + 1));
	int value = (int) (minValue + Math.random() * (maxValue - minValue + 1));
	Sensor sensor = new Sensor(id, ++seqNumber, value, System.currentTimeMillis());
	System.out.println("seqNumber "+ seqNumber +" sensor"+sensor.id+ " value "+value);
	LOG.debug("sensor {}" ,sensor.id);
	return mapper.writeValueAsString(sensor);
}

}
