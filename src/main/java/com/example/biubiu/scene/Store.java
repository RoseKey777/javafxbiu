package com.example.biubiu.scene;

import com.example.biubiu.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Store {

    public void load(Stage stage){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/biubiu/fxml/store-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 750);

            Image image = new Image(Index.class.getResource("/com/example/biubiu/image/store/window.png").toExternalForm(),900,750,false,true);
            BackgroundImage backgroundImage =new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    null);
            Pane gridPane=(Pane) scene.getRoot();
            gridPane.setBackground(new Background(backgroundImage));

            stage.setTitle("商店");
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
