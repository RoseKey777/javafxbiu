package com.example.biubiu;

import com.example.biubiu.scene.GameScene;
import com.example.biubiu.scene.Index;
import com.example.biubiu.scene.Login;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Director {

    public static final double WIDTH = 1024, HEIGHT = 1024;

    private static Director instance = new Director();

    private Stage stage;
    private GameScene gameScene = new GameScene();
    private Director(){}

    public static Director getInstance(){
        return instance;
    }

    public void init(Stage stage){
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, WIDTH,HEIGHT);
        stage.setTitle("biubiu");
        stage.getIcons().add(new Image(getClass().getResource("image/background.png").toExternalForm(),600,400,false,true));
        stage.setResizable(false);
        stage.setScene(scene);
//        stage.setWidth(WIDTH);
//        stage.setHeight(HEIGHT);
        this.stage = stage;
        toLogin();
        stage.show();
    }

    public void Toindex(Stage stage){
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, WIDTH,HEIGHT);
        stage.setTitle("biubiu");
        stage.getIcons().add(new Image(getClass().getResource("image/background.png").toExternalForm(),600,400,false,true));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        this.stage = stage;
        toIndex();
        stage.show();
    }


    public Stage getStage(){
        return stage;
    }
    public void toIndex(){
        Index.load(stage);
    }
    public void toLogin(){
        Login.load(stage);
    }

    public void gameOver(){

    }

    public void gameStart(){
        gameScene.init(stage);
    }

}
