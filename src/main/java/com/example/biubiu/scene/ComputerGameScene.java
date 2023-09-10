package com.example.biubiu.scene;

import com.example.biubiu.Director;
import com.example.biubiu.sprite.*;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ComputerGameScene {

    public int enemynum;
    private Canvas canvas = new Canvas(1024,1024);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    private ComputerGameScene.KeyProcess keyProcess = new ComputerGameScene.KeyProcess();

    private ComputerGameScene.MouseProcess mouseProcess = new ComputerGameScene.MouseProcess();
    private ComputerGameScene.Refresh refresh = new ComputerGameScene.Refresh();

    private boolean running  = false;//为false则停止刷新，为true则启动刷新

    private Background background = new Background();

    private State state = new State();
    private Player selfPlayer;

    private int gamemode;
    String []mpURL = {"/com/example/biubiu/image/test01.png","/com/example/biubiu/image/test01.png","/com/example/biubiu/image/test01.png"};

    public List<Bullet> bullets = new CopyOnWriteArrayList<>();

    public List<Enemy> enemys = new CopyOnWriteArrayList<>();

    public List<EnemyBullet> enemybullets = new CopyOnWriteArrayList<>();

    public List<Drop> drops = new ArrayList<>();

    public int[] enemynums = {4,6,8};

    private int [][]positionPlayer = {{32,32},{950,32},{32,950},{950,950}};

    private int [][]positionEnemy = {{32,500},{950,32},{32,950},{950,950},{32,450},{950,64},{32,780},{640,950}};

    public int ChaID[] = new int[9];

    public int WeaID[] = new int[9];

    public String[] username = new String[8];

    public void init(Stage stage, int mode, String playername, int mapchoose, int chaID,int weaID){
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

        enemynum = enemynums[mode];

        ChaID[0] = chaID;
        WeaID[0] = weaID;

        gamemode = mode;

        for(int i = 0; i < enemynum; ++i){
            if(mode == 0){
                ChaID[i + 1] = 0;
                WeaID[i + 1] = 0;
            }else if(mode == 1){
                ChaID[i + 1] = 1;
                WeaID[i + 1] = 1;
            }else {
                ChaID[i + 1] = 2;
                WeaID[i + 1] = 2;
            }
        }
        selfPlayer = new Player(positionPlayer[0][0],positionPlayer[0][1],ChaID[0], WeaID[0],0,
                0.0,mapchoose,this);
        selfPlayer.username = playername;
        selfPlayer.alive = true;

        background.image = new Image(Background.class.getResource(mpURL[mapchoose]).toExternalForm());
        for(int i = 0;i < enemynum ;++i){
            Enemy tmpenemy = new Enemy(positionEnemy[i][0],positionEnemy[i][1],ChaID[i + 1],WeaID[i + 1],0,0, mapchoose,mode,
                    selfPlayer.x,selfPlayer.y,this);
            tmpenemy.alive = true;
            tmpenemy.username = "NPC";
            tmpenemy.idd = i + 1;
            enemys.add(tmpenemy);
//            enemys.put(ips[i],tmpenemy);
        }

        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_CLICKED, mouseProcess);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, mouseProcess);
        stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, keyProcess);
        stage.getScene().addEventHandler(KeyEvent.KEY_RELEASED, keyProcess);

        running=true;
        refresh.start();
    }

    private class Refresh extends AnimationTimer {

        @Override
        public void handle(long l) {//每一帧的刷新会调用paint
            if(running){
//                String tmpDropString = "drop|" + selfIP + "|";
//                for(Drop drop:drops){
//                    if(!drop.alive) tmpDropString = tmpDropString + drop.id + "|";
//                }

//                String tmpString = "loc|"+ selfIP+ "|" + selfPlayer.x + "|" + selfPlayer.y + "|" + selfPlayer.weaponDir +
//                        "|" + selfPlayer.height + "|" + selfPlayer.width + "|" + selfPlayer.imageMap.get("weapon") + "|" + selfPlayer.numOfwudi + "|";
//                sendToAll(tmpString);
////                send.sendData(tmpString);
//                String tmpString1 = "hp|"+ selfIP +"|" + selfPlayer.hp;
//                sendToAll(tmpString1);
////                send.sendData(tmpString);
//                sendToAll(tmpDropString);

                paint();
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


        for(Enemy enemy:enemys){
            if(enemy.alive){
                enemy.paint(graphicsContext);
            }
        }

        for(Bullet bullet:bullets){

            for(Enemy enemy:enemys){
                if(!bullet.alive) break;
                if(!enemy.alive){
                    continue;
                }
                if(bullet.getContour().intersects(enemy.getContour()) && bullet.idd != enemy.idd){
                    if(enemy.numOfwudi > 0){
                        enemy.numOfwudi --;
                    }else {
                        enemy.hp --;
                    }
                    if(enemy.hp == 0){
                        enemy.alive = false;
                        enemynum --;
                        if(enemynum == 0){
                            Director.getInstance().gameOver(true,gamemode + 1);
                        }
                        if(enemynum == 1 && !selfPlayer.alive){
                            Director.getInstance().gameOver(false,gamemode + 1);
                        }
                    }
                    bullet.alive = false;
                }
            }
            if(bullet.idd != 0 && selfPlayer != null && bullet.getContour().intersects(selfPlayer.getContour())){
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
                if(!selfPlayer.alive){
                    Director.getInstance().gameOver(false,gamemode + 1);
                }
                if(selfPlayer!=null && selfPlayer.illegal(selfPlayer.x + 5 * Math.cos(bullet.dir),selfPlayer.y - 5 * Math.sin(bullet.dir))){//击退特效
                    selfPlayer.x += 5 * Math.cos(bullet.dir);
                    selfPlayer.y -= 5 * Math.sin(bullet.dir);
                }
                bullet.alive = false;
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
                    Director.getInstance().gameOver(false,gamemode + 1);
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

    public void clear(Stage stage) {
        stage.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, keyProcess);
        stage.getScene().removeEventHandler(KeyEvent.KEY_RELEASED, keyProcess);
        stage.getScene().removeEventHandler(MouseEvent.MOUSE_CLICKED,mouseProcess);
        stage.getScene().removeEventHandler(MouseEvent.MOUSE_MOVED,mouseProcess);
//        selfPlayer.clicked();
        refresh.stop();
        selfPlayer = null;
//        for(TalkSend send1:send){
//            send1.datagramSocket.close();//todo
//        }
        bullets.clear();
        enemybullets.clear();
        enemys.clear();
        drops.clear();
    }

    private class KeyProcess implements EventHandler<KeyEvent> {

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
//                String tmpString = "atk|"+ selfIP +"|" + selfPlayer.x + "|" + selfPlayer.y + "|" + selfPlayer.weaponDir +
//                        "|" + "5" + "|";
//                sendToAll(tmpString);
            }
        }
    }

}
