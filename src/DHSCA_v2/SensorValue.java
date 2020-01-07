package DHSCA_v2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SensorValue implements Comparable <SensorValue>{
    private String timeStamp;

    public SensorValue(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String readTimestamp() {
        final String regex = "[2][0][\\d]{2}[-]([0][\\d]|([1][0-2]))[-]([0][1-9]|[1-2][\\d]|[3][0-1])[ ]([0-1]{1}[\\d]{1}|([2][0-3]))[:][0-5][\\d][:][0-5][\\d]";
        Scanner input = new Scanner(System.in);
        String dateInput = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateObject = new Date();
        do {
            System.out.println("please input the current date and time (yyyy-MM-dd HH:mm:ss)\nIf you press enter you will get the current time.");
            dateInput = input.nextLine();
            if (!dateInput.matches(regex) && !dateInput.equals("")) {
                System.out.println("Wrong format");
            }

        } while (!dateInput.matches(regex) && !dateInput.equals(""));

        if (!dateInput.equalsIgnoreCase("")) {
            return dateInput;
        } else return dateFormat.format(dateObject);
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "timeStamp='" + timeStamp + '\'' +
                '}';
    }

    @Override
    public int compareTo(SensorValue o) {
        SimpleDateFormat sortDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return sortDate.parse(o.getTimeStamp()).compareTo(sortDate.parse(this.getTimeStamp()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
