package DHSCA_v2;

import java.util.ArrayList;

public class StaticClasses  {

    //Test!!! Används inte just nu.
    private static StaticClasses mInstance;
    private ArrayList<String> list = null;

    private OutdoorTemp outdoorTemp = new OutdoorTemp("", 0.0, "");
    private IndoorTemp indoorTemp = new IndoorTemp(0, 0.0, "");
    private HeatRegulation heatRegulation = new HeatRegulation(0, 0, "");
    private Admin admin = new Admin("", "");
    private ApartmentOwner apartmentOwner = new ApartmentOwner("", "", 0, 0, "");

    public static StaticClasses getInstance() {
        if(mInstance == null)
            mInstance = new StaticClasses();

        return mInstance;
    }

    private StaticClasses() {
        list = new ArrayList<String>();
    }
    // retrieve array from anywhere
    public ArrayList<String> getArray() {
        return this.list;
    }
    //Add element to array
    public void addToArray(String value) {
        list.add(value);
    }

    public static StaticClasses getmInstance() {
        return mInstance;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public OutdoorTemp getOutdoorTemp() {
        return outdoorTemp;
    }

    public IndoorTemp getIndoorTemp() {
        return indoorTemp;
    }

    public HeatRegulation getHeatRegulation() {
        return heatRegulation;
    }

    public Admin getAdmin() {
        return admin;
    }

    public ApartmentOwner getApartmentOwner() {
        return apartmentOwner;
    }
}
