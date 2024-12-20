package com.example.semesterrproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends Application {

    private CarsList carsList;
    private Stage primaryStage;
    private Car car;

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
        sidebar.setPrefWidth(200);

        ImageView logo = new ImageView("CarLogo.png");
        logo.setFitHeight(80);
        logo.setFitWidth(100);

        VBox sidebarIcons = new VBox(50);

        ImageView carlistlogo = new ImageView("carlist.png");
        carlistlogo.setFitHeight(40);
        carlistlogo.setFitWidth(40);

        ImageView bookedcars = new ImageView("booked.png");
        bookedcars.setOnMouseClicked(event -> {
            BookedCars bookedCars = new BookedCars(carsList);
            bookedCars.showBookedCars(primaryStage);
        });
        bookedcars.setOnMouseEntered(event -> bookedcars.setCursor(Cursor.HAND));
        bookedcars.setOnMouseExited(event -> bookedcars.setCursor(Cursor.DEFAULT));
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
                new CarDetailView(car).start(new Stage());
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

        carGrid.setAlignment(Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane(carGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-border-width: 0; -fx-background-color: transparent;");

        VBox rightSidebar = new VBox(20);
        rightSidebar.setPadding(new Insets(20));

        ArrayList<String> locationList = new ArrayList<>();
        for (Car car : availableCars){
            if(!locationList.contains(car.getLocation())){
                locationList.add(car.getLocation());
            }
        }

        Label locationLabel = new Label("Filter Cars");
        locationLabel.setStyle("-fx-text-fill: #000; -fx-font-weight: 700;");
        locationLabel.setFont(Font.font("Arial", 16));
        ComboBox<String> locationDropdown = new ComboBox<String>();
        locationDropdown.getItems().addAll(locationList);
        locationDropdown.setValue("Select Location");
        locationDropdown.getItems().add("Select Location");

        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #0078D7; -fx-padding: 12 30 13 32; -fx-background-radius: 5; -fx-border-radius: 5; -fx-font-size: 15px; -fx-text-fill: #fff;");
        clearButton.setVisible(false);


        locationDropdown.setOnAction(e -> {
            String selectedLocation = locationDropdown.getValue();
            carGrid.getChildren().clear();


            for (int i = 0; i < availableCars.size(); i++) {
                Car car = availableCars.get(i);

                if (selectedLocation.equals("Select Location") || car.getLocation().equals(selectedLocation)) {
                    clearButton.setVisible(true);
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
                    viewDetailsButton.setOnAction(viewEvent -> {
                        primaryStage.close();
                        new CarDetailView(car).start(new Stage());
                    });
                    viewDetailsButton.setOnMouseEntered(hoverEvent -> viewDetailsButton.setCursor(Cursor.HAND));
                    viewDetailsButton.setOnMouseExited(hoverEvent -> viewDetailsButton.setCursor(Cursor.DEFAULT));

                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setRadius(20);
                    dropShadow.setOffsetY(5);
                    dropShadow.setOffsetX(5);
                    Color shadowColor = Color.rgb(0, 0, 0, 0.2);
                    dropShadow.setColor(shadowColor);

                    carCardbox.setEffect(dropShadow);

                    carCardbox.getChildren().addAll(carNameLabel, carLocationLabel, carImageView, viewDetailsButton);

                    int column = carGrid.getChildren().size() % 3;
                    int row = carGrid.getChildren().size() / 3;
                    carGrid.add(carCardbox, column, row);
                    carGrid.setAlignment(Pos.TOP_LEFT);
                    carGrid.setPadding(new Insets(30, 0, 0, 55));
                }
            }
        });

        clearButton.setOnAction(event -> {
            locationDropdown.setPromptText("Select Location");
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
                    new CarDetailView(car).start(new Stage());
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
        });

        clearButton.setOnMouseClicked(event -> {
            clearButton.setVisible(false);
        });


        rightSidebar.getChildren().addAll(locationLabel, locationDropdown, clearButton);
        rightSidebar.setPrefWidth(200);
        mainLayout.setRight(rightSidebar);

        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(scrollPane);

        VBox rentedCars = new VBox(10);


        double width = Screen.getPrimary().getVisualBounds().getWidth();
        double height = Screen.getPrimary().getVisualBounds().getHeight();


        Scene scene = new Scene(mainLayout, width, height);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
