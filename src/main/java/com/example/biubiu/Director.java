package com.example.biubiu;

import com.example.biubiu.domain.Request;
import com.example.biubiu.domain.Room;
import com.example.biubiu.net.tcp.UserClient;
import com.example.biubiu.scene.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Director {

    public static final double WIDTH = 1024, HEIGHT = 1024;

    private static Director instance = new Director();

    private Stage stage;
    private GameScene gameScene;
    private Gamehall gamehall = new Gamehall();

    private WaitingRoom waitingRoom = new WaitingRoom();

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

    public void gameOver(boolean success){
        gameScene.clear(stage);
        GameOver.load(stage,success);
//        gameScene.clear(stage);
        gameScene = null;
    }

    public void gameStart(Room room, int Roomchair, int gamePort){
        gameScene = new GameScene();
        gameScene.roomid = room.id;
        gameScene.gamePort = gamePort;
        for(int i = 0; i < room.userClients.size(); ++i){
            gameScene.username[i] = room.userClients.get(i).user.getUsername();
        }
        int total = room.num;
        ArrayList<UserClient> userClients = room.userClients;
        int []ChaID = new int[4];
        int []WeaID = new int[4];
        String []ips = new String[4];

        int tmp = 0;

        for(UserClient userClient:userClients){
            WeaID[tmp] = userClient.weapenid;
            ChaID[tmp] = userClient.characterid;
            ips[tmp] = userClient.ip;
            tmp++;
        }
        // 房间总人数,我是第几人(0开始,角色编号数组,武器编号数组,用户IP数组
        gameScene.init(stage, total, Roomchair, ChaID, WeaID, ips);
    }

    public void gamehallStart(){
        gamehall.load(stage);
    }

    public void storeStart(){
        store.load(stage);
    }

    public void waitingRoomStart(){
        waitingRoom.load(stage);
    }
}
