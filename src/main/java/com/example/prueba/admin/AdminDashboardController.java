package com.example.prueba.admin;

import backend.DBConnection;
import backend.Utils;
import com.example.prueba.PanelLoginController;
import com.example.prueba.models.ReceptionModel;
import com.example.prueba.models.RoomModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.prueba.Main.stage;
import static com.example.prueba.reception.ReceptionLoginController.currentReceptionUsername;

public class AdminDashboardController implements Initializable {


    @FXML
    private TableView<ReceptionModel> receptionTable;
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
    private TableColumn <ReceptionModel, Void> editCol;
    @FXML
    private TableColumn <ReceptionModel, Void> deleteCol;
    //receptionist list
    public ArrayList<ReceptionModel> receptionList;


    @FXML
    private TableView<RoomModel> roomsTable;
    @FXML
    private TableColumn<String, String> numCol;
    @FXML
    private TableColumn<String, String> floorCol;
    @FXML
    private TableColumn<String, String> availabilityCol;
    @FXML
    private TableColumn<String, String> typeCol;
    @FXML
    private TableColumn<String, String> priceCol;
    @FXML
    private TableColumn<String, String> characteristicsCol;
    @FXML
    private TableColumn <RoomModel, Void> editRoomCol;
    @FXML
    private TableColumn <RoomModel, Void> deleteRoomCol;
    //receptionist list
    public ArrayList<RoomModel> roomList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage.setTitle("Panel administrador");

        addReceptionists(location, resources);

