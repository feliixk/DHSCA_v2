package DHSCA_v2;


import java.util.Collections;

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
        return new HeatRegulation(aptNumber, heatValue, timeStamp);

    }

    public void totalRent(){


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
