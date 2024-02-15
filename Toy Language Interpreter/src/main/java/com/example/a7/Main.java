package com.example.a7;

import Model.Exceptions.MyException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, MyException {
        FXMLLoader mainLoader = new FXMLLoader(Main.class.getResource("ProgramListLayout.fxml"));
        Parent mainWindow = mainLoader.load();

        ProgramListController controller = mainLoader.getController();
        controller.setListView();

        stage.setTitle("Select program");
        stage.setScene(new Scene(mainWindow));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}