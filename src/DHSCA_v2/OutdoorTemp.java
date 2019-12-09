package DHSCA_v2;

public class OutdoorTemp extends Temperature {
    private String buildingAddress;

    public OutdoorTemp(String buildingAddress, double degrees, String timeStamp){
        super(degrees, timeStamp);
        this.buildingAddress = buildingAddress;
    }
}
