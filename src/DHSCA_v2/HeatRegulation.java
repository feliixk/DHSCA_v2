package DHSCA_v2;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class HeatRegulation extends SensorValue {
    private double percentageValue;
    public int aptNumber;
    private boolean validInput = false;


    public HeatRegulation(int aptNumber, double percentageValue, String timeStamp){
        super(timeStamp);
        this.percentageValue = percentageValue;
        this.aptNumber = aptNumber;
    }

    public double readHeatValueFromKeyBoard(){
        double heatValue = 0;
        boolean cont = true;
        Scanner input = new Scanner(System.in);
        //TODO: Fixa så man inte kan skriva över 100%

        System.out.println("Input desired heat value: ");
        System.out.print("> ");
        heatValue = Double.parseDouble(input.nextLine());


        return heatValue;
    }

    public double getPercentageValue(){
        return percentageValue;
    }
}
