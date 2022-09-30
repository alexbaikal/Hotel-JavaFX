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

import static com.example.prueba.Main.stage;
import static com.example.prueba.PanelLoginController.screenController;

public class ReceptionLoginController {
    public TextField receptionUsernameField;
    public PasswordField receptionPasswordField;

    public static String currentReceptionUsername = "";

    public void LoginReceptionHome(ActionEvent actionEvent) throws IOException {
        String receptionUsername = receptionUsernameField.getText();
        currentReceptionUsername = receptionUsername;
        String receptionPassword = receptionPasswordField.getText();
        try {
            Connection connection = DBConnection.getConnections();
            if (receptionUsername.isEmpty() || receptionPassword.isEmpty() || Objects.equals(currentReceptionUsername, "")) {
                CommonTask.showAlert(Alert.AlertType.WARNING, "Error", "Las entradas de texto no pueden estar vacías!");
            } else {
                String sql = "SELECT * FROM recepcionista WHERE username = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, receptionUsername);
                preparedStatement.setString(2, receptionPassword);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    CommonTask.showAlert(Alert.AlertType.INFORMATION, "Iniciado correctamente!", "Sesión iniciada correctamente");
                    screenController.removeScreen("receptionlogin");
                    stage.setWidth(1000);
                    stage.setHeight(500);
                    screenController.addScreen("receptiondashboard", FXMLLoader.load(getClass().getResource( "/fxml/reception-dashboard.fxml" )));
                    screenController.activate("receptiondashboard");


                } else {
                    CommonTask.showAlert(Alert.AlertType.ERROR, "Login Failed!", "Usuario o contraseña incorrectos!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void RegisterReception() throws IOException {
        screenController.removeScreen("receptionlogin");
        screenController.addScreen("receptionregister", FXMLLoader.load(getClass().getResource( "/fxml/reception-register.fxml" )));
        screenController.activate("receptionregister");
    }
}