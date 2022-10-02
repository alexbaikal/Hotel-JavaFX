package com.example.prueba;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.prueba.Main.scene;
import static com.example.prueba.Main.stage;

public class PanelLoginController implements Initializable {

    public static ScreenController screenController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void AdminLogin() throws IOException {
        screenController = new ScreenController(scene);
        screenController.addScreen("adminlogin", FXMLLoader.load(getClass().getResource( "admin-login.fxml" )));
        screenController.addScreen("admindashboard", FXMLLoader.load(getClass().getResource("admin-dashboard.fxml")));
        screenController.addScreen("panellogin", FXMLLoader.load(getClass().getResource( "/fxml/panel-login.fxml" )));
        screenController.activate("adminlogin");
    }

    public void ReceptionLogin(ActionEvent actionEvent) throws IOException {
        screenController = new ScreenController(scene);
        screenController.addScreen("receptionlogin", FXMLLoader.load(getClass().getResource( "/fxml/reception-login.fxml" )));
        screenController.addScreen("panellogin", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/panel-login.fxml"))));
        stage.setWidth(400);
        stage.setHeight(450);
        stage.setTitle("Login recepción");
        screenController.activate("receptionlogin");

    }
}
