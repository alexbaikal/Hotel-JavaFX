package backend;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.example.prueba.Main;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class CommonTask extends Main {

    public static void showAlert(Alert.AlertType type, String title, String header){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        // alert.setContentText(message);
        alert.show();
    }

}
