package com.example.prueba.reception;

import backend.DBConnection;
import backend.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.prueba.PanelLoginController.screenController;

public class ReceptionAddReservationController {
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


    //create list of receptionists
    List<String> receptionists;
    List<String> rooms;
    List<String> clients;



    //crete initialize method

    public void initialize() throws SQLException {
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
            if (resultSet.getString("disponibilidad").equals("Disponible")) {
                //add room to list
                String idHabitacion = resultSet.getString("id_habitacion");
                String numHabitacion = resultSet.getString("num_habitacion");
                String planta = resultSet.getString("planta");
                String tipo = resultSet.getString("tipo");
                String precio = resultSet.getString("precio");
                String room = idHabitacion + ": " + "Num.: " + numHabitacion + " Planta: " + planta + " Tipo: " + tipo + " - " + precio+"€";
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

            //add listener to listview, on click, show selected item in searchfield
            clientListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                clientSearchField.setText(newValue);
                clientListView.getItems().clear();
                clientListView.getItems().addAll(clients);
            });
    }



        public void ReceptionAddReservation(ActionEvent actionEvent) throws IOException {



        String clientField = clientSearchField.getText();
        String receptionistField = clientSearchField.getText();
        String roomField = roomSearchField.getText();
        String cost = costField.getText();

        LocalDate start = LocalDate.parse(startDate.getValue().toString());
        LocalDate end = LocalDate.parse(endDate.getValue().toString());

        String id_cliente = clientField.substring(0, clientField.indexOf(":"));
        String id_recepcionista = receptionistField.substring(0, receptionistField.indexOf(":"));
        String id_habitacion = roomField.substring(0, roomField.indexOf(":"));

            try {
            Connection connection = DBConnection.getConnections();
            if (clientSearchField.getText().isEmpty() || receptionistSearchField.getText().isEmpty() || roomSearchField.getText().isEmpty() || start == null || end == null || cost.isEmpty()) {
                Utils.showAlert(Alert.AlertType.WARNING, "Error", "Las entradas de texto no pueden estar vacías!");
            } else {
                String query = "INSERT INTO reserva (id_cliente, id_recepcionista, id_habitacion, fecha_inicio, fecha_final, costo, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
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

                Utils.showAlert(Alert.AlertType.INFORMATION, "Éxito", "Reserva agregada con éxito!");
                screenController.addScreen("receptiondashboard", FXMLLoader.load(getClass().getResource( "/fxml/reception-dashboard.fxml" )));
                screenController.removeScreen("receptionaddclient");
                screenController.activate("receptiondashboard");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void GoBack (ActionEvent actionEvent) throws IOException {
        screenController.addScreen("receptiondashboard", FXMLLoader.load(getClass().getResource( "/fxml/reception-dashboard.fxml" )));
        screenController.removeScreen("receptionaddclient");
        screenController.activate("receptiondashboard");
    }
}