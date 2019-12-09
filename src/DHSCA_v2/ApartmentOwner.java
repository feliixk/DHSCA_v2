package DHSCA_v2;

public class ApartmentOwner extends User {

    public int apartmentNumber;
    public int rentCost;

    public ApartmentOwner(String name, String pass, int apartmentNumber, int rentCost){
        super(name, pass);
        this.apartmentNumber = apartmentNumber;
        this.rentCost = rentCost;
    }

    public void addIndoorTemp(){

    }

    @Override
    public String toString() {
        return "ApartmentOwner{" +
                "apartmentNumber=" + apartmentNumber +
                ", user='" + user + '\'' +
                '}';
    }

    public int getRentCost() {
        return rentCost;
    }
}
