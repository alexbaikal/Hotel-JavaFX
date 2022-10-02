package backend;

import javafx.scene.control.Alert;
import com.example.prueba.Main;

public class Utils extends Main {

    public static void showAlert(Alert.AlertType type, String title, String header){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        // alert.setContentText(message);
        alert.show();
    }

}
