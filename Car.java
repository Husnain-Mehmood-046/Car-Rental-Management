package com.example.semesterrproject;


import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Car {
    private int id;
    private String name;
    private int seats;
    private String location;
    private double pricePerDay;
    private String imageUrl;
    private boolean isWithFuel;
    private boolean isAvailable;
    private LocalDate pickupDate;
    private LocalDate dropOffDate;
    private List<Reservation> reservationList;

    public Car(int id, String name, int seats, String location, double pricePerDay, String imageUrl, boolean isWithFuel) {
        this.name = name;
        this.seats = seats;
        this.location = location;
        this.isWithFuel = isWithFuel;
        this.pricePerDay = pricePerDay;
        this.imageUrl = imageUrl;
        this.isAvailable = true;
        this.pickupDate = null;
        this.dropOffDate = null;
        this.id = id;
        this.reservationList = new ArrayList<>();
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

    public LocalDate getPickupDate(){
        return pickupDate;
    }

    public LocalDate getDropOffDate(){
        return dropOffDate;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public boolean isAvailableDuring(LocalDate pickupDate, LocalDate dropoffDate) {
        List<Reservation> reservations = getReservationsFromFile();
        for (Reservation reservation : reservations) {
            if (reservation.getCarId() == this.id &&
                    !(dropoffDate.isBefore(reservation.getPickupDate()) || pickupDate.isAfter(reservation.getDropoffDate()))) {
                return false;
            }
        }
        return true;
    }


    public void addReservation(Reservation reservation) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("booking.txt", true))) {
            writer.write(reservation.getCarId() + "," +
                    reservation.getCarName() + "," +
                    reservation.getPickupDate() + "," +
                    reservation.getDropoffDate());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<Reservation> getReservationsFromFile() {
        List<Reservation> reservations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("booking.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int carId = Integer.parseInt(parts[0]);
                    String carName = parts[1];
                    LocalDate pickupDate = LocalDate.parse(parts[2]);
                    LocalDate dropoffDate = LocalDate.parse(parts[3]);
                    reservations.add(new Reservation(carId, carName, pickupDate, dropoffDate));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservations;
    }


    public int getId(){
        return id;
    }
}
