package com.example.semesterrproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookedCars {
    private final CarsList carsList;

    public BookedCars(CarsList carsList) {
        this.carsList = carsList;
    }

    private Stage primaryStage;

    public void showBookedCars(Stage PrimaryStage) {
        Stage stage = new Stage();
        stage.setTitle("Booked Cars");
        this.primaryStage = PrimaryStage;

        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10, 50, 50, 50));
        mainLayout.setStyle("-fx-background-color: white;");

        HBox headerbox = new HBox();
        Label header = new Label("Car Rental Management System");
        header.setFont(new Font("Arial", 25));
        header.setStyle("-fx-font-weight: bold; -fx-text-fill: #000; -fx-padding: 10;");
        headerbox.setPadding(new Insets(20, 0 ,15, 0));
        headerbox.getChildren().add(header);
        headerbox.setAlignment(Pos.CENTER);

        mainLayout.setTop(headerbox);

        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #202020; -fx-pref-width: 125px; -fx-border-radius: 20px; -fx-background-radius: 20px;");
        sidebar.setPrefWidth(200);

        ImageView logo = new ImageView("CarLogo.png");
        logo.setFitHeight(80);
        logo.setFitWidth(100);

        VBox sidebarIcons = new VBox(50);

        ImageView carlistlogo = new ImageView("carlist.png");
        carlistlogo.setOnMouseClicked(event -> {
            primaryStage.close();
            new Dashboard().start(new Stage());
        });
        carlistlogo.setOnMouseEntered(event -> carlistlogo.setCursor(Cursor.HAND));
        carlistlogo.setOnMouseExited(event -> carlistlogo.setCursor(Cursor.DEFAULT));
        carlistlogo.setFitHeight(40);
        carlistlogo.setFitWidth(40);

        ImageView bookedcars = new ImageView("booked.png");
        bookedcars.setFitHeight(40);
        bookedcars.setFitWidth(40);

        ImageView userData = new ImageView("user.png");
        userData.setFitHeight(40);
        userData.setFitWidth(40);

        sidebarIcons.getChildren().addAll(carlistlogo, bookedcars, userData);
        sidebarIcons.setAlignment(Pos.CENTER);

        VBox logoutBox = new VBox();
        ImageView logout = new ImageView("logout.png");
        logout.setFitHeight(35);
        logout.setFitWidth(35);
        logoutBox.getChildren().addAll(logout);
        logoutBox.setAlignment(Pos.CENTER);
        logoutBox.setOnMouseClicked(event -> {
            primaryStage.close();
            new LoginSignupForm().start(new Stage());
        });
        logoutBox.setOnMouseEntered(event -> logoutBox.setCursor(Cursor.HAND));
        logoutBox.setOnMouseExited(event -> logoutBox.setCursor(Cursor.DEFAULT));

        VBox.setMargin(logo, new Insets(0, 0, 110, 0));
        VBox.setMargin(logoutBox, new Insets(130, 0, 0, 0));

        sidebar.getChildren().addAll(logo, sidebarIcons, logoutBox);

        VBox centerBox = new VBox(30);
        centerBox.setPadding(new Insets(20 , 50, 10, 50));
        centerBox.setAlignment(Pos.TOP_CENTER);


        List<Reservation> reservations = readBookings();
        if (reservations.isEmpty()) {
            Label noCarsLabel = new Label("No cars are currently booked.");
            noCarsLabel.setFont(new Font(18));
            noCarsLabel.setTextFill(Color.GRAY);
            centerBox.getChildren().add(noCarsLabel);
        } else {
            for (Reservation reservation : reservations) {
                Car car = findCarById(reservation.getCarId());
                if (car != null) {
                    centerBox.getChildren().add(createCarCardBox(car, reservation));
                }
            }
        }


        centerBox.setStyle("-fx-background-color: white;");
        ScrollPane scrollPane = new ScrollPane(centerBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-border-width: 0; -fx-background-color: white;");

        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(scrollPane);


        double width = Screen.getPrimary().getVisualBounds().getWidth();
        double height = Screen.getPrimary().getVisualBounds().getHeight();
        Scene scene = new Scene(mainLayout, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private List<Reservation> readBookings() {
        List<Reservation> reservations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("booking.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int carId = Integer.parseInt(parts[0].trim());
                    String carName = parts[1].trim();
                    LocalDate pickupDate = LocalDate.parse(parts[2].trim());
                    LocalDate dropoffDate = LocalDate.parse(parts[3].trim());
                    reservations.add(new Reservation(carId, carName, pickupDate, dropoffDate));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    private Car findCarById(int carId) {
        for (Car car : carsList.getAvailableCars()) {
            if (car.getId() == carId) {
                return car;
            }
        }
        return null;
    }

    private VBox createCarCardBox(Car car, Reservation reservation) {
        VBox carCardbox = new VBox(10);
        carCardbox.setAlignment(Pos.CENTER);
        carCardbox.setStyle("-fx-background-color: #fff; -fx-border-radius: 10px; -fx-padding: 20; -fx-background-radius: 5; -fx-border-width: 1;");

        VBox carnameBox = new VBox(15);

        Label carNameLabel = new Label(car.getName());
        carNameLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        Label carLocationLabel = new Label(car.getLocation());
        carLocationLabel.setStyle("-fx-font-size: 18px;");
        carnameBox.getChildren().addAll(carNameLabel, carLocationLabel);

        Label carId = new Label("#000K" +car.getId());
        carId.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        HBox carIdBox = new HBox();
        Region spacer = new Region();
        carIdBox.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: gray");
        carIdBox.setPadding(new Insets(0, 0, 15, 0));
        HBox.setHgrow(spacer, Priority.ALWAYS);
        carIdBox.getChildren().addAll(carnameBox,spacer, carId);

        ImageView carImageView = new ImageView("bookedcar.png");
        carImageView.setFitWidth(85);
        carImageView.setFitHeight(85);
        carImageView.setPreserveRatio(true);

        HBox carlogoBox = new HBox();

        HBox carlogoinnerBox = new HBox(60);

        VBox pickup = new VBox(5);
        Label pickupLabel = new Label("Pick-UP Date");
        pickupLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label pickupDate = new Label(reservation.getPickupDate() + "");
        pickupDate.setStyle("-fx-font-size: 16px; -fx-text-fill: gray");
        pickup.getChildren().addAll(pickupLabel, pickupDate);

        VBox dropoff = new VBox(5);
        Label dropoffLabel = new Label("Dropoff Date");
        dropoffLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label dropoffDate = new Label(reservation.getDropoffDate() + "");
        dropoffDate.setStyle("-fx-font-size: 16px; -fx-text-fill: gray");
        dropoff.getChildren().addAll(dropoffLabel, dropoffDate);

        carlogoinnerBox.getChildren().addAll(carImageView, pickup, dropoff);

        VBox bookedbuttonBox = new VBox();
        Button bookedButton = new Button("Booked");
        bookedButton.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-padding: 12 35; -fx-font-size: 15px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-font-weight: bold;");
        bookedbuttonBox.getChildren().add(bookedButton);
        bookedbuttonBox.setPadding(new Insets(10, 0, 0, 0));

        Region spacer3 = new Region();
        HBox.setHgrow(spacer3, Priority.ALWAYS);
        carlogoBox.getChildren().addAll(carlogoinnerBox,spacer3, bookedbuttonBox);
        carlogoBox.setPadding(new Insets(10, 0, 0,0));


        carCardbox.setStyle("-fx-border-width: 1; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #202020");
        carCardbox.setPadding(new Insets(15 ,30, 0,30));

        carCardbox.getChildren().addAll(carIdBox, carlogoBox);
        return carCardbox;
    }
}
