package DHSCA_v2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class OutdoorTemp extends Temperature {
    private String buildingAddress;

    public OutdoorTemp(String buildingAddress, double degrees, String timeStamp){
        super(degrees, timeStamp);
        this.buildingAddress = buildingAddress;
    }
    public  double readTempFromKeyboard(){
        System.out.println("Enter what temperature it is in your apartment!");
        System.out.print("> ");
        double degrees = input.nextDouble();
        input.nextLine();
        return degrees;
    }

    public String readTimestamp(){
        Scanner input = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateObject = new Date();
        System.out.println("please input the current date and time (yyyy-MM-dd HH:mm:ss)\nIf you press enter you will get the current time.");
        String dateInput = input.nextLine();
        if (!dateInput.equalsIgnoreCase("")) {
            return dateInput;
        } else return dateFormat.format(dateObject);
    }
}
