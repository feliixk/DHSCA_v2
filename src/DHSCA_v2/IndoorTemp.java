package DHSCA_v2;



public class IndoorTemp extends Temperature {
    private int apartmentNr;

    public IndoorTemp(int apartmentNr, double degrees, String timeStamp){
        super(degrees, timeStamp);
        this.apartmentNr = apartmentNr;

    }

    public int getAptNumber() {
        return apartmentNr;
    }
}
