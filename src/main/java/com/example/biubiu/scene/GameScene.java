package com.example.biubiu.scene;

import com.example.biubiu.Director;
import com.example.biubiu.sprite.*;
import com.example.biubiu.util.SoundEffect;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameScene {

//    public String[] ips = new String[4];
    public String[] username = new String[4];
    public int numOfPlayer;

    public int enemynum;

    private int [][]positionPlayer = {{32,32},{950,32},{32,950},{950,950}};

    public String selfIP;//自己的IP

    public int selfNum;//自己的位置

    public int roomid;//房间号

    public int gamePort;//本局游戏使用的端口

    public int mapChoose;//本局游戏选择地图

    public double mouseX,mouseY;
    private Canvas canvas = new Canvas(1024,1024);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    private KeyProcess keyProcess = new KeyProcess();

    private MouseProcess mouseProcess = new MouseProcess();
    private Refresh refresh = new Refresh();

    private boolean running  = false;//为false则停止刷新，为true则启动刷新

    private boolean socketFlag;

    private Background background = new Background();

    private State state = new State();
    private Player selfPlayer;

    String []mpURL = {"/com/example/biubiu/image/test01.png","/com/example/biubiu/image/test01.png","/com/example/biubiu/image/test01.png"};

//    private Enemy enemy = new Enemy(400,500,0, 0.0,this);

    public List<Bullet> bullets = new CopyOnWriteArrayList<>();

    public Map<String,Enemy> enemys = new HashMap<>();

    public List<EnemyBullet> enemybullets = new CopyOnWriteArrayList<>();

    public List<Drop> drops = new ArrayList<>();

    //网络类
    TalkSend[] send = new TalkSend[4];
    TalkReceive receive;

    private void sendToAll(String data){
        for(int i = 0;i < numOfPlayer;++i){
            if(i == selfNum) continue;
            if(send[i] != null){
                send[i].sendData(data);
            }
        }
    }

    private void paint(){
        background.paint(graphicsContext);
        state.hp = selfPlayer.hp;
        state.numOfbullet = selfPlayer.numOfbullet;
        state.speed = selfPlayer.speed;
        state.ChaID = selfPlayer.characterid;
        state.WeaID = selfPlayer.weaponid;
        state.paint(graphicsContext);

        for(int i = 0;i < drops.size(); ++i){
            Drop drop = drops.get(i);
            if(drop.alive){
                drop.paint(graphicsContext);
            }
        }

        if(selfPlayer.alive && !selfPlayer.realDie){
            selfPlayer.paint(graphicsContext);
            selfPlayer.impact(drops);
        }

//        if(!selfPlayer.alive){
//            selfPlayer
//        }


        for(String key:enemys.keySet()){
            Enemy enemy = enemys.get(key);
            if(enemy.alive){
                enemy.paint(graphicsContext);
            }
        }

        for(Bullet bullet:bullets){

            for(String key:enemys.keySet()){
                if(bullet.alive == false) break;
                Enemy enemy = enemys.get(key);
                if(enemy.alive == false){
                    continue;
                }
                if(bullet.getContour().intersects(enemy.getContour())){
                    if(enemy.numOfwudi > 0){
                        enemy.numOfwudi --;
                    }else {
                        enemy.hp --;
                    }
                    if(enemy.hp == 0){
                        enemy.alive = false;
                        enemynum --;
                        if(enemynum == 0){
                            Director.getInstance().gameOver(true);
                        }
                        if(enemynum == 1 && !selfPlayer.alive){
                            Director.getInstance().gameOver(false);
                        }
                    }
                    bullet.alive = false;
                }
            }
            bullet.paint(graphicsContext);
        }
        for(EnemyBullet bullet:enemybullets){
            if(selfPlayer!=null && bullet.getContour().intersects(selfPlayer.getContour())){
                if(selfPlayer.alive == false){
                    continue;
                }
                if(selfPlayer.hp>0) {
                    if(selfPlayer.numOfwudi > 0){
                        selfPlayer.numOfwudi --;
                    }else {
                        selfPlayer.hp --;
                    }
                }
                if(selfPlayer.hp == 0){
                    selfPlayer.alive = false;
                }
                if(enemynum == 1 && !selfPlayer.alive){
                    Director.getInstance().gameOver(false);
                }
                if(selfPlayer!=null && selfPlayer.illegal(selfPlayer.x + 5 * Math.cos(bullet.dir),selfPlayer.y - 5 * Math.sin(bullet.dir))){//击退特效
                    selfPlayer.x += 5 * Math.cos(bullet.dir);
                    selfPlayer.y -= 5 * Math.sin(bullet.dir);
                }
                bullet.alive = false;
            }
            bullet.paint(graphicsContext);
        }
    }

    public void init(Stage stage,int total, int roomchair, int ChaID[], int WeaID[], String ips[],int mapchoose){//房间总人数,我是第几人(0开始,角色编号数组,武器编号数组,用户IP数组
        //拾取物绘制
        Drop drop1 = new Drop("/com/example/biubiu/image/hp.png",200,300,0,0);
        Drop drop2 = new Drop("/com/example/biubiu/image/hp.png",400,500,0,1);
        Drop drop3 = new Drop("/com/example/biubiu/image/加号.png",800,600,1,2);
        Drop drop4 = new Drop("/com/example/biubiu/image/加号.png",700,300,1,3);
        Drop drop5 = new Drop("/com/example/biubiu/image/die6.png",300,600,2,4);
        Drop drop6 = new Drop("/com/example/biubiu/image/die6.png",520,110,2,5);
        drops.add(drop1);
        drops.add(drop2);
        drops.add(drop3);
        drops.add(drop4);
        drops.add(drop5);
        drops.add(drop6);

        numOfPlayer = total;
        enemynum = numOfPlayer - 1;
        selfNum = roomchair;
        socketFlag = true;
        background.image = new Image(Background.class.getResource(mpURL[mapchoose]).toExternalForm());
        for(int i = 0;i < numOfPlayer ;++i){
            if(i == roomchair) continue;//roomchair这个位置的是selfplayer
            Enemy tmpenemy = new Enemy(positionPlayer[i][0],positionPlayer[i][1],ChaID[i],WeaID[i],0,0, mapchoose,this);
            tmpenemy.alive = true;
            tmpenemy.username = username[i];
            enemys.put(ips[i],tmpenemy);
            send[i] = new TalkSend(gamePort + i + 1, ips[i], gamePort);
            new Thread(send[i]).start();
        }
        selfPlayer = new Player(positionPlayer[roomchair][0],positionPlayer[roomchair][1],ChaID[roomchair], WeaID[roomchair],0,
                0.0,mapchoose,this);
        selfPlayer.username = username[roomchair];
        selfIP = ips[roomchair];
        receive = new TalkReceive(gamePort ,"老师",this);
        new Thread(receive).start();
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_CLICKED, mouseProcess);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, mouseProcess);
        stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, keyProcess);
        stage.getScene().addEventHandler(KeyEvent.KEY_RELEASED, keyProcess);

        running=true;
        refresh.start();
    }

    public void clear(Stage stage) {
        stage.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, keyProcess);
        stage.getScene().removeEventHandler(KeyEvent.KEY_RELEASED, keyProcess);
        stage.getScene().removeEventHandler(MouseEvent.MOUSE_CLICKED,mouseProcess);
        stage.getScene().removeEventHandler(MouseEvent.MOUSE_MOVED,mouseProcess);
