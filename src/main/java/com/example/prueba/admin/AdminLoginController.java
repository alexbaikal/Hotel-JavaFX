package com.example.prueba.admin;

import backend.Utils;
import com.example.prueba.PanelLoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import backend.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static com.example.prueba.Main.stage;
import static com.example.prueba.PanelLoginController.screenController;

public class AdminLoginController {
    public TextField adminUsernameField;
    public PasswordField adminPasswordField;

    public static String currentAdminUsername;

    public void LoginAdminHome(ActionEvent actionEvent) throws IOException {
        String adminUsername = adminUsernameField.getText();
        currentAdminUsername = adminUsername;
        String adminPassword = adminPasswordField.getText();
        try {
            Connection connection = DBConnection.getConnections();
            if (adminUsername.isEmpty() || adminPassword.isEmpty() || Objects.equals(currentAdminUsername, "")) {
                Utils.showAlert(Alert.AlertType.WARNING, "Error", "Las entradas de texto no pueden estar vacías!");
            } else {
                String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, adminUsername);
                preparedStatement.setString(2, adminPassword);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    Utils.showAlert(Alert.AlertType.INFORMATION, "Inicio de sesión.", "Iniciado sesión correctamente!");
                    stage.setWidth(850);
                    stage.setHeight(400);
                    PanelLoginController.screenController.removeScreen("adminlogin");
                    PanelLoginController.screenController.activate("admindashboard");

                } else {
                    Utils.showAlert(Alert.AlertType.ERROR, "Fallo en el inicio de sesión.", "Usuario o contraseña incorrectos!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void GoBack() throws IOException {
        screenController.removeScreen("adminlogin");
        stage.setWidth(500);
        stage.setHeight(400);
        screenController.addScreen("panellogin", FXMLLoader.load(getClass().getResource( "/fxml/panel-login.fxml" )));
        screenController.activate("panellogin");
    }
}