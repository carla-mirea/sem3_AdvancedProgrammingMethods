module com.example.procedures {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.procedures to javafx.fxml;
    exports com.example.procedures;
}