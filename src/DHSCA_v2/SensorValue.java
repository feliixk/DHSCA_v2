package DHSCA_v2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SensorValue  {
    private String timeStamp;

    public SensorValue(String timeStamp) {
        this.timeStamp = timeStamp;
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
