package com.example.biubiu.scene;

import com.example.biubiu.net.udp.TalkReceive;
import com.example.biubiu.net.udp.TalkSend;
import com.example.biubiu.sprite.*;
import com.example.biubiu.util.SoundEffect;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameScene {

    public String[] ips = new String[4];
    public int numOfPlayer = 3;

    private int [][]positionPlayer = {{32,32},{950,32},{32,950},{950,950}};

    public double mouseX,mouseY;
    private Canvas canvas = new Canvas(1024,1024);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    private KeyProcess keyProcess = new KeyProcess();

    private MouseProcess mouseProcess = new MouseProcess();
    private Refresh refresh = new Refresh();

    private boolean running  = false;//为false则停止刷新，为true则启动刷新

    private Background background = new Background();
    private Player selfPlayer = new Player(400,500,0, 0.0,this);

//    private Enemy enemy = new Enemy(400,500,0, 0.0,this);

    public List<Bullet> bullets = new ArrayList<>();

    public Map<String,Enemy> enemys = new HashMap<>();

    public List<EnemyBullet> enemybullets = new ArrayList<>();

    class TalkReceive implements Runnable{
        DatagramSocket datagramSocket =null;

        private int formPort;
        private String user;
        private GameScene gs;

        public TalkReceive(int formPort,String user, GameScene gs){
            this.formPort = formPort;
            this.user = user;
            this.gs = gs;
            try {
                datagramSocket=new DatagramSocket(this.formPort);
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {

            while (true){
                try {
                    byte[] bytes = new byte[1024];
                    DatagramPacket datagramPacket = new DatagramPacket(bytes, 0, bytes.length);
                    datagramSocket.receive(datagramPacket);
                    byte[] data = datagramPacket.getData();
                    String s = new String(data, 0, datagramPacket.getLength());
                    handleData(s.trim());
                    if (s.contains("bye")){
                        break;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            datagramSocket.close();
        }

        public void handleData(String data){
            String[] dataList = data.split("\\|");
//            System.out.println(data);
            if("loc".equals(dataList[0])){  //位置信息数据包
                String tmpip = dataList[1];
                enemys.get(tmpip).x = Double.parseDouble(dataList[2]);
                enemys.get(tmpip).y = Double.parseDouble(dataList[3]);
                enemys.get(tmpip).weaponDir = Double.parseDouble(dataList[4]);

            }else if("atk".equals(dataList[0])){   //开枪数据包
                double tmpx = Double.parseDouble(dataList[2]);
                double tmpy = Double.parseDouble(dataList[3]);
                double tmpdir = Double.parseDouble(dataList[4]);
                SoundEffect.play("/com/example/biubiu/mp3/gun.mp3");
                double bx = tmpx + 24;
                double by = tmpy + 24;
                EnemyBullet bullet = new EnemyBullet(bx,by,48,25,tmpdir,this.gs);
                enemybullets.add(bullet);
            }else if("hp".equals(dataList[0])){
                double tmpx = Double.parseDouble(dataList[2]);
                String tmpip = dataList[1];
                enemys.get(tmpip).hp = tmpx;
            }
        }
    }

    //网络类
    TalkSend[] send = new TalkSend[4];
    TalkReceive receive = new TalkReceive(8888,"老师",this);

    private void sendToAll(String data){
        for(int i = 0;i < numOfPlayer - 1;++i){
            if(send[i] != null){
                send[i].sendData(data);
            }
        }
    }

    private void paint(){
        background.paint(graphicsContext);
        selfPlayer.paint(graphicsContext);
        for(String key:enemys.keySet()){
            Enemy enemy = enemys.get(key);
            enemy.paint(graphicsContext);
        }

        for(Bullet bullet:bullets){

            for(String key:enemys.keySet()){
                if(bullet.alive == false) break;
                Enemy enemy = enemys.get(key);
                if(bullet.getContour().intersects(enemy.getContour())){
                    enemy.hp --;
                    bullet.alive = false;
                }
            }
            bullet.paint(graphicsContext);
        }
        for(EnemyBullet bullet:enemybullets){
            if(bullet.getContour().intersects(selfPlayer.getContour())){
                selfPlayer.hp --;
                bullet.alive = false;
            }
            bullet.paint(graphicsContext);
        }
    }

    public void init(Stage stage){
        for(int i = 1;i < numOfPlayer ;++i){
            Enemy tmpenemy = new Enemy(positionPlayer[i][0],positionPlayer[i][1],0,0,this);
            enemys.put(ips[i],tmpenemy);
            send[i - 1] = new TalkSend(6666 + i,ips[i],8888);
            new Thread(send[i - 1]).start();
        }

//        new Thread(send).start();
        new Thread(receive).start();
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        stage.getScene().setOnKeyReleased(keyProcess);
        stage.getScene().setOnKeyPressed(keyProcess);
        stage.getScene().setOnMouseClicked(mouseProcess);
        stage.getScene().setOnMouseMoved(mouseProcess);
        running=true;
        refresh.start();
    }

    public void clear(Stage stage){
        stage.getScene().removeEventHandler(KeyEvent.KEY_RELEASED,keyProcess);
        refresh.stop();
    }

    private class Refresh extends AnimationTimer{

        @Override
        public void handle(long l) {//每一帧的刷新会调用paint
            if(running){
                String tmpString = "loc|"+ ips[0]+ "|" + selfPlayer.x + "|" + selfPlayer.y + "|" + selfPlayer.weaponDir +
                        "|" + selfPlayer.height + "|" + selfPlayer.width + "|" + selfPlayer.imageMap.get("weapon") + "|";
                sendToAll(tmpString);
//                send.sendData(tmpString);
                String tmpString1 = "hp|"+ ips[0] +"|" + selfPlayer.hp;
                sendToAll(tmpString1);
//                send.sendData(tmpString);
                paint();
            }
        }
    }

    private class KeyProcess implements EventHandler<KeyEvent>{

        @Override
        public void handle(KeyEvent keyEvent) {
            KeyCode keyCode = keyEvent.getCode();
//            if(keyCode.equals(KeyCode.SPACE)){//按下空格键暂停
//                pauseOrContinue();
//            }
            if(keyEvent.getEventType() == KeyEvent.KEY_RELEASED){
                selfPlayer.released(keyCode);
            }else if(keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
                selfPlayer.pressed(keyCode);
            }
        }
    }

    private class MouseProcess implements EventHandler<MouseEvent>{

        @Override
        public void handle(MouseEvent mouseEvent) {
            double sx = mouseEvent.getSceneX();
            double sy = mouseEvent.getSceneY();
            selfPlayer.direct(sx,sy);
            if(mouseEvent.getEventType() == mouseEvent.MOUSE_CLICKED){
                selfPlayer.clicked();
                String tmpString = "atk|"+ ips[0] +"|" + selfPlayer.x + "|" + selfPlayer.y + "|" + selfPlayer.weaponDir +
                        "|" + "5" + "|";
                sendToAll(tmpString);
            }
        }
    }

    public void pauseOrContinue(){
        if(running){
            running = false;
        }else{
            running = true;
        }
    }

}
