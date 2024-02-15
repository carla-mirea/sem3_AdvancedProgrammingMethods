module com.example.countsemaphore {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.countsemaphore to javafx.fxml;
    exports com.example.countsemaphore;
}