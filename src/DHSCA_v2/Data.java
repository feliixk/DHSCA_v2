package DHSCA_v2;

import java.util.ArrayList;

public class Data {
    private static Data single_instance = null;

    // all data här (alltid public)
    public User currentLoggedInUser;
    public ArrayList<User> userArrayList = new ArrayList<>();
    public ArrayList<OutdoorTemp> outdoorTemps = new ArrayList<>();
    public ArrayList<IndoorTemp> indoorTemps = new ArrayList<>();
    public ArrayList<String> sensorValues = new ArrayList<>();
    public ArrayList<HeatRegulation> heatValues = new ArrayList<HeatRegulation>();

    private Data()
    {
    }

    public static Data getInstance()
    {
        if (single_instance == null)
            single_instance = new Data();
        return single_instance;
    }
}
