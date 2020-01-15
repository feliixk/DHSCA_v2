package DHSCA_v2;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Admin extends User {

    public Admin(String user, String pass) {
        super(user, pass);
    }

    /*
    This method adds a new Apartment Owner, returnar ett User-objekt
     */
    public User addApartmentOwner() {
        Scanner input = new Scanner(System.in);
        String user, pass, buildingAddress;
        int aptNumber = 0;
        int rentCost = 0;
        boolean validInput = false;

        do {
            System.out.println("Please set the name of the owner (not admin): ");
            System.out.print("> ");
            user = input.nextLine();
        }while (user.contains("admin"));

        System.out.println("Please set the password of the owner: ");
        System.out.print("> ");
        pass = input.nextLine();

        while (!validInput) {
            try {
                System.out.println("Please set the Apartment number: ");
                System.out.print("> ");
                aptNumber = Integer.parseInt(input.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("<ERROR> Only numeric values allowed");
            }
        }
        validInput = false;
        while (!validInput) {
            try {
                System.out.println("Please set the rent cost: ");
                System.out.print("> ");
                rentCost = Integer.parseInt(input.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("<ERROR> Only numeric values allowed");
            }
        }

        System.out.println("Please set the building address: ");
        System.out.print("> ");
        buildingAddress = input.nextLine();


        System.out.println("Apartment owner added");
        return new ApartmentOwner(user, pass, aptNumber, rentCost, buildingAddress);

    }

    public OutdoorTemp addOutdoorTemp() {
        Scanner input = new Scanner(System.in);
        String timestamp = Data.getInstance().outdoorTemp.readTimestamp();
        double tempstamp = Data.getInstance().outdoorTemp.readTempFromKeyboard();
        System.out.println("Insert building adress: ");
        System.out.print("> ");
        input.next();
        String buildingadress = input.nextLine();
        OutdoorTemp test = new OutdoorTemp(buildingadress, tempstamp, timestamp);
        return test;
    }

    /* This method shows the average heat setting for a selected apartment for a certain date
    Also shows total savings/penalty for the month
     */
    public void showAverageHeatSetting() {
        Scanner input = new Scanner(System.in);
        final String regex = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
        int aptNumber = 0;
        String timeStamp;
        double averageHeatSetting = 0;
        Double totalAverageHeatSettingToday;
        double averageHeatMonthSetting = 0;
        double daily_base_amount = 10;
        double daily_saving_or_penalty = 0;
        double total_saving_penalty = 0;;
        String monthInput;
        double rentcost = 0;
        double total_rent_cost;
        int numberOfHeatValues = 0;
        int numberOfHeatMonthValues = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateInput = "";
        Date dateObject = new Date();
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Type number of apartment to show: ");
                System.out.print("> ");
                aptNumber = Integer.parseInt(input.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("<ERROR> Only numeric values allowed");
            }
        }

        do {
            System.out.println("Type the date you want to show (yyyy-MM-dd)\nIf you press enter you will get the current date.");
            System.out.print("> ");
            dateInput = input.nextLine();
            if (!dateInput.matches(regex) && !dateInput.equals("")) {
                System.out.println("<ERROR> Wrong format");
            }

        } while (!dateInput.matches(regex) && !dateInput.equals(""));
        if (!dateInput.equalsIgnoreCase("")) {
            timeStamp = dateInput;
        } else timeStamp = dateFormat.format(dateObject);

        for (User u : Data.getInstance().userArrayList) {
            if (u instanceof ApartmentOwner) {
                if (aptNumber == ((ApartmentOwner) u).getApartmentNumber()) {
                    rentcost = ((ApartmentOwner) u).getRentCost();
                }
            }
        }

        monthInput = timeStamp.substring(0, 7);
        for (int i = 0; i < Data.getInstance().heatValues.size(); i++) {
            if (aptNumber == Data.getInstance().heatValues.get(i).aptNumber && Data.getInstance().heatValues.get(i).getTimeStamp().startsWith(timeStamp)) {
                averageHeatSetting = Data.getInstance().heatValues.get(i).getPercentageValue();
                numberOfHeatValues = Data.getInstance().heatValues.get(i).getAmountOfValues();
                break;
            }else{
            }
        }

        for (int i = 0; i < Data.getInstance().heatValues.size(); i++) {
            if (aptNumber == Data.getInstance().heatValues.get(i).aptNumber && Data.getInstance().heatValues.get(i).getTimeStamp().contains(monthInput)) {
                if(Data.getInstance().heatValues.get(i).getAmountOfValues() > 1){
                    averageHeatMonthSetting += (Data.getInstance().heatValues.get(i).getPercentageValue() / Data.getInstance().heatValues.get(i).getAmountOfValues());
                    numberOfHeatMonthValues++;
                }else{
                    averageHeatMonthSetting += Data.getInstance().heatValues.get(i).getPercentageValue();
                    numberOfHeatMonthValues += Data.getInstance().heatValues.get(i).getAmountOfValues();
                }
            }else{}
        }

        for (int i = 0; i < numberOfHeatMonthValues; i++){
            total_saving_penalty += ((averageHeatMonthSetting / numberOfHeatMonthValues) * 20 - daily_base_amount);
        }

        daily_saving_or_penalty = (averageHeatSetting / numberOfHeatValues) * 20 - daily_base_amount;
        totalAverageHeatSettingToday = (averageHeatSetting / numberOfHeatValues) * 100;
        total_rent_cost = rentcost + total_saving_penalty;

        String out;
        if (totalAverageHeatSettingToday.isNaN()){
            out = "<ERROR>No heat values saved for this day";
        }else{
            out = String.format("%.2f", totalAverageHeatSettingToday) + " %";
        }

        System.out.println("--------------------------------------------------------\n" +
                "Results for Apartment " + aptNumber + " on " +  timeStamp + ":\n--------------------------------------------------------");
        System.out.println("Average heat setting\t: " + out);
        System.out.println("Daily saving or penalty\t: " + String.format("%.2f", daily_saving_or_penalty) + " SEK\n");
        System.out.println("Total saving/penalty this month\t: " + String.format("%.2f", total_saving_penalty) + " SEK");
        System.out.println("Total rent cost this month\t\t: " + String.format("%.2f", total_rent_cost) + " SEK (Base rent cost is: " + rentcost + ")");
    }

    public void printOutdoorTemp_Currentday() {
        int indexes = 0;
        DecimalFormat myFormat= new DecimalFormat("#.##");
        ArrayList<Double> avreage = new ArrayList<>();

        double sum = 0;
        for (var i = 0; i < Data.getInstance().outdoorTemps.size(); i++) {
            avreage.add(Data.getInstance().outdoorTemps.get(i).getDegrees());
        }

        if (Data.getInstance().outdoorTemps.size() >= 1) {
            Collections.sort(Data.getInstance().outdoorTemps);
            

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
            sum/=indexes;
            System.out.println("Average temperature:" + myFormat.format(sum) + "°C");
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
                    System.out.println("");
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
