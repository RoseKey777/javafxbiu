package com.example.biubiu.controller;

import com.alibaba.fastjson.JSON;
import com.example.biubiu.Director;
import com.example.biubiu.HelloApplication;
import com.example.biubiu.domain.Request;
import com.example.biubiu.scene.Index;
import com.example.biubiu.scene.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

public class IndexController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private Label setting;

    @FXML
    private Button gamehall;

    @FXML
    private Label store;

    @FXML
    private Button playchoose;
    @FXML
    private Label username;
    @FXML
    private Label scores_icon;
    @FXML
    private Label scores;
    @FXML
    private Label coin_icon;
    @FXML
    private Label coins;
    @FXML
    private Label dengji;
    @FXML
    private Button help;
    @FXML
    private Button touxiang;
    @FXML
    private Label add_coin;
    @FXML
    private Label avatar;


    @FXML
    void mouseClikedGameHall(MouseEvent event) {
        //Director.getInstance().gameStart();
        Director.getInstance().gamehallStart();
    }

    @FXML
    void mouseEnteredGameHall(MouseEvent event) {
        gamehall.setOpacity(0.8);
    }

    @FXML
    void mouseExitedGameHall(MouseEvent event) {
        gamehall.setOpacity(1);
    }

    @FXML
    void mouseClikedStore(MouseEvent event) {
        Director.getInstance().storeStart();
    }

    @FXML
    void mouseEnteredStore(MouseEvent event) {

    }

    @FXML
    void mouseExitedStore(MouseEvent event) {

    }

    @FXML
    void mouseClikedSetting(MouseEvent event) {

    }

    @FXML
    void mouseEnteredSetting(MouseEvent event) {

    }

    @FXML
    void mouseExitedSetting(MouseEvent event) {

    }

    @FXML
    void mouseClikedPlayChoose(MouseEvent event) {

    }

    @FXML
    void mouseEnteredPlayChoose(MouseEvent event) {

    }

    @FXML
    void mouseExitedPlayChoose(MouseEvent event) {

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String fontPath = IndexController.class.getResource("/com/example/biubiu/spider.ttf").toExternalForm();
        Font spiderFont = new Font(fontPath,20);
        this.setting.setFont(spiderFont);
        System.out.println(spiderFont);
        this.username.setFont(spiderFont);
        this.dengji.setFont(spiderFont);
        this.gamehall.setFont(spiderFont);
        this.playchoose.setFont(spiderFont);
        this.store.setFont(spiderFont);
        this.help.setFont(spiderFont);

        Image image = new Image(Login.class.getResource("/com/example/biubiu/image/gold.png").toExternalForm(),46,46,false,true);
        coin_icon.setGraphic(new ImageView(image));
        image = new Image(Login.class.getResource("/com/example/biubiu/image/加号.png").toExternalForm(),40,40,false,true);
        add_coin.setGraphic(new ImageView(image));
        image = new Image(Login.class.getResource("/com/example/biubiu/image/设置.png").toExternalForm(),46,46,false,true);
        setting.setGraphic(new ImageView(image));
        image = new Image(Login.class.getResource("/com/example/biubiu/image/星星.png").toExternalForm(),46,46,false,true);
        scores_icon.setGraphic(new ImageView(image));
        image = new Image(Login.class.getResource("/com/example/biubiu/image/购物车.png").toExternalForm(),46,46,false,true);
        store.setGraphic(new ImageView(image));



        Request request1 = new Request("getuserinfo", null);
        String str = HelloApplication.sendRequest(request1);
        Map<String, Object> user =  JSON.parseObject(str);
        this.username.setText((String) user.get("username"));
        this.scores.setText(String.valueOf(user.get("score")));
        this.coins.setText(String.valueOf(user.get("coins")));
        String avatar = (String) user.get("avatar");
        System.out.println(avatar);
        image = new Image(Login.class.getResource(avatar).toExternalForm(),60,60,false,true);
        this.avatar.setGraphic(new ImageView(image));
    }

    public void mouseClikedusername(MouseEvent mouseEvent) {
    }

    //TODO:头像显示
    public void mouseClikedAvatar(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
        if(file!=null){
            String absolutePath = file.getAbsolutePath();
            String substring = absolutePath.substring(31);
            String replace = substring.replace("\\", "/");
            System.out.println(replace);
            Image image = new Image(Index.class.getResource(replace).toExternalForm(),60,60,false,true);
            avatar.setGraphic(new ImageView(image));
        }

    }

    public void addMouseEntered(MouseEvent mouseEvent) {
        add_coin.setCursor(Cursor.HAND);
    }


    public void settingMouseEntered(MouseEvent mouseEvent) {
        setting.setCursor(Cursor.HAND);
    }

    public void storeMouseEntered(MouseEvent mouseEvent) {
        store.setCursor(Cursor.HAND);
    }

    //TODO:设置界面
    public void settingMouseClicked(MouseEvent mouseEvent) {

    }


    public void storeMouseClicked(MouseEvent mouseEvent) {
        Stage stage = Director.getInstance().getStage();
        Director.getInstance().Tostore(stage);
    }


    public void helpMouseClicked(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource("/com/example/biubiu/fxml/help-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 500, 300);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("教程");
        stage.show();
    }

    public void mouseEnteredAvatar(MouseEvent mouseEvent) {
        avatar.setCursor(Cursor.HAND);
    }
}


