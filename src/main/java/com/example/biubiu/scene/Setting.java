package com.example.biubiu.scene;

import com.example.biubiu.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;


public class Setting {
    private Stage stage = new Stage();
    public void load(){
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource("/com/example/biubiu/fxml/setting-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 420, 350);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        Image image = new Image(HelloApplication.class.getResource("/com/example/biubiu/image/background.png").toExternalForm(),420,350,false,true);
        BackgroundImage backgroundImage =new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                null);
        AnchorPane anchorPane=(AnchorPane) scene.getRoot();
        anchorPane.setBackground(new Background(backgroundImage));


        stage.setScene(scene);
        stage.setTitle("设置");
        stage.show();
    }

    public void close() {
        if (stage != null) {
            stage.close();
        }
    }

}