module com.example.semesterrproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.semesterrproject to javafx.fxml;
    exports com.example.semesterrproject;
}