package com.example.biubiu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.biubiu.Director;
import com.example.biubiu.HelloApplication;
import com.example.biubiu.domain.Request;
import com.example.biubiu.scene.Login;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class BagController implements Initializable {
    public Label weapon1;
    public Button weapon_Button1;
    public Label character1;
    public Button character1_Button1;
    public Label weapon2;
    public Button weapon_Button2;
    public Label character2;
    public Button character1_Button2;
    public Label weapon3;
    public Button weapon_Button3;
    public Label character3;
    public Button character1_Button3;
    @FXML
    private Label coins;
    @FXML
    private Label coin_icon;
    @FXML
    private Label add_coin;

    private String username;
    private ArrayList<Label> labelList = new ArrayList<>();
    private ArrayList<Button> buttonList = new ArrayList<>();

    public void addMouseEntered(MouseEvent mouseEvent) {
        add_coin.setCursor(Cursor.HAND);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelList.add(weapon1);
        labelList.add(weapon2);
        labelList.add(weapon3);
        labelList.add(character1);
        labelList.add(character2);
        labelList.add(character3);

        buttonList.add(weapon_Button1);
        buttonList.add(weapon_Button2);
        buttonList.add(weapon_Button3);
        buttonList.add(character1_Button1);
        buttonList.add(character1_Button2);
        buttonList.add(character1_Button3);

        Image image = new Image(Login.class.getResource("/com/example/biubiu/image/gold.png").toExternalForm(),46,46,false,true);
        coin_icon.setGraphic(new ImageView(image));
        image = new Image(Login.class.getResource("/com/example/biubiu/image/加号.png").toExternalForm(),40,40,false,true);
        add_coin.setGraphic(new ImageView(image));
        Request request1 = new Request("getuserinfo", null);
        String str = HelloApplication.sendRequest(request1);
        Map<String, Object> user =  JSON.parseObject(str);
        this.coins.setText(String.valueOf(user.get("coins")));
        username= (String) user.get("username");

        Map<String,Object> map=new HashMap<>();
        map.put("username",username);
        Request request2 = new Request("getuserbag", map);
        str = HelloApplication.sendRequest(request2);
        System.out.println(str);
        JSONArray array = JSON.parseArray(str);

        for (int i = 1; i <= array.size(); i++) {
            Map<String, Object> item = JSON.parseObject(array.get(0).toString());
            String weapon = String.valueOf(item.get("weapon"));
            int state= (int) item.get("weapon_state");
            Font font = new Font(0);
//            image = new Image(Login.class.getResource(weapon).toExternalForm(),40,40,false,true);
//            arr[i].setGraphic(new ImageView(image));
            if(weapon!=null){
                if(i==1){
                    image = new Image(Login.class.getResource(weapon).toExternalForm(),40,40,false,true);
                    weapon1.setGraphic(new ImageView(image));
                    weapon1.setText(weapon);
                    weapon1.setFont(font);
                    if(state==1){
                        weapon_Button1.setText("正在使用");
                    }else{
                        weapon_Button1.setText("使用");
                    }
                }
                if(i==2){
                    image = new Image(Login.class.getResource(weapon).toExternalForm(),40,40,false,true);
                    weapon2.setGraphic(new ImageView(image));
                    weapon2.setText(weapon);
                    weapon2.setFont(font);
                    if(state==1){
                        weapon_Button2.setText("正在使用");
                    }else{
                        weapon_Button2.setText("使用");
                    }
                }
                if(i==3){
                    image = new Image(Login.class.getResource(weapon).toExternalForm(),40,40,false,true);
                    weapon3.setGraphic(new ImageView(image));
                    weapon3.setText(weapon);
                    weapon3.setFont(font);
                    if(state==1){
                        weapon_Button3.setText("正在使用");
                    }else{
                        weapon_Button3.setText("使用");
                    }
                }

            }
            String character = String.valueOf(item.get("character"));
            if(character!=null){
                if(i==1){
                    image = new Image(Login.class.getResource(character).toExternalForm(),40,40,false,true);
                    character1.setGraphic(new ImageView(image));
                    character1.setText(character);
                    character1.setFont(font);
                    if(state==1){
                        character1_Button1.setText("正在使用");
                    }else{
                        character1_Button1.setText("使用");
                    }
                }
                if(i==2){
                    image = new Image(Login.class.getResource(character).toExternalForm(),40,40,false,true);
                    character2.setGraphic(new ImageView(image));
                    character2.setText(character);
                    character2.setFont(font);
                    if(state==1){
                        character1_Button2.setText("正在使用");
                    }else{
                        character1_Button2.setText("使用");
                    }
                }
                if(i==3){
                    image = new Image(Login.class.getResource(character).toExternalForm(),40,40,false,true);
                    character3.setGraphic(new ImageView(image));
                    character3.setText(character);
                    character3.setFont(font);
                    if(state==1){
                        character1_Button3.setText("正在使用");
                    }else{
                        character1_Button3.setText("使用");
                    }
                }

            }

        }


    }

    public void exitToIndex(MouseEvent mouseEvent) {
        Stage stage = Director.getInstance().getStage();
        Director.getInstance().Toindex(stage);
    }

    //使用
    public void ButtonClicked(MouseEvent mouseEvent) {

        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        //设置对话框标题
        alert2.setTitle("Information");
        //设置内容
        alert2.setHeaderText("您确定执行该操作吗？");
        //显示对话框
        Optional<ButtonType> result = alert2.showAndWait();
        //如果点击OK
        if (result.get() == ButtonType.OK){

            Button clickedButton = (Button) mouseEvent.getSource();
            for (int i = 0; i < 6; i++) {
                if(clickedButton== buttonList.get(i)){
                    String text = labelList.get(i).getText();
                    Map<String,Object> map =new HashMap<>();
                    map.put("username",username);
                    map.put("weapon",text);
                    Request request = new Request("updateWeapon",map);
                    String s = HelloApplication.sendRequest(request);
                    if(s.equals("1")){
                        buttonList.get(i).setText("正在使用");
                    }else{
                        buttonList.get(i).setText("使用");
                    }
                }
            }

        }

    }

    public void ButtonClicked1(MouseEvent mouseEvent) {
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        //设置对话框标题
        alert2.setTitle("Information");
        //设置内容
        alert2.setHeaderText("您确定执行该操作吗？");
        //显示对话框
        Optional<ButtonType> result = alert2.showAndWait();
        //如果点击OK
        if (result.get() == ButtonType.OK){

            Button clickedButton = (Button) mouseEvent.getSource();
            for (int i = 0; i < 6; i++) {
                if(clickedButton== buttonList.get(i)){
                    String text = labelList.get(i).getText();
                    Map<String,Object> map =new HashMap<>();
                    map.put("username",username);
                    map.put("character",text);
                    Request request = new Request("updateCharacter",map);
                    String s = HelloApplication.sendRequest(request);
                    if(s.equals("1")){
                        buttonList.get(i).setText("正在使用");
                    }else{
                        buttonList.get(i).setText("使用");
                    }
                }
            }

        }
    }
}
