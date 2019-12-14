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

    public void showAverageHeatSetting(){
        Scanner input = new Scanner(System.in);
        int apartmentNumber;
        String dateInput;
        double averageHeatSetting = 0;
        double daily_base_amount = 0;
        double daily_saving_or_penalty = 0;
        int heatValues = 0;
        ArrayList<HeatRegulation> heatRegulationsInMethod = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObject = new Date();
        String formattedDate = "";

        System.out.println("Type number of apartment to show: ");
        apartmentNumber = input.nextInt();
        input.nextLine();

        System.out.println("Type the date you want to show (yyyy-MM-dd): ");
        dateInput = input.nextLine();

        for (HeatRegulation heatRegulation :
             Data.getInstance().heatValues) {
            if (apartmentNumber == heatRegulation.aptNumber && heatRegulation.getTimeStamp().contains(dateInput)){
                heatRegulationsInMethod.add(heatRegulation);
            }
        }

        for(User u: Data.getInstance().userArrayList){
            if (u instanceof ApartmentOwner) {
                if (apartmentNumber == ((ApartmentOwner) u).getApartmentNumber()) {
                    daily_base_amount = ((ApartmentOwner) u).getRentCost() / 300;
                }
            }
        }

        //Tillfällig metod
        for (HeatRegulation heatRegulation:
                heatRegulationsInMethod) {
            averageHeatSetting += heatRegulation.percentageValue;
            heatValues ++;
        }

        averageHeatSetting = averageHeatSetting / 100;


        daily_saving_or_penalty = (averageHeatSetting / heatValues) * 20 - daily_base_amount;

        System.out.println("Average heat setting for this apartment is: " + ((averageHeatSetting / heatValues) * 100) + " %");
        System.out.println("Daily saving or penalty for this day : " + daily_saving_or_penalty + " SEK");


        //TODO[SS, FK]: Beräkna avg heat value och printa, just nu hittar den rätt apartment till rätt datum, men
        // den beräknar inte avg heat value
    }

    public double getPercentageValue(){
        return percentageValue;
    }
}
