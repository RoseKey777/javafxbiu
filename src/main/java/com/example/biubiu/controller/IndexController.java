package com.example.biubiu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.biubiu.Director;
import com.example.biubiu.HelloApplication;
import com.example.biubiu.domain.Request;
import com.example.biubiu.scene.Index;
import com.example.biubiu.scene.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

public class IndexController implements Initializable {
    private String fontPath = LoginController.class.getResource("/com/example/biubiu/zpix.ttf").toExternalForm();

    @FXML
    public Button toPVE;
    public ListView<String> List;
    public ListView<String> list_scores;
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
    private Label paihangbang;
    @FXML
    private Button help;
    @FXML
    private Button touxiang;
    @FXML
    private Label add_coin;
    @FXML
    private Label avatar;

    private String _username;

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
        Stage stage = Director.getInstance().getStage();
        Director.getInstance().Tobag(stage);
    }

    @FXML
    void mouseEnteredPlayChoose(MouseEvent event) {
        playchoose.setOpacity(0.8);
    }

    @FXML
    void mouseExitedPlayChoose(MouseEvent event) {
        playchoose.setOpacity(1);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Font font = Font.loadFont(fontPath, 15);
        username.setFont(font);
        dengji.setFont(font);
        font = Font.loadFont(fontPath, 32);
        scores.setFont(font);
        coins.setFont(font);
        paihangbang.setFont(font);
        List.setStyle("-fx-font-family:'Zpix'; -fx-font-size: 15px;");
        list_scores.setStyle("-fx-font-family:'Zpix'; -fx-font-size: 15px;");

        Image image1 = new Image(Login.class.getResource("/com/example/biubiu/images/bag_button_01.gif").toExternalForm(),172,84,false,true);
        BackgroundImage backgroundImage =new BackgroundImage(image1,null,null,null,null);
        playchoose.setBackground(new Background(backgroundImage));

        image1 = new Image(Login.class.getResource("/com/example/biubiu/images/join_room_button_01.gif").toExternalForm(),172,84,false,true);
        backgroundImage =new BackgroundImage(image1,null,null,null,null);
        gamehall.setBackground(new Background(backgroundImage));

        image1 = new Image(Login.class.getResource("/com/example/biubiu/images/help_button_01.gif").toExternalForm(),172,84,false,true);
        backgroundImage =new BackgroundImage(image1,null,null,null,null);
        help.setBackground(new Background(backgroundImage));

        image1 = new Image(Login.class.getResource("/com/example/biubiu/images/computer_mode_button_01.gif").toExternalForm(),172,84,false,true);
        backgroundImage =new BackgroundImage(image1,null,null,null,null);
        toPVE.setBackground(new Background(backgroundImage));


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
        _username =(String) user.get("username");
        this.username.setText((String) user.get("username"));
        this.scores.setText(String.valueOf(user.get("score")));
        this.coins.setText(String.valueOf(user.get("coins")));
        String avatar = (String) user.get("avatar");
        System.out.println(avatar);
        image = new Image(Login.class.getResource(avatar).toExternalForm(),60,60,false,true);
        this.avatar.setGraphic(new ImageView(image));

        // 创建一个可观察的列表，用于存储ListView中的数据
        ObservableList<String> item = FXCollections.observableArrayList();
        ObservableList<String> item1 = FXCollections.observableArrayList();
        Request request2 = new Request("getalluser", null);
        str= HelloApplication.sendRequest(request2);
        System.out.println(str);
        JSONArray array = JSON.parseArray(str);
        for(int i = 0; i < array.size(); i++){
            Map<String, Object> map = JSON.parseObject(array.get(i).toString());
            String username = (String)map.get("username");
            BigDecimal score = (BigDecimal) map.get("score");
            item.add(username);
            item1.add("得分："+String.valueOf(score));
            List.setItems(item);
            list_scores.setItems(item1);
        }
        List.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list_scores.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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
            String substring = absolutePath.substring(44);
            String replace = substring.replace("\\", "/");
            System.out.println(replace);
            Image image = new Image(Index.class.getResource(replace).toExternalForm(),60,60,false,true);
            avatar.setGraphic(new ImageView(image));
            Map<String,Object> map =new HashMap<>();
            map.put("username",_username);
            map.put("avatar",replace);
            Request request1 = new Request("updateavatar", map);
            HelloApplication.sendRequest(request1);
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
        Stage stage = Director.getInstance().getStage();
        Director.getInstance().Tosetting();
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
            scene = new Scene(fxmlLoader.load(), 640, 500);
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

    public void toPVEMouseClicked(MouseEvent mouseEvent) {
        Stage stage = Director.getInstance().getStage();
        Director.getInstance().ToPVE(stage);
    }

    public void toPVEMouseEntered(MouseEvent mouseEvent) {
        toPVE.setOpacity(0.8);
    }

    public void toPVEMouseExited(MouseEvent mouseEvent) {
        toPVE.setOpacity(1);
    }
}


