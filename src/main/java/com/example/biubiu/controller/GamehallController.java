package com.example.biubiu.controller;

import com.alibaba.fastjson.JSON;
import com.example.biubiu.Director;
import com.example.biubiu.HelloApplication;
import com.example.biubiu.domain.Request;
import com.example.biubiu.domain.User;
import com.example.biubiu.net.tcp.RequestHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GamehallController implements Initializable {

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
    public void joinRoomClicked(){
        if(selectedLabel != null){
            int roomid = selectedLabel.getText().charAt(2) - 48;
            Map<String, Object> map = new HashMap<>();
            map.put("roomid", roomid);
            Request request = new Request("joinRoom", map);
            String result = HelloApplication.sendRequest(request);

            if(result.equals("加入成功")){
                Director.getInstance().waitingRoomStart();
            }else{
                waitMsg.setText(result);
            }
//            Label numberLabel = numberList.get(findLabelIndex(selectedLabel));
//            int number = numberLabel.getText().charAt(0) - 48;
//            if(number < 4){
//                number++;
//                String roonName = selectedLabel.getText();
//                numberLabel.setText(number + "/4");
//                Director.getInstance().waitingRoomStart();
//            }else{
//                waitMsg.setText("房间人数已满");
//            }
        }else{
            waitMsg.setText("请先选择房间");
        }
    }

    @FXML
    public void returnClicked(MouseEvent event){
        Director.getInstance().toIndex();
    }

    @FXML
    private void refresh(){
        Request request = new Request("getRoomNum", null);
        String roomNum = HelloApplication.sendRequest(request);

        for(int i = 0; i < 8; i++){
            numberList.get(i).setText(roomNum.charAt(i) - 48 + "/4");
        }
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

        //为标签绑定点击函数
        for(int i = 0; i < 8; i++){
            int finalI = i;
            labelList.get(i).setOnMouseClicked(event -> {
                handleCellClick(labelList.get(finalI));
            });
        }

        refresh();
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

    //寻找第几个标签
    private int findLabelIndex(Label selectedLabel){
        for(int i = 0; i < 8; i++){
            if(selectedLabel == labelList.get(i)){
                return i;
            }
        }
        return -1;
    }
}
