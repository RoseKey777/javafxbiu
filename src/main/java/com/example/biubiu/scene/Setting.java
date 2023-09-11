package com.example.biubiu.scene;

import com.example.biubiu.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Setting {
    public void load(Stage stage){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/biubiu/fxml/setting-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 420, 350);
            stage.setTitle("设置");
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
