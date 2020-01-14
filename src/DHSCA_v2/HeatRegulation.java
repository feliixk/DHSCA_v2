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

        while (!validInput) {
            try {
                System.out.println("Input desired heat value (0-100%): ");
                System.out.print("> ");
                heatValue = Double.parseDouble(input.nextLine());
                if (heatValue < 0 || heatValue > 100){
                    System.out.println("<ERROR> Input was not in 0-100% range");
                }else{
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("<ERROR> Only numeric values allowed");
            }
        }
        return heatValue;
    }

    public double getPercentageValue(){
        return Totals;
    }

    public void setPercentageValue(Double value) {
        this.Totals += (value/100);
        amountsOfValues++;
    }

    public int getAmountOfValues() { return amountsOfValues;}
}
