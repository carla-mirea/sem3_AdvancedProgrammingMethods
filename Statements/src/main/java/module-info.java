module com.example.statements {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.statements to javafx.fxml;
    exports com.example.statements;
}