package com.example.prueba.reception;

import backend.DBConnection;
import backend.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.prueba.PanelLoginController.screenController;

public class ReceptionAddReservationController {
    @FXML
    public TextField nameClienteField;
    @FXML
    public TextField surnameClienteField;
    @FXML
    public TextField dniClienteField;
    @FXML
    public TextField nationalityClienteField;
    @FXML
    public TextField phoneClienteField;
    @FXML
    public TextField emailClienteField;
    @FXML
    public TextField occupationClienteField;
    @FXML
    public TextField civilstateClienteField;
    @FXML
    public ListView<String> receptionistListView;
    @FXML
    public ListView<String> roomListView;
    @FXML
    public ListView<String> clientListView;
    @FXML
    TextField receptionistSearchField;
    @FXML
    TextField roomSearchField;
    @FXML
    TextField clientSearchField;
    @FXML
    DatePicker startDate;
    @FXML
    DatePicker endDate;

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



        String nameCliente = nameClienteField.getText();
        String surnameCliente = surnameClienteField.getText();
        String dniCliente = dniClienteField.getText();
        String nationalityCliente = nationalityClienteField.getText();
        String phoneCliente = phoneClienteField.getText();
        String emailCliente = emailClienteField.getText();
        String occupationCliente = occupationClienteField.getText();
        String civilstateCliente = civilstateClienteField.getText();

        try {
            Connection connection = DBConnection.getConnections();
            if (nameCliente.isEmpty() || surnameCliente.isEmpty() || dniCliente.isEmpty() || nationalityCliente.isEmpty() || phoneCliente.isEmpty() || emailCliente.isEmpty()) {
                Utils.showAlert(Alert.AlertType.WARNING, "Error", "Las entradas de texto no pueden estar vacías!");
            } else {
                String sql = "INSERT INTO clientes (name_cliente, surname_cliente, DNI_cliente, nationality_cliente, phone_cliente, email_cliente, occupation_cliente, civilstate_cliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, nameCliente);
                preparedStatement.setString(2, surnameCliente);
                preparedStatement.setString(3, dniCliente);
                preparedStatement.setString(4, nationalityCliente);
                preparedStatement.setString(5, phoneCliente);
                preparedStatement.setString(6, emailCliente);
                preparedStatement.setString(7, occupationCliente);
                preparedStatement.setString(8, civilstateCliente);
                preparedStatement.executeUpdate();
                Utils.showAlert(Alert.AlertType.INFORMATION, "Éxito", "Cliente agregado con éxito!");
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