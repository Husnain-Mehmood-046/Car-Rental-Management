package com.example.semesterrproject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarsList {
    private List<Car> carslist;

    public CarsList() {
        carslist = new ArrayList<>();
        loadSampleCars();
    }

    private void loadSampleCars() {
        carslist.add(new Car(1, "Honda Civic RS 2024", 5, "Lahore", 20000, "civic.jpg", false));
        carslist.add(new Car(2, "Toyota Corolla Altis", 5, "Islamabad", 17000, "corolla.png", false));
        carslist.add(new Car(3, "Suzuki Swift 2023", 4, "Multan", 25000, "swift.png", true));
        carslist.add(new Car(4, "Changan Alsvin", 5, "Sheikhupura", 15000, "alsvin.png", false));
        carslist.add(new Car(5, "Toyota Revo", 5, "Islamabad", 25000, "revo.png", false));
        carslist.add(new Car(6, "Suzuki Wagon-R", 4, "Faisalabad", 20000, "wagon-r.jpg", true));
    }

    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : carslist) {
            if (car.isAvailable()) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }
}
