package com.example.prueba.reception;

import backend.Utils;
import backend.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import static com.example.prueba.Main.stage;
import static com.example.prueba.PanelLoginController.screenController;

public class ReceptionRegisterController {
    @FXML
    public TextField usernameReceptionField;
    @FXML
    public PasswordField passwordReceptionField;
    @FXML
    public TextField nameReceptionField;
    @FXML
    public TextField surnameReceptionField;
    @FXML
    public TextField phoneReceptionField;
    @FXML
    public TextField emailReceptionField;
    @FXML
    public TextField dniReceptionField;
    @FXML
    public TextField nationalityReceptionField;

    public static String currentReceptionUsername = "";

    public void RegisterReception() throws IOException {
        String receptionUsername = usernameReceptionField.getText();
        currentReceptionUsername = receptionUsername;
        String receptionPassword = passwordReceptionField.getText();
        String nameReception = nameReceptionField.getText();
        String surnameReception = surnameReceptionField.getText();
        String phoneReception = phoneReceptionField.getText();
        String emailReception = emailReceptionField.getText();
        String dniReception = dniReceptionField.getText();
        String nationalityReception = nationalityReceptionField.getText();

        try {
            Connection connection = DBConnection.getConnections();
            if (receptionUsername.isEmpty() || receptionPassword.isEmpty() || Objects.equals(currentReceptionUsername, "") || nameReception.isEmpty() || surnameReception.isEmpty() || phoneReception.isEmpty() || emailReception.isEmpty() || dniReception.isEmpty() || nationalityReception.isEmpty()) {
                Utils.showAlert(Alert.AlertType.WARNING, "Error", "Las entradas de texto no pueden estar vacías!");
            } else if (receptionPassword.length() <6) {
                Utils.showAlert(Alert.AlertType.WARNING, "Error", "Contraseña menor de 6 caracteres!");
            } else {
                String sql = "INSERT INTO `recepcionista`(" +
                        "name_recepcionista," +
                        "surname_recepcionista," +
                        "DNI_recepcionista," +
                        "nationality_recepcionista," +
                        "phone_recepcionista," +
                        "email_recepcionista," +
                        "username," +
                        "password," +
                        "active_recepcionista) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, nameReception);
                preparedStatement.setString(2, surnameReception);
                preparedStatement.setString(3, dniReception);
                preparedStatement.setString(4, nationalityReception);
                preparedStatement.setString(5, phoneReception);
                preparedStatement.setString(6, emailReception);
                preparedStatement.setString(7, receptionUsername);
                preparedStatement.setString(8, receptionPassword);
                preparedStatement.setInt(9, 0);
                try {
                    preparedStatement.executeUpdate();
                    Utils.showAlert(Alert.AlertType.INFORMATION, "Dado de alta correctamente! ➕", "Espera de alta recepcionista");
                    screenController.removeScreen("receptionregister");
                    stage.setWidth(400);
                    stage.setHeight(450);
                    screenController.addScreen("receptionlogin", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/reception-login.fxml"))));
                    screenController.activate("receptionlogin");
                } catch (SQLException e) {
                    e.printStackTrace();
                    Utils.showAlert(Alert.AlertType.ERROR, "Fallo de alta!", "Hubo un error en la base de datos!");
                }




            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}