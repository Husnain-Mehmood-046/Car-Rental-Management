package com.example.semesterrproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;


public class CarDetailView extends Application {

    private Stage primaryStage;
    private Car car;
    Label carstatusLabel;
    private CarsList list;

    public CarDetailView(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "CarDetailView{" +
                "car=" + car +
                '}';
    }

    @Override
    public void start(Stage primaryStage) {
        list = new CarsList();
        this.primaryStage = primaryStage;
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: white;");

        // Sidebar that contains the icons

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
        bookedcars.setOnMouseClicked(event -> {
            BookedCars bookedCars = new BookedCars(list);
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
        VBox.setMargin(sidebar, new Insets(0, 0, 30, 0));

        sidebar.getChildren().addAll(logo, sidebarIcons, logoutBox);

        // Here comes the main content area where car image and details displaying.

        VBox content = new VBox(20);
        content.setPadding(new Insets(50));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #fff;");


        ImageView carImage = new ImageView(new Image(car.getImageUrl()));
        carImage.setFitWidth(360);
        carImage.setPreserveRatio(true);

        HBox carTitle = new HBox(10);
        carTitle.setPadding(new Insets(0, 45, 0, 45));

        Label carName = new Label(car.getName());
        carName.setStyle("-fx-font-size: 45px; -fx-font-weight: bold;");
        Label carPrice = new Label("Rs. " + car.getPricePerDay() + " P/D");
        carPrice.setStyle("-fx-font-size: 45px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        carTitle.getChildren().addAll(carName,spacer, carPrice);

        HBox specs = new HBox(40);
        specs.setAlignment(Pos.CENTER);

        VBox seatsInfo = createSpecBox("persons.png","Seats", car.getSeats() + " Persons" );
        VBox fuelInfo = createSpecBox("fuel.png","Fuel", car.getIsWithFuel() ? "With Fuel" : "Without Fuel" );
        VBox stateInfo = createSpecBox("car-location.png" ,"Location", car.getLocation());
        specs.getChildren().addAll(seatsInfo, fuelInfo, stateInfo);

        //This area is on the top means in header and the pickup and dropoff options are available here.

        Label selectLocation = new Label("Select Date : ");
        selectLocation.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #000;");

        DatePicker pickupDatePicker = new DatePicker();
        pickupDatePicker.setPromptText("Select Pickup Date");
        pickupDatePicker.setStyle("-fx-border-width: 2; -fx-border-color: #0078D7; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        DatePicker dropOffDatePicker = new DatePicker();
        dropOffDatePicker.setPromptText("Select Drop-Off Date");
        dropOffDatePicker.setStyle("-fx-border-width: 2; -fx-border-color: #0078D7; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        HBox mainDateSelectionBOX = new HBox(10);
        HBox dateSelectionBOX = new HBox(10);
        dateSelectionBOX.setAlignment(Pos.CENTER);
        dateSelectionBOX.getChildren().addAll(selectLocation ,pickupDatePicker, dropOffDatePicker);


        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        Button bookNow = new Button("BOOK NOW");
        HBox.setHgrow(bookNow, Priority.ALWAYS);


        bookNow.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 22 305 22 305; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        Button backButton = new Button("Cancel");
        backButton.setStyle("-fx-font-size: 20px; -fx-background-color: white; -fx-text-fill: #0078D7; -fx-border-radius: 10px; -fx-padding: 22 90 22 90; -fx-border-color: #0078D7; -fx-background-radius: 10px;");

        bookNow.setOnMouseEntered(e -> bookNow.setCursor(Cursor.HAND));
        bookNow.setOnMouseExited(e -> bookNow.setCursor(Cursor.DEFAULT));

        backButton.setOnMouseEntered(e -> backButton.setCursor(Cursor.HAND));
        backButton.setOnMouseExited(e -> backButton.setCursor(Cursor.DEFAULT));

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

        content.getChildren().addAll(carImage, carTitle, specs, buttons);

        mainLayout.setTop(dateSelectionBOX);
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

    private VBox createSpecBox(String ImageURL, String title, String detail) {
        VBox box = new VBox(12);
        box.setPadding(new Insets(20, 95, 20, 95));
        box.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: gray;");
        Label detailLabel = new Label(detail);
        detailLabel.setStyle("-fx-font-size: 21px; -fx-font-weight: bold;");
        ImageView iconImage = new ImageView(ImageURL);
        iconImage.setFitWidth(50);
        iconImage.setFitHeight(50);

        box.setStyle("-fx-border-color: #0078D7; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        box.getChildren().addAll(iconImage, titleLabel, detailLabel);
        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
