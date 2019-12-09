package DHSCA_v2;

public class Temperature extends SensorValue {
    private double degrees;

    public Temperature(double degrees, String timeStamp) {
        super(timeStamp);
        this.degrees = degrees;
    }
}