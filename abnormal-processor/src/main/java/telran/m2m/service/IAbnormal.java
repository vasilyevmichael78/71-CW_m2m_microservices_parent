package telran.m2m.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface IAbnormal {
	final String CONFIG = "config";
	final String ABNORMAL_SENSORS = "abnormalSensors";
	@Input
SubscribableChannel config();
	@Input
SubscribableChannel abnormalSensors();
}
