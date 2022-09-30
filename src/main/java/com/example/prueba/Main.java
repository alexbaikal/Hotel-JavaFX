package com.example.prueba;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    public static Stage stage;
    public static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("panel-login.fxml"));
        scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setTitle("Seleccionar tipo de usuario");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {

        launch();
    }
}