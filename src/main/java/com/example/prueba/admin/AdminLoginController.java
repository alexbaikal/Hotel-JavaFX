package com.example.prueba.admin;

import backend.Utils;
import com.example.prueba.PanelLoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class AdminLoginController {
    public TextField adminUsernameField;
    public PasswordField adminPasswordField;

    public static String currentAdminUsername;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void LoginAdminHome(ActionEvent actionEvent) throws IOException {
        String adminUsername = adminUsernameField.getText();
        currentAdminUsername = adminUsername;
        String adminPassword = adminPasswordField.getText();
        System.out.println("3");
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
                    Utils.showAlert(Alert.AlertType.INFORMATION, "Login Success!", "Successfully Logged In!");
                    PanelLoginController.screenController.removeScreen("adminlogin");
                    PanelLoginController.screenController.activate("admindashboard");

                } else {
                    Utils.showAlert(Alert.AlertType.ERROR, "Login Failed!", "Incorrect NID or Password!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}