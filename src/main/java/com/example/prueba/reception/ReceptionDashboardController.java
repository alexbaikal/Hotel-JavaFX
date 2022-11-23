package com.example.prueba.reception;

import com.example.prueba.PanelLoginController;
import com.example.prueba.models.ClientModel;
import com.example.prueba.models.ReservaDataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import static com.example.prueba.Main.stage;
import static com.example.prueba.admin.AdminDashboardController.connection;
import static com.example.prueba.reception.ReceptionLoginController.currentReceptionUsername;

public class ReceptionDashboardController implements Initializable {

    @FXML
    private TableView<ClientModel> clientTable;
    @FXML
    private TableColumn<String, String> nameCol;
    @FXML
    private TableColumn<String, String> surnameCol;
    @FXML
    private TableColumn<String, String> dniCol;
    @FXML
    private TableColumn<String, String> nationalityCol;
    @FXML
    private TableColumn<String, String> phoneCol;
    @FXML
    private TableColumn<String, String> emailCol;
    @FXML
    private TableColumn<String, String> occupationCol;
    @FXML
    private TableColumn<String, String> civilStateCol;
    @FXML
    private TableColumn <ClientModel, Void> editClientCol;
    @FXML
    private TableColumn <ClientModel, Void> deleteCol;

    //client list
    @FXML
    public TableView<ReservaDataModel> reservationTable;
    @FXML
    private TableColumn<String, String> clientCol;
    @FXML
    private TableColumn<String, String> numCol;
    @FXML
    private TableColumn<String, String> receptionistCol;
    @FXML
    private TableColumn<String, String> startDateCol;

    @FXML
    private TableColumn<String, String> endDateCol;
    @FXML
    private TableColumn<String, String> priceCol;
    @FXML
    private TableColumn <ReservaDataModel, Void> editReservationCol;
    @FXML
    private TableColumn <ReservaDataModel, Void> deleteReservationCol;

    //client list
    public static ArrayList<ReservaDataModel> reservesList;
    public ArrayList<ClientModel> clientList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage.setTitle("Panel recepción");

        addClientsToTable();

