package DHSCA_v2;

import java.util.ArrayList;
import java.util.Scanner;

public class DistrictHeatSavingsApp {
    public User currentLoggedInUser;
    private ArrayList<User> userArrayList = new ArrayList<>();

    public static void main(String[] args) {
        DistrictHeatSavingsApp DHSCA = new DistrictHeatSavingsApp();

        //variabler för testning av users
        Admin admin = new Admin ("admin", "root");
        DHSCA.userArrayList.add(admin);
        DHSCA.userArrayList.add(new ApartmentOwner("sven", "nevs", 2, 300));
        DHSCA.userArrayList.add(new ApartmentOwner("felix", "hemligt", 3, 320));

        DHSCA.currentLoggedInUser = DHSCA.login();
        System.out.println("<Inloggad som: "+DHSCA.currentLoggedInUser.toString()+">");
        DHSCA.userArrayList.add(admin.addApartmentOwner());
    }

    public User login(){
        User result = null;
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = input.nextLine();
        System.out.println("Please enter your password: ");
        String password = input.nextLine();

        for (User userInLoop :
                userArrayList) {
            if (userInLoop.getUser().contains(username) && userInLoop.getPass().contains(password)) {
                if (userInLoop instanceof Admin) {
                    result = userInLoop;
                } else if (userInLoop instanceof ApartmentOwner) {
                    result = userInLoop;
                }
            }
        }
        //TODO[SS]: Gör att login inte kan krascha vid input
        return result;
    }
}
