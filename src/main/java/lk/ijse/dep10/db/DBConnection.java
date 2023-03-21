package lk.ijse.dep10.db;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection instance;
    private final Connection connection;

    private DBConnection() {
        try {
            File file=new File("application.properties");
            Properties properties=new Properties();
            FileReader fir=new FileReader(file);
            properties.load(fir);
            fir.close();

            String host=properties.getProperty("mysql.host","127.0.0.1");
            String port=properties.getProperty("mysql.port","3306");
            String password=properties.getProperty("mysql.password","1995");
            String username=properties.getProperty("mysql.username","root");
            String database=properties.getProperty("mysql.database","dep10_students_attendance");

            String sql="jdbc:mysql://"+host+":"+port+"/"+database+"?createDatabaseIfNotExit=true&allowMultiQueries=true";
            connection= DriverManager.getConnection(sql,username,password);

        } catch (FileNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,"Configuration file doesn't exist").showAndWait();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to establish the database connection.try again.").showAndWait();
            throw new RuntimeException(e);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to read configuration").showAndWait();
            throw new RuntimeException(e);
        }
    }
    public static DBConnection getInstance(){
        return instance ==null?instance=new DBConnection():instance;
    }
    public Connection getConnection(){
        return connection;
    }
}
