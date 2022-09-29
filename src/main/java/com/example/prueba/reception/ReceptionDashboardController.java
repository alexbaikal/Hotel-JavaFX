package com.example.prueba.reception;

import backend.CommonTask;
import backend.DBConnection;
import com.example.prueba.Main;
import com.example.prueba.PanelLoginController;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.prueba.reception.ReceptionLoginController.currentReceptionUsername;

public class ReceptionDashboardController implements Initializable {

    public Label welcomeTxt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.stage.setTitle("Reception Dashboard");

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
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void LogOut() {
        currentReceptionUsername = "";
        Main.stage.setTitle("Panel Login");
        PanelLoginController.screenController.removeScreen("receptiondashboard");
        PanelLoginController.screenController.removeScreen("receptionlogin");
        PanelLoginController.screenController.activate("panellogin");
    }
    public void closeApplication(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
