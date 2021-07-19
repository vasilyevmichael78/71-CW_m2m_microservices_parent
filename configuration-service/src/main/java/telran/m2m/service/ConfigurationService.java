package telran.m2m.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.m2m.dto.Configuration;

@EnableBinding(Source.class)
public class ConfigurationService {
	@Autowired
Source source;
	@Value("${config.min_value:40}")
	int minValue;
	@Value("${config.max_value:220}")
	int maxValue;
	@Value("${config.avg_period:60}")
	int avgPeriod;
	@Value("${config.e_mail}")
	String eMail;
	ObjectMapper mapper = new ObjectMapper();
	
	public void sendInitialConfiguration() throws JsonProcessingException {
		String configStr = mapper.writeValueAsString(new Configuration(minValue,
				maxValue, avgPeriod, eMail));
		source.output().send(MessageBuilder.withPayload(configStr).build());
	}
}
