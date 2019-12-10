package DHSCA_v2;
import java.util.Scanner;

public class HeatRegulation extends SensorValue {
    private double percentageValue;
    public int aptNumber;


    public HeatRegulation(int aptNumber, double percentageValue, String timeStamp){
        super(timeStamp);
        this.percentageValue = percentageValue;
        this.aptNumber = aptNumber;
    }

    public double readHeatValueFromKeyBoard(){
        double heatValue = 0;
        boolean cont = true;
        Scanner input = new Scanner(System.in);
        System.out.println("Input desired heat value: ");
        System.out.print("> ");

        heatValue = Double.parseDouble(input.nextLine());
        return heatValue;
    }

    public double getPercentageValue(){
        return percentageValue;
    }
}
