package DHSCA_v2;

public class Temperature extends SensorValue {
    private float degrees;

    public Temperature(float degrees, String timeStamp) {
        super(timeStamp);
        this.degrees = degrees;
    }
}