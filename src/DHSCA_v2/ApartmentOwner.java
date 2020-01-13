package DHSCA_v2;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ApartmentOwner extends User {

    public int apartmentNumber;
    public int rentCost;
    public String buildingAddress;
    private double monthlySavingOrPenalty;



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

    public void changeHeatingValue(User user){
        List<HeatRegulation> thingsToBeAdd = new ArrayList<HeatRegulation>();
        String timeStamp = Data.getInstance().heatRegulation.readTimestamp();
        double heatValue = Data.getInstance().heatRegulation.readHeatValueFromKeyBoard();
        int aptNumber = ((ApartmentOwner) user).getApartmentNumber();
        thingsToBeAdd.clear();

        System.out.println("apt nr: " + aptNumber);
        boolean added = false;


        for (HeatRegulation heatR : Data.getInstance().heatValues) {
                if (heatR.aptNumber == aptNumber && heatR.getTimeStamp().substring(0, 10).startsWith(timeStamp.substring(0, 10))) {
                    heatR.setPercentageValue(heatValue);
                    System.out.println("DATE ALREADY EXISTS, CORRECT DATE, UPDATING HEATVALUE");
                    added = true;
                    break;
                } else if (heatR.aptNumber == aptNumber && !heatR.getTimeStamp().substring(0, 10).startsWith(timeStamp.substring(0, 10))) {
                    System.out.println("DATE ALREADY EXISTS, BUT WRONG DATE, NOT UPDATING HEATVALUE");
                }
                else if (heatR.aptNumber != aptNumber) {
                System.out.println("DEBUG WRONG USER");
                } else {
                    System.out.println("no heat value");
        }
    }

        if (!added) {
        Data.getInstance().heatValues.add(new HeatRegulation(aptNumber, heatValue, timeStamp));
        System.out.println("ADDING TO ARRAYLIST HEATVALUES");
    }

    }

    public void calculateMonthlySavingOrPenalty(double dailySavings){

    }


    public void showAverageHeatSetting(User user) {
        Data.getInstance().savingOrPenalty.clear();
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

        System.out.println("DEBUG APARTMENTNUMBER: " + apartmentNr);

        System.out.println("Type the date you want to show (yyyy-MM-dd): ");
        dateInput = input.nextLine();


        //for (HeatRegulation heatRegulation :
        //        Data.getInstance().heatValues) {
        //   if (apartmentNr == heatRegulation.aptNumber && heatRegulation.getTimeStamp().contains(dateInput)){
        //       heatRegulationsInMethod.add(heatRegulation);
        //   }else{
        //      System.out.println("ERROR");
        //  }
        //}

        daily_base_amount = ((ApartmentOwner) user).getRentCost() / 300;

        //Tillfällig metod

        double averageHeatMonthSetting = 0;
        int numberOfHeatMonthValues = 0;
        double total_saving_penalty = 0;
        String monthInput = dateInput.substring(0, 7);

        for (int i = 0; i < Data.getInstance().heatValues.size(); i++) {
            if (apartmentNr == Data.getInstance().heatValues.get(i).aptNumber && Data.getInstance().heatValues.get(i).getTimeStamp().startsWith(dateInput)) {
                //System.out.println("HEATVALUES SIZE: " + Data.getInstance().heatValues.size());
                //System.out.println("APARTMENT FOUND");
                averageHeatSetting = Data.getInstance().heatValues.get(i).getPercentageValue();
                numberOfHeatValues = Data.getInstance().heatValues.get(i).getAmountOfValues();
                //System.out.println("DEBUG LOOP HEAT: " + averageHeatSetting);
                //System.out.println("DEBUG LOOP VALUES: " + numberOfHeatValues);
                break;
            }else{

            }
        }
        for (int i = 0; i < Data.getInstance().heatValues.size(); i++) {
            if (apartmentNr == Data.getInstance().heatValues.get(i).aptNumber && Data.getInstance().heatValues.get(i).getTimeStamp().contains(monthInput)) {


                if(Data.getInstance().heatValues.get(i).getAmountOfValues() > 1){
                    averageHeatMonthSetting += (Data.getInstance().heatValues.get(i).getPercentageValue() / Data.getInstance().heatValues.get(i).getAmountOfValues());
                    numberOfHeatMonthValues++;
                    System.out.println("DEBUGGER: GETTING AVERAGE VALUE, MORE THAN 1 VALUE IN DAY " + Data.getInstance().heatValues.get(i).getTimeStamp().substring(0,10));
                }else{
                    averageHeatMonthSetting += Data.getInstance().heatValues.get(i).getPercentageValue();
                    numberOfHeatMonthValues += Data.getInstance().heatValues.get(i).getAmountOfValues();
                    System.out.println("DEBUGGER: GETTING AVERAGE VALUE, ONLY 1 VALUE IN DAY " + Data.getInstance().heatValues.get(i).getTimeStamp().substring(0,10));
                }
            }
        }


            //System.out.println("AverageHeatSetting: " + averageHeatSetting);
            //System.out.println("number of heatvalues: " + numberOfHeatValues);


            daily_saving_or_penalty = (averageHeatSetting / numberOfHeatValues) * 20 - daily_base_amount;

           // System.out.println("Average Heat Month Setting: " + averageHeatMonthSetting);
        //System.out.println("Number of heat values Setting: " + numberOfHeatMonthValues);

            for (int i = 0; i < numberOfHeatMonthValues; i++){
                total_saving_penalty += ((averageHeatMonthSetting / numberOfHeatMonthValues) * 20 - daily_base_amount);
                //System.out.println("Debug total_saving_penalty: " + total_saving_penalty);
            }

            /*

            index 0: total_saving_penalty = 400/4 * 20 - 10; = 1990;
            index 1: total_saving_penalty = 3980


             */
            //System.out.println("Total saving/penalty DEBUG: " + total_saving_penalty);



            Double averageValues = (averageHeatSetting / numberOfHeatValues) * 100;

            monthlySavingOrPenalty += daily_saving_or_penalty;

            double total_rent_cost = ((ApartmentOwner) user).getRentCost() + total_saving_penalty;

            //----------------------------------
        System.out.println("-------------------------------\nResults: ");

            System.out.println("Average heat setting for this apartment today is: " + String.format("%.2f", averageValues) + " %");
            System.out.println("Daily saving or penalty for this day : " + String.format("%.2f", daily_saving_or_penalty) + " SEK");
            System.out.println("Total saving/penalty this month so far: " + total_saving_penalty);
            System.out.println("Total rent cost this month so far: " + total_rent_cost);

            System.out.println("------");
            for (HeatRegulation hReg : Data.getInstance().heatValues){
                if (hReg.aptNumber == apartmentNr){
                    System.out.println(hReg.toString());
                }
            }

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


            for (int i = 0; i < Data.getInstance().indoorTemps.size(); i++) {
                if (Data.getInstance().indoorTemps.get(i).getAptNumber() == ((ApartmentOwner) Data.getInstance().currentLoggedInUser).apartmentNumber) {
                    if (Data.getInstance().indoorTemps.get(i).getTimeStamp().contains(dateFormat.format(dateObject))) {
                        System.out.println("Outdoor temperature: " + Data.getInstance().indoorTemps.get(i).getDegrees() + "°C" + "\nDate: " + Data.getInstance().indoorTemps.get(i).getTimeStamp());
                        System.out.println("----------------------------------");
                        sum += Data.getInstance().indoorTemps.get(i).getDegrees();
                        indexes++;
                        noSaved = false;
                    }
                }
                if (noSaved) {
                    System.out.println("You do not have any temperatures saved!\nTry adding some indoor measurements with option [1]");
                }


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

                            System.out.println("-------------------------");
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
