package com.example.biubiu.controller;//package com.example.biubiu.controller;

import com.example.biubiu.Director;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StoreController implements Initializable {

    @FXML
    private Label cargo1;

    private ArrayList<Label> labelList = new ArrayList<Label>();

    @FXML
    private Button cargoButton1;

    @FXML
    private TextArea allOfMsg;

    @FXML
    void displayInformation(MouseEvent event){
        String information = "您点击了Label：" + cargo1.getText();
        allOfMsg.setText(information);
    }
    @FXML
    void exitToIndex(MouseEvent mouseEvent){
        Director.getInstance().toIndex();
    }
    @FXML
    public void cargoShopping(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelList.add(cargo1);
    }


}
