package DHSCA_v2;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class HeatRegulation extends SensorValue {
    private double percentageValue;
    private double Totals;
    private int amountsOfValues;
    public int aptNumber;
    private boolean validInput = false;


    public HeatRegulation(int aptNumber, double percentageValue, String timeStamp){
        super(timeStamp);
        this.Totals += (percentageValue / 100);
        this.aptNumber = aptNumber;
        amountsOfValues++;
    }

    public double readHeatValueFromKeyBoard(){
        final String regex = "[2][0][\\d]{2}[-]([0][\\d]|([1][0-2]))[-]([0][1-9]|[1-2][\\d]|[3][0-1])";
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
        return Totals;
    }

    public void setPercentageValue(Double value) {
        this.Totals += (value/100);


        amountsOfValues++;

        System.out.println("Totals: " + Totals);
        System.out.println("Values: " + amountsOfValues);
    }

    public int getAmountOfValues() { return amountsOfValues;}
}
