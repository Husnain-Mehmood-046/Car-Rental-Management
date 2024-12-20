package com.example.semesterrproject;

import java.time.LocalDate;

public class Reservation {
    private int carId;
    private String carName;
    private LocalDate pickupDate;
    private LocalDate dropoffDate;

    public Reservation(int carId, String carName, LocalDate pickupDate, LocalDate dropoffDate) {
        this.carId = carId;
        this.carName = carName;
        this.pickupDate = pickupDate;
        this.dropoffDate = dropoffDate;
    }

    public int getCarId() {
        return carId;
    }

    public String getCarName() {
        return carName;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public LocalDate getDropoffDate() {
        return dropoffDate;
    }
}