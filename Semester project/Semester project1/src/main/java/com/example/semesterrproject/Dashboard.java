package com.example.semesterrproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.List;

public class Dashboard extends Application {

    private CarsList carsList;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        carsList = new CarsList();
        List<Car> availableCars = carsList.getAvailableCars();

        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));
        BorderPane.setMargin(mainLayout, new Insets(0,0,20,0));

        HBox headerbox = new HBox();
        Label header = new Label("Car Rental Management System");
        header.setFont(new Font("Arial", 25));
        header.setStyle("-fx-font-weight: bold; -fx-text-fill: #000; -fx-padding: 10;");
        headerbox.getChildren().add(header);
        headerbox.setAlignment(Pos.CENTER);

        mainLayout.setTop(headerbox);

        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #202020; -fx-pref-width: 125px; -fx-border-radius: 20px; -fx-background-radius: 20px;");

        ImageView logo = new ImageView("CarLogo.png");
        logo.setFitHeight(80);
        logo.setFitWidth(100);

        VBox sidebarIcons = new VBox(50);

        ImageView carlistlogo = new ImageView("carlist.png");
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

        VBox.setMargin(logo, new Insets(0, 0, 110, 0));
        VBox.setMargin(logoutBox, new Insets(130, 0, 0, 0));

        sidebar.getChildren().addAll(logo, sidebarIcons, logoutBox);

        // Create the central grid for cars
        GridPane carGrid = new GridPane();
        carGrid.setHgap(25);
        carGrid.setVgap(25);
        carGrid.setPadding(new Insets(30));


        for (int i = 0; i < availableCars.size(); i++) {
            Car car = availableCars.get(i);

            VBox carCardbox = new VBox(10);
            carCardbox.setAlignment(Pos.CENTER);
            carCardbox.setStyle("-fx-background-color: #fff; -fx-border-radius: 10px; -fx-padding: 20; -fx-background-radius: 5; -fx-border-width: 1;");


            Label carNameLabel = new Label(car.getName());
            carNameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            Label carLocationLabel = new Label(car.getLocation());
            carLocationLabel.setStyle("-fx-font-size: 14px;");

            ImageView carImageView = new ImageView(car.getImageUrl());
            carImageView.setFitWidth(230);
            carImageView.setFitHeight(195);
            carImageView.setPreserveRatio(true);

            Button viewDetailsButton = new Button("View Details");
            viewDetailsButton.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 12px 40px 12px 40px; -fx-font-size: 18px; -fx-font-weight: 900;");
            viewDetailsButton.setOnAction(e -> {
                primaryStage.close();
                new CarDetailView(car).start(new Stage());  // Pass the selected car to the details view
            });
            viewDetailsButton.setOnMouseEntered(e -> viewDetailsButton.setCursor(Cursor.HAND));
            viewDetailsButton.setOnMouseExited(e -> viewDetailsButton.setCursor(Cursor.DEFAULT));

           DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(20);
            dropShadow.setOffsetY(5);
            dropShadow.setOffsetX(5);
            Color shadowColor = Color.rgb(0,0,0,0.2);
            dropShadow.setColor(shadowColor);

            carCardbox.setEffect(dropShadow);

            carCardbox.getChildren().addAll(carNameLabel, carLocationLabel, carImageView, viewDetailsButton);

            int column = i % 3;
            int row = i / 3;
            carGrid.add(carCardbox, column, row);
        }

        ScrollPane scrollPane = new ScrollPane(carGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-border-width: 0; -fx-background-color: transparent;");

        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(scrollPane);

        double width = Screen.getPrimary().getVisualBounds().getWidth();
        double height = Screen.getPrimary().getVisualBounds().getHeight();

        Scene scene = new Scene(mainLayout, width, height);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}