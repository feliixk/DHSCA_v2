package DHSCA_v2;

import java.util.ArrayList;
import java.util.Scanner;

public class DistrictHeatSavingsApp {
    public User currentLoggedInUser;
    private ArrayList<User> userArrayList = new ArrayList<>();

    public static void main(String[] args) {
        DistrictHeatSavingsApp DHSCA = new DistrictHeatSavingsApp();

        //variabler för testning av users
        DHSCA.userArrayList.add(new Admin("admin", "root"));
        DHSCA.userArrayList.add(new ApartmentOwner("sven", "nevs", 2, 300));
        DHSCA.userArrayList.add(new ApartmentOwner("felix", "hemligt", 3, 320));

        DHSCA.currentLoggedInUser = DHSCA.login();
        System.out.println("<Inloggad som: "+DHSCA.currentLoggedInUser.toString()+">");

        DHSCA.showMenu();
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

    public void showMenu() {
        int choice;
        Scanner input = new Scanner(System.in);

        do {
            if (currentLoggedInUser instanceof Admin) {
                System.out.println("[1] Add apartment" +
                        "\n[2] Add outdoor temperature measurement" +
                        "\n[3] Show today's outdoor measurements" +
                        "\n[4] Show last 7 days outdoor measurements" +
                        "\n[5] Show average heat setting" +
                        "\n[0] EXIT");
            } else if (currentLoggedInUser instanceof ApartmentOwner) {
                System.out.println("[1] Add indoor temperature" +
                        "\n[2] Change heat value" +
                        "\n[3] Show average heat setting" +
                        "\n[4] Show today's indoor measurement" +
                        "\n[5] Show last 7 day's indoor measurement" +
                        "\n[0] EXIT");
            }
            choice = input.nextInt();
            //TODO[SS]: Gör så den inte kan krascha vid input

            switch (choice) {
                case 1:
                    if (currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde add apartment>");
                    } else if (currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde add indoor temperature>");
                    }
                    break;
                case 2:
                    if (currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde add outdoor temperature measurement>");
                    } else if (currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde change heat value>");
                    }
                    break;
                case 3:
                    if (currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde show today's outdoor measurements>");
                    } else if (currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde show average heat setting>");
                    }
                    break;
                case 4:
                    if (currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde show last 7 days outdoor measurements>");
                    } else if (currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde show today's indoor measurement>");
                    }
                    break;
                case 5:
                    if (currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde show average heat setting>");
                    } else if (currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde show last 7 day's indoor measurement>");
                    }
                    break;
            }
        } while (choice != 0);
    }
}
