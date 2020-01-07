package DHSCA_v2;

import java.lang.module.FindException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DistrictHeatSavingsApp {
    public static void main(String[] args) {
        DistrictHeatSavingsApp DHSCA = new DistrictHeatSavingsApp();

        //variabler för testning av users
        Data.getInstance().userArrayList.add(new Admin("admin", "root"));
        Data.getInstance().userArrayList.add(new ApartmentOwner("sven", "nevs", 2, 300, "Kyrkogatan"));
        Data.getInstance().userArrayList.add(new ApartmentOwner("felix", "hemligt", 3, 320, "Kyrkogatan"));

        while(1==1) {
            Data.getInstance().currentLoggedInUser = Data.getInstance().logic.login();
            System.out.println("<Inloggad som: " + Data.getInstance().currentLoggedInUser.toString() + ">");
            Data.getInstance().logic.showMenu();
        }
    }
}