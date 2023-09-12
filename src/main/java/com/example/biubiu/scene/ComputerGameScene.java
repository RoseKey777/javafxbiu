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

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ComputerGameScene {

    public int Tim[] = new int[1005];

    public int newdropid = 0;
    public int enemynum;
    private Canvas canvas = new Canvas(960,640);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    private ComputerGameScene.KeyProcess keyProcess = new ComputerGameScene.KeyProcess();

    private ComputerGameScene.MouseProcess mouseProcess = new ComputerGameScene.MouseProcess();
    private ComputerGameScene.Refresh refresh = new ComputerGameScene.Refresh();

    private boolean running  = false;//为false则停止刷新，为true则启动刷新

    private Background background = new Background();

    private State state = new State();
    public Player selfPlayer;

    private int gamemode;
    String []mpURL = {"","/com/example/biubiu/image/desert.png","/com/example/biubiu/image/ocean.png","/com/example/biubiu/image/forest.png"};

    public List<Bullet> bullets = new CopyOnWriteArrayList<>();

    public List<Enemy> enemys = new CopyOnWriteArrayList<>();

    public List<EnemyBullet> enemybullets = new CopyOnWriteArrayList<>();

    public List<Drop> drops = new ArrayList<>();

    public int[] enemynums = {2,3,4};

    private int [][]positionPlayer = {{32,32},{850,32},{32,850},{850,600}};

    private int [][]positionEnemy = {{32,500},{850,32},{32,400},{850,500},{32,450},{850,64},{32,430},{640,200}};

    private int bulletdamage[] = {0,1,3,2};

    public int ChaID[] = new int[9];

    public int WeaID[] = new int[9];

    public String[] username = new String[8];

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
                Drop drop = new Drop(dropURL[typ],xx,yy,typ,newdropid + 4);
                drops.add(drop);
                break;
            }
        }
    }

    public String[] dropURL= {"/com/example/biubiu/image/hp.png","/com/example/biubiu/image/danyao.png",
            "/com/example/biubiu/image/die6.png","/com/example/biubiu/image/bomb.png","/com/example/biubiu/image/ran.png"};

    public void init(Stage stage, int mode, String playername, int mapchoose, int chaID,int weaID){
        //拾取物绘制

        enemynum = enemynums[mode];

        ChaID[0] = chaID;
        WeaID[0] = weaID;

        gamemode = mode;

        for(int i = 0; i < enemynum; ++i){
            if(mode == 0){
                ChaID[i + 1] = 1;
                WeaID[i + 1] = 1;
            }else if(mode == 1){
                ChaID[i + 1] = 2;
                WeaID[i + 1] = 2;
            }else {
                ChaID[i + 1] = 3;
                WeaID[i + 1] = 3;
            }
        }
        selfPlayer = new Player(positionPlayer[0][0],positionPlayer[0][1],ChaID[0], WeaID[0],0,
                0.0,mapchoose,this);
        selfPlayer.username = playername;
        selfPlayer.alive = true;

        RandomCreatDrop(5);

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
            }else if(!drop.alive && drop.dieflag){
                drop.dieflag = false;
                newdropid ++;
                System.out.println(newdropid);
                Tim[newdropid] = 500;
            }
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


        for(Enemy enemy:enemys){
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

            for(Enemy enemy:enemys){
                if(!bullet.alive) break;
                if(!enemy.alive){
                    continue;
                }
                if(bullet.getContour().intersects(enemy.getContour()) && bullet.idd != enemy.idd && bullet.idd == 0){
                    int dmg = bulletdamage[selfPlayer.weaponid];
                    if(enemy.numOfwudi >= dmg){
                        enemy.numOfwudi -= dmg;
                    }else if(enemy.numOfwudi > 0 && enemy.numOfwudi < dmg){
                        enemy.hp -= (dmg - enemy.numOfwudi);
                        enemy.numOfwudi = 0;
                    } else {
                        enemy.hp -= dmg;
                    }
                    if(enemy.hp <= 0){
                        enemy.alive = false;
                        enemynum --;
                        if(enemynum == 0){
                            Director.getInstance().gameOver(true,gamemode + 1);
                            SoundEffect.play("/com/example/biubiu/mp3/win.mp3");
                        }
                        if(!selfPlayer.alive){
                            Director.getInstance().gameOver(false,gamemode + 1);
                            SoundEffect.play("/com/example/biubiu/mp3/gg.mp3");
                        }
                    }
                    if(enemy.alive && enemy.illegal(enemy.x + 5 * dmg * Math.cos(bullet.dir),
                            enemy.y - 5 * dmg * Math.sin(bullet.dir))){//击退特效
                        enemy.x += 5 * dmg * Math.cos(bullet.dir);
                        enemy.y -= 5 * dmg * Math.sin(bullet.dir);
                    }
                    bullet.alive = false;
                }
            }
            if(bullet.idd != 0 && selfPlayer != null && bullet.getContour().intersects(selfPlayer.getContour())){
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
                if(!selfPlayer.alive){
                    Director.getInstance().gameOver(false,gamemode + 1);
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
        for(EnemyBullet bullet:enemybullets){
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
                if(!selfPlayer.alive){
                    Director.getInstance().gameOver(false,gamemode + 1);
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

    public void gamepause(){
        if(running){
            running = false;
        }
        Image image = new Image(Player.class.getResource("/com/example/biubiu/image/gamepause.png").toExternalForm());
        graphicsContext.drawImage(image,240,200,480,240);
    }

    public void gamecontinue(){
        if(!running){
            running = true;
        }
        graphicsContext.clearRect(240,200,480,240);
    }
    private class KeyProcess implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent keyEvent) {
            KeyCode keyCode = keyEvent.getCode();
            if(keyCode.equals(KeyCode.ESCAPE) && running){
                gamepause();
            }
            if(keyCode.equals(KeyCode.N) && !running){
                gamecontinue();
            }
            if(keyCode.equals(KeyCode.Y) && !running){
                Director.getInstance().gameOver(false,888);
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
//                String tmpString = "atk|"+ selfIP +"|" + selfPlayer.x + "|" + selfPlayer.y + "|" + selfPlayer.weaponDir +
//                        "|" + "5" + "|";
//                sendToAll(tmpString);
            }
        }
    }

}
