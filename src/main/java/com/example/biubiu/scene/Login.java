package com.example.biubiu.scene;

import com.example.biubiu.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {
    public static void load(Stage stage){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/biubiu/fxml/login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);

            Image image = new Image(Login.class.getResource("/com/example/biubiu/image/background.png").toExternalForm(),400,300,false,true);
            BackgroundImage backgroundImage =new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    null);
            GridPane gridPane=(GridPane) scene.getRoot();
            gridPane.setBackground(new Background(backgroundImage));

            stage.setTitle("biubiu~");
            stage.setScene(scene);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
