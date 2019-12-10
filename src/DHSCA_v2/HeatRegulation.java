package DHSCA_v2;
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

    public void showAverageHeatSetting(ArrayList<HeatRegulation> heatRegulationArrayList){
        Scanner input = new Scanner(System.in);
        int apartmentNumber;
        String dateInput;
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
             heatRegulationArrayList) {
            if (apartmentNumber == heatRegulation.aptNumber && formattedDate.equals(dateInput)){
                heatRegulationsInMethod.add(heatRegulation);
            }
        }

        //för testning bara
        for (HeatRegulation heatRegulation:
                heatRegulationsInMethod) {
            System.out.println(heatRegulation.aptNumber + " ");
        }

        //TODO[SS, FK]: Beräkna avg heat value och printa, just nu hittar den rätt apartment till rätt datum, men
        // den beräknar inte avg heat value
    }

    public double getPercentageValue(){
        return percentageValue;
    }
}
