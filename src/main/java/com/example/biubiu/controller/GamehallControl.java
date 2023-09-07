package com.example.biubiu.controller;

import com.example.biubiu.Director;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GamehallControl implements Initializable {
    @FXML
    private Button joinBtn;

    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;
    @FXML
    private Label label8;
    private ArrayList<Label> labelList = new ArrayList<>();
    private Label selectedLabel = null;

    @FXML
    public void joinRoomClicked(MouseEvent event){
        Director.getInstance().gameStart();
    }

    @FXML
    public void returnClicked(MouseEvent event){
        Director.getInstance().toIndex();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelList.add(label1);
        labelList.add(label2);
        labelList.add(label3);
        labelList.add(label4);
        labelList.add(label5);
        labelList.add(label6);
        labelList.add(label7);
        labelList.add(label8);

        for(int i = 0; i < 8; i++){
            int finalI = i;
            labelList.get(i).setOnMouseClicked(event -> {
                handleCellClick(labelList.get(finalI));
            });
        }
    }

    private void handleCellClick(Label clickedLabel) {
        if (selectedLabel != null) {
            // 恢复先前选中格子的样式和内容
            selectedLabel.setStyle("");
            selectedLabel.setTextFill(Color.BLACK);
        }

        // 修改选中格子的样式
        selectedLabel = clickedLabel;
        selectedLabel.setStyle("-fx-background-color: lightblue;");
        selectedLabel.setTextFill(Color.RED);
    }
}
