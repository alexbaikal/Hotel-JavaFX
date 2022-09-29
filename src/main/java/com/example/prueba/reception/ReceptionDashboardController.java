package com.example.prueba.reception;

import com.example.prueba.PanelLoginController;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionDashboardController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void LogOut() {
        ReceptionLoginController.currentReceptionUsername = "";
        PanelLoginController.screenController.removeScreen("receptiondashboard");
        PanelLoginController.screenController.removeScreen("receptionlogin");
        PanelLoginController.screenController.activate("panellogin");
    }
    public void closeApplication(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
