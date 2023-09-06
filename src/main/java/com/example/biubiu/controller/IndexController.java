package com.example.biubiu.controller;

import com.example.biubiu.Director;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private Button setting;

    @FXML
    private Button gamehall;

    @FXML
    private Button store;

    @FXML
    private Button playchoose;

    @FXML
    void mouseClikedGameHall(MouseEvent event) {
        Director.getInstance().gameStart();
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
        this.gamehall.setFont(spiderFont);
        this.playchoose.setFont(spiderFont);
        this.store.setFont(spiderFont);
    }
}
