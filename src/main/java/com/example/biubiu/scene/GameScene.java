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
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameScene {

    public int Tim[] = new int[1005];

    public int newdropid = 0;

    public String[] username = new String[4];
    public int numOfPlayer;

    public int enemynum;

    private int [][]positionPlayer = {{32,32},{850,32},{32,850},{850,600}};
    private int bulletdamage[] = {0,1,3,2};

    public String selfIP;//自己的IP

    public int selfNum;//自己的位置

    public int roomid;//房间号

    public int gamePort;//本局游戏使用的端口

    public int Mapchoose;//本局游戏选择地图

    public double mouseX,mouseY;
    private Canvas canvas = new Canvas(960,640);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    private KeyProcess keyProcess = new KeyProcess();

    private MouseProcess mouseProcess = new MouseProcess();
    private Refresh refresh = new Refresh();

    private boolean running  = false;//为false则停止刷新，为true则启动刷新

    private boolean socketFlag;

    private Background background = new Background();

    private State state = new State();
    private Player selfPlayer;

    String []mpURL = {"","/com/example/biubiu/image/desert.png","/com/example/biubiu/image/ocean.png","/com/example/biubiu/image/forest.png"};

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

        if(selfPlayer.alive && !selfPlayer.openfireflag && selfPlayer.timer < selfPlayer.weaponCD[selfPlayer.weaponid]){
            selfPlayer.timer ++;
            if(selfPlayer.timer == selfPlayer.weaponCD[selfPlayer.weaponid]){
                selfPlayer.openfireflag = true;
                selfPlayer.timer = 0;
            }
        }


        if(selfPlayer.alive && !selfPlayer.realDie){
            selfPlayer.paint(graphicsContext);
            selfPlayer.impact(drops);
        }

        for(int i = 0;i < drops.size(); ++i){
            Drop drop = drops.get(i);
            if(drop.alive){
                drop.paint(graphicsContext);
            }
//            else if(!drop.alive && drop.dieflag){
//                drop.dieflag = false;
//                newdropid ++;
//                Tim[newdropid] = 500;
//            }
        }

        for(int i = 1;i <= newdropid;++i){
            if(Tim[i] > 0) Tim[i]--;
            if(Tim[i] == 0){
                Tim[i] --;
                RandomCreatNewDrop();
            }
        }
//        if(!selfPlayer.alive){
//            selfPlayer
//        }


        for(String key:enemys.keySet()){
            Enemy enemy = enemys.get(key);
            if(enemy.alive){
                enemy.paint(graphicsContext);
                if(!enemy.openfireflag && enemy.timer < enemy.weaponCD[enemy.weaponid]){
                    enemy.timer ++;
                    if(enemy.timer == enemy.weaponCD[enemy.weaponid]){
                        enemy.openfireflag = true;
                        enemy.timer = 0;
                    }
                }
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
                    if(enemy.numOfwudi >= bulletdamage[selfPlayer.weaponid]){
                        enemy.numOfwudi -= bulletdamage[selfPlayer.weaponid];
                    }else if(enemy.numOfwudi > 0 && enemy.numOfwudi < bulletdamage[selfPlayer.weaponid]){
                        enemy.hp -= (bulletdamage[selfPlayer.weaponid] - enemy.numOfwudi);
                        enemy.numOfwudi = 0;
                    } else {
                        enemy.hp -= bulletdamage[selfPlayer.weaponid];
                    }

                    if(enemy.hp <= 0){
                        enemy.alive = false;
                        enemynum --;
                        if(enemynum == 0){
                            Director.getInstance().gameOver(true,0);
                            SoundEffect.play("/com/example/biubiu/mp3/win.mp3");
                        }
                        if(enemynum == 1 && !selfPlayer.alive){
                            Director.getInstance().gameOver(false,0);
                            SoundEffect.play("/com/example/biubiu/mp3/gg.mp3");
                        }
                    }
                    bullet.alive = false;
                }
            }
            bullet.paint(graphicsContext);
        }
        for(EnemyBullet bullet:enemybullets){
            if(!bullet.alive) continue;
            if(selfPlayer!=null && bullet.getContour().intersects(selfPlayer.getContour())){
                if(selfPlayer.alive == false){
                    continue;
                }
                int dmg = bulletdamage[bullet.bullettype];
                if(selfPlayer.hp>0) {
                    SoundEffect.play("/com/example/biubiu/mp3/pain.mp3");
                    if(selfPlayer.numOfwudi >= dmg){
                        selfPlayer.numOfwudi -= dmg;
                    }else if(selfPlayer.numOfwudi > 0 && selfPlayer.numOfwudi < dmg){
                        selfPlayer.hp -= (dmg - selfPlayer.numOfwudi);
                        selfPlayer.numOfwudi = 0;
                    } else {
                        selfPlayer.hp -= dmg;
                    }
                }

                if(selfPlayer.hp <= 0){
                    selfPlayer.alive = false;
                }
                if(enemynum == 1 && !selfPlayer.alive){
                    Director.getInstance().gameOver(false,0);
                    SoundEffect.play("/com/example/biubiu/mp3/gg.mp3");
                }
                if(selfPlayer!=null && selfPlayer.illegal(selfPlayer.x + 5 * dmg * Math.cos(bullet.dir),
                        selfPlayer.y - 5 * dmg * Math.sin(bullet.dir))){//击退特效
                    selfPlayer.x += 5 * dmg * Math.cos(bullet.dir);
                    selfPlayer.y -= 5 * dmg * Math.sin(bullet.dir);
                }
                bullet.alive = false;
            }
            bullet.paint(graphicsContext);
        }
    }

    public boolean illegal(double xx,double yy){
        return selfPlayer.illegal(xx,yy);
    }

    public void RandomCreatDrop(int num){
        Random random = new Random();
        for(int i = 0; i < num; ++i){
            int typ = random.nextInt(5);
            while(true){
                double xx = random.nextDouble(928);
                double yy = random.nextDouble(576);
                if(illegal(xx,yy)){
                    Drop drop = new Drop(dropURL[typ],xx,yy,typ,i);
                    drops.add(drop);
                    break;
                }
            }
        }
    }

    public void RandomCreatNewDrop(){
        Random random = new Random();
        int typ = random.nextInt(5);
        while(true){
            double xx = random.nextDouble(928);
            double yy = random.nextDouble(576);
            if(illegal(xx,yy)){
                int len = drops.size();
                Drop drop = new Drop(dropURL[typ],xx,yy,typ,len);
                drops.add(drop);
                String tmpString = "newdrop|"+ selfIP +"|" + xx + "|" + yy + "|" + typ +
                        "|" + len + "|";
                sendToAll(tmpString);
                break;
            }
        }
    }

    public String[] dropURL= {"/com/example/biubiu/image/hp.png","/com/example/biubiu/image/danyao.png",
            "/com/example/biubiu/image/die6.png","/com/example/biubiu/image/bomb.png","/com/example/biubiu/image/ran.png"};

    public void init(Stage stage,int total, int roomchair, int ChaID[], int WeaID[], String ips[],int mapchoose){//房间总人数,我是第几人(0开始,角色编号数组,武器编号数组,用户IP数组
        //拾取物绘制
        newdropid = 0;
        Drop drop1 = new Drop("/com/example/biubiu/image/hp.png",200,300,0,0);
        Drop drop2 = new Drop("/com/example/biubiu/image/hp.png",400,500,0,1);
        Drop drop3 = new Drop("/com/example/biubiu/image/danyao.png",800,400,1,2);
        Drop drop4 = new Drop("/com/example/biubiu/image/die6.png",700,300,2,3);
        Drop drop5 = new Drop("/com/example/biubiu/image/bomb.png",300,200,3,4);
        Drop drop6 = new Drop("/com/example/biubiu/image/ran.png",520,110,4,5);
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
        Mapchoose = mapchoose;
        background.image = new Image(Background.class.getResource(mpURL[mapchoose]).toExternalForm());

        selfPlayer = new Player(positionPlayer[roomchair][0],positionPlayer[roomchair][1],ChaID[roomchair], WeaID[roomchair],0,
                0.0,mapchoose,this);
        selfPlayer.username = username[roomchair];
        selfPlayer.alive = true;
        selfPlayer.NPCflag = 0;
//        RandomCreatDrop(5);

        for(int i = 0;i < numOfPlayer ;++i){
            if(i == roomchair) continue;//roomchair这个位置的是selfplayer
            Enemy tmpenemy = new Enemy(positionPlayer[i][0],positionPlayer[i][1],ChaID[i],WeaID[i],0,0, mapchoose,this);
            tmpenemy.alive = true;
            tmpenemy.username = username[i];
            tmpenemy.NPCflag = 0;
            enemys.put(ips[i],tmpenemy);
            send[i] = new TalkSend(gamePort + i + 1, ips[i], gamePort);
            new Thread(send[i]).start();
        }

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

    public void gamepause(){
        if(running){
            running = false;
        }
        Image image = new Image(Player.class.getResource("/com/example/biubiu/image/gameescape.png").toExternalForm());
        graphicsContext.drawImage(image,240,200,480,240);
    }

    public void gamecontinue(){
        if(!running){
            running = true;
        }
        graphicsContext.clearRect(240,200,480,240);
    }

    private class KeyProcess implements EventHandler<KeyEvent>{

        @Override
        public void handle(KeyEvent keyEvent) {
            KeyCode keyCode = keyEvent.getCode();
            if(keyCode.equals(KeyCode.ESCAPE)){
                gamepause();
            }
            if(keyCode.equals(KeyCode.N)){
                gamecontinue();
            }
            if(keyCode.equals(KeyCode.Y)){
                Director.getInstance().gameOver(false,889);
                SoundEffect.play("/com/example/biubiu/mp3/gg.mp3");
            }
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
                        "|" + selfPlayer.weaponid + "|";
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
                EnemyBullet bullet = new EnemyBullet(bx,by,48,25,tmpdir,Mapchoose,this.gs);
                bullet.bullettype = Integer.parseInt(dataList[5]);
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
            }else if("newdrop".equals(dataList[0])){
                double tmpx = Double.parseDouble(dataList[2]);
                double tmpy = Double.parseDouble(dataList[3]);
                int typp = Integer.parseInt(dataList[4]);
                int idd = Integer.parseInt(dataList[5]);
                Drop drop = new Drop(dropURL[typp],tmpx,tmpy,typp,idd);
                drops.add(drop);
            }
        }
    }
}
