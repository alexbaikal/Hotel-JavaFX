package com.example.prueba.reception;

import backend.Utils;
import backend.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.prueba.PanelLoginController.screenController;

public class ReceptionAddClientController implements Initializable {
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
    @FXML
    public Button addButton;

    public static int idCliente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (idCliente != 0) {
            addButton.setText("Actualizar");
            try {
                Connection connection = DBConnection.getConnections();
                String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idCliente);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    nameClienteField.setText(resultSet.getString("name_cliente"));
                    surnameClienteField.setText(resultSet.getString("surname_cliente"));
                    dniClienteField.setText(resultSet.getString("DNI_cliente"));
                    nationalityClienteField.setText(resultSet.getString("nationality_cliente"));
                    phoneClienteField.setText(resultSet.getString("phone_cliente"));
                    emailClienteField.setText(resultSet.getString("email_cliente"));
                    occupationClienteField.setText(resultSet.getString("occupation_cliente"));
                    civilstateClienteField.setText(resultSet.getString("civilstate_cliente"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void ReceptionAddClient() throws IOException {




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
                Utils.showAlert(Alert.AlertType.WARNING, "Error", "Las entradas de texto no pueden estar vacías!");
            } else {
                String sql;
                String alertMsg;
                if (idCliente != 0) {
                    alertMsg = "Cliente actualizado correctamente!";
                    sql = "UPDATE clientes SET name_cliente = ?, surname_cliente = ?, DNI_cliente = ?, nationality_cliente = ?, phone_cliente = ?, email_cliente = ?, occupation_cliente = ?, civilstate_cliente = ? WHERE id_cliente = "+idCliente;
                } else {
                    alertMsg = "Cliente añadido correctamente!";
                    sql = "INSERT INTO clientes (name_cliente, surname_cliente, DNI_cliente, nationality_cliente, phone_cliente, email_cliente, occupation_cliente, civilstate_cliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                }
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

                Utils.showAlert(Alert.AlertType.INFORMATION, "Éxito", alertMsg);
                screenController.addScreen("receptiondashboard", FXMLLoader.load(getClass().getResource( "/fxml/reception-dashboard.fxml" )));
                screenController.removeScreen("receptionaddclient");
                screenController.activate("receptiondashboard");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void GoBack (ActionEvent actionEvent) throws IOException {
        screenController.addScreen("receptiondashboard", FXMLLoader.load(getClass().getResource( "/fxml/reception-dashboard.fxml" )));
        screenController.removeScreen("receptionaddclient");
        screenController.activate("receptiondashboard");
    }
}