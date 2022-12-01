package com.example.prueba.reception;

import backend.Utils;
import com.example.prueba.PanelLoginController;
import com.example.prueba.models.ClientModel;
import com.example.prueba.models.ReservaDataModel;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
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
    private TableColumn<String, String> stateReservationCol;
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
    @FXML
    private TableColumn<ReservaDataModel, Void> facturaReservationCol;
    @FXML
    private TextField searchField;

    //client list
    public static ArrayList<ReservaDataModel> reservesList;
    public ArrayList<ClientModel> clientList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage.setTitle("Panel recepción");

        addClientsToTable();

        addReservationsToTable(null);


        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                addReservationsToTable(null);
            } else {
                addReservationsToTable(newValue);
            }
        });

    }




    public void addReservationsToTable(String searchTerm) {
        reservationTable.getItems().clear();

        try {
            connection();

            String sql = "SELECT * FROM reserva";
            PreparedStatement preparedStatement = connection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            reservesList = new ArrayList<>();
            while (resultSet.next()) {
                //get fecha inicio and fecha final (column indexes 5 and 6) in Date format
                Date startDate = resultSet.getDate(5);
                Date endDate = resultSet.getDate(6);
                String estado_reserva = "Checked-out";
                if (Utils.isBetween(startDate, endDate)) {
                    estado_reserva = "Checked-in";
                } else if (Utils.isBefore(startDate, endDate)) {
                    estado_reserva = "Pendiente";
                }

                ReservaDataModel reservaDataModel = new ReservaDataModel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), estado_reserva, resultSet.getString(7));

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

                if (searchTerm != null) {
                    System.out.println("nombre: "+reservaDataModel.getNombre_cliente());
                    if (reservaDataModel.getEstado_reserva().toLowerCase().contains(searchTerm.toLowerCase()) ||reservaDataModel.getNombre_cliente().toLowerCase().contains(searchTerm.toLowerCase()) || reservaDataModel.getNombre_recepcionista().toLowerCase().contains(searchTerm.toLowerCase())) {
                        reservesList.add(reservaDataModel);
                    }
                } else {
                    reservesList.add(reservaDataModel);
                }

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
                stateReservationCol.setCellValueFactory(new PropertyValueFactory<>("estado_reserva"));

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

                //add Factura button to the facturaReservationCol column
                facturaReservationCol.setCellFactory(new Callback<>() {

                    @Override
                    public TableCell<ReservaDataModel, Void> call(final TableColumn<ReservaDataModel, Void> param) {
                        return new TableCell<>() {

                            private final Button btn = new Button("\uD83D\uDCDDGenerar PDF");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    ReservaDataModel data = getTableView().getItems().get(getIndex());
                                    //generate a PDF based on data from the selected row
                                    generatePDF(data);

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

    private void generatePDF(ReservaDataModel data) {
        String fileName = data.getId_reserva()+".Factura_"+data.getNombre_cliente()+".pdf";
        String nombreCliente = data.getNombre_cliente();
        String nombreRecepcionista = data.getNombre_recepcionista();
        Integer numeroHabitacion = data.getNumero_habitacion();
        String fechaInicio = data.getFecha_inicio_string();
        String fechaFinal = data.getFecha_final_string();
        Integer precio = data.getPrecio();

        BarChart<String, Number> chart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        Random rng = new Random();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data");
        /*
        for (int i = 1 ; i<=10; i++) {
            series.getData().add(new XYChart.Data<>("Group "+i, rng.nextDouble()));
        }*/

        //get all reservation prices and dates and put them to XYChart
        try {
            PreparedStatement preparedStatement = connection().prepareStatement("SELECT costo, fecha_inicio FROM reserva");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                series.getData().add(new XYChart.Data<>(resultSet.getString(2), resultSet.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        chart.getData().add(series);

        Button save = new Button("Save to pdf");
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        save.setOnAction(e -> {
            //generate PDF
            File file = chooser.showSaveDialog(stage);
            if (file != null) {
                try {

                  //  com.itextpdf.text.pdf.PdfDocument doc = new com.itextpdf.text.pdf.PdfDocument();
                    com.itextpdf.text.Document document = new com.itextpdf.text.Document();
                    PdfWriter.getInstance(document, new FileOutputStream(file));
                    document.open();


                    //add image
                    Image image = new Image("https://imgur.com/5FOmooW.jpg");
                    com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(image.getUrl());
                    img.scaleAbsolute(100, 100);
                    img.setAbsolutePosition(400, 700);
                    document.add(img);

                    document.add(new Paragraph("Factura de la reserva Nº"+data.getId_reserva()));

                    document.add(new Paragraph("Nombre del cliente: "+nombreCliente));
                    document.add(new Paragraph("Nombre del recepcionista: "+nombreRecepcionista));
                    document.add(new Paragraph("Numero de habitacion: "+numeroHabitacion));
                    document.add(new Paragraph("Fecha de inicio: "+fechaInicio));
                    document.add(new Paragraph("Fecha de finalizacion: "+fechaFinal));
                    document.add(new Paragraph("Precio: "+precio+"€"));


                    document.close();


                } catch (DocumentException | IOException ex) {
                    throw new RuntimeException(ex);
                }


            }



        });

        VBox root = new VBox(chart, save);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();



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
            addReservationsToTable(null);
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
            Utils.showAlert(Alert.AlertType.ERROR, "Error", "No se ha podido eliminar el cliente porque tiene reservas a su nombre");

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