        addReservationsToTable();

    }

    public void addReservationsToTable() {

        try {
            connection();

            String sql = "SELECT * FROM reserva";
            PreparedStatement preparedStatement = connection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            reservesList = new ArrayList<>();
            while (resultSet.next()) {
                ReservaDataModel reservaDataModel = new ReservaDataModel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
                reservesList.add(reservaDataModel);
            }

            //create a loop for reservasList to fetch all the necessary data from database with the id's
            for (ReservaDataModel reservaDataModel : reservesList) {
                //fetch room number
                String sql1 = "SELECT num_habitacion FROM habitacion WHERE id_habitacion = ?";
                PreparedStatement preparedStatement1 = connection().prepareStatement(sql1);
                preparedStatement1.setInt(1, reservaDataModel.getId_habitacion());
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()) {
                    reservaDataModel.setNumero_habitacion(resultSet1.getInt(1));
                }
                //fetch client name
                String sql2 = "SELECT name_cliente, surname_cliente FROM clientes WHERE id_cliente = ?";
                PreparedStatement preparedStatement2 = connection().prepareStatement(sql2);
                preparedStatement2.setInt(1, reservaDataModel.getId_cliente());
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                while (resultSet2.next()) {
                    reservaDataModel.setNombre_cliente(resultSet2.getString(1) + " " + resultSet2.getString(2));
                }
                //fetch receptionist name
                String sql3 = "SELECT name_recepcionista, surname_recepcionista FROM recepcionista WHERE id_recepcionista = ?";
                PreparedStatement preparedStatement3 = connection().prepareStatement(sql3);
                preparedStatement3.setInt(1, reservaDataModel.getId_recepcionista());
                ResultSet resultSet3 = preparedStatement3.executeQuery();
                while (resultSet3.next()) {
                    reservaDataModel.setNombre_recepcionista(resultSet3.getString(1) + " " + resultSet3.getString(2));
                }
                //fetch start date and end date
                String sql4 = "SELECT fecha_inicio, fecha_final FROM reserva WHERE id_reserva = ?";
                PreparedStatement preparedStatement4 = connection().prepareStatement(sql4);
                preparedStatement4.setInt(1, reservaDataModel.getId_reserva());
                ResultSet resultSet4 = preparedStatement4.executeQuery();
                while (resultSet4.next()) {
                    String dateStart = resultSet4.getString(1);
                    String dateEnd = resultSet4.getString(2);
                    //set the values
                    reservaDataModel.setFecha_inicio_string(dateStart);
                    reservaDataModel.setFecha_final_string(dateEnd);
                }

                /*
                    //if dateEnd is the same or before today, then set disponibilidad column of habitacion table to "Disponible"
                    if (dateEnd.compareTo(LocalDate.now().toString()) <= 0) {
                        String sql5 = "UPDATE habitacion SET disponibilidad = ? WHERE id_habitacion = ?";
                        PreparedStatement preparedStatement5 = connection().prepareStatement(sql5);
                        preparedStatement5.setString(1, "Disponible");
                        preparedStatement5.setInt(2, reservaDataModel.getId_habitacion());
                        preparedStatement5.executeUpdate();
                    }
                } */
                //fetch price
                String sql5 = "SELECT costo FROM reserva WHERE id_reserva = ?";
                PreparedStatement preparedStatement5 = connection().prepareStatement(sql5);
                preparedStatement5.setInt(1, reservaDataModel.getId_reserva());
                ResultSet resultSet5 = preparedStatement5.executeQuery();
                while (resultSet5.next()) {
                    reservaDataModel.setPrecio(resultSet5.getInt(1));
                }

            }

            connection().close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(reservesList.size());

        if (reservesList != null) {
            for (ReservaDataModel reserva : reservesList) {
                System.out.println(reserva.getId_reserva());


                numCol.setCellValueFactory(new PropertyValueFactory<>("numero_habitacion"));
                clientCol.setCellValueFactory(new PropertyValueFactory<>("nombre_cliente"));
                receptionistCol.setCellValueFactory(new PropertyValueFactory<>("nombre_recepcionista"));
                startDateCol.setCellValueFactory(new PropertyValueFactory<>("fecha_inicio_string"));
                endDateCol.setCellValueFactory(new PropertyValueFactory<>("fecha_final_string"));
                priceCol.setCellValueFactory(new PropertyValueFactory<>("precio"));

                //add edit button to the editReservationCol column
                editReservationCol.setCellFactory(new Callback<>() {
                    @Override
                    public TableCell<ReservaDataModel, Void> call(final TableColumn<ReservaDataModel, Void> param) {
                        return new TableCell<>() {

                            private final Button btn = new Button("✏Editar");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    ReservaDataModel data = getTableView().getItems().get(getIndex());

                                    try {
                                        stage.setTitle("✏Editar reserva");
                                        ReceptionAddReservationController.idReserva = data.getId_reserva();
                                        PanelLoginController.screenController.addScreen("receptionaddreservation", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/reception-addreservation.fxml"))));
                                        PanelLoginController.screenController.removeScreen("receptiondashboard");
                                        PanelLoginController.screenController.activate("receptionaddreservation");
                                        //pass variable to the next screen
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btn);
                                }
                            }
                        };
                    }
                });

                //add delete button to the deleteCol column

                deleteReservationCol.setCellFactory(new Callback<>() {
                    @Override
                    public TableCell<ReservaDataModel, Void> call(final TableColumn<ReservaDataModel, Void> param) {
                        return new TableCell<>() {

                            private final Button btn = new Button("❌Eliminar");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    ReservaDataModel data = getTableView().getItems().get(getIndex());
                                    System.out.println("selectedData: " + data.getId_habitacion());
                                    deleteReservation(data);
                                });
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btn);
                                }
                            }
                        };
                    }
                });

                reservationTable.getItems().add(reserva);

            }


        }

    }

    private void deleteReservation(ReservaDataModel data) {
        try {
            String sql = "DELETE FROM reserva WHERE id_reserva = ?";
            PreparedStatement preparedStatement = connection().prepareStatement(sql);
            preparedStatement.setInt(1, data.getId_reserva());
            preparedStatement.executeUpdate();
/*
            String sql2 = "UPDATE habitacion SET disponibilidad = ? WHERE id_habitacion = ?";
            PreparedStatement preparedStatement2 = connection().prepareStatement(sql2);
            preparedStatement2.setString(1, "Disponible");
            preparedStatement2.setInt(2, data.getId_habitacion());
            preparedStatement2.executeUpdate();
*/

            connection().close();
            //refresh the table
            reservationTable.getItems().clear();
            addReservationsToTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addClientsToTable() {

        try {
            connection();

            String sql = "SELECT * FROM clientes";
            PreparedStatement preparedStatement = connection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            clientList = new ArrayList<>();
            while (resultSet.next()) {
                ClientModel clientModel = new ClientModel(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9));
                clientList.add(clientModel);
            }
            connection().close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(clientList.size());

        if (clientList != null) {
            for (ClientModel client : clientList) {
                System.out.println(client.getName());


                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
                dniCol.setCellValueFactory(new PropertyValueFactory<>("dni"));
                nationalityCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
                phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
                emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
                occupationCol.setCellValueFactory(new PropertyValueFactory<>("occupation"));
                civilStateCol.setCellValueFactory(new PropertyValueFactory<>("civilState"));

                //add edit button to the editClientCol column
                editClientCol.setCellFactory(new Callback<>() {
                    @Override
                    public TableCell<ClientModel, Void> call(final TableColumn<ClientModel, Void> param) {
                        return new TableCell<>() {

                            private final Button btn = new Button("✏Editar");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    ClientModel data = getTableView().getItems().get(getIndex());


                                    try {
                                        stage.setTitle("✏Editar cliente");
                                        ReceptionAddClientController.idCliente = data.getId();
                                        PanelLoginController.screenController.addScreen("receptionaddclient", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/reception-addclient.fxml"))));
                                        PanelLoginController.screenController.removeScreen("receptiondashboard");
                                        PanelLoginController.screenController.activate("receptionaddclient");
                                        //pass variable to the next screen
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                });
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btn);
                                }
                            }
                        };
                    }
                });

                //add delete button to the deleteCol column

                deleteCol.setCellFactory(new Callback<>() {
                    @Override
                    public TableCell<ClientModel, Void> call(final TableColumn<ClientModel, Void> param) {
                        return new TableCell<>() {

                            private final Button btn = new Button("❌Eliminar");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    ClientModel data = getTableView().getItems().get(getIndex());
                                    System.out.println("selectedData: " + data.getId());
                                    //delete client from database
                                    deleteClient(data);

                                });
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btn);
                                }
                            }
                        };
                    }
                });

                clientTable.getItems().add(client);

            }


        }

    }

    private void deleteClient(ClientModel data) {
        try {
            String sql = "DELETE FROM clientes WHERE id_cliente = ?";
            PreparedStatement preparedStatement = connection().prepareStatement(sql);
            preparedStatement.setInt(1, data.getId());
            preparedStatement.executeUpdate();
            connection().close();
            //refresh the table
            clientTable.getItems().clear();
            addClientsToTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void LogOut() {
        currentReceptionUsername = "";
        stage.setTitle("Panel Login");
        PanelLoginController.screenController.removeScreen("receptiondashboard");
        PanelLoginController.screenController.removeScreen("receptionlogin");
        stage.setWidth(400);
        stage.setHeight(400);
        PanelLoginController.screenController.activate("panellogin");
    }

    public void AddClient() throws IOException {
        stage.setTitle("➕Añadir cliente");
        PanelLoginController.screenController.addScreen("receptionaddclient", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/reception-addclient.fxml"))));
        PanelLoginController.screenController.removeScreen("receptiondashboard");
        PanelLoginController.screenController.activate("receptionaddclient");
    }

    public void AddReservation() throws IOException {
        stage.setTitle("➕Añadir reserva");
        PanelLoginController.screenController.addScreen("receptionaddreservation", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/reception-addreservation.fxml"))));
        PanelLoginController.screenController.removeScreen("receptiondashboard");
        PanelLoginController.screenController.activate("receptionaddreservation");
    }
}

