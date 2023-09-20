// This is a class representing a ride
public class Ride {
    //The attributes of class Ride
    private int rideNumber;
    private int rideCost;
    private int tripDuration;
    // Constructor for the Ride class
    public Ride(int rideNumber, int rideCost, int tripDuration) {
        // set the instance variables to the values passed in through the constructor
        this.rideNumber = rideNumber;
        this.rideCost = rideCost;
        this.tripDuration = tripDuration;
    }
    //getter and setter methods
    public int getRideNumber() {
        return rideNumber;
    }

    public void setRideNumber(int rideNumber) {
        this.rideNumber = rideNumber;
    }

    public int getRideCost() {
        return rideCost;
    }

    public void setRideCost(int rideCost) {
        this.rideCost = rideCost;
    }

    public int getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(int tripDuration) {
        this.tripDuration = tripDuration;
    }
}
