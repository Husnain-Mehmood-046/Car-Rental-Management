package com.example.semesterrproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarDetailView extends Application {

    private Stage primaryStage;
    private Car car;
    Label carstatusLabel;

    public CarDetailView(Car car) {
        this.car = car;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        BorderPane mainLayout = new BorderPane();


        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(10));
        sidebar.setStyle("-fx-background-color: #202020; -fx-pref-width: 80px;");


        for (int i = 0; i < 5; i++) {
            Label icon = new Label("Icon " + (i + 1));
            icon.setStyle("-fx-text-fill: white;");
            sidebar.getChildren().add(icon);
        }


        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);


        ImageView carImage = new ImageView(new Image(car.getImageUrl()));
        carImage.setFitWidth(300);
        carImage.setPreserveRatio(true);

        Label carName = new Label(car.getName());
        carName.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label carPrice = new Label("Rs. " + car.getPricePerDay() + " per Day");
        carPrice.setStyle("-fx-font-size: 16px; -fx-text-fill: gray;");

        HBox specs = new HBox(20);
        specs.setAlignment(Pos.CENTER);

        VBox seatsInfo = createSpecBox("Seats", car.getSeats() + " Persons");
        VBox fuelInfo = createSpecBox("Fuel", car.getIsWithFuel() ? "With Fuel" : "Without Fuel");
        VBox locationInfo = createSpecBox("Location", car.getLocation());
        specs.getChildren().addAll(seatsInfo, fuelInfo, locationInfo);

        DatePicker pickupDatePicker = new DatePicker();
        pickupDatePicker.setPromptText("Select Pickup Date");

        DatePicker dropOffDatePicker = new DatePicker();
        dropOffDatePicker.setPromptText("Select Drop-Off Date");

        Label statusLabel = new Label(car.isAvailable() ? "Status: Available" : "Status: Not Available");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        Button bookNow = new Button("BOOK NOW");

        bookNow.setDisable(!car.isAvailable());

        bookNow.setStyle("-fx-background-color: #000; -fx-text-fill: white; -fx-font-size: 14px;");
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px;");

        buttons.getChildren().addAll(bookNow, backButton);


        bookNow.setOnAction(e -> {
            LocalDate pickupDate = pickupDatePicker.getValue();
            LocalDate dropoffDate = dropOffDatePicker.getValue();

            if (pickupDate == null || dropoffDate == null || pickupDate.isAfter(dropoffDate)) {
                new Alert(Alert.AlertType.ERROR, "Please select valid Pick-up and Drop-Off dates.").showAndWait();
                return;
            }

            if (!car.isAvailableDuring(pickupDate, dropoffDate)) {
                new Alert(Alert.AlertType.ERROR, "Car is not available for the selected dates.").showAndWait();
                return;
            }

            Reservation newReservation = new Reservation(car.getId(), car.getName(), pickupDate, dropoffDate);
            car.addReservation(newReservation);

            new Alert(Alert.AlertType.INFORMATION, "Car successfully booked!").showAndWait();
            new BookingConfirmation(car, pickupDate, dropoffDate).start(new Stage());
            primaryStage.close();
        });


        backButton.setOnAction(e -> {
            primaryStage.close();
            new Dashboard().start(new Stage());
        });

        content.getChildren().addAll(carImage, carName, carPrice, specs, pickupDatePicker, dropOffDatePicker,statusLabel, buttons);

        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(content);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        Scene scene = new Scene(mainLayout, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Car Detail View");
        primaryStage.show();
    }

    private VBox createSpecBox(String title, String detail) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
        Label detailLabel = new Label(detail);
        detailLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        box.getChildren().addAll(titleLabel, detailLabel);
        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}