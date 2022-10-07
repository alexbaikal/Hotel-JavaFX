package com.example.prueba.admin;

import backend.DBConnection;
import backend.Utils;
import com.example.prueba.PanelLoginController;
import com.example.prueba.models.ReceptionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage.setTitle("Panel administrador");


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
        PanelLoginController.screenController.activate("panellogin");


    }
}
