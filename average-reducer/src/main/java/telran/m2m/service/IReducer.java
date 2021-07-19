package telran.m2m.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface IReducer {
	final String CONFIG = "config";
	final String AVGREDUCER = "avgreducer";
	final String SENSORS_DATA_BASE = "sensorsDataBase";
	@Input
SubscribableChannel config();
	@Input
SubscribableChannel avgreducer();
	@Output
MessageChannel sensorsDataBase();	
}
