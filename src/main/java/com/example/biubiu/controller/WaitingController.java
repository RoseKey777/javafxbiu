package com.example.biubiu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.biubiu.Director;
import com.example.biubiu.HelloApplication;
import com.example.biubiu.domain.Request;
import com.example.biubiu.domain.Room;
import com.example.biubiu.domain.User;
import com.example.biubiu.net.tcp.UserClient;
import com.example.biubiu.scene.Login;
import com.example.biubiu.scene.WaitingRoom;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class
WaitingController implements Initializable {

    private Room room = new Room();
    private int myNumber;

    private Listener listener;

    @FXML
    private Label roomidLb;

    @FXML
    private Button startBtn;

    @FXML
    private Label name1;
    @FXML
    private Label name2;
    @FXML
    private Label name3;
    @FXML
    private Label name4;
    private ArrayList<Label> nameList = new ArrayList<>();

    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;
    @FXML
    private Label player4;
    private ArrayList<Label> playerList = new ArrayList<>();

    @FXML
    private Label prepare2;
    @FXML
    private Label prepare3;
    @FXML
    private Label prepare4;
    private ArrayList<Label> prepareList = new ArrayList<>();

    @FXML
    private void exit(){
        Request request = new Request("leaveRoom", null);
        HelloApplication.sendRequest(request);
        Director.getInstance().gamehallStart();
    }

    @FXML
    private void startClick(){
        //if(startBtn.getText().equals("开始"))
        Request request1 = new Request("gameStart", null);
        HelloApplication.sendRequest(request1);
        Director.getInstance().gameStart(room,myNumber);
    }

    private void refreshRoom(){

        //显示房间
        roomidLb.setText("当前房间：房间" + room.id);

        ArrayList<UserClient> userClients = room.userClients;
        int size = userClients.size();

        //显示名字
        for(int i = 0; i < 4; i++){
            nameList.get(i).setText("");
        }
        for(int i = 0; i < size; i++){
            String username = userClients.get(i).user.getUsername();
            nameList.get(i).setText(username);
        }

        //显示头像
        for(int i = 0; i < 4; i++){
            playerList.get(i).setText("");
            playerList.get(i).setGraphic(null);
        }
        for(int i = 0; i < size; i++){
            String avatar = userClients.get(i).user.getAvatar();
            Image image = new Image(WaitingRoom.class.getResource(avatar).toExternalForm(),100,100,false,true);
            playerList.get(i).setText("");
            playerList.get(i).setGraphic(new ImageView(image));
        }

        //显示开始游戏按钮
        if(myNumber == 0){
            startBtn.setVisible(true);
            startBtn.setDisable(false);
        }else{
            startBtn.setVisible(false);
            startBtn.setDisable(true);
        }
    }

    private Room toRoom(String str){
        Room room = new Room();

        Map<String, Object> roomMap =  JSON.parseObject(str);
        System.out.println("----------------------");
        room.id = (int) roomMap.get("id");
        room.num = (int) roomMap.get("num");
        JSONArray userClients = JSON.parseArray(roomMap.get("userClients").toString());

        int index = 0;
        ArrayList<UserClient> userClientsArray = new ArrayList<>();
        try{
            while(index < userClients.size()){
                UserClient userClient = new UserClient();
                String str1 = userClients.get(index).toString();
                Map<String, Object> userClientMap =  JSON.parseObject(str1);
                int weapenid = (int) userClientMap.get("weapenid");
                String ip = userClientMap.get("ip").toString();
                int characterid = (int) userClientMap.get("characterid");

                User user = new User();
                String str2 = userClientMap.get("user").toString();
                Map<String, Object> userMap =  JSON.parseObject(str2);
                user.setScore((Integer) userMap.get("score"));
                user.setPassword(userMap.get("password").toString());
                user.setCoins(Double.parseDouble(userMap.get("coins").toString()));
                user.setAvatar(userMap.get("avatar").toString());
                user.setId((Integer) userMap.get("id"));
                user.setUsername(userMap.get("username").toString());

                userClient.weapenid = weapenid;
                userClient.ip = ip;
                userClient.characterid = characterid;
                userClient.user = user;

                userClientsArray.add(userClient);
                index++;
            }
        }finally {

        }
        room.userClients = userClientsArray;

        return room;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameList.add(name1);
        nameList.add(name2);
        nameList.add(name3);
        nameList.add(name4);

        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);

        prepareList.add(prepare2);
        prepareList.add(prepare3);
        prepareList.add(prepare4);

        Request request = new Request("getCurrentRoom", null);
        String str = HelloApplication.sendRequest(request);
        room.id = Integer.parseInt(str);

        Map<String, Object> map = new HashMap<>();
        map.put("roomid", room.id);
        Request request1 = new Request("refreshRoom", map);
        String str1 = HelloApplication.sendRequest(request1);
        room = toRoom(str1);

        Request request2 = new Request("getPlayerNum", null);
        String str2 = HelloApplication.sendRequest(request2);
        myNumber = Integer.parseInt(str2);

        refreshRoom();

        try {
            listener = new Listener("192.168.43.168", 6666);
            new Thread(listener).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //用于监听服务器的类
    class Listener implements Runnable{
        Socket listenSocket;
        BufferedReader reader;
        PrintWriter writer;

        Listener(String ip, int port) throws IOException {
            listenSocket = new Socket(ip, port);
            reader = new BufferedReader(
                    new InputStreamReader(listenSocket.getInputStream(), "UTF-8"));
            writer = new PrintWriter(
                    new OutputStreamWriter(listenSocket.getOutputStream(), "UTF-8"), true);
        }

        //实时监听
        @Override
        public void run() {
            String msgString;
            while(true) {
                try {
                    if ((msgString = reader.readLine())!= null) {   //接收到数据后进行的操作
                        System.out.println("收到服务器的消息：" + msgString);
                        if(msgString.equals("游戏开始")){
                            Request request = new Request("getPlayerNum", null);
                            String str = HelloApplication.sendRequest(request);
                            int number = Integer.parseInt(str);
                            Director.getInstance().gameStart(room,number);
                        }
                        else
                            room = toRoom(msgString);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //更新JavaFX的主线程的代码放在此处
                                refreshRoom();
                            }
                        });

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
