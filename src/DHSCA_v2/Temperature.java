package DHSCA_v2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Temperature extends SensorValue {
    Scanner input = new Scanner(System.in);
    private double degrees;

    public Temperature(double degrees, String timeStamp) {
        super(timeStamp);
        this.degrees = degrees;
    }
    public  double readTempFromKeyboard(){
        double degrees =0;
        int counter = 0;
        while (counter == 0) {
            try {

                System.out.println("Enter what temperature: ");
                System.out.print("> ");
                degrees = Double.parseDouble(input.nextLine());
                counter++;
            }
            catch (NumberFormatException e) {
                System.out.println("<ERROR> Enter a valid temperature, please!");
            }
        }
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