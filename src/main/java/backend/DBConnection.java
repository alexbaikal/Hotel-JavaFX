package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static final String DB_NAME = "HOTEL";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    public static Connection connection = null;

    public static Connection getConnections(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+DB_NAME, USERNAME, PASSWORD
            );
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
