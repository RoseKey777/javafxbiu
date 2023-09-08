package com.example.biubiu;

import com.example.biubiu.scene.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Director {

    public static final double WIDTH = 1024, HEIGHT = 1024;

    private static Director instance = new Director();

    private Stage stage;
    private GameScene gameScene = new GameScene();
    private Gamehall gamehall = new Gamehall();

    private Store store = new Store();
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
        Pane root = new Pane();
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
    public void Tostore(Stage stage){
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH,HEIGHT);
        stage.setTitle("biubiu");
        stage.getIcons().add(new Image(getClass().getResource("image/background.png").toExternalForm(),600,400,false,true));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        this.stage = stage;
        storeStart();
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
        gameScene.ips[0] = "192.168.43.37";
        gameScene.ips[1] = "192.168.43.168";
        gameScene.ips[2] = "192.168.43.144";
        gameScene.init(stage);
    }

    public void gamehallStart(){
        gamehall.load(stage);
    }

    public void storeStart(){
        store.load(stage);
    }
}
