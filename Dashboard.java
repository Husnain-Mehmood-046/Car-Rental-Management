import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(10));
        sidebar.setStyle("-fx-background-color: #202020; -fx-pref-width: 100px;");

        Button carlist = new Button("Cars List");
        carlist.setStyle("-fx-background-color: #000; -fx-text-fill: white;");
        sidebar.getChildren().add(carlist);

        ListView<String> carListView = new ListView<>();
        for (Car car : availableCars) {
            carListView.getItems().add(car.getName() + " - Rs. " + car.getPricePerDay() + "/day");
        }

        VBox detailsLayout = new VBox(20);
        detailsLayout.setPadding(new Insets(20));
        detailsLayout.setAlignment(Pos.CENTER);

        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setStyle("-fx-background-color: #000; -fx-text-fill: white;");

        carListView.getSelectionModel().selectedItemProperty().addListener((newValue) -> {
            if (newValue != null) {
                int selectedIndex = carListView.getSelectionModel().getSelectedIndex();
                Car selectedCar = availableCars.get(selectedIndex);
                viewDetailsButton.setOnAction(e -> {
                    primaryStage.close();
                    new CarDetailView(selectedCar).start(new Stage());
                });
            }
        });

        detailsLayout.getChildren().addAll(carListView, viewDetailsButton);

        HBox footer = new HBox(20);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10));
        footer.setStyle("-fx-background-color: #f4f4f4;");

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #000; -fx-text-fill: white;");
        exitButton.setOnAction(e -> primaryStage.close());
        footer.getChildren().add(exitButton);

        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(detailsLayout);
        mainLayout.setBottom(footer);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Car Rental System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}