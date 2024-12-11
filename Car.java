import java.util.Date;

public class Car {
    private String name;
    private int seats;
    private String location;
    private double pricePerDay;
    private String imageUrl;
    private boolean isWithFuel;
    //private Date pickupDate;
    //private Date dropoffDate;

    public Car(String name, int seats, String location, double pricePerDay, String imageUrl, boolean isWithFuel) {
        this.name = name;
        this.seats = seats;
        this.location = location;
        this.isWithFuel = isWithFuel;
        this.pricePerDay = pricePerDay;
        this.imageUrl = imageUrl;
        //this.pickupDate = pickupDate;
        //this.dropoffDate = dropoffDate;
    }

    public String getName() {
        return name;
    }

    public int getSeats() {
        return seats;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public boolean getIsWithFuel(){
        return isWithFuel;
    }

//    public Date getPickupDate() {
//        return pickupDate;
//    }
//
//    public Date getDropoffDate() {
//        return dropoffDate;
//    }

}