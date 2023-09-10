package com.example.biubiu.controller;

import com.alibaba.fastjson.JSON;
import com.example.biubiu.Director;
import com.example.biubiu.HelloApplication;
import com.example.biubiu.domain.Request;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;

public class pveController  implements Initializable {
    public Button medium;
    public Button difficult;
    public Button easy;

    private String username;

    private int characterid;

    private int weaponid;

    public void handleinformation(){
        String res = HelloApplication.sendRequest(new Request("getUserClient", null));
        Map<String, Object> map = JSON.parseObject(res);
        System.out.println(map);
        String user = map.get("user").toString();
        username = (String)JSON.parseObject(user).get("username");
        characterid = (int) map.get("characterid");
        weaponid = (int) map.get("weapenid");
        System.out.println(username);
        System.out.println(characterid);
        System.out.println(weaponid);

    }

    //TODO:模式跳转
    public void easyMouseClicked(MouseEvent mouseEvent) {
        handleinformation();
        Director.getInstance().NPCgamestart(username,characterid,weaponid,0);
    }
    public void mediumMouseClicked(MouseEvent mouseEvent) {
        handleinformation();
        Director.getInstance().NPCgamestart(username,characterid,weaponid,1);
    }

    public void difficultMouseClicked(MouseEvent mouseEvent) {
        handleinformation();
        Director.getInstance().NPCgamestart(username,characterid,weaponid,2);
    }

    public void easyMouseEntered(MouseEvent mouseEvent) {
        easy.setOpacity(0.8);
    }

    public void easyMouseExited(MouseEvent mouseEvent) {
        easy.setOpacity(1);
    }


    public void mediumMouseEntered(MouseEvent mouseEvent) {
        medium.setOpacity(0.8);
    }

    public void mediumMouseExited(MouseEvent mouseEvent) {
        medium.setOpacity(1);
    }

    public void difficultMouseEntered(MouseEvent mouseEvent) {
        difficult.setOpacity(0.8);
    }

    public void difficultMouseExited(MouseEvent mouseEvent) {
        difficult.setOpacity(1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String fontPath = IndexController.class.getResource("/com/example/biubiu/spider.ttf").toExternalForm();
        Font spiderFont = new Font(fontPath,20);
        this.easy.setFont(spiderFont);
        this.medium.setFont(spiderFont);
        this.difficult.setFont(spiderFont);
    }


}
