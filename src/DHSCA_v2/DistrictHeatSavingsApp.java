package DHSCA_v2;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DistrictHeatSavingsApp {
    public User currentLoggedInUser;
    // Arraylistor som sparar allt
    private ArrayList<User> userArrayList = new ArrayList<>();
    private ArrayList<OutdoorTemp> outdoorTemps = new ArrayList<>();
    private ArrayList<IndoorTemp> indoorTemps = new ArrayList<>();
    private ArrayList<String> sensorValues = new ArrayList<>();
    // Objekt som används för att kalla på metoder
    private OutdoorTemp outdoorTemp = new OutdoorTemp("", 0.0, "");
    private IndoorTemp indoorTemp = new IndoorTemp(0,0.0,"");
    private Admin admin = new Admin("","");
    private ApartmentOwner apartmentOwner = new ApartmentOwner("", "",0,0,"");

    public static void main(String[] args) {
        DistrictHeatSavingsApp DHSCA = new DistrictHeatSavingsApp();

        //variabler för testning av users
        DHSCA.userArrayList.add(new Admin("admin", "root"));
        DHSCA.userArrayList.add(new ApartmentOwner("sven", "nevs", 2, 300, "Kyrkogatan"));
        DHSCA.userArrayList.add(new ApartmentOwner("felix", "hemligt", 3, 320, "Kyrkogatan"));

        DHSCA.currentLoggedInUser = DHSCA.login();
        System.out.println("<Inloggad som: " + DHSCA.currentLoggedInUser.toString() + ">");

        DHSCA.showMenu();
    }

    public User login() {
        User result = null;
        Scanner input = new Scanner(System.in);

        while(result == null){
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

            if(result == null){
                System.out.println("<Wrong username or password>");
            }
    }
        return result;
    }

    public void showMenu() {
        int choice;
        Scanner input = new Scanner(System.in);

        do {
            if (currentLoggedInUser instanceof Admin) {
                System.out.println("--------------------------------------------------------");
                System.out.println("[1] Add apartment Owner" +
                        "\n[2] Add outdoor temperature measurement" +
                        "\n[3] Show today's outdoor measurements" +
                        "\n[4] Show last 7 days outdoor measurements" +
                        "\n[5] Show average heat setting" +
                        "\n[6] Show apartments" +
                        "\n[0] Exit menu" +
                        "\n[666] TERMINATE APPLICATION");
                System.out.println("--------------------------------------------------------");
            } else if (currentLoggedInUser instanceof ApartmentOwner) {
                System.out.println("--------------------------------------------------------");
                System.out.println("[1] Add indoor temperature" +
                        "\n[2] Change heat value" +
                        "\n[3] Show average heat setting" +
                        "\n[4] Show today's indoor measurement" +
                        "\n[5] Show last 7 day's indoor measurement" +
                        "\n[6] Show apartments" +
                        "\n[0] Exit menu" +
                        "\n[666] TERMINATE APPLICATION");
                System.out.println("--------------------------------------------------------");
            }

            try {
                choice = input.nextInt();
            } catch (InputMismatchException e) {
                choice = 404;
                input.nextLine();
            }

            switch (choice) {
                case 1:
                    if (currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde add apartment>");
                        userArrayList.add(((Admin) currentLoggedInUser).addApartmentOwner());
                    } else if (currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde add indoor temperature>");
                        indoorTemps.add(apartmentOwner.addIndoorTemp(currentLoggedInUser));

                    }
                    break;
                case 2:
                    if (currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde add outdoor temperature measurement>");
                        outdoorTemps.add(admin.addOutdoorTemp());
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
                case 6:
                    printApartments();
                    break;
                case 0:
                    System.out.println("<Exiting menu>");
                    break;
                case 666:
                    System.out.println("<TERMINATING APPLICATION>");
                    System.exit(0);
                default:
                    System.out.println("<No option found for input>");
                    break;
            }
        } while (choice != 0);
    }

    public void printApartments() {
        System.out.println("--------------------------------------------------------");
        if (currentLoggedInUser instanceof Admin) {
            for (User u :
                    userArrayList) {
                if (u instanceof ApartmentOwner) {
                    System.out.println(u.toString() + " Rent cost: " + ((ApartmentOwner) u).getRentCost() +
                            ", Password: " + u.getPass());
                }
            }
        } else if (currentLoggedInUser instanceof ApartmentOwner) {
            for (User u :
                    userArrayList) {
                if (u instanceof ApartmentOwner && currentLoggedInUser.equals(u)) {
                    System.out.println("<LOGGED IN USER> " + u.toString() + " Rent cost: " +
                            ((ApartmentOwner) u).getRentCost() + ", Password: " + u.getPass());
                } else if (u instanceof ApartmentOwner && !currentLoggedInUser.equals(u)) {
                    System.out.println(u.toString());
                }
            }
        }
        System.out.println("--------------------------------------------------------");
    }

    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }
}