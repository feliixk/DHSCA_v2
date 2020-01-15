package DHSCA_v2;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

    /*
    Method for changing heatvalue for given user
     */
    public void changeHeatingValue(User user){
        final String regex = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
        String timeStamp = Data.getInstance().heatRegulation.readTimestamp();
        double heatValue = Data.getInstance().heatRegulation.readHeatValueFromKeyBoard();
        int aptNumber = ((ApartmentOwner) user).getApartmentNumber();
        boolean added = false;

        /* Checks if heat value for a given date exists
        If heat value exists for date: It updates the heatvalue
        If not a new HeatValue (HeatRegulation object) is created
         */
        for (HeatRegulation heatR : Data.getInstance().heatValues) {
                if (heatR.aptNumber == aptNumber && heatR.getTimeStamp().substring(0, 10).startsWith(timeStamp.substring(0, 10))) {
                    heatR.setPercentageValue(heatValue);
                    //Date exists, updating heat value
                    added = true;
                    break;
                } else if (heatR.aptNumber == aptNumber && !heatR.getTimeStamp().substring(0, 10).startsWith(timeStamp.substring(0, 10))) {
                    //Wrong date
                }
                else if (heatR.aptNumber != aptNumber) {
                //No user match
                } else {
                    //System.out.println("no heat value");
                }
        }

        if (!added) {
        Data.getInstance().heatValues.add(new HeatRegulation(aptNumber, heatValue, timeStamp));
        //System.out.println("ADDING TO ARRAYLIST HEATVALUES");
        }
    }

    /* Method for showing average heat setting for a day, for a given user
    And also the rent reduction/penalty this day and for the whole month
     */
    public void showAverageHeatSetting(User user) {
        final String regex = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String timeStamp;
        Scanner input = new Scanner(System.in);
        String dateInput;
        double averageHeatSetting = 0;
        double averageHeatMonthSetting = 0;
        double daily_base_amount = 10;
        double daily_saving_or_penalty = 0;
        double total_saving_penalty = 0;
        int numberOfHeatValues = 0;
        int numberOfHeatMonthValues = 0;
        Double totalAverageHeatSettingToday;
        double total_rent_cost;
        double rentcost;
        int apartmentNr;
        apartmentNr = ((ApartmentOwner) user).getApartmentNumber();
        Date dateObject = new Date();

        // Gets date input from user and checks if it's in the correct format
        do {
            System.out.println("Type the date that you want to show (yyyy-MM-dd)\n(If you press enter you will get the current date)");
            System.out.print("> ");
            timeStamp = input.nextLine();
            if (!timeStamp.matches(regex) && !timeStamp.equals("")) {
                System.out.println("<ERROR> Wrong format");
            }
        } while (!timeStamp.matches(regex) && !timeStamp.equals(""));
        if (!timeStamp.equalsIgnoreCase("")) {
            dateInput = timeStamp;
        } else dateInput = dateFormat.format(dateObject);

        // Gets month from date input, e.g: 2020-01 from dateinput: 2020-01-01
        String monthInput = dateInput.substring(0, 7);

        // Gets average heat setting value for the given input date
        for (int i = 0; i < Data.getInstance().heatValues.size(); i++) {
            if (apartmentNr == Data.getInstance().heatValues.get(i).aptNumber && Data.getInstance().heatValues.get(i).getTimeStamp().startsWith(dateInput)) {
                averageHeatSetting = Data.getInstance().heatValues.get(i).getPercentageValue();
                numberOfHeatValues = Data.getInstance().heatValues.get(i).getAmountOfValues();
                break;
            }else{
                //doesnt exist
            }
        }

        // Gets all heat values for this month
        for (int i = 0; i < Data.getInstance().heatValues.size(); i++) {
            if (apartmentNr == Data.getInstance().heatValues.get(i).aptNumber && Data.getInstance().heatValues.get(i).getTimeStamp().contains(monthInput)) {
                if(Data.getInstance().heatValues.get(i).getAmountOfValues() > 1){
                    averageHeatMonthSetting += (Data.getInstance().heatValues.get(i).getPercentageValue() / Data.getInstance().heatValues.get(i).getAmountOfValues());
                    numberOfHeatMonthValues++;
                }else{
                    averageHeatMonthSetting += Data.getInstance().heatValues.get(i).getPercentageValue();
                    numberOfHeatMonthValues += Data.getInstance().heatValues.get(i).getAmountOfValues();
                }
            }
        }

        // Calculates daily saving/penalty
        daily_saving_or_penalty = (averageHeatSetting / numberOfHeatValues) * 20 - daily_base_amount;

        // Calculates total saving/penalty this month
        for (int i = 0; i < numberOfHeatMonthValues; i++){
            total_saving_penalty += ((averageHeatMonthSetting / numberOfHeatMonthValues) * 20 - daily_base_amount);
        }

        totalAverageHeatSettingToday = (averageHeatSetting / numberOfHeatValues) * 100;

        rentcost = ((ApartmentOwner) user).getRentCost();
        total_rent_cost = rentcost + total_saving_penalty;

        // Checks if average heat setting value exists
        String out;
        if (totalAverageHeatSettingToday.isNaN()){
            out = "<ERROR>No heat values saved for this day";
        }else{
            out = String.format("%.2f", totalAverageHeatSettingToday) + " %";
        }

        // Prints out all info and formats it for the user
        System.out.println("--------------------------------------------------------\n" +
                "Results for date " +  dateInput + ":\n--------------------------------------------------------");
        System.out.println("Average heat setting\t: " + out);
        System.out.println("Daily saving or penalty\t: " + String.format("%.2f", daily_saving_or_penalty) + " SEK\n");
        System.out.println("Total saving/penalty this month\t: " + String.format("%.2f", total_saving_penalty) + " SEK");
        System.out.println("Total rent cost this month\t\t: " + String.format("%.2f", total_rent_cost) + " SEK (Base rent cost is: " + rentcost + ")");
    }

        public void printIndoorTempToday () {
            int timer = 1;
            double sum = 0;
            boolean noSaved = true;
            int indexes = 0;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateObject = new Date();

            // for (int i = 0; i < Data.getInstance().indoorTemps.size() ; i++) {
            //    sum = sum + Data.getInstance().indoorTemps.get(i).getDegrees();

            //}

            /*
            Gör så att högsta index skrivs först
             */
            Collections.sort(Data.getInstance().indoorTemps);
            System.out.println();


            for (int i = 0; i < Data.getInstance().indoorTemps.size(); i++) {
                if (Data.getInstance().indoorTemps.get(i).getAptNumber() == ((ApartmentOwner) Data.getInstance().currentLoggedInUser).apartmentNumber) {
                    if (Data.getInstance().indoorTemps.get(i).getTimeStamp().contains(dateFormat.format(dateObject))) {

                        System.out.println("Outdoor temperature: " + Data.getInstance().indoorTemps.get(i).getDegrees() + "°C" + "\nDate: " + Data.getInstance().indoorTemps.get(i).getTimeStamp());

                        sum += Data.getInstance().indoorTemps.get(i).getDegrees();
                        indexes++;
                        noSaved = false;
                    }
                }
                if (noSaved) {
                    System.out.println("<ERROR> You do not have any temperatures saved!\nTry adding some indoor measurements with option [1]");
                }
                System.out.println();


            }

            System.out.println("Average: " + sum / indexes + "°C");
        }


        public void printIndoorTemp7Days() {
            double sums = 0;
            int index;
            double sum;
            int indexes = 0;

            Collections.sort(Data.getInstance().indoorTemps);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendarDate = Calendar.getInstance();
            DecimalFormat deci = new DecimalFormat("#.##");


            //for (int i = 0; i < Data.getInstance().indoorTemps.size(); i++) {
            //  sum = sum + Data.getInstance().indoorTemps.get(i).getDegrees();
            //}

            //System.out.println("Saved measurements for apartment: " + indoorTemps.get(index).getApartmentNumber(apartmentOwner));
            /*
            Gör så att högsta index skrivs först
             */
            // Collections.sort(Data.getInstance().indoorTemps);

            //System.out.println("Saved measurements for apartment: " + indoorTemps.get(index).getApartmentNumber(apartmentOwner));
            System.out.println();
            for (int i = 7; 0 < i; i--) {
                Date dateToday = calendarDate.getTime();
                sum = 0;
                index = 0;

                for (int j = 0; j < Data.getInstance().indoorTemps.size(); j++) {
                    if (Data.getInstance().indoorTemps.get(j).getAptNumber() == ((ApartmentOwner) Data.getInstance().currentLoggedInUser).apartmentNumber) {
                        if (Data.getInstance().indoorTemps.get(j).getTimeStamp().contains(dateFormatter.format(calendarDate.getTime()))) {
                            dateToday = calendarDate.getTime();
                            sum += Data.getInstance().indoorTemps.get(j).getDegrees();
                            index++;
                            sums += Data.getInstance().indoorTemps.get(j).getDegrees();
                            indexes++;


                            System.out.println("Indoor temperature: " + Data.getInstance().indoorTemps.get(j).getDegrees() + "°C " +
                                    "\nDate: " + Data.getInstance().indoorTemps.get(j).getTimeStamp());

                            System.out.println();
                        }
                    }
                }
                 /*
            En dags average
             */
                if (sum > 0) {
                    sum = sum / index;
                    System.out.println("Average: " + deci.format(sum) + "°C" +
                            "\nDate: " + dateFormatter.format(dateToday));
                    System.out.println("-------------------------");
                }
                calendarDate.add(Calendar.DATE, -1);
            }
         /*
          flera dagar average
         */
            sums = sums / indexes;

            System.out.println("Total average: " + deci.format(sums) + "°C");
            // System.out.println("Average: " + sum/indexes + "°C" );
        }


    public void printoutSelectedTimeInterval() {
        int index2 = 0;
        int index1 = 0;
        Scanner input = new Scanner(System.in);

        final String regex = "[2][0][\\d]{2}[-]([0][\\d]|([1][0-2]))[-]([0][1-9]|[1-2][\\d]|[3][0-1])[ ]([0-1]{1}[\\d]{1}|([2][0-3]))[:][0-5][\\d][:][0-5][\\d]";
        String dateInput1 = "";
        String dateInput2 = "";
        Collections.sort(Data.getInstance().indoorTemps);
        System.out.println("");
        System.out.println("choose between this intervals down below ");
        System.out.println("");
        for (int i = 0; i < Data.getInstance().indoorTemps.size(); i++) {
            if (Data.getInstance().indoorTemps.get(i).getAptNumber() == ((ApartmentOwner) Data.getInstance().currentLoggedInUser).apartmentNumber) {
                System.out.println("Indoor temperature: " + Data.getInstance().indoorTemps.get(i).getDegrees() + "°C" + "\nDate: " + Data.getInstance().indoorTemps.get(i).getTimeStamp());
                System.out.println("");
            }
        }
        System.out.println("----------------------------------");

        do {
            System.out.println("Please input the latest time interval in this format yyyy-MM-dd HH:mm:ss ");
            System.out.print("> ");
            dateInput1 = input.nextLine();
            if (!dateInput1.matches(regex)) {
                System.out.println("<ERROR> Wrong format");
            }

        } while (!dateInput1.matches(regex));

        do {
            System.out.println("Please input the oldest time interval in this format yyyy-MM-dd HH:mm:ss ");
            System.out.print("> ");
            dateInput2 = input.nextLine();
            if (!dateInput2.matches(regex)) {
                System.out.println("<ERROR> Wrong format");
            }

        }

        while (!dateInput2.matches(regex));
        System.out.println("----------------------------------");


        for (int i = 0; i < Data.getInstance().indoorTemps.size(); i++) {
            if (Data.getInstance().indoorTemps.get(i).getAptNumber() == ((ApartmentOwner) Data.getInstance().currentLoggedInUser).apartmentNumber) {
                if (Data.getInstance().indoorTemps.get(i).getTimeStamp().contains(dateInput1)) {
                    index1 += i;
                }

            }

        }
        for (int i = 0; i < Data.getInstance().indoorTemps.size(); i++) {
            if (Data.getInstance().indoorTemps.get(i).getAptNumber() == ((ApartmentOwner) Data.getInstance().currentLoggedInUser).apartmentNumber) {
                if (Data.getInstance().indoorTemps.get(i).getTimeStamp().contains(dateInput2)) {
                    index2 += i;

                }
                double sum = 0;
                for (int b = index1; b <= index2; b++) {
                    sum += Data.getInstance().indoorTemps.get(b).getDegrees();
                }
            }

        }
        System.out.println("");
        double average=0;
        int times =0;
        for (; index1 <=index2; index1++) {
            if (Data.getInstance().indoorTemps.get(index1).getAptNumber() == ((ApartmentOwner) Data.getInstance().currentLoggedInUser).apartmentNumber) {
                System.out.println("Indoor temperature: " + Data.getInstance().indoorTemps.get(index1).getDegrees() + "°C" + "\nDate: " + Data.getInstance().indoorTemps.get(index1).getTimeStamp());
                average += Data.getInstance().indoorTemps.get(index1).getDegrees();
                times++;
                System.out.println("");
            }

        }
        average/=times;
        System.out.printf("Average: %.2f°C  %n",average);
        System.out.println("----------------------------------");
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
        return getUser();
    }

    @Override
    public String toString() {
        return "ApartmentOwner{" +
                "apartmentNumber=" + apartmentNumber +
                ", buildingAddress='" + buildingAddress + '\'' +
                ", user='" + getUser() + '\'' +
                '}';
    }
}
