package com.example.prueba.reception;

import backend.CommonTask;
import backend.DBConnection;
import com.example.prueba.PanelLoginController;
import com.example.prueba.models.ClientModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
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

public class ReceptionDashboardController implements Initializable {

    public Label welcomeTxt;
    @FXML
    private TableView<String> clientTable;
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

    //client list
    public ArrayList<ClientModel> clientList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage.setTitle("Panel recepción");

        try {
            Connection connection = DBConnection.getConnections();
            if (!currentReceptionUsername.isEmpty()) {
                String sql = "SELECT * FROM recepcionista WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, currentReceptionUsername);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    //re-validate welcomeTxt so it will resize the label
                    welcomeTxt.setText("Bienvenido " + resultSet.getString(2));

                } else {
                    CommonTask.showAlert(Alert.AlertType.ERROR, "Login Failed!", "Incorrect NID or Password!");
                    PanelLoginController.screenController.removeScreen("receptionlogin");
                    PanelLoginController.screenController.activate("receptionlogin");
                }
            }

            String sql = "SELECT * FROM clientes";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            clientList = new ArrayList<>();
            while (resultSet.next()) {
                ClientModel clientModel = new ClientModel(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9));
                clientList.add(clientModel);
            }
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(clientList.size());

        if (clientList != null) {
                for (ClientModel client : clientList) {
                    nameCol.setCellValueFactory(c -> new SimpleStringProperty(client.getName()));
                    surnameCol.setCellValueFactory(c -> new SimpleStringProperty(client.getSurname()));
                    dniCol.setCellValueFactory(c -> new SimpleStringProperty(client.getDni()));
                    nationalityCol.setCellValueFactory(c -> new SimpleStringProperty(client.getNationality()));
                    phoneCol.setCellValueFactory(c -> new SimpleStringProperty(client.getPhone()));
                    emailCol.setCellValueFactory(c -> new SimpleStringProperty(client.getEmail()));
                    occupationCol.setCellValueFactory(c -> new SimpleStringProperty(client.getOccupation()));
                    civilStateCol.setCellValueFactory(c -> new SimpleStringProperty(client.getCivilState()));
                    //add an edit button column to the table
                    TableColumn<String, Void> colBtn = new TableColumn<>("Button Column");


                    Callback<TableColumn<String, Void>, TableCell<String, Void>> cellFactory = new Callback<>() {
                        @Override
                        public TableCell<String, Void> call(final TableColumn<String, Void> param) {
                            return new TableCell<>() {

                                private final Button btn = new Button("Editar");

                                {
                                    btn.setOnAction((ActionEvent event) -> {
                                        String data = getTableView().getItems().get(getIndex());
                                        System.out.println("selectedData: " + client.getId());
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
                    };

                    colBtn.setCellFactory(cellFactory);

                    clientTable.getColumns().add(colBtn);
                }
            }
        clientTable.getItems().add("");




    }

    public void LogOut() {
        currentReceptionUsername = "";
        stage.setTitle("Panel Login");
        PanelLoginController.screenController.removeScreen("receptiondashboard");
        PanelLoginController.screenController.removeScreen("receptionlogin");
        PanelLoginController.screenController.activate("panellogin");
    }


}

