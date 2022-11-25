package backend;

import javafx.scene.control.Alert;
import com.example.prueba.Main;

import java.sql.Date;

public class Utils extends Main {

    public static void showAlert(Alert.AlertType type, String title, String header){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        // alert.setContentText(message);
        alert.show();
    }
    //check if current date is between start date and end date boolean
    public static boolean isBetween(Date startDate, Date endDate) {
        Date currentDate = new Date(System.currentTimeMillis());
        return currentDate.after(startDate) && currentDate.before(endDate);
    }

    public static boolean isBefore(Date startDate, Date endDate) {
        Date currentDate = new Date(System.currentTimeMillis());
        return currentDate.before(startDate) && currentDate.before(endDate);
    }
}
