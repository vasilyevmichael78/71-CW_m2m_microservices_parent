package telran.m2m.dto;

public class Sensor {
public int id;
public int sequenceNumber;
public int value;
public long timestamp;
public Sensor(){};
public Sensor(int id, int sequenceNumber, int value, long timestamp) {
	super();
	this.id = id;
	this.sequenceNumber = sequenceNumber;
	this.value = value;
	this.timestamp = timestamp;
}
public int getId() {
	return id;
}
public int getSequenceNumber() {
	return sequenceNumber;
}
public int getValue() {
	return value;
}
public long getTimestamp() {
	return timestamp;
}

}
