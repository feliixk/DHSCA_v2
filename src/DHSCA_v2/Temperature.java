package DHSCA_v2;

import java.util.Scanner;

public class Temperature extends SensorValue {
    Scanner input = new Scanner(System.in);
    private double degrees;

    public Temperature(double degrees, String timeStamp) {
        super(timeStamp);
        this.degrees = degrees;
    }
    public  double readTempFromKeyboard(){
        System.out.println("Enter what temperature: ");
        System.out.print("> ");
        double degrees = input.nextDouble();
        input.nextLine();
        return degrees;
    }

    public double getDegrees() {
        return degrees;
    }

    @Override
    public String toString() {
        return super.getTimeStamp()+ " Degrees='" + degrees ;
    }


}