package com.example.biubiu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("index.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
////        Image backgroundImage = new Image(getClass().getResource("background.png").toExternalForm(),600,400,false,true);
//        Image backgroundImage = new Image(getClass().getResourceAsStream("map1.tmx"));
//        BackgroundImage background = new BackgroundImage(backgroundImage,
//                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.DEFAULT, null);
//
//        Pane newPane = (Pane) scene.getRoot();
//        newPane.setBackground(new Background((background)));
//
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
        Director.getInstance().init(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
