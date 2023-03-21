package lk.ijse.dep10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.dep10.db.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Data connection is about to close");
                if (!DBConnection.getInstance().getConnection().isClosed()) {
                    DBConnection.getInstance().getConnection().close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        generateSchemaIfNotExist();
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"))));
        primaryStage.setTitle("Student Details Application");
        primaryStage.show();
        primaryStage.centerOnScreen();
        primaryStage.setMaximized(true);

    }


    private void generateSchemaIfNotExist() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SHOW TABLES");
            HashSet<String> tableNameList = new HashSet<>();
            while (rst.next()) {
                tableNameList.add(rst.getString(1));
            }
            boolean tableExists=tableNameList.contains(Set.of("Attendance","User","Picture","Student"));
            System.out.println(tableNameList);
            if(!tableExists){
                System.out.println("some table are missing");
                stm.execute(readDBScript());
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String readDBScript(){
        InputStream is = getClass().getResourceAsStream("/schema.sql");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder dbScript = new StringBuilder();
            while ((line = br.readLine()) != null) {
                dbScript.append(line).append("\n");
            }
            return dbScript.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }
}
