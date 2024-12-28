package org.example.rakkenishokran.Entities;

public class ParkingLotReport {
    private Long parkingLotId;
    private String parkingLotName;
    private String location;

    public Long getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Long parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
    }

    public int getOccupiedSpots() {
        return occupiedSpots;
    }

    public void setOccupiedSpots(int occupiedSpots) {
        this.occupiedSpots = occupiedSpots;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public double getOccupancyRate() {
        return occupancyRate;
    }

    public void setOccupancyRate(double occupancyRate) {
        this.occupancyRate = occupancyRate;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public Long getParkingManagerId() {
        return parkingManagerId;
    }

    public void setParkingManagerId(Long parkingManagerId) {
        this.parkingManagerId = parkingManagerId;
    }

    public String getParkingManagerName() {
        return parkingManagerName;
    }

    public void setParkingManagerName(String parkingManagerName) {
        this.parkingManagerName = parkingManagerName;
    }

    public String getParkingManagerEmail() {
        return parkingManagerEmail;
    }

    public void setParkingManagerEmail(String parkingManagerEmail) {
        this.parkingManagerEmail = parkingManagerEmail;
    }

    private int totalSpots;
    private int occupiedSpots;
    private int availableSpots;
    private double occupancyRate;
    private double revenue;
    private Long parkingManagerId;
    private String parkingManagerName;
    private String parkingManagerEmail;


}
