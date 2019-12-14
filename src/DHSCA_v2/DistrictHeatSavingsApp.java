package DHSCA_v2;

import java.lang.module.FindException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DistrictHeatSavingsApp {

    private HeatRegulation heatRegulation = new HeatRegulation(0, 0, "");
    private Admin admin = new Admin("", "");
    private ApartmentOwner apartmentOwner = new ApartmentOwner("", "", 0, 0, "");

    private StaticClasses storer = StaticClasses.getInstance();

    public static void main(String[] args) {
        DistrictHeatSavingsApp DHSCA = new DistrictHeatSavingsApp();

        //variabler för testning av users
        Data.getInstance().userArrayList.add(new Admin("admin", "root"));
        Data.getInstance().userArrayList.add(new ApartmentOwner("sven", "nevs", 2, 300, "Kyrkogatan"));
        Data.getInstance().userArrayList.add(new ApartmentOwner("felix", "hemligt", 3, 320, "Kyrkogatan"));

        Data.getInstance().currentLoggedInUser = DHSCA.login();
        System.out.println("<Inloggad som: " + Data.getInstance().currentLoggedInUser.toString() + ">");

        DHSCA.showMenu();
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
                        Data.getInstance().indoorTemps.add(apartmentOwner.addIndoorTemp(Data.getInstance().currentLoggedInUser));

                    }
                    break;
                case 2:
                    if (Data.getInstance().currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde add outdoor temperature measurement>");
                        Data.getInstance().outdoorTemps.add(admin.addOutdoorTemp());
                        
                    } else if (Data.getInstance().currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde change heat value>");
                        Data.getInstance().heatValues.add(apartmentOwner.changeHeatingValue(Data.getInstance().currentLoggedInUser));
                    }
                    break;
                case 3:
                    if (Data.getInstance().currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde show today's outdoor measurements>");
                        printOutdoorTemp_Currentday();
                    } else if (Data.getInstance().currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde show average heat setting>");
                    }
                    break;
                case 4:
                    if (Data.getInstance().currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde show last 7 days outdoor measurements>");
                        printOutdoorTemp_7lastDays();
                    } else if (Data.getInstance().currentLoggedInUser instanceof ApartmentOwner) {
                        System.out.println("<Du valde show today's indoor measurement>");
                    }
                    break;
                case 5:
                    if (Data.getInstance().currentLoggedInUser instanceof Admin) {
                        System.out.println("<Du valde show average heat setting>");
                        //för testning

                        Data.getInstance().heatValues.add(new HeatRegulation(3, 23.3, "2019-12-06 21:34:55"));
                        Data.getInstance().heatValues.add(new HeatRegulation(3, 56, "2019-12-07 21:34:55"));
                        Data.getInstance().heatValues.add(new HeatRegulation(1, 23.3, "2019-12-06 21:34:55"));
                        Data.getInstance().heatValues.add(new HeatRegulation(3, 23.3, "2019-12-06 20:34:55"));
                        Data.getInstance().heatValues.add(new HeatRegulation(2, 23.3, "2019-12-06 21:34:55"));
                        Data.getInstance().heatValues.add(new HeatRegulation(3, 23.3, "2019-12-06 21:34:55"));

                        heatRegulation.showAverageHeatSetting(Data.getInstance().heatValues, Data.getInstance().userArrayList);

                        Data.getInstance().heatValues.clear();
                    } else if (Data.getInstance().currentLoggedInUser instanceof ApartmentOwner) {
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

    public User getCurrentLoggedInUser() {
        return Data.getInstance().currentLoggedInUser;
    }

    public void printOutdoorTemp_Currentday() {
        ArrayList<Double> test= new ArrayList<>();
        double sum = 0;
        for (var i = 0; i <Data.getInstance().outdoorTemps.size(); i++) {
            test.add(Data.getInstance().outdoorTemps.get(i).getDegrees());
        }

        for (int i = 0; i < test.size(); i++) {
            sum+=test.get(i);
        }
        var times = 1;
        if (times <= Data.getInstance().outdoorTemps.size()) {
            Collections.reverse(Data.getInstance().outdoorTemps);


            for (int i = 0; i < times; i++) {
                System.out.println("Outdoor temperature: " + Data.getInstance().outdoorTemps.get(i).getDegrees() + "°C" + "\nDate: " + Data.getInstance().outdoorTemps.get(i).getTimeStamp());
                System.out.println("----------------------------------");

            }
            System.out.println("Average temperature:"+sum/test.size()+"°C");
        } else {
            System.out.println("You have not saved anything!!");
        }
    }


    public void printOutdoorTemp_7lastDays() {
        ArrayList<Double> test= new ArrayList<>();
        double sum = 0;
        for (var i = 0; i <Data.getInstance().outdoorTemps.size(); i++) {
            test.add(Data.getInstance().outdoorTemps.get(i).getDegrees());
        }

        for (int i = 0; i < test.size(); i++) {
            sum+=test.get(i);
        }
        int times = 7;
        if (times <= Data.getInstance().outdoorTemps.size()) {
            Collections.reverse(Data.getInstance().outdoorTemps);


            for (int i = 0; i < times; i++) {
                System.out.println("Outdoor temperature: " + Data.getInstance().outdoorTemps.get(i).getDegrees() + "°C" + "\nDate: " + Data.getInstance().outdoorTemps.get(i).getTimeStamp());
                System.out.println("----------------------------------");

            }
        } else {
            System.out.println("This is what you have saved!");
            for (int i = Data.getInstance().outdoorTemps.size() - 1; i >= 0; i--) {
                System.out.println("Outdoor temperature: " + Data.getInstance().outdoorTemps.get(i).getDegrees() + "°C" + "\nDate: " + Data.getInstance().outdoorTemps.get(i).getTimeStamp());
                System.out.println("----------------------------------");

            }
            System.out.println("Average temperature:"+sum/test.size()+"°C");
        }
    }
}