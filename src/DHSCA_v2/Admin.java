package DHSCA_v2;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Admin extends User {

    public Admin(String user, String pass) {
        super(user, pass);
    }

    public User addApartmentOwner() {
        Scanner input = new Scanner(System.in);
        String user, pass, buildingAddress;
        int aptNumber = 0;
        int rentCost = 0;
        boolean validInput = false;
        System.out.print("Please set the name of the owner: ");
        user = input.nextLine();

        if (user.contains("admin")) {
            System.out.println("Owner cannot be admin");
            user = input.nextLine();
        }
        System.out.print("Please set the password of the owner: ");
        pass = input.nextLine();

        //Tillfällig lösning.
        while (!validInput) {
            try {
                System.out.print("Please set the Apartment number: ");
                aptNumber = Integer.parseInt(input.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("<ERROR> Only numeric values allowed");
            }
        }
        validInput = false;
        while (!validInput) {
            try {
                System.out.print("Please set the rent cost: ");
                rentCost = Integer.parseInt(input.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("<ERROR> Only numeric values allowed");
            }
        }

        System.out.print("Please set the building address: ");
        buildingAddress = input.nextLine();


        System.out.println("Apartment owner added");
        return new ApartmentOwner(user, pass, aptNumber, rentCost, buildingAddress);

    }

    public OutdoorTemp addOutdoorTemp() {
        Scanner input = new Scanner(System.in);
        String timestamp = Data.getInstance().outdoorTemp.readTimestamp();
        double tempstamp = Data.getInstance().outdoorTemp.readTempFromKeyboard();
        System.out.println("Insert building adress: ");
        input.next();
        String buildingadress = input.nextLine();
        OutdoorTemp test = new OutdoorTemp(buildingadress, tempstamp, timestamp);
        return test;
    }


    public void showAverageHeatSetting() {
        Scanner input = new Scanner(System.in);
        int apartmentNumber;
        String dateInput;
        double averageHeatSetting = 0;
        double daily_base_amount = 0;
        double daily_saving_or_penalty = 0;
        int numberOfHeatValues = 0;
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
            if (apartmentNumber == heatRegulation.aptNumber && heatRegulation.getTimeStamp().contains(dateInput)) {
                heatRegulationsInMethod.add(heatRegulation);
            }
        }

        for (User u : Data.getInstance().userArrayList) {
            if (u instanceof ApartmentOwner) {
                if (apartmentNumber == ((ApartmentOwner) u).getApartmentNumber()) {
                    daily_base_amount = ((ApartmentOwner) u).getRentCost() / 300;
                }
            }
        }

        //Tillfällig metod
        for (HeatRegulation heatRegulation :
                heatRegulationsInMethod) {
            averageHeatSetting += heatRegulation.getPercentageValue();
            numberOfHeatValues++;
        }

        averageHeatSetting = averageHeatSetting / 100;
        daily_saving_or_penalty = (averageHeatSetting / numberOfHeatValues) * 20 - daily_base_amount;

        Double averageValues = (averageHeatSetting / numberOfHeatValues) * 100;

        System.out.println("TEST " + daily_base_amount);

        System.out.println("Average heat setting for this apartment today is: " + String.format("%.2f", averageValues) + " %");
        System.out.println("Daily saving or penalty for this day : " + String.format("%.2f", daily_saving_or_penalty) + " SEK");
    }

    public void printOutdoorTemp_Currentday() {
        Date date = new Date();
        int indexes = 0;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        System.out.println(formatter.format(date));

        ArrayList<Double> avreage = new ArrayList<>();
        double sum = 0;
        for (var i = 0; i < Data.getInstance().outdoorTemps.size(); i++) {
            avreage.add(Data.getInstance().outdoorTemps.get(i).getDegrees());
        }


        if (Data.getInstance().outdoorTemps.size() >= 1) {
            Collections.sort(Data.getInstance().outdoorTemps);
            String currentDay = String.valueOf(calendar.getTime());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateObject = new Date();

            for (int i = 0; i < Data.getInstance().outdoorTemps.size(); i++) {
                if (Data.getInstance().outdoorTemps.get(i).getTimeStamp().contains(dateFormat.format(dateObject))) {
                    System.out.println("Outdoor temperature: " + Data.getInstance().outdoorTemps.get(i).getDegrees() + "°C" + "\nDate: " + Data.getInstance().outdoorTemps.get(i).getTimeStamp());
                    System.out.println("----------------------------------");
                    sum += Data.getInstance().outdoorTemps.get(i).getDegrees();
                    indexes++;
                }
            }

            System.out.println("Average temperature:" + sum / indexes + "°C");
        } else {
            System.out.println("You have not saved anything!!");
        }
    }


    public void printOutdoorTemp_7lastDays() {
        Collections.sort(Data.getInstance().outdoorTemps);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        DecimalFormat myFormat= new DecimalFormat("#.##");
        double sum;
        int times;
        int timesTotal = 0;
        double sumTotal = 0;

        for (int i = 7; 0 < i; i--) {
            sum = 0;
            times = 0;
            Date currentDate = cal.getTime();
            for (int u = 0; u < Data.getInstance().outdoorTemps.size(); u++) {
                if (Data.getInstance().outdoorTemps.get(u).getTimeStamp().contains(dateFormat.format(cal.getTime()))) {
                    currentDate = cal.getTime();
                    sum += Data.getInstance().outdoorTemps.get(u).getDegrees();
                    sumTotal += Data.getInstance().outdoorTemps.get(u).getDegrees();
                    times++;
                    timesTotal++;
                    System.out.println("");// så att man får bättre utskrift
                    System.out.println("Outdoor temperature: " + Data.getInstance().outdoorTemps.get(u).getDegrees() + "°C" + "\nDate: " + Data.getInstance().outdoorTemps.get(u).getTimeStamp());
                    System.out.println("----------------------------------");

                }
            }
            if (sum != 0) {
                sum/=times;
                System.out.println("Average: " + myFormat.format(sum) + "°C" + "  \nDate: " + dateFormat.format(currentDate));
                System.out.println("----------------------------------");
            }
            cal.add(Calendar.DATE, -1);
        }
        sumTotal /= timesTotal;
        System.out.printf("Average: %.2f°C of total %n", sumTotal);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "user='" + user + '\'' +
                '}';
    }
}
