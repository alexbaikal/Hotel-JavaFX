package com.example.prueba.reception;

import backend.CommonTask;
import backend.DBConnection;
import com.example.prueba.PanelLoginController;
import javafx.event.ActionEvent;
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

public class ReceptionLoginController {
    public TextField receptionUsernameField;
    public PasswordField receptionPasswordField;

    public static String currentReceptionUsername;

    public void LoginReceptionHome(ActionEvent actionEvent) throws IOException {
        String receptionUsername = receptionUsernameField.getText();
        currentReceptionUsername = receptionUsername;
        String adminPassword = receptionPasswordField.getText();
        System.out.println("3");
        try {
            Connection connection = DBConnection.getConnections();
            if (receptionUsername.isEmpty() || adminPassword.isEmpty() || Objects.equals(currentReceptionUsername, "")) {
                CommonTask.showAlert(Alert.AlertType.WARNING, "Error", "Las entradas de texto no pueden estar vacías!");
            } else {
                String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, receptionUsername);
                preparedStatement.setString(2, adminPassword);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    CommonTask.showAlert(Alert.AlertType.INFORMATION, "Iniciado correctamente!", "Sesión iniciada correctamente");
                    PanelLoginController.screenController.addScreen("receptiondashboard", FXMLLoader.load(getClass().getResource( "reception-dashboard.fxml" )));
                    PanelLoginController.screenController.removeScreen("receptionlogin");
                    PanelLoginController.screenController.activate("receptiondashboard");

                } else {
                    CommonTask.showAlert(Alert.AlertType.ERROR, "Login Failed!", "Incorrect NID or Password!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}