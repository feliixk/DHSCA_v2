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

        Data.getInstance().indoorTemps.add(new IndoorTemp(1, 15, "2020-01-16 09:10:15"));
        Data.getInstance().indoorTemps.add(new IndoorTemp(1, 20, "2020-01-15 16:20:10"));
        Data.getInstance().indoorTemps.add(new IndoorTemp(1, 17, "2020-01-15 08:20:11"));
        Data.getInstance().indoorTemps.add(new IndoorTemp(1, 18, "2020-01-14 17:02:40"));

        Data.getInstance().outdoorTemps.add(new OutdoorTemp("Monk 1", 15, "2020-01-16 09:00:17"));
        Data.getInstance().outdoorTemps.add(new OutdoorTemp("Doodle 7", 10, "2020-01-16 07:11:45"));
        Data.getInstance().outdoorTemps.add(new OutdoorTemp("Doodle 7", 18, "2020-01-15 16:20:35"));
        Data.getInstance().outdoorTemps.add(new OutdoorTemp("Doodle 4", 23, "2020-01-14 22:01:36"));
        Data.getInstance().outdoorTemps.add(new OutdoorTemp("Monk 1", 16, "2020-01-05 11:20:17"));

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