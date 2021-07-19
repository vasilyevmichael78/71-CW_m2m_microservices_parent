package telran.m2m.service;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.m2m.dto.Configuration;
import telran.m2m.dto.Sensor;
@Component
@EnableBinding(IAbnormal.class)
public class AbnormalService  {
	String email="yes";
	static Logger LOG = LoggerFactory.getLogger(AbnormalService.class);
	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	 JavaMailSender emailSender;

	@Autowired
	IAbnormal abnormal;

	@StreamListener(IAbnormal.CONFIG)
	void takeConfiguration(String configJson) throws JsonParseException, JsonMappingException, IOException {
		LOG.info("Configuration {}", configJson);
		Configuration config = mapper.readValue(configJson, Configuration.class);
		email = config.eMailAddress;
		System.out.println(email);

	}
	@StreamListener(IAbnormal.ABNORMAL_SENSORS)
	public void sendAbnormalValues(String sensorJson) throws JsonParseException, JsonMappingException, IOException {
		Sensor sensor = mapper.readValue(sensorJson, Sensor.class);
		SimpleMailMessage message = new SimpleMailMessage();
		LOG.info("id {}; value {}",sensor.id, sensor.value) ;
		message.setTo(email);
		message.setSubject("Warning: abnormal values!");
		message.setText("sensor id: "+sensor.id+" value: "+sensor.value);

		getEmailSender().send(message);
		System.out.println(message.toString());
	}

	public JavaMailSender getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}
}
