package com.example.semesterrproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
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
        BorderPane Layout = new BorderPane();

        VBox innerLayout = new VBox(20);

        VBox headerBox = new VBox();
        Label headerLabel = new Label("Car Rental Management System");
        headerBox.setPadding(new Insets(20, 10, 20, 10));
        headerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 40px; -fx-text-fill: #000;");
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().add(headerLabel);

        HBox upperLayout = new HBox(250);
        VBox userDetails = new VBox(10);


        Label title = new Label("Hey! Muneeb,");
        title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");
        Label subtitle = new Label("Your order is waiting for approval.");
        subtitle.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");
        Label instruction = new Label("You will receive a confirmation email soon\n with your booking details.");
        instruction.setStyle("-fx-font-size: 18px; -fx-text-fill: #000;");
        userDetails.getChildren().addAll(title, subtitle, instruction);


        ImageView pendingIcon = new ImageView(new Image("pendingapproval.png"));
        pendingIcon.setFitWidth(200);
        pendingIcon.setPreserveRatio(true);

        upperLayout.getChildren().addAll(userDetails, pendingIcon);
        HBox.setMargin(userDetails, new Insets(0, 0, 50, 0));


        FlowPane iconBoxes = new FlowPane();
        iconBoxes.setHgap(100);
        iconBoxes.setVgap(40);

        HBox upperIcons = new HBox(145);
        upperIcons.getChildren().add(createDetailRow("Car Name", car.getName(), "car.png"));
        upperIcons.getChildren().add(createDetailRow("Location", car.getLocation(), "location.png"));
        upperIcons.getChildren().add(createDetailRow("Price" , car.getPricePerDay() + " per day", "price.png"));

        HBox dates = new HBox(150);
        dates.getChildren().add(createDetailRow("Pick-Up Date" , pickupDate + "", "clock.png"));
        dates.getChildren().add(createDetailRow("Drop-OFF Date" , dropoffDate + "", "clock.png"));

        iconBoxes.getChildren().addAll(upperIcons, dates);


        HBox buttonBox = new HBox();
        Button goToDashboard = new Button("Go to Dashboard");
        goToDashboard.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-padding: 15 40; -fx-font-size: 15px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        buttonBox.getChildren().addAll(goToDashboard);
        goToDashboard.setOnMouseEntered(e -> goToDashboard.setCursor(Cursor.HAND));
        goToDashboard.setOnMouseExited(e -> goToDashboard.setCursor(Cursor.DEFAULT));
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);

        goToDashboard.setOnAction(e -> {
            primaryStage.close();
            new Dashboard().start(new Stage());
        });


        innerLayout.getChildren().addAll(upperLayout, iconBoxes, buttonBox);
        innerLayout.setPadding(new Insets(75, 100, 50, 100));
        Layout.setTop(headerBox);
        Layout.setCenter(innerLayout);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        Scene scene = new Scene(Layout, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Booking Confirmation");
        primaryStage.show();
    }

    private HBox createDetailRow(String label, String value, String iconPath) {
        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);


        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitWidth(80);
        icon.setPreserveRatio(true);


        VBox textContainer = new VBox(5);
        Label labelText = new Label(label);
        labelText.setStyle("-fx-font-size: 16px; -fx-text-fill: gray;");
        Label valueText = new Label(value);
        valueText.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        textContainer.getChildren().addAll(labelText, valueText);
        row.getChildren().addAll(icon, textContainer);

        return row;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
