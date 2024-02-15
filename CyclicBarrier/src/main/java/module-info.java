module com.example.cyclicbarrier {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cyclicbarrier to javafx.fxml;
    exports com.example.cyclicbarrier;
}