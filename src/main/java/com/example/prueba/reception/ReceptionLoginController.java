package com.example.prueba.reception;

import backend.Utils;
import backend.DBConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static com.example.prueba.Main.stage;
import static com.example.prueba.PanelLoginController.screenController;



public class ReceptionLoginController {
    public static Integer idRecepcionista;

    public TextField receptionUsernameField;
    public PasswordField receptionPasswordField;

    public static String currentReceptionUsername = "";

    public void LoginReceptionHome() throws IOException {
        String receptionUsername = receptionUsernameField.getText();
        currentReceptionUsername = receptionUsername;
        String receptionPassword = receptionPasswordField.getText();
        try {
            Connection connection = DBConnection.getConnections();
            if (receptionUsername.isEmpty() || receptionPassword.isEmpty() || Objects.equals(currentReceptionUsername, "")) {
                Utils.showAlert(Alert.AlertType.WARNING, "Error", "Las entradas de texto no pueden estar vacías!");
            } else {
                String sql = "SELECT * FROM recepcionista WHERE username = ? AND password = ? AND active_recepcionista = 1";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, receptionUsername);
                preparedStatement.setString(2, receptionPassword);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    idRecepcionista = resultSet.getInt("id_recepcionista");
                    screenController.removeScreen("receptionlogin");
                    stage.setWidth(1000);
                    stage.setHeight(500);
                    screenController.addScreen("receptiondashboard", FXMLLoader.load(getClass().getResource( "/fxml/reception-dashboard.fxml" )));
                    screenController.activate("receptiondashboard");


                } else {
                    Utils.showAlert(Alert.AlertType.ERROR, "Login Failed!", "Usuario o contraseña incorrectos!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void RegisterReception() throws IOException {
        screenController.removeScreen("receptionlogin");
        stage.setWidth(1000);
        stage.setHeight(500);
        stage.setTitle("➕Alta de recepcionista");
        screenController.addScreen("receptionregister", FXMLLoader.load(getClass().getResource( "/fxml/reception-register.fxml" )));
        screenController.activate("receptionregister");
    }

    public void GoBack() throws IOException {
        screenController.removeScreen("receptionlogin");
        stage.setWidth(400);
        stage.setHeight(400);
        screenController.addScreen("panellogin", FXMLLoader.load(getClass().getResource( "/fxml/panel-login.fxml" )));
        screenController.activate("panellogin");
    }

}