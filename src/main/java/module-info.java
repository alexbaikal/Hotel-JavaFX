module com.example.prueba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;


    opens com.example.prueba to javafx.fxml;
    exports com.example.prueba;
    exports com.example.prueba.admin;
    opens com.example.prueba.admin to javafx.fxml;
    exports com.example.prueba.reception;
    opens com.example.prueba.reception to javafx.fxml;
}