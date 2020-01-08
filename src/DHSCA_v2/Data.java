package DHSCA_v2;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {
    private static Data single_instance = null;

    // all data här (alltid public)
    public User currentLoggedInUser;
    public ArrayList<User> userArrayList = new ArrayList<>();
    public ArrayList<OutdoorTemp> outdoorTemps = new ArrayList<>();
    public ArrayList<IndoorTemp> indoorTemps = new ArrayList<>();
    public ArrayList<String> sensorValues = new ArrayList<>();
    public ArrayList<HeatRegulation> heatValues = new ArrayList<HeatRegulation>();
    //public ArrayList<Double> heatSavings = new ArrayList<>();
    //Behövs inte, använd bara heatValues arrayen ovanför. Den har alla procent-värden.

    public HeatRegulation heatRegulation = new HeatRegulation(0, 0, "");
    public Admin admin = new Admin("", "");
    public OutdoorTemp outdoorTemp = new OutdoorTemp("",0,"");
    public IndoorTemp indoorTemp = new IndoorTemp(0,0.0,"");
    public ApartmentOwner apartmentOwner = new ApartmentOwner("", "", 0, 0, "");
    public Logic logic = new Logic();

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
                printWriter.println(name + ", " + password);
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("users.txt"));
        while(fileScanner.hasNextLine()){
            String userString = fileScanner.nextLine();
            String[] stringSplitter = userString.split(", ");
            String name = stringSplitter[0];
            String password = stringSplitter[1];
            userArrayList.add(new User(name, password));
        }
    }

    private Data() {
    }

    public static Data getInstance()
    {
        if (single_instance == null)
            single_instance = new Data();
        return single_instance;
    }
}
