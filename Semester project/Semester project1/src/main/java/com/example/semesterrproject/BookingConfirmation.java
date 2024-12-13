package com.example.semesterrproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.time.LocalDate;

public class BookingConfirmation extends Application {

    private Stage primaryStage;

    private Car car;
    private LocalDate pickupDate;
    private LocalDate dropoffDate;

    public BookingConfirmation(Car car, LocalDate pickupDate, LocalDate dropoffDate) {
        this.car = car;
        this.pickupDate = pickupDate;
        this.dropoffDate = dropoffDate;
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Hey! User,");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Label subtitle = new Label("Your order is waiting for approval.");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: gray;");
        Label instruction = new Label("You will receive a confirmation email soon with your booking details.");
        instruction.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");


        ImageView pendingIcon = new ImageView(new Image("pendingapproval.png")); // Replace with actual image path
        pendingIcon.setFitWidth(100);
        pendingIcon.setPreserveRatio(true);


        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(20);
        detailsGrid.setVgap(30);
        detailsGrid.setPadding(new Insets(10));
        detailsGrid.setAlignment(Pos.CENTER_LEFT);

        detailsGrid.addRow(0, createDetailRow("Car Information", car.getName(), "car.png"));
        detailsGrid.addRow(1, createDetailRow("Location", car.getLocation(), "location.png"));
        detailsGrid.addRow(2, createDetailRow("Price", "Rs. " + car.getPricePerDay() + " per-Day", "price.png"));
        Label dateLabel = new Label("Pickup Date: " + pickupDate + "\nDrop-Off Date: " + dropoffDate);
        dateLabel.setStyle("-fx-font-size: 14px;");


        Button goToDashboard = new Button("Go to Dashboard");
        goToDashboard.setStyle("-fx-background-color: #000; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-size: 14px;");

        goToDashboard.setOnAction(e -> {
            primaryStage.close();
            new Dashboard().start(new Stage());
        });

        mainLayout.getChildren().addAll(title, subtitle, instruction, pendingIcon, detailsGrid, dateLabel, goToDashboard);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        Scene scene = new Scene(mainLayout, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Booking Confirmation");
        primaryStage.show();
    }

    private HBox createDetailRow(String label, String value, String iconPath) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);


        ImageView icon = new ImageView(new Image(iconPath)); // Replace with actual icon path
        icon.setFitWidth(50);
        icon.setPreserveRatio(true);


        VBox textContainer = new VBox(5);
        Label labelText = new Label(label);
        labelText.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
        Label valueText = new Label(value);
        valueText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        textContainer.getChildren().addAll(labelText, valueText);
        row.getChildren().addAll(icon, textContainer);

        return row;
    }

    public static void main(String[] args) {
        launch(args);
    }
}