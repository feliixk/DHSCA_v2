package DHSCA_v2;

public class HeatRegulation extends SensorValue {
    private double percentageValue;

    public HeatRegulation(double percentageValue, String timeStamp){
        super(timeStamp);
        this.percentageValue = percentageValue;
    }

    public double readHeatValueFromKeyBoard(){
        return 0.0; //returnerar 0.0 för att förhindra error
    }
}
