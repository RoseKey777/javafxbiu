package com.example.biubiu.scene;

import com.example.biubiu.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Index {

    public static void load(Stage stage){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/biubiu/fxml/index.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1024, 1024);

            Image image = new Image(Index.class.getResource("/com/example/biubiu/image/beijing.jpg").toExternalForm(),1024,1024,false,true);
            BackgroundImage backgroundImage =new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    null);
            Pane gridPane=(Pane) scene.getRoot();
            gridPane.setBackground(new Background(backgroundImage));

            stage.setTitle("biubiu~");
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
