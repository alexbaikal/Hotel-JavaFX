package com.example.prueba.reception;

import backend.CommonTask;
import backend.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

import static com.example.prueba.PanelLoginController.screenController;

public class ReceptionAddClientController {
    @FXML
    public TextField nameClienteField;
    @FXML
    public TextField surnameClienteField;
    @FXML
    public TextField dniClienteField;
    @FXML
    public TextField nationalityClienteField;
    @FXML
    public TextField phoneClienteField;
    @FXML
    public TextField emailClienteField;
    @FXML
    public TextField occupationClienteField;
    @FXML
    public TextField civilstateClienteField;

    public void ReceptionAddClient(ActionEvent actionEvent) throws IOException {
        String nameCliente = nameClienteField.getText();
        String surnameCliente = surnameClienteField.getText();
        String dniCliente = dniClienteField.getText();
        String nationalityCliente = nationalityClienteField.getText();
        String phoneCliente = phoneClienteField.getText();
        String emailCliente = emailClienteField.getText();
        String occupationCliente = occupationClienteField.getText();
        String civilstateCliente = civilstateClienteField.getText();

        try {
            Connection connection = DBConnection.getConnections();
            if (nameCliente.isEmpty() || surnameCliente.isEmpty() || dniCliente.isEmpty() || nationalityCliente.isEmpty() || phoneCliente.isEmpty() || emailCliente.isEmpty()) {
                CommonTask.showAlert(Alert.AlertType.WARNING, "Error", "Las entradas de texto no pueden estar vacías!");
            } else {
                String sql = "INSERT INTO clientes (name_cliente, surname_cliente, DNI_cliente, nationality_cliente, phone_cliente, email_cliente, occupation_cliente, civilstate_cliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, nameCliente);
                preparedStatement.setString(2, surnameCliente);
                preparedStatement.setString(3, dniCliente);
                preparedStatement.setString(4, nationalityCliente);
                preparedStatement.setString(5, phoneCliente);
                preparedStatement.setString(6, emailCliente);
                preparedStatement.setString(7, occupationCliente);
                preparedStatement.setString(8, civilstateCliente);
                preparedStatement.executeUpdate();
                CommonTask.showAlert(Alert.AlertType.INFORMATION, "Éxito", "Cliente agregado con éxito!");
                screenController.addScreen("receptiondashboard", FXMLLoader.load(getClass().getResource( "/fxml/reception-dashboard.fxml" )));
                screenController.removeScreen("receptionaddclient");
                screenController.activate("receptiondashboard");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}