package telran.m2m.dto;

public class SensorsResponse<T> {
    public T result;
    public SensorsResponseCode code;

    public SensorsResponse(){};
    public SensorsResponse(T result, SensorsResponseCode code){
        this.result = result;
        this.code=code;
    }

}
