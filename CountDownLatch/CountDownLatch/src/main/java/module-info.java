module com.example.countdownlatch {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.countdownlatch to javafx.fxml;
    exports com.example.countdownlatch;
}