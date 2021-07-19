package telran.m2m.dto;

import java.time.LocalDateTime;

public class AverageSensorData {

   public int sensorId;
 public    LocalDateTime dateTime;
 public    double value;
public AverageSensorData(){}
public AverageSensorData(int sensorId, LocalDateTime dateTime, double value){
    this.sensorId = sensorId;
    this.dateTime = dateTime;
    this.value=value;
}
    public int getSensorId() {
        return sensorId;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "AverageSensorData{" +
                "sensorId=" + sensorId +
                ", dateTime=" + dateTime +
                ", value=" + value +
                '}';
    }
}
