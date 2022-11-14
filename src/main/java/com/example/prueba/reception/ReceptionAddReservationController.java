package com.example.prueba.reception;

import backend.DBConnection;
import backend.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.prueba.PanelLoginController.screenController;

public class ReceptionAddReservationController extends ReceptionDashboardController implements Initializable {
    @FXML
    public TextField clientSearchField;
    @FXML
    public TextField receptionistSearchField;
    @FXML
    public TextField roomSearchField;

    @FXML
    public TextField costField;

    @FXML
    DatePicker startDate;
    @FXML
    DatePicker endDate;

    @FXML
    public ListView<String> receptionistListView;
    @FXML
    public ListView<String> roomListView;
    @FXML
    public ListView<String> clientListView;
    @FXML
    Button addButton;

    //create list of receptionists
    List<String> receptionists;
    List<String> rooms;
    List<String> clients;

    public static int idReserva;

    private String idRecepcionista;
    private String idHabitacion;
    private String idCliente;

    //crete initialize method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (idReserva != 0) {
            addButton.setText("Actualizar");
            try {
                Connection connection = DBConnection.getConnections();
                String sql = "SELECT * FROM reserva WHERE id_reserva = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idReserva);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    startDate.setValue(LocalDate.parse(resultSet.getString("fecha_inicio")));
                    endDate.setValue(LocalDate.parse(resultSet.getString("fecha_final")));
                    costField.setText(String.valueOf(resultSet.getInt("costo")));
                    idRecepcionista = resultSet.getString("id_recepcionista");
                    idHabitacion = resultSet.getString("id_habitacion");
                    idCliente = resultSet.getString("id_cliente");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        try {
            //get receptionist list
            receptionists = getReceptionistsFromDB();

            //set receptionists to listview
            setReceptionistsField(receptionists);

            //get room list
            rooms = getRoomsFromDB();

            //set rooms to listview
            setRoomsField(rooms);

            //get client list
            clients = getClientsFromDB();

            //set clients to listview
            setClientsField(clients);

            //setDisable to item.isBefore(currentDate) to startDate and endDate to DataCell
            LocalDate currentDate = LocalDate.now();

            startDate.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(empty || item.isBefore(currentDate));
                }
            });
            endDate.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(empty || item.isBefore(currentDate));
                }
            });
            //if startDate is selected, endDate DateCell setDisable to item.isBefore(startDate)
            startDate.setOnAction(event -> {
                //get startDate value to LocalDate
                System.out.println(startDate.getValue());
                LocalDate start = LocalDate.parse(startDate.getValue().toString());
                endDate.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(currentDate) || item.isBefore(start));
                    }
                });
            });
            //if endDate is selected, startDate DateCell setDisable to item.isAfter(endDate)
            endDate.setOnAction(event -> {
                //get endDate value to LocalDate
                LocalDate end = LocalDate.parse(endDate.getValue().toString());
                startDate.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(currentDate) || item.isAfter(end));
                    }
                });
            });

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }


    }




    private List<String> getRoomsFromDB() throws SQLException {

        List<String> roomsFromDB = new ArrayList<>();

        Connection connection = DBConnection.getConnections();
        //get list of receptionists (id_recepcionista, nombre_recepcionista) from database
        String recepcionistaQuery = "SELECT id_habitacion, num_habitacion, planta, disponibilidad, tipo, precio FROM habitacion";
        PreparedStatement preparedStatement = connection.prepareStatement(recepcionistaQuery);
        ResultSet resultSet = preparedStatement.executeQuery();


        while (resultSet.next()) {
            //check if disponibilidad is "Disponible"
            boolean checkDisponibility;
            if (idHabitacion != null && Integer.parseInt(idHabitacion) != 0 ) {
                checkDisponibility = resultSet.getInt("id_habitacion") == Integer.parseInt(idHabitacion);
            }
            else {
                checkDisponibility = resultSet.getString("disponibilidad").equals("Disponible");
            }
            if (checkDisponibility) {
                //add room to list
                String idHabitacionList = resultSet.getString("id_habitacion");
                String numHabitacion = resultSet.getString("num_habitacion");
                String planta = resultSet.getString("planta");
                String tipo = resultSet.getString("tipo");
                String precio = resultSet.getString("precio");
                String room = idHabitacionList + ": " + "Num.: " + numHabitacion + " Planta: " + planta + " Tipo: " + tipo + " - " + precio+"€";
                roomsFromDB.add(room);
            }
        }

        //close connection
        connection.close();

        return new ArrayList<>(roomsFromDB);
    }

    private void setReceptionistsField(List<String> receptionists) {

        //add listview to receptionistlistview
        receptionistListView.getItems().addAll(receptionists);
        receptionistListView.setPrefHeight(150);

        //add searchfield to search in listview
        receptionistSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            receptionistListView.getItems().clear();
            for (String receptionist : receptionists) {
                if (receptionist.toLowerCase().contains(newValue.toLowerCase())) {
                    receptionistListView.getItems().add(receptionist);
                }
            }
        });

        //if idRecepcionista != 0, set textfield to idRecepcionista from receptionists list if includes idRecepcionista
        if (idRecepcionista != null) {
            if (Integer.parseInt(idRecepcionista) != 0) {
                for (String receptionist : receptionists) {
                    if (receptionist.toLowerCase().contains(String.valueOf(idRecepcionista).toLowerCase())) {
                        receptionistSearchField.setText(receptionist);
                    }
                }
            }
        }


        //add listener to listview, on click, show selected item in searchfield
        receptionistListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            receptionistSearchField.setText(newValue);
                    receptionistListView.getItems().clear();
                    receptionistListView.getItems().addAll(receptionists);
        });

    }
    private void setRoomsField(List<String> rooms) {

            //add listview to roomlistview
            roomListView.getItems().addAll(rooms);
            roomListView.setPrefHeight(150);

            //add searchfield to search in listview
            roomSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                roomListView.getItems().clear();
                for (String room : rooms) {
                    if (room.toLowerCase().contains(newValue.toLowerCase())) {
                        roomListView.getItems().add(room);
                    }
                }
            });

        //if idHabitacion != 0, set textfield to idHabitacion from rooms list if includes idHabitacion
        if (idHabitacion != null && Integer.parseInt(idHabitacion) != 0) {
            for (String room : rooms) {
                if (room.toLowerCase().contains(String.valueOf(idHabitacion).toLowerCase())) {
                    roomSearchField.setText(room);
                }
            }
        }
            //add listener to listview, on click, show selected item in searchfield
            roomListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                roomSearchField.setText(newValue);
                    roomListView.getItems().clear();
                    roomListView.getItems().addAll(rooms);
            });
    }
    private List<String> getReceptionistsFromDB() throws SQLException {

        List<String> receptionistsFromDb = new ArrayList<>();

        Connection connection = DBConnection.getConnections();
        //get list of receptionists (id_recepcionista, nombre_recepcionista) from database
        String recepcionistaQuery = "SELECT id_recepcionista, name_recepcionista, surname_recepcionista FROM recepcionista";
        PreparedStatement preparedStatement = connection.prepareStatement(recepcionistaQuery);
        ResultSet resultSet = preparedStatement.executeQuery();


        while (resultSet.next()) {
            String idRecepcionista = resultSet.getString("id_recepcionista");
            String nameRecepcionista = resultSet.getString("name_recepcionista");
            String surnameRecepcionista = resultSet.getString("surname_recepcionista");
            String receptionist = idRecepcionista + ": " + nameRecepcionista + " " + surnameRecepcionista;
            receptionistsFromDb.add(receptionist);
        }

        //close connection
        connection.close();

        return new ArrayList<>(receptionistsFromDb);
    }

    private List<String> getClientsFromDB() throws SQLException {

        List<String> clientsFromDb = new ArrayList<>();

        Connection connection = DBConnection.getConnections();
        //get list of clients (id_cliente, name_cliente, surname_cliente) from database
        String clientQuery = "SELECT id_cliente, name_cliente, surname_cliente FROM clientes";
        PreparedStatement preparedStatement = connection.prepareStatement(clientQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        while (resultSet.next()) {
            String idCliente = resultSet.getString("id_cliente");
            String nameCliente = resultSet.getString("name_cliente");
            String surnameCliente = resultSet.getString("surname_cliente");
            String client = idCliente + ": " + nameCliente + " " + surnameCliente;
            clientsFromDb.add(client);
        }
        
        //close connection
        connection.close();
        
        return new ArrayList<>(clientsFromDb);
    }

    private void setClientsField(List<String> clients) {

            //add listview to clientlistview
            clientListView.getItems().addAll(clients);
            clientListView.setPrefHeight(150);

            //add searchfield to search in listview
            clientSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                clientListView.getItems().clear();
                for (String client : clients) {
                    if (client.toLowerCase().contains(newValue.toLowerCase())) {
                        clientListView.getItems().add(client);
                    }
                }
            });
            //if idCliente != 0, set textfield to idCliente from clients list if includes idCliente
            if (idCliente != null && Integer.parseInt(idCliente) != 0) {
                for (String client : clients) {
                    if (client.toLowerCase().contains(String.valueOf(idCliente).toLowerCase())) {
                        clientSearchField.setText(client);
                    }
                }
            }
            //add listener to listview, on click, show selected item in searchfield
            clientListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                clientSearchField.setText(newValue);
                clientListView.getItems().clear();
                clientListView.getItems().addAll(clients);
            });
    }



        public void ReceptionAddReservation() throws IOException {



        String clientField = clientSearchField.getText();
        String receptionistField = receptionistSearchField.getText();
        String roomField = roomSearchField.getText();
        String cost = costField.getText();

        LocalDate start = LocalDate.parse(startDate.getValue().toString());
        LocalDate end = LocalDate.parse(endDate.getValue().toString());
            String id_cliente;
            String id_recepcionista;
            String id_habitacion;
        try {
            id_cliente = clientField.substring(0, clientField.indexOf(":"));
            id_recepcionista = receptionistField.substring(0, receptionistField.indexOf(":"));
            id_habitacion = roomField.substring(0, roomField.indexOf(":"));
        } catch (StringIndexOutOfBoundsException e) {
            id_cliente = "0";
            id_recepcionista = "0";
            id_habitacion = "0";
            //create alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al añadir reserva");
            alert.setContentText("No se ha podido añadir la reserva, comprueba que has introducido todos los datos correctamente");
            alert.showAndWait();

        }

            try {
            Connection connection = DBConnection.getConnections();
            if (clientSearchField.getText().isEmpty() || receptionistSearchField.getText().isEmpty() || roomSearchField.getText().isEmpty() || start == null || end == null || cost.isEmpty()) {
                Utils.showAlert(Alert.AlertType.WARNING, "Error", "Las entradas de texto no pueden estar vacías!");
            } else {
                String query;
                String message;

                if (idReserva != 0) {
                    message = "Reserva actualizada correctamente!";
                    query = "UPDATE reserva SET id_cliente = ?, id_recepcionista = ?, id_habitacion = ?, fecha_inicio = ?, fecha_final = ?, costo = ?, estado = ? WHERE id_reserva = "+idReserva;
                } else {
                    message = "Reserva agregada correctamente!";
                    query = "INSERT INTO reserva (id_cliente, id_recepcionista, id_habitacion, fecha_inicio, fecha_final, costo, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

                }
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, id_cliente);
                preparedStatement.setString(2, id_recepcionista);
                preparedStatement.setString(3, id_habitacion);
                preparedStatement.setDate(4, Date.valueOf(start));
                preparedStatement.setDate(5, Date.valueOf(end));
                preparedStatement.setString(6, cost);
                preparedStatement.setString(7, "Checked in");
                preparedStatement.executeUpdate();

                //query id_habitacion in habitacion and set disponibilidad to "Ocupada"
                String query2 = "UPDATE habitacion SET disponibilidad = 'Ocupada' WHERE id_habitacion = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                preparedStatement2.setString(1, id_habitacion);
                preparedStatement2.executeUpdate();



                Utils.showAlert(Alert.AlertType.INFORMATION, "Éxito", message);
                screenController.addScreen("receptiondashboard", FXMLLoader.load(getClass().getResource( "/fxml/reception-dashboard.fxml" )));
                screenController.removeScreen("receptionaddclient");
                screenController.activate("receptiondashboard");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



    public void GoBack (ActionEvent actionEvent) throws IOException {
        idReserva = 0;
        screenController.addScreen("receptiondashboard", FXMLLoader.load(getClass().getResource( "/fxml/reception-dashboard.fxml" )));
        screenController.removeScreen("receptionaddclient");
        screenController.activate("receptiondashboard");

    }


}