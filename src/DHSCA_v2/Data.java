package DHSCA_v2;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {
    private static Data single_instance = null;

    // all data here (public)
    public User currentLoggedInUser;
    public ArrayList<User> userArrayList = new ArrayList<>();
    public ArrayList<OutdoorTemp> outdoorTemps = new ArrayList<>();
    public ArrayList<IndoorTemp> indoorTemps = new ArrayList<>();
    public ArrayList<String> sensorValues = new ArrayList<>();
    public ArrayList<HeatRegulation> heatValues = new ArrayList<HeatRegulation>();
    public HeatRegulation heatRegulation = new HeatRegulation(0, 0, "");
    public Admin admin = new Admin("", "");
    public OutdoorTemp outdoorTemp = new OutdoorTemp("",0,"");
    public IndoorTemp indoorTemp = new IndoorTemp(0,0.0,"");
    public ApartmentOwner apartmentOwner = new ApartmentOwner("", "", 0, 0, "");
    public Logic logic = new Logic();

    /*
    Method that saves all apartmentowners stored in the arrays to the textfile users.txt. The admin user is hardcoded
    into the program as it should be and isn't stored in textfile format.
     */
    public void saveToFile(){
        try {
            File users = new File("users.txt");
            if(!users.exists()){
                    users.createNewFile();
            }
            PrintWriter printWriter = new PrintWriter(users);
            for (User user:
                 userArrayList) {
                String name = user.getUser();
                String password = user.getPass();
                if(user instanceof ApartmentOwner){
                    int apartmentNumber = ((ApartmentOwner) user).getApartmentNumber();
                    String buildingAdress = ((ApartmentOwner) user).buildingAddress;
                    int rentCost = ((ApartmentOwner) user).rentCost;
                    printWriter.println(name + ", " + password + ", " + apartmentNumber + ", " + rentCost + ", " + buildingAdress);
                }
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Method that load all the apartmentowners stored in the textfile users.txt into the arrays during program startup
    (adminuser excluded)
     */
    public void loadFromFile() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("users.txt"));
        while(fileScanner.hasNextLine()){
            String userString = fileScanner.nextLine();
            String[] stringSplitter = userString.split(", ");
            String name = stringSplitter[0];
            String password = stringSplitter[1];
            int apartmentNumber = Integer.parseInt(stringSplitter[2]);
            int rentCost = Integer.parseInt(stringSplitter[3]);
            String buildingAdress = stringSplitter[4];
            userArrayList.add(new ApartmentOwner(name, password, apartmentNumber, rentCost, buildingAdress));
        }
    }

    private Data() {
        //empty private constructor
    }

    public static Data getInstance()
    {
        if (single_instance == null)
            single_instance = new Data();
        return single_instance;
    }
}
