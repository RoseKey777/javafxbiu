package com.example.biubiu.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class Store {

    public void load(Stage stage){
        try{
            Parent root = FXMLLoader.load(Index.class.getResource("/com/example/biubiu/fxml/store-view.fxml"));
            stage.getScene().setRoot(root);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
