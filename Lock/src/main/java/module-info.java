module com.example.lock {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lock to javafx.fxml;
    exports com.example.lock;
}