package com.example.prueba.reception;

import backend.CommonTask;
import backend.DBConnection;
import com.example.prueba.Main;
import com.example.prueba.PanelLoginController;
import com.example.prueba.models.ClientModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.prueba.Main.stage;
import static com.example.prueba.reception.ReceptionLoginController.currentReceptionUsername;

public class ReceptionDashboardController implements Initializable {

    public Label welcomeTxt;
    @FXML
    private TableView<String> clientTable;
    @FXML
    private TableColumn<String, String> nameCol;
    @FXML
    private TableColumn<String, String> surnameCol;
    @FXML
    private TableColumn<String, String> dniCol;
    @FXML
    private TableColumn<String, String> nationalityCol;
    @FXML
    private TableColumn<String, String> phoneCol;
    @FXML
    private TableColumn<String, String> emailCol;
    @FXML
    private TableColumn<String, String> occupationCol;
    @FXML
    private TableColumn<String, String> civilStateCol;

    //client list

    public ArrayList<ClientModel> clientList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage.setTitle("Reception Dashboard");

        try {
            Connection connection = DBConnection.getConnections();
            if (!currentReceptionUsername.isEmpty()) {
                String sql = "SELECT * FROM recepcionista WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, currentReceptionUsername);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    //system out all the columns of resultSet
                    CommonTask.showAlert(Alert.AlertType.INFORMATION, "Bienvenido", resultSet.getString(2));
                    welcomeTxt.setText("Bienvenido " + resultSet.getString(2));

                } else {
                    CommonTask.showAlert(Alert.AlertType.ERROR, "Login Failed!", "Incorrect NID or Password!");
                    PanelLoginController.screenController.removeScreen("receptionlogin");
                    PanelLoginController.screenController.activate("receptionlogin");
                }
            }

            String sql = "SELECT * FROM clientes";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clientList.add(new ClientModel(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9)));
            }
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        nameCol.setCellValueFactory(c -> new SimpleStringProperty("123"));



    }

    public void LogOut() {
        currentReceptionUsername = "";
        stage.setTitle("Panel Login");
        PanelLoginController.screenController.removeScreen("receptiondashboard");
        PanelLoginController.screenController.removeScreen("receptionlogin");
        PanelLoginController.screenController.activate("panellogin");
    }
    public void closeApplication(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
