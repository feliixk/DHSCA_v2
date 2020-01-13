package DHSCA_v2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.module.FindException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

public class DistrictHeatSavingsApp {
    public static void main(String[] args) {
        DistrictHeatSavingsApp DHSCA = new DistrictHeatSavingsApp();
        Data.getInstance().userArrayList.add(new Admin("admin", "root"));

        Data.getInstance().heatValues.add(new HeatRegulation(1, 100, "2020-01-13 12:12:12"));
        Data.getInstance().heatValues.add(new HeatRegulation(1, 100, "2020-01-12 12:12:12"));
        Data.getInstance().heatValues.add(new HeatRegulation(1, 100, "2020-01-11 12:12:12"));
        Data.getInstance().heatValues.add(new HeatRegulation(1, 100, "2020-01-10 12:12:12"));
        Data.getInstance().heatValues.add(new HeatRegulation(1, 100, "2020-01-09 12:12:12"));
        //Test values
        //Förutsätter att aptNumber 1 finns i users.txt

        try {
            Data.getInstance().loadFromFile();
        } catch (FileNotFoundException e) {
            System.out.println("<Userfile not found, creating on exit>");
        }

        while(1==1) {
            Data.getInstance().currentLoggedInUser = Data.getInstance().logic.login();
            System.out.println("<Inloggad som: " + Data.getInstance().currentLoggedInUser.toString() + ">");
            Data.getInstance().logic.showMenu();
        }
    }
}