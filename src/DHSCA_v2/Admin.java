package DHSCA_v2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
public class Admin extends User {

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
        String timestamp=Data.getInstance().outdoorTemp.readTimestamp();
        double tempstamp=Data.getInstance().outdoorTemp.readTempFromKeyboard();
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

    public void printOutdoorTemp_Currentday() {
        ArrayList<Double> test= new ArrayList<>();
        double sum = 0;
        for (var i = 0; i <Data.getInstance().outdoorTemps.size(); i++) {
            test.add(Data.getInstance().outdoorTemps.get(i).getDegrees());
        }

        for (int i = 0; i < test.size(); i++) {
            sum+=test.get(i);
        }
        var times = 1;
        if (times <= Data.getInstance().outdoorTemps.size()) {
            Collections.reverse(Data.getInstance().outdoorTemps);


            for (int i = 0; i < times; i++) {
                System.out.println("Outdoor temperature: " + Data.getInstance().outdoorTemps.get(i).getDegrees() + "°C" + "\nDate: " + Data.getInstance().outdoorTemps.get(i).getTimeStamp());
                System.out.println("----------------------------------");

            }
            System.out.println("Average temperature:"+sum/test.size()+"°C");
        } else {
            System.out.println("You have not saved anything!!");
        }
    }


    public void printOutdoorTemp_7lastDays() {
        ArrayList<Double> test= new ArrayList<>();
        double sum = 0;
        for (var i = 0; i <Data.getInstance().outdoorTemps.size(); i++) {
            test.add(Data.getInstance().outdoorTemps.get(i).getDegrees());
        }

        for (int i = 0; i < test.size(); i++) {
            sum+=test.get(i);
        }
        int times = 7;
        if (times <= Data.getInstance().outdoorTemps.size()) {
            Collections.reverse(Data.getInstance().outdoorTemps);


            for (int i = 0; i < times; i++) {
                System.out.println("Outdoor temperature: " + Data.getInstance().outdoorTemps.get(i).getDegrees() + "°C" + "\nDate: " + Data.getInstance().outdoorTemps.get(i).getTimeStamp());
                System.out.println("----------------------------------");

            }
        } else {
            System.out.println("This is what you have saved!");
            for (int i = Data.getInstance().outdoorTemps.size() - 1; i >= 0; i--) {
                System.out.println("Outdoor temperature: " + Data.getInstance().outdoorTemps.get(i).getDegrees() + "°C" + "\nDate: " + Data.getInstance().outdoorTemps.get(i).getTimeStamp());
                System.out.println("----------------------------------");

            }
            System.out.println("Average temperature:"+sum/test.size()+"°C");
        }
    }

    @Override
    public String toString() {
        return "Admin{" +
                "user='" + user + '\'' +
                '}';
    }
}
