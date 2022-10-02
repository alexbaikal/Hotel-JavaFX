package com.example.prueba.reception;

import backend.CommonTask;
import backend.DBConnection;
import com.example.prueba.PanelLoginController;
import com.example.prueba.models.ClientModel;
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
import java.util.ResourceBundle;
import static com.example.prueba.Main.stage;
import static com.example.prueba.reception.ReceptionLoginController.currentReceptionUsername;

public class ReceptionDashboardController implements Initializable {

    public Label welcomeTxt;
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
                                    //   try {
                                    //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reception/clientEdit.fxml"));
                                    // stage.getScene().setRoot(loader.load());
                                    //   ClientEditController clientEditController = loader.getController();
                                    //  clientEditController.setClient(data);
                                    //      } catch (IOException e) {
                                    //      e.printStackTrace();
                                    //    }
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
                                    //   try {
                                    //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reception/clientEdit.fxml"));
                                    // stage.getScene().setRoot(loader.load());
                                    //   ClientEditController clientEditController = loader.getController();
                                    //  clientEditController.setClient(data);
                                    //      } catch (IOException e) {
                                    //      e.printStackTrace();
                                    //    }
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
        PanelLoginController.screenController.addScreen("receptionaddclient", FXMLLoader.load(getClass().getResource("/fxml/reception-addclient.fxml")));
        PanelLoginController.screenController.removeScreen("receptiondashboard");
        PanelLoginController.screenController.activate("receptionaddclient");
    }


}

