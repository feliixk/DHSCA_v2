package DHSCA_v2;

import java.util.Scanner;

public class Admin extends User {

    public Admin(String user, String pass){
        super(user, pass);
    }

    public User addApartmentOwner(){
        String user, pass;
        int aptNumber;
        int rentCost;
        Scanner input = new Scanner(System.in);
        System.out.println("What is the name of owner?");
        user = input.nextLine();
        System.out.println("What is the password of owner?");
        pass = input.nextLine();
        System.out.println("What is the apartment number");
        aptNumber = Integer.parseInt(input.nextLine());
        System.out.println("What is the rent cost?");
        rentCost = Integer.parseInt(input.nextLine());

        return new ApartmentOwner(user, pass, aptNumber, rentCost);

    }

    public void addOutdoorTemp(){

    }
}
