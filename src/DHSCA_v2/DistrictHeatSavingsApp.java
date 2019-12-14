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

        Data.getInstance().currentLoggedInUser = DHSCA.login();
        System.out.println("<Inloggad som: " + Data.getInstance().currentLoggedInUser.toString() + ">");

        Data.getInstance().logic.showMenu();
    }

    public User login() {
        User result = null;
        Scanner input = new Scanner(System.in);

        while (result == null) {
            System.out.println("Please enter your username: ");
            String username = input.nextLine();
            System.out.println("Please enter your password: ");
            String password = input.nextLine();

            for (User userInLoop :
                    Data.getInstance().userArrayList) {
                if (userInLoop.getUser().equals(username) && userInLoop.getPass().equals(password)) {
                    if (userInLoop instanceof Admin) {
                        result = userInLoop;
                    } else if (userInLoop instanceof ApartmentOwner) {
                        result = userInLoop;
                    }
                }
            }

            if (result == null) {
                System.out.println("<Wrong username or password>");
            }
        }
        return result;
    }

    public User getCurrentLoggedInUser() {
        return Data.getInstance().currentLoggedInUser;
    }
}