package com.example.prueba.reception;

import com.example.prueba.PanelLoginController;
import com.example.prueba.admin.AdminLoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReceptionDashboardController implements Initializable {

    public BorderPane borderpane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void windowLoad(String URL) {
        try {
            Pane window = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(URL)));
            borderpane.setCenter(window);
        } catch (Exception err) {
            System.out.println("Problem : " + err);
        }
    }

    public void LogOut(ActionEvent actionEvent) throws IOException {
        AdminLoginController.currentAdminUsername = "";
        PanelLoginController.screenController.addScreen("panellogin", FXMLLoader.load(getClass().getResource( "panel-login.fxml" )));
        PanelLoginController.screenController.removeScreen("receptiondashboard");
        PanelLoginController.screenController.activate("panellogin");

        //CommonTask.pageNavigation("com/example/prueba/panel-login.fxml", Main.stage,this.getClass(),"Admin Dashboard", 1000, 600);

    }
    public void closeApplication(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
