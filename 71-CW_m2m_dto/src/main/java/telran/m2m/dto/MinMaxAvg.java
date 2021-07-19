package telran.m2m.dto;

import org.springframework.data.annotation.Id;

public class MinMaxAvg {
    @Id
    int sensorId;
    double minValue;
    double maxValue;
    double avgValue;


    @Override
    public String toString() {
        return "MinMaxAvg{" +
                "sensorId=" + sensorId +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", avgValue=" + avgValue +
                '}';
    }

    public MinMaxAvg() {
    }

    public int getSensorId() {
        return sensorId;
    }

    public double getAvgValue() {

        return avgValue;
    }

    public double getMaxValue() {

        return maxValue;
    }

    public double getMinValue() {

        return minValue;
    }
}
