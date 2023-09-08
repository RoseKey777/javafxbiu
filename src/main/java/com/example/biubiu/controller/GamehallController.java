package com.example.biubiu.controller;

import com.example.biubiu.Director;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GamehallController implements Initializable {
    @FXML
    private Button joinBtn;

    @FXML
    private Label waitMsg;

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
    private Label number1;
    @FXML
    private Label number2;
    @FXML
    private Label number3;
    @FXML
    private Label number4;
    @FXML
    private Label number5;
    @FXML
    private Label number6;
    @FXML
    private Label number7;
    @FXML
    private Label number8;
    private ArrayList<Label> numberList = new ArrayList<>();

    @FXML
    public void joinRoomClicked(MouseEvent event){
        if(selectedLabel != null){
            Label numberLabel = numberList.get(findLabelIndex(selectedLabel));
            int number = numberLabel.getText().charAt(0) - 48;
            if(number < 4){
                number++;
                String roonName = selectedLabel.getText();



                numberLabel.setText(number + "/4");
                if(number != 4){
                    waitMsg.setText("等待其他玩家加入...");
                }else{
                    Director.getInstance().gameStart();
                }
                Director.getInstance().gameStart();
            }else{
                waitMsg.setText("房间人数已满");
            }
        }else{
            waitMsg.setText("请先选择房间");
        }
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

        numberList.add(number1);
        numberList.add(number2);
        numberList.add(number3);
        numberList.add(number4);
        numberList.add(number5);
        numberList.add(number6);
        numberList.add(number7);
        numberList.add(number8);

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

    private int findLabelIndex(Label selectedLabel){
        for(int i = 0; i < 8; i++){
            if(selectedLabel == labelList.get(i)){
                return i;
            }
        }
        return -1;
    }
}