        addRooms(location, resources);
    }

    private void addRooms(URL location, ResourceBundle resources) {



        try {
            connection();

            String sql = "SELECT * FROM habitacion";
            PreparedStatement preparedStatement = connection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            roomList = new ArrayList<>();
            while (resultSet.next()) {
                RoomModel roomModel = new RoomModel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
                roomList.add(roomModel);
            }
            connection().close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (roomList != null) {

            for (RoomModel room : roomList) {
                numCol.setCellValueFactory(new PropertyValueFactory<>("roomNum"));
                floorCol.setCellValueFactory(new PropertyValueFactory<>("floor"));
                availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
                typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
                priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                characteristicsCol.setCellValueFactory(new PropertyValueFactory<>("characteristics"));


                //add edit button to the editRoomCol column

                editRoomCol.setCellFactory(new Callback<>() {
                    @Override
                    public TableCell<RoomModel, Void> call(final TableColumn<RoomModel, Void> param) {
                        return new TableCell<>() {

                            private final Button btn = new Button("Editar");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    final RoomModel data = getTableView().getItems().get(getIndex());

                                    try {
                                        stage.setTitle("Añadir habitación");
                                        AdminAddRoomController.idHabitacion = data.getRoomId();
                                        PanelLoginController.screenController.addScreen("adminaddroom", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/admin-addroom.fxml"))));
                                        PanelLoginController.screenController.removeScreen("admindashboard");
                                        PanelLoginController.screenController.activate("adminaddroom");
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

                deleteRoomCol.setCellFactory(new Callback<>() {
                    @Override
                    public TableCell<RoomModel, Void> call(final TableColumn<RoomModel, Void> param) {
                        return new TableCell<>() {

                            private final Button btn = new Button("Eliminar");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    RoomModel data = getTableView().getItems().get(getIndex());
                                    //delete from habitacion table where num_habitacion = data.getRoomId() and update table
                                    try {
                                        String sql = "DELETE FROM habitacion WHERE id_habitacion = ?";
                                        PreparedStatement preparedStatement = connection().prepareStatement(sql);
                                        preparedStatement.setInt(1, data.getRoomId());
                                        preparedStatement.executeUpdate();
                                        connection().close();
                                        //create alert
                                        Utils.showAlert(Alert.AlertType.INFORMATION, "Éxito", "Habitación eliminada con éxito!");

                                        //refresh table
                                        roomsTable.getItems().clear();
                                        initialize(location, resources);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
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

                roomsTable.getItems().add(room);

            }

        }

    }

    private void addReceptionists(URL location, ResourceBundle resources) {

        try {
            connection();

            String sql = "SELECT * FROM recepcionista";
            PreparedStatement preparedStatement = connection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            receptionList = new ArrayList<>();
            while (resultSet.next()) {
                ReceptionModel receptionModel = new ReceptionModel(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getInt(10));
                receptionList.add(receptionModel);
            }
            connection().close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (receptionList != null) {

            for (ReceptionModel reception : receptionList) {
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
                dniCol.setCellValueFactory(new PropertyValueFactory<>("dni"));
                nationalityCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
                phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
                emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

                //add edit button to the editCol column

                editCol.setCellFactory(new Callback<>() {
                    @Override
                    public TableCell<ReceptionModel, Void> call(final TableColumn<ReceptionModel, Void> param) {
                        return new TableCell<>() {

                            private final Button btn = new Button("Alta");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    final ReceptionModel data = getTableView().getItems().get(getIndex());

                                    try {
                                        String sql = "UPDATE recepcionista SET active_recepcionista = 1 WHERE id_recepcionista = ?";
                                        PreparedStatement preparedStatement = connection().prepareStatement(sql);
                                        preparedStatement.setInt(1, data.getId());
                                        preparedStatement.executeUpdate();
                                        connection().close();
                                        //create alert
                                        Utils.showAlert(Alert.AlertType.INFORMATION, "Éxito", "Recepcionista dado de alta con éxito!");

                                        //refresh table
                                        receptionTable.getItems().clear();
                                        initialize(location, resources);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
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
                    public TableCell<ReceptionModel, Void> call(final TableColumn<ReceptionModel, Void> param) {
                        return new TableCell<>() {

                            private final Button btn = new Button("Eliminar");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    ReceptionModel data = getTableView().getItems().get(getIndex());
                                    //delete from recepcionista table where id_recepcionista = data.getId() and update table
                                    try {
                                        String sql = "DELETE FROM recepcionista WHERE id_recepcionista = ?";
                                        PreparedStatement preparedStatement = connection().prepareStatement(sql);
                                        preparedStatement.setInt(1, data.getId());
                                        preparedStatement.executeUpdate();
                                        connection().close();
                                        //create alert
                                        Utils.showAlert(Alert.AlertType.INFORMATION, "Éxito", "Recepcionista eliminado con éxito!");

                                        //refresh table
                                        receptionTable.getItems().clear();
                                        initialize(location, resources);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
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

                receptionTable.getItems().add(reception);

            }


        }
    }

    public static Connection connection() throws SQLException {
        Connection connection = DBConnection.getConnections();
        if (!currentReceptionUsername.isEmpty()) {
            String sql = "SELECT * FROM recepcionista WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, currentReceptionUsername);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                Utils.showAlert(Alert.AlertType.ERROR, "Inicio de sesión fallido!", "Usuario o contraseña incorrectos!");
                PanelLoginController.screenController.removeScreen("receptionlogin");
                PanelLoginController.screenController.activate("receptionlogin");
            }
        }
        return connection;
    }


    public void LogOut() {
        AdminLoginController.currentAdminUsername = "";
        PanelLoginController.screenController.removeScreen("admindashboard");
        PanelLoginController.screenController.removeScreen("adminlogin");
        stage.setWidth(400);
        stage.setHeight(400);
        PanelLoginController.screenController.activate("panellogin");
    }

    public void AddRoom() throws IOException {
        stage.setTitle("Añadir habitación");
        stage.setWidth(600);
        stage.setHeight(600);
        PanelLoginController.screenController.addScreen("adminaddroom", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/admin-addroom.fxml"))));
        PanelLoginController.screenController.removeScreen("admindashboard");
        PanelLoginController.screenController.activate("adminaddroom");
        //pass variable to the next screen
        AdminAddRoomController.idHabitacion = 0;
    }
}
