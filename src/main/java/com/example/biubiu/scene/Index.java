package com.example.biubiu.scene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class Index {

    public static void load(Stage stage){
        try{
            Parent root = FXMLLoader.load(Index.class.getResource("/com/example/biubiu/fxml/index.fxml"));
//            String s1 = Index.class.getResource("/com/example/biubiu/fxml/index.fxml").getPath();
//            System.out.println(s1);
            stage.getScene().setRoot(root);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
