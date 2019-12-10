package DHSCA_v2;

import java.util.Scanner;

public class Admin extends User {
private OutdoorTemp outdoorTemp = new OutdoorTemp("",0,"");
    public Admin(String user, String pass){
        super(user, pass);
    }

    public User addApartmentOwner(){
        Scanner input = new Scanner(System.in);
        String user, pass, buildingAddress;
        int aptNumber = 0;
        int rentCost = 0;
        boolean validInput = false;
        System.out.print("Please set the name of the owner: ");
        user = input.nextLine();
        System.out.print("Please set the password of the owner: ");
        pass = input.nextLine();

        //Tillfällig lösning.
        while(!validInput){
            try {
                System.out.print("Please set the Apartment number: ");
                aptNumber = Integer.parseInt(input.nextLine());
                validInput = true;
            } catch (NumberFormatException e){
                System.out.println("<ERROR> Only numeric values allowed");
            }
        }
        validInput = false;
        while(!validInput){
            try {
                System.out.print("Please set the rent cost: ");
                rentCost = Integer.parseInt(input.nextLine());
                validInput = true;
            } catch (NumberFormatException e){
                System.out.println("<ERROR> Only numeric values allowed");
            }
        }

        System.out.print("Please set the building address: ");
        buildingAddress = input.nextLine();




        System.out.println("Apartment owner added");
        return new ApartmentOwner(user, pass, aptNumber, rentCost, buildingAddress);

    }

    public OutdoorTemp addOutdoorTemp(){
        Scanner input = new Scanner(System.in);
        String temptimes=outdoorTemp.readTimestamp();
        double temptepm=outdoorTemp.readTempFromKeyboard();
        System.out.println("Insert building adress number: ");
        input.next();
        String buildingadress = input.nextLine();
        OutdoorTemp test = new OutdoorTemp(buildingadress,temptepm,temptimes);
        return test;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "user='" + user + '\'' +
                '}';
    }
}
