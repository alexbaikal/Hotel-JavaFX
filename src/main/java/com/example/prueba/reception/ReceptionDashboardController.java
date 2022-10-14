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
    private TableColumn <ClientModel, Void> editCol;
    @FXML
    private TableColumn <ClientModel, Void> deleteCol;

    //client list
    public ArrayList<ClientModel> clientList;
    @FXML
    private TableView<ReservaDataModel> reservationTable;
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
    private TableColumn<String, String> endTimeCol;
    @FXML
    private TableColumn<String, String> priceCol;
    @FXML
    private TableColumn <ReservaDataModel, Void> editReservationCol;
    @FXML
    private TableColumn <ReservaDataModel, Void> deleteReservationCol;

    //client list
    public static ArrayList<ReservaDataModel> reservasList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage.setTitle("Panel recepción");

        addClientsToTable();

        addReservationsToTable();

    }

    private void addReservationsToTable() {

        try {
            connection();

            String sql = "SELECT * FROM reserva";
            PreparedStatement preparedStatement = connection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            reservasList = new ArrayList<>();
            while (resultSet.next()) {
                ReservaDataModel reservaDataModel = new ReservaDataModel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getString(7));
                reservasList.add(reservaDataModel);
            }

            //create a loop for reservasList to fetch all the necessary data from database with the id's
            for (ReservaDataModel reservaDataModel : reservasList) {
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
                    //timestamp fecha inicio
                    int dateStart = resultSet4.getInt(1);
                    //timestamp fecha final
                    int dateEnd = resultSet4.getInt(2);
                    //convertir timestamp a fecha
                    String dateStartString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date (dateStart*1000L));
                    String dateEndString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date (dateEnd*1000L));
                    //transform timestamp to hh:mm
                    //set the values
                    reservaDataModel.setFecha_inicio_string(dateStartString);
                    reservaDataModel.setFecha_final_string(dateEndString);
                }
                //fetch price
                String sql5 = "SELECT precio FROM habitacion WHERE id_habitacion = ?";
                PreparedStatement preparedStatement5 = connection().prepareStatement(sql5);
                preparedStatement5.setInt(1, reservaDataModel.getId_habitacion());
                ResultSet resultSet5 = preparedStatement5.executeQuery();
                while (resultSet5.next()) {
                    reservaDataModel.setPrecio(resultSet5.getInt(1));
                }

            }

            connection().close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(reservasList.size());

        if (reservasList != null) {
            for (ReservaDataModel reserva : reservasList) {
                System.out.println(reserva.getId_reserva());


                numCol.setCellValueFactory(new PropertyValueFactory<>("numero_habitacion"));
                clientCol.setCellValueFactory(new PropertyValueFactory<>("nombre_cliente"));
                receptionistCol.setCellValueFactory(new PropertyValueFactory<>("nombre_recepcionista"));
                startDateCol.setCellValueFactory(new PropertyValueFactory<>("fecha_inicio_string"));
                endDateCol.setCellValueFactory(new PropertyValueFactory<>("fecha_final_string"));
                priceCol.setCellValueFactory(new PropertyValueFactory<>("precio"));

                //add edit button to the editCol column
                editReservationCol.setCellFactory(new Callback<>() {
                    @Override
                    public TableCell<ReservaDataModel, Void> call(final TableColumn<ReservaDataModel, Void> param) {
                        return new TableCell<>() {

                            private final Button btn = new Button("Editar");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    ReservaDataModel data = getTableView().getItems().get(getIndex());
                                    System.out.println("selectedData: " + data.getId_reserva());
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

                            private final Button btn = new Button("Eliminar");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    ReservaDataModel data = getTableView().getItems().get(getIndex());
                                    System.out.println("selectedData: " + data.getId_habitacion());
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

                //add edit button to the editCol column
                editCol.setCellFactory(new Callback<>() {
                    @Override
                    public TableCell<ClientModel, Void> call(final TableColumn<ClientModel, Void> param) {
                        return new TableCell<>() {

                            private final Button btn = new Button("Editar");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    ClientModel data = getTableView().getItems().get(getIndex());
                                    System.out.println("selectedData: " + data.getId());
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

                            private final Button btn = new Button("Eliminar");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    ClientModel data = getTableView().getItems().get(getIndex());
                                    System.out.println("selectedData: " + data.getId());
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
        stage.setTitle("Añadir cliente");
        PanelLoginController.screenController.addScreen("receptionaddclient", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/reception-addclient.fxml"))));
        PanelLoginController.screenController.removeScreen("receptiondashboard");
        PanelLoginController.screenController.activate("receptionaddclient");
    }

    public void AddReservation() throws IOException {
        stage.setTitle("Añadir reserva");
        PanelLoginController.screenController.addScreen("receptionaddreservation", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/reception-addreservation.fxml"))));
        PanelLoginController.screenController.removeScreen("receptiondashboard");
        PanelLoginController.screenController.activate("receptionaddreservation");
    }
}

