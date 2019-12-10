package DHSCA_v2;

import java.util.Scanner;

public class Temperature extends SensorValue {
    private double degrees;

    public Temperature(double degrees, String timeStamp) {
        super(timeStamp);
        this.degrees = degrees;
    }

}