//        selfPlayer.clicked();
        refresh.stop();
        selfPlayer = null;
        socketFlag = false;
//        for(TalkSend send1:send){
//            send1.datagramSocket.close();//todo
//        }
        bullets.clear();
        enemybullets.clear();
        enemys.clear();
        drops.clear();
    }

    private class Refresh extends AnimationTimer{

        @Override
        public void handle(long l) {//每一帧的刷新会调用paint
            if(running){
                String tmpDropString = "drop|" + selfIP + "|";
                for(Drop drop:drops){
                    if(!drop.alive) tmpDropString = tmpDropString + drop.id + "|";
                }

                String tmpString = "loc|"+ selfIP+ "|" + selfPlayer.x + "|" + selfPlayer.y + "|" + selfPlayer.weaponDir +
                        "|" + selfPlayer.height + "|" + selfPlayer.width + "|" + selfPlayer.imageMap.get("weapon") + "|" + selfPlayer.numOfwudi + "|";
                sendToAll(tmpString);
//                send.sendData(tmpString);
                String tmpString1 = "hp|"+ selfIP +"|" + selfPlayer.hp;
                sendToAll(tmpString1);
//                send.sendData(tmpString);
                sendToAll(tmpDropString);

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
                String tmpString = "atk|"+ selfIP +"|" + selfPlayer.x + "|" + selfPlayer.y + "|" + selfPlayer.weaponDir +
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

    //发送信息
    public class TalkSend implements Runnable {

        public DatagramSocket datagramSocket =null;
        BufferedReader bufferedReader =null;
        DatagramPacket datagramPacket = null;

        private int fromPort;
        private String toIP;
        private int toPort;


        public TalkSend(int fromPort,String toIP,int toPort){
            this.fromPort=fromPort;
            this.toIP=toIP;
            this.toPort=toPort;

            try {
                System.out.println("绑定端口："+fromPort);
                datagramSocket = new DatagramSocket(this.fromPort);
                bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            try {
                //控制台读取数据
                while (true){
                    String data = bufferedReader.readLine();
                    sendData(data);
                    if(!socketFlag){
                        datagramSocket.close();
                        System.out.println("结束23213");
                        return;
                    }
                }

            } catch (SocketException e) {
                throw new RuntimeException("发送错误");
            } catch (IOException e) {
                throw new RuntimeException("信息读取错误");
            }finally{
                if(!socketFlag){
                    datagramSocket.close();
                    System.out.println("结束23213");
                    return;
                }
            }

        }

        //发送数据
        public void sendData(String data){
            try {
                byte[] bytes = data.getBytes();
                datagramPacket = new DatagramPacket(bytes,0,bytes.length,new InetSocketAddress(this.toIP,this.toPort));
                datagramSocket.send(datagramPacket);
            }catch (SocketException e) {
                throw new RuntimeException("发送错误");
            } catch (IOException e) {
                throw new RuntimeException("信息读取错误");
            }

        }
    }

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

            while (socketFlag){
//                System.out.println(socketFlag);
                try {
                    byte[] bytes = new byte[1024];
                    DatagramPacket datagramPacket = new DatagramPacket(bytes, 0, bytes.length);
                    datagramSocket.receive(datagramPacket);
                    byte[] data = datagramPacket.getData();
                    String s = new String(data, 0, datagramPacket.getLength());
                    handleData(s.trim());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    if(socketFlag == false){
                        System.out.println("结束");
                        datagramSocket.close();
                    }
                }
            }
            System.out.println("结束");
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
                enemys.get(tmpip).numOfwudi = Integer.parseInt(dataList[8]);
            }else if("atk".equals(dataList[0])){   //开枪数据包
                double tmpx = Double.parseDouble(dataList[2]);
                double tmpy = Double.parseDouble(dataList[3]);
                double tmpdir = Double.parseDouble(dataList[4]);
                SoundEffect.play("/com/example/biubiu/mp3/gun.mp3");
                double bx = tmpx + 16;
                double by = tmpy + 16;
                EnemyBullet bullet = new EnemyBullet(bx,by,48,25,tmpdir,this.gs);
                enemybullets.add(bullet);
//                System.out.println(999999);
            }else if("hp".equals(dataList[0])){
                double tmpx = Double.parseDouble(dataList[2]);
                String tmpip = dataList[1];
                enemys.get(tmpip).hp = tmpx;
            }else if("drop".equals(dataList[0])){
                for(int i = 2; i < dataList.length;++i){
                    int dropid = Integer.parseInt(dataList[i]);
                    drops.get(dropid).alive = false;
                }
            }
        }
    }
}
