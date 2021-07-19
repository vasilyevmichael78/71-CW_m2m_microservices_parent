package telran.m2m.dto;

public class Configuration {
public int minValue;
public int maxValue;
public long averagePeriod;
public String eMailAddress;
public Configuration() {
	// TODO Auto-generated constructor stub
}
public Configuration(int minValue, int maxValue, long averagePeriod,String eMailAddress
		) {
	super();
	this.minValue = minValue;
	this.maxValue = maxValue;
	this.averagePeriod = averagePeriod;
	this.eMailAddress = eMailAddress;
}



}
