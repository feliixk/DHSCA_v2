package DHSCA_v2;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class ApartmentOwner extends User {

    public int apartmentNumber;
    public int rentCost;
    public String buildingAddress;


    public ApartmentOwner(String name, String pass, int apartmentNumber, int rentCost, String buildingAddress){
        super(name, pass);
        this.apartmentNumber = apartmentNumber;
        this.rentCost = rentCost;
        this.buildingAddress = buildingAddress;
    }

    public IndoorTemp addIndoorTemp(User user){
        String tempsTimes = Data.getInstance().indoorTemp.readTimestamp();
        double tempIndoor = Data.getInstance().indoorTemp.readTempFromKeyboard();
        int aptNumber = ((ApartmentOwner) user).getApartmentNumber();
        return new IndoorTemp(aptNumber, tempIndoor, tempsTimes);

    }

    public HeatRegulation changeHeatingValue(User user){
        String timeStamp = Data.getInstance().heatRegulation.readTimestamp();
        double heatValue = Data.getInstance().heatRegulation.readHeatValueFromKeyBoard();
        int aptNumber = ((ApartmentOwner) user).getApartmentNumber();
        //calculateAverageHeatSetting(((ApartmentOwner) user).getApartmentNumber(), timeStamp);
        return new HeatRegulation(aptNumber, heatValue, timeStamp);

    }

    public void totalRent(){


    }

    public void showAverageHeatSetting(User user){
        Scanner input = new Scanner(System.in);
        int apartmentNumber;
        String dateInput;
        double averageHeatSetting = 0;
        double daily_base_amount = 0;
        double daily_saving_or_penalty = 0;
        int numberOfHeatValues = 0;
        int apartmentNr;
        ArrayList<HeatRegulation> heatRegulationsInMethod = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObject = new Date();
        String formattedDate = "";
        apartmentNr = ((ApartmentOwner) user).getApartmentNumber();

        System.out.println("DEBUG: " + apartmentNr);

        System.out.println("Type the date you want to show (yyyy-MM-dd): ");
        dateInput = input.nextLine();

        for (HeatRegulation heatRegulation :
                Data.getInstance().heatValues) {
            if (apartmentNr == heatRegulation.aptNumber && heatRegulation.getTimeStamp().contains(dateInput)){
                heatRegulationsInMethod.add(heatRegulation);
            }
        }

        daily_base_amount = ((ApartmentOwner) user).getRentCost() / 300;

        //Tillfällig metod
        for (HeatRegulation heatRegulation:
                heatRegulationsInMethod) {
            averageHeatSetting += heatRegulation.getPercentageValue();
            numberOfHeatValues++;
        }

        averageHeatSetting = averageHeatSetting / 100;
        daily_saving_or_penalty = (averageHeatSetting / numberOfHeatValues) * 20 - daily_base_amount;
        Double averageValues = (averageHeatSetting/numberOfHeatValues)*100;


        System.out.println("Average heat setting for this apartment today is: " + String.format("%.2f", averageValues) + " %");
        System.out.println("Daily saving or penalty for this day : " + String.format("%.2f", daily_saving_or_penalty) + " SEK");

    }

    public void printIndoorTempToday(){
        int timer = 1;
        double sum = 0;
        int indexes = Data.getInstance().indoorTemps.size();

        for (int i = 0; i < Data.getInstance().indoorTemps.size() ; i++) {
            sum = sum + Data.getInstance().indoorTemps.get(i).getDegrees();

        }

        if (timer <= Data.getInstance().indoorTemps.size()){
            /*
            Gör så att högsta index skrivs först
             */
            Collections.reverse(Data.getInstance().indoorTemps);

            for (int i = 0; i < timer ; i++) {
               /*
               appartment funkar inte 100 ännu
                */
                System.out.println("Indoor temperature: " + Data.getInstance().indoorTemps.get(i).getDegrees() + "°C "
                        + "\nDate: " + Data.getInstance().indoorTemps.get(i).getTimeStamp());

                System.out.println("---------------------------------");

            }
            System.out.println("Average: " + sum/indexes + "°C" );
        }else {
            System.out.println("You haven't saved any temperatures inside! " +
                    "\nPlease do it before going to this option.");
        }
    }

    public void printIndoorTemp7Days(){
        int timers = 7;
        int index = 0;
        double sum = 0;
        int indexes = Data.getInstance().indoorTemps.size();

        for (int i = 0; i < Data.getInstance().indoorTemps.size(); i++) {
            sum = sum + Data.getInstance().indoorTemps.get(i).getDegrees();
        }

        //System.out.println("Saved measurements for apartment: " + indoorTemps.get(index).getApartmentNumber(apartmentOwner));
        if (timers <= Data.getInstance().indoorTemps.size()){
            /*
            Gör så att högsta index skrivs först
             */
            Collections.reverse(Data.getInstance().indoorTemps);

            //System.out.println("Saved measurements for apartment: " + indoorTemps.get(index).getApartmentNumber(apartmentOwner));
            for (int i = 0; i < Data.getInstance().indoorTemps.size() ; i++) {
                System.out.println("Indoor temperature: " + Data.getInstance().indoorTemps.get(i).getDegrees() + "°C " +
                        "\nDate: " + Data.getInstance().indoorTemps.get(i).getTimeStamp());

                System.out.println("-------------------------");

            }
        }else{
            System.out.println("This is what you have saved to this option");
            for (int i = Data.getInstance().indoorTemps.size()-1; i >= 0; i--) {

                System.out.println("Indoor temperature: " + Data.getInstance().indoorTemps.get(i).getDegrees() + "°C " +
                        "\nDate: " + Data.getInstance().indoorTemps.get(i).getTimeStamp());


                System.out.println("-------------------------");


            }
        }
        System.out.println("Average: " + sum/indexes + "°C" );
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public String getBuildingAddress(){
        return buildingAddress;
    }

    public int getRentCost(){
        return rentCost;
    }

    public void setRentCost(int rentCost){
        this.rentCost = rentCost;
    }

    public String getName(){
        return user;
    }

    @Override
    public String toString() {
        return "ApartmentOwner{" +
                "apartmentNumber=" + apartmentNumber +
                ", buildingAddress='" + buildingAddress + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
