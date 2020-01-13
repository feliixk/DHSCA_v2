package DHSCA_v2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Logic {

    public void showMenu() {
        int choice;
        Scanner input = new Scanner(System.in);

        do {
            if (Data.getInstance().currentLoggedInUser instanceof Admin) {
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
            } else if (Data.getInstance().currentLoggedInUser instanceof ApartmentOwner) {
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
                    if (Data.getInstance().currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde add apartment>");
                        Data.getInstance().userArrayList.add(((Admin) Data.getInstance().currentLoggedInUser).addApartmentOwner());
                    } else if (Data.getInstance().currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde add indoor temperature>");
                        Data.getInstance().indoorTemps.add(Data.getInstance().apartmentOwner.addIndoorTemp(Data.getInstance().currentLoggedInUser));

                    }
                    break;
                case 2:
                    if (Data.getInstance().currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde add outdoor temperature measurement>");
                        Data.getInstance().outdoorTemps.add(Data.getInstance().admin.addOutdoorTemp());

                    } else if (Data.getInstance().currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde change heat value>");
                        Data.getInstance().apartmentOwner.changeHeatingValue(Data.getInstance().currentLoggedInUser);
                    }
                    break;
                case 3:
                    if (Data.getInstance().currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde show today's outdoor measurements>");
                        Data.getInstance().admin.printOutdoorTemp_Currentday();
                    } else if (Data.getInstance().currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde show average heat setting>");
                        Data.getInstance().apartmentOwner.showAverageHeatSetting(Data.getInstance().currentLoggedInUser);
                    }
                    break;
                case 4:
                    if (Data.getInstance().currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde show last 7 days outdoor measurements>");
                        Data.getInstance().admin.printOutdoorTemp_7lastDays();
                    } else if (Data.getInstance().currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde show today's indoor measurement>");
                        Data.getInstance().apartmentOwner.printIndoorTempToday();
                    }
                    break;
                case 5:
                    if (Data.getInstance().currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde show average heat setting>");
                        //för testning
/*                        Data.getInstance().heatValues.add(new HeatRegulation(3, 23.3, "2019-12-06 21:34:55"));
                        Data.getInstance().heatValues.add(new HeatRegulation(3, 56, "2019-12-07 21:34:55"));
                        Data.getInstance().heatValues.add(new HeatRegulation(1, 23.3, "2019-12-06 21:34:55"));
                        Data.getInstance().heatValues.add(new HeatRegulation(3, 23.3, "2019-12-06 20:34:55"));
                        Data.getInstance().heatValues.add(new HeatRegulation(2, 23.3, "2019-12-06 21:34:55"));
                        Data.getInstance().heatValues.add(new HeatRegulation(3, 23.3, "2019-12-06 21:34:55"));*/

                        Data.getInstance().admin.showAverageHeatSetting();

                        //Data.getInstance().heatValues.clear();
                    } else if (Data.getInstance().currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde show last 7 day's indoor measurement>");
                        Data.getInstance().apartmentOwner.printIndoorTemp7Days();
                    }
                    break;
                case 6:
                    printApartments();
                    break;
                case 7:
                    //Data.getInstance().admin.printoutSelectedTime();

                case 0:
                    System.out.println("<Exiting menu>");
                    Data.getInstance().currentLoggedInUser = null;
                    break;
                case 666:
                    System.out.println("<TERMINATING APPLICATION>");
                    Data.getInstance().saveToFile();
                    System.exit(0);
                default:
                    System.out.println("<No option found for input>");
                    break;
            }
        } while (choice != 0);
    }

    //

    public void printApartments() {
        System.out.println("--------------------------------------------------------");
        if (Data.getInstance().currentLoggedInUser instanceof Admin) {
            for (User u :
                    Data.getInstance().userArrayList) {
                if (u instanceof ApartmentOwner) {
                    System.out.println(u.toString() + " Rent cost: " + ((ApartmentOwner) u).getRentCost() +
                            ", Password: " + u.getPass());
                }
            }
        } else if (Data.getInstance().currentLoggedInUser instanceof ApartmentOwner) {
            for (User u :
                    Data.getInstance().userArrayList) {
                if (u instanceof ApartmentOwner && Data.getInstance().currentLoggedInUser.equals(u)) {
                    System.out.println("<LOGGED IN USER> " + u.toString() + " Rent cost: " +
                            ((ApartmentOwner) u).getRentCost() + ", Password: " + u.getPass());
                } else if (u instanceof ApartmentOwner && !Data.getInstance().currentLoggedInUser.equals(u)) {
                    System.out.println(u.toString());
                }
            }
        }
        System.out.println("--------------------------------------------------------");
    }

    public User login() {
        User result = null;
        Scanner input = new Scanner(System.in);

        while (result == null) {
            System.out.println("Please enter your username: ");
            String username = input.nextLine();
            System.out.println("Please enter your password: ");
            String password = input.nextLine();

            if(username.length() < 1 && password.length() < 1) {
                System.out.println("TERMINATE APPLICATION? \nConfirm with 666");
                if(input.nextLine().equals("666")){
                    Data.getInstance().saveToFile();
                    System.exit(0);
                }
            }

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
                System.out.println("<Unable to find username or password>");
            }
        }
        return result;
    }
}
