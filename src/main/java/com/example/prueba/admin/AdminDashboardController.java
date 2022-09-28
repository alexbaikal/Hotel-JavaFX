package com.example.prueba.admin;

import com.example.prueba.PanelLoginController;
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

public class AdminDashboardController implements Initializable {

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
        PanelLoginController.screenController.removeScreen("admindashboard");
        PanelLoginController.screenController.removeScreen("adminlogin");
        PanelLoginController.screenController.activate("panellogin");

        //CommonTask.pageNavigation("com/example/prueba/panel-login.fxml", Main.stage,this.getClass(),"Admin Dashboard", 1000, 600);

    }
    public void closeApplication(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
