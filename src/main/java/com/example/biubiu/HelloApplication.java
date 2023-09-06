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

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class HelloApplication extends Application {
    public static Socket clientSocket;//客户端

    //发送请求给服务器并接收响应
    public static String sendRequest(String req) {
        try{
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
            PrintWriter pw = new PrintWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"), true);
            System.out.println(req);
            pw.println(req);
            return br.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

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

//        client.sendRequest("123");
//        client.sendRequest("{\"data\":{\"username\": \"123\", \"password\": \"123\"},\"type\":\"login\"}");
    }

    public static void main(String[] args) throws IOException {
        //启动客户端
        clientSocket = new Socket("localhost",6666);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
        PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"), true);
        launch();
    }
}
