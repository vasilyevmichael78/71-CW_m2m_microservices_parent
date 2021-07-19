package telran.m2m.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface IDispatcher {
	final String CONFIG = "config";
	final String SENSORS = "sensors";
	final String ABNORMAL = "abnormal";
	final String REDUCER = "reducer";
	@Input
SubscribableChannel config();
	@Input
SubscribableChannel sensors();
	@Output
MessageChannel abnormal();
	@Output
MessageChannel reducer();
}
