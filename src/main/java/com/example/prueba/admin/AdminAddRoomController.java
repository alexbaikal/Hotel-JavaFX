package com.example.prueba.admin;

import backend.DBConnection;
import backend.Utils;
import com.example.prueba.PanelLoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.prueba.Main.stage;
import static com.example.prueba.PanelLoginController.screenController;

public class AdminAddRoomController implements Initializable {
    @FXML
    public TextField numRoomField;
    @FXML
    public TextField floorField;
    @FXML
    public ComboBox<String> typeDropdown;
    @FXML
    public TextField priceField;
    @FXML
    public TextField characteristicsField;
    @FXML
    public ComboBox<String> availabilityDropdown;




    public static int idHabitacion;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (idHabitacion != 0) {
            try {
                Connection connection = DBConnection.getConnections();
                String sql = "SELECT * FROM habitacion WHERE id_habitacion = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idHabitacion);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    numRoomField.setText(resultSet.getString("num_habitacion"));
                    floorField.setText(resultSet.getString("planta"));
                    availabilityDropdown.getSelectionModel().select(resultSet.getString("disponibilidad"));
                    typeDropdown.getSelectionModel().select(resultSet.getString("tipo"));
                    priceField.setText(resultSet.getString("precio"));
                    characteristicsField.setText(resultSet.getString("caracteristicas"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        //make numRoomField only numbers
        numRoomField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numRoomField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        //make floorField only numbers
        floorField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                floorField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        //make priceField only numbers
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                priceField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        availabilityDropdown.getItems().addAll("Disponible", "Ocupada", "En manteniment");
        typeDropdown.getItems().addAll("Individual", "Doble", "Familiar");
    }

    public void AddRoom() {
        String numRoomFieldText = numRoomField.getText();
        String floorFieldText = floorField.getText();
        String availabilityDropdownText = availabilityDropdown.getValue();
        String typeFieldText = typeDropdown.getValue();
        String priceFieldText = priceField.getText();
        String characteristicsFieldText = characteristicsField.getText();

        String error = "";

        if (numRoomFieldText.isEmpty()) {
            error += "El campo de número de habitación no puede estar vacío";
        } else if (floorFieldText.isEmpty()) {
            error += "El campo de piso no puede estar vacío";
        } else if (priceFieldText.isEmpty()) {
            error += "El campo de precio no puede estar vacío";
        }
        //check if numRoom is a number
        else if (!numRoomFieldText.matches("[0-9]+")) {
            error += "El campo de número de habitación debe ser un número";
        }
        //check if floor is a number
        else if (!floorFieldText.matches("[0-9]+")) {
            error += "El campo de piso debe ser un número";
        }
        //check if numRoomFieldText already exists in the database
        else if (idHabitacion == 0 && numRoomExists(numRoomFieldText)) {
            error += "El número de habitación ya existe";
        }


        try {
            Connection connection = DBConnection.getConnections();
            if (!error.equals("")) {
                Utils.showAlert(Alert.AlertType.WARNING, "Error", error);
            } else {
                //update room in database
                if (idHabitacion != 0) {
                    String sql = "UPDATE habitacion SET num_habitacion = ?, planta = ?, disponibilidad = ?, tipo = ?, precio = ?, caracteristicas = ? WHERE id_habitacion = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, numRoomFieldText);
                    preparedStatement.setString(2, floorFieldText);
                    preparedStatement.setString(3, availabilityDropdownText);
                    preparedStatement.setString(4, typeFieldText);
                    preparedStatement.setString(5, priceFieldText);
                    preparedStatement.setString(6, characteristicsFieldText);
                    preparedStatement.setInt(7, idHabitacion);
                    preparedStatement.executeUpdate();
                    Utils.showAlert(Alert.AlertType.INFORMATION, "Éxito", "Habitación actualizada correctamente");
                    //go back to admin panel
                    stage.setWidth(850);
                    stage.setHeight(400);
                    PanelLoginController.screenController.addScreen("admindashboard", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/admin-dashboard.fxml"))));
                    PanelLoginController.screenController.removeScreen("adminaddroom");
                    PanelLoginController.screenController.activate("admindashboard");
                } else {
                    String sql = "INSERT INTO habitacion (id_reserva, num_habitacion, planta, disponibilidad, tipo, precio, caracteristicas) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, 0+"");
                    preparedStatement.setString(2, numRoomFieldText);
                    preparedStatement.setString(3, floorFieldText);
                    preparedStatement.setString(4, availabilityDropdownText);
                    preparedStatement.setString(5, typeFieldText);
                    preparedStatement.setString(6, priceFieldText);
                    preparedStatement.setString(7, characteristicsFieldText);
                    preparedStatement.executeUpdate();
                    Utils.showAlert(Alert.AlertType.INFORMATION, "Éxito", "Habitación añadida correctamente");
                    //go back to admin panel
                    stage.setWidth(850);
                    stage.setHeight(400);
                    PanelLoginController.screenController.addScreen("admindashboard", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/admin-dashboard.fxml"))));
                    PanelLoginController.screenController.removeScreen("adminaddroom");
                    PanelLoginController.screenController.activate("admindashboard");

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            Utils.showAlert(Alert.AlertType.ERROR, "Fallo", "No se ha podido añadir la habitación: "+e.getMessage());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        idHabitacion = 0;
    }

    private boolean numRoomExists(String numRoomFieldText) {
        try {
            Connection connection = DBConnection.getConnections();
            String sql = "SELECT * FROM habitacion WHERE num_habitacion = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, numRoomFieldText);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void GoBack() throws IOException {
        screenController.removeScreen("admin-addroom");
        stage.setWidth(1000);
        stage.setHeight(500);
        screenController.addScreen("admindashboard", FXMLLoader.load(getClass().getResource( "/fxml/admin-dashboard.fxml" )));
        screenController.activate("admindashboard");
    }
}