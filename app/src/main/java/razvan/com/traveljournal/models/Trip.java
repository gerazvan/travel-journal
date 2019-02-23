package razvan.com.traveljournal.models;

import java.util.Date;

public class Trip {
    private String tripName;
    private String destination;
    private String tripType;
    private int price;
    private Date startDate;
    private Date endDate;
    private double rating;
    private String imagePath;

    public Trip(String tripName, String destination, String tripType, int price, Date startDate, Date endDate, double rating, String imagePath) {
        this.tripName = tripName;
        this.destination = destination;
        this.tripType = tripType;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
        this.imagePath = imagePath;
    }

    public String getTripName() {
        return tripName;
    }

    public String getDestination() {
        return destination;
    }

    public String getTripType() {
        return tripType;
    }

    public int getPrice() {
        return price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getRating() {
        return rating;
    }

    public String getImagePath() {
        return imagePath;
    }
}
