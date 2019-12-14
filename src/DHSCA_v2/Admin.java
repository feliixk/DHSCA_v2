package DHSCA_v2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
public class Admin extends User {
private OutdoorTemp outdoorTemp = new OutdoorTemp("",0,"");
    public Admin(String user, String pass){
        super(user, pass);
    }

    public User addApartmentOwner(){
        Scanner input = new Scanner(System.in);
        String user, pass, buildingAddress;
        int aptNumber = 0;
        int rentCost = 0;
        boolean validInput = false;
        System.out.print("Please set the name of the owner: ");
        user = input.nextLine();
        System.out.print("Please set the password of the owner: ");
        pass = input.nextLine();

        //Tillfällig lösning.
        while(!validInput){
            try {
                System.out.print("Please set the Apartment number: ");
                aptNumber = Integer.parseInt(input.nextLine());
                validInput = true;
            } catch (NumberFormatException e){
                System.out.println("<ERROR> Only numeric values allowed");
            }
        }
        validInput = false;
        while(!validInput){
            try {
                System.out.print("Please set the rent cost: ");
                rentCost = Integer.parseInt(input.nextLine());
                validInput = true;
            } catch (NumberFormatException e){
                System.out.println("<ERROR> Only numeric values allowed");
            }
        }

        System.out.print("Please set the building address: ");
        buildingAddress = input.nextLine();




        System.out.println("Apartment owner added");
        return new ApartmentOwner(user, pass, aptNumber, rentCost, buildingAddress);

    }
    public OutdoorTemp addOutdoorTemp(){
        Scanner input = new Scanner(System.in);
        String timestamp=outdoorTemp.readTimestamp();
        double tempstamp=outdoorTemp.readTempFromKeyboard();
        System.out.println("Insert building adress number: ");
        input.next();
        String buildingadress = input.nextLine();
        OutdoorTemp test = new OutdoorTemp(buildingadress,tempstamp,timestamp);
        return test;
    }

    public void showAverageHeatSetting(ArrayList<HeatRegulation> heatRegulationArrayList, ArrayList<User> userList){
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
                heatRegulationArrayList) {
            if (apartmentNumber == heatRegulation.aptNumber && heatRegulation.getTimeStamp().contains(dateInput)){
                heatRegulationsInMethod.add(heatRegulation);
            }
        }

        for(User u: userList){
            if (u instanceof ApartmentOwner) {
                if (apartmentNumber == ((ApartmentOwner) u).getApartmentNumber()) {
                    daily_base_amount = ((ApartmentOwner) u).getRentCost() / 300;
                }
            }
        }

        //Tillfällig metod
        for (HeatRegulation heatRegulation:
                heatRegulationsInMethod) {
            averageHeatSetting += heatRegulation.getPercentageValue();
            heatValues ++;
        }

        averageHeatSetting = averageHeatSetting / 100;


        daily_saving_or_penalty = (averageHeatSetting / heatValues) * 20 - daily_base_amount;

        System.out.println("Average heat setting for this apartment is: " + ((averageHeatSetting / heatValues) * 100) + " %");
        System.out.println("Daily saving or penalty for this day : " + daily_saving_or_penalty + " SEK");


        //TODO[SS, FK]: Beräkna avg heat value och printa, just nu hittar den rätt apartment till rätt datum, men
        // den beräknar inte avg heat value
    }

    @Override
    public String toString() {
        return "Admin{" +
                "user='" + user + '\'' +
                '}';
    }
}
