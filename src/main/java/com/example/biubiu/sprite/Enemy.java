package com.example.biubiu.sprite;

import com.example.biubiu.Director;
import com.example.biubiu.scene.ComputerGameScene;
import com.example.biubiu.scene.GameScene;
import com.example.biubiu.util.SoundEffect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

import java.util.Random;

public class Enemy extends Role{

    public int idd;
    public int mapChoose;
    public String username;
    public boolean realDie;
    public int numOfwudi;
    Image weaponImage;
    private int count = 0;

    private int cnt = 0;

    private int countDie = 0;
    private int MOD = 7;

    public int characterid = 0;

    public int weaponid = 0;
    public double hp;

    public int NPCflag;

    public int NPCmode;

    public double playerX;
    public double playerY;

    private int [][][]block={
            {},
            //0号地图
            {
                    {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 10, 0, 0, 0, 10, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 10, 0, 0, 0, 10, 0, 0, 0, 0, 10, 0, 0, 10, 10, 10, 10, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 10, 0, 0, 0, 10, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 10, 10, 10, 10, 0, 0, 10, 10, 0, 0, 0, 10, 0, 0, 0, 10, 0, 0, 0, 0, 10, 0, 0, 10, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 10, 0, 0, 0, 0, 10, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 10, 0, 0, 0, 0, 10, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 10},
                    {10, 0, 0, 0, 0, 0, 10, 10, 10, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 10, 10, 10},
                    {10, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10, 10, 10, 0, 0, 0, 10},
                    {10, 10, 10, 10, 10, 10, 10, 0, 0, 0, 0, 0, 10, 0, 0, 10, 10, 10, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 10, 10, 10, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 10, 10, 0, 0, 0, 0, 0, 0, 10, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 10, 10, 10, 10, 10, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 10, 10, 10, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10, 10, 10, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 10, 0, 0, 0, 10, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 10, 0, 0, 0, 10, 10, 10, 10, 10, 0, 0, 10, 0, 0, 0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 0, 10},
                    {10, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 10, 10, 10, 10, 10, 10, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 10, 10, 10, 10, 10, 10, 0, 0, 0, 10, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 10, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 10, 0, 0, 10, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
            }
    };

    private static Image[][] images = new Image[][] {
            {},
            {

                    new Image(Enemy.class.getResource("/com/example/biubiu/image/moverole1-1.gif").toExternalForm()),

                    new Image(Enemy.class.getResource("/com/example/biubiu/image/moverole1-2.gif").toExternalForm()),

                    new Image(Enemy.class.getResource("/com/example/biubiu/image/moverole1-3.gif").toExternalForm()),

                    new Image(Enemy.class.getResource("/com/example/biubiu/image/moverole1-4.gif").toExternalForm()),

                    new Image(Enemy.class.getResource("/com/example/biubiu/image/moverole1-5.gif").toExternalForm()),

                    new Image(Enemy.class.getResource("/com/example/biubiu/image/moverole1-2.gif").toExternalForm()),

                    new Image(Enemy.class.getResource("/com/example/biubiu/image/moverole1-6.gif").toExternalForm()),
            }

    };

    private static Image[] dieImages = new Image[] {
//            new Image(Player.class.getResource("/com/example/biubiu/image/moverole1-0.gif").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die1.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die2.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die3.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die4.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die5.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die6.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die7.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die8.png").toExternalForm()),

    };

    public double weaponDir;//武器方向

    boolean keyup, keydown, keyleft, keyright;

    double sceneX,sceneY;

    private String[] chaURL = {"","/com/example/biubiu/image/moverole1-0.gif","/com/example/biubiu/image/moverole1-0.gif",
            "/com/example/biubiu/image/moverole1-0.gif"};

    private String[] weaURL = {"","/com/example/biubiu/image/ak47.png","/com/example/biubiu/image/awm.png",
            "/com/example/biubiu/image/Kar98k.png"};

    private double spd[] = {0, 2, 4, 1.5};
    private double hps[] = {0, 10, 8, 12};
    private double bulletspeed[] = {0,5,7,8};

    public void dressup(int chaID,int weaID){
        speed = spd[chaID];
        hp = hps[chaID];
        imageMap.put("walk",new Image(Player.class.getResource(chaURL[chaID]).toExternalForm()));
        imageMap.put("weapon",new Image(Player.class.getResource(weaURL[weaID]).toExternalForm()));
    }

    public Enemy(double x, double y, int chaID, int weaID, double dir, double weaponDir, int mapchoose, GameScene gameScene) {
        super(x, y, 32, 32, dir, gameScene);
        this.weaponDir = weaponDir;
        characterid = chaID;
        weaponid = weaID;
        mapChoose = mapchoose;
        NPCflag = 0;

        realDie = false;
        dressup(chaID,weaID);
    }

    public Enemy(double x, double y, int chaID, int weaID, double dir, double weaponDir, int mapchoose,int npcmode,
                 double playerx, double playery, ComputerGameScene gameScene) {
        super(x, y, 32, 32, dir, gameScene);
        this.weaponDir = weaponDir;
        characterid = chaID;
        weaponid = weaID;
        mapChoose = mapchoose;
        NPCflag = 1;//人机flag
        NPCmode = npcmode;
        playerX = playerx;
        playerY = playery;

        realDie = false;
        speed = spd[chaID];
        hp = hps[chaID] - 6;
        imageMap.put("walk",new Image(Player.class.getResource(chaURL[chaID]).toExternalForm()));
        imageMap.put("weapon",new Image(Player.class.getResource(weaURL[weaID]).toExternalForm()));
    }

//    public void pressed(KeyCode keyCode){
//        switch (keyCode){
//            case W:
//                keyup = true;
//                break;
//            case S:
//                keydown = true;
//                break;
//            case A:
//                keyleft = true;
//                break;
//            case D:
//                keyright = true;
//        }
//        //每次键盘状态改变，调用重定向
//        redirect();
//    }

    private double calc(){
        double tmp = Math.atan((sceneY - y - 16)/(sceneX - x - 16));
        if(sceneX < x){
            return Math.PI - tmp;
        }else {
            double tmp1 = Math.atan((y - sceneY + 16)/(sceneX - x - 16));
            return tmp1;
        }
    }

    public double calcNPC(){
        playerX = computerGameScene.selfPlayer.x;
        playerY = computerGameScene.selfPlayer.y;
        double tmp = Math.atan((playerY - y - 16)/(playerX - x - 16));
        if(playerX < x){
            return Math.PI - tmp;
        }else {
            double tmp1 = Math.atan((y - playerY + 16)/(playerX - x - 16));
            return tmp1;
        }
    }

//    public void released(KeyCode keyCode){
//        switch (keyCode){
////            case SPACE:
////                openFire();
////                break;
//            case W:
//                keyup = false;
//                break;
//            case S:
//                keydown = false;
//                break;
//            case A:
//                keyleft = false;
//                break;
//            case D:
//                keyright = false;
//        }
//        redirect();
//    }

    public void direct(double sx, double sy){
        sceneX = sx;
        sceneY = sy;
        weaponDir = calc();
    }

    public void redirect(){
        if(keyup && !keydown && !keyleft && !keyright){
            dir = 3.0;
        }
        else if(!keyup && keydown && !keyleft && !keyright){
            dir = 7.0;
        }
        else if(!keyup && !keydown && keyleft && !keyright){
            dir = 5.0;
        }
        else if(!keyup && !keydown && !keyleft && keyright){
            dir = 1.0;
        }
        else if(keyup && !keydown && !keyleft && keyright){
            dir = 2.0;
        }
        else if(keyup && !keydown && keyleft && !keyright){
            dir = 4.0;
        }
        else if(!keyup && keydown && keyleft && !keyright){
            dir = 6.0;
        }
        else if(!keyup && keydown && !keyleft && keyright){
            dir = 8.0;
        }
        else if(!keyup && !keydown && !keyleft && !keyright){//啥方向都没按 停
            dir = 0.0;
        }
        if(dir != 0){
            weaponDir = calc();//todo: 目前是武器和人物一个方向，需要修改武器360度转向
        }
    }
    public void wakeChange(){
        imageMap.put("walk",images[characterid][count]);//video 7 diffrent
    }

    public void dieChange(){
        imageMap.put("walk",dieImages[countDie]);
    }

    public boolean illegal(double xx,double yy){
        int realX = (int)Math.floor((xx+5)/32.0);
        int realY = (int)Math.floor((yy+10)/32.0);
        int realXX = (int)Math.floor((xx+24)/32.0);
        int realYY = (int)Math.floor((yy+32)/32.0);
        int realXXX = (int)Math.floor((xx+5)/32.0);
        int realYYY = (int)Math.floor((yy+32)/32.0);
        int realXXXX = (int)Math.floor((xx+24)/32.0);
        int realYYYY = (int)Math.floor((yy+10)/32.0);

        if(block[mapChoose][realY][realX] == 10){//左上
            return false;
        }
        if(block[mapChoose][realYY][realXX] == 10){//右下
            return false;
        }
        if(block[mapChoose][realYYY][realXXX] == 10){//左下
            return false;
        }
        if(block[mapChoose][realYYYY][realXXXX] == 10){//右上
            return false;
        }

        return true;
    }

    @Override
    public void move() {

//        System.out.println("x = " + x);
//        System.out.println("y = " + y);
        if(dir == 1.0){
            if(illegal(x+speed,y)){
                x += speed;
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if(MOD== 0){
                    wakeChange();
                }
            }
        }else if(dir == 2.0){
            if(illegal(x+speed/Math.sqrt(2), y-speed/Math.sqrt(2))){
                x += speed/Math.sqrt(2);
                y -= speed/Math.sqrt(2);
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if(MOD== 0){
                    wakeChange();
                }
            }
        }else if(dir == 3.0){
            if(illegal(x, y-speed)) {
                y -= speed;
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else if(dir == 4.0){
            if(illegal(x-speed/Math.sqrt(2), y-speed/Math.sqrt(2))) {
                y -= speed / Math.sqrt(2);
                x -= speed / Math.sqrt(2);
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else if(dir == 5.0){
            if(illegal(x-speed, y)) {
                x -= speed;
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else if(dir == 6.0){
            if(illegal(x-speed/Math.sqrt(2), y+speed/Math.sqrt(2))) {
                y += speed / Math.sqrt(2);
                x -= speed / Math.sqrt(2);
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else if(dir == 7.0){
            if(illegal(x, y+speed)) {
                y += speed;
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else if(dir == 8.0){
            if(illegal(x+speed/Math.sqrt(2), y+speed/Math.sqrt(2))) {
                x += speed / Math.sqrt(2);
                y += speed / Math.sqrt(2);
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else{
            count = 0;
            if(!alive && !realDie) {
                dieChange();
                countDie++;
                if(countDie == 7) realDie = true;
            }
            if(alive) {
                imageMap.put("walk",new Image(Player.class.getResource(chaURL[characterid]).toExternalForm()));//video 7 diffrent
            }
        }
        if(dir != 0){
            weaponDir = dir;//todo: 目前是武器和人物一个方向，需要修改武器360度转向
        }
        if(x < 0) x = 0;
        if(y < 0) y = 0;
        if(x > Director.WIDTH - width) x = Director.WIDTH - width;
        if(y > Director.HEIGHT - height) y = Director.HEIGHT - height;
    }

    public int randomdir(int dd){
        Random random = new Random();
        while (true){
            int tmp = random.nextInt(9);
            if(tmp != dd) return tmp;
        }
    }

    public void NPCmove() {
        Random random = new Random();
        int dirr =random.nextInt(60) ;
        double weadir = random.nextDouble(Math.PI*2);

        if(NPCmode == 0){
            if(dirr == 1)
            {
                weaponDir = weadir;
                openFire();
            }
        }else if(NPCmode == 1){
            if(dirr == 1 || dirr == 2)
            {
                weaponDir = weadir;
                openFire();
            }
        }else  {
            if(dirr == 1 || dirr == 2 || dirr == 3)
            {
                weaponDir = calcNPC();
                openFire();
            }
        }

        double spd = speed;
        if(dir == 1){
            if(illegal(x+spd,y)){
                x += spd;
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if(MOD== 0){
                    wakeChange();
                }
            }else{
                dir = randomdir(1);
            }
        }else if(dir == 2){
            if(illegal(x+spd/Math.sqrt(2), y-spd/Math.sqrt(2))){
                x += spd/Math.sqrt(2);
                y -= spd/Math.sqrt(2);
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if(MOD== 0){
                    wakeChange();
                }
            }else{
                dir = randomdir(2);
            }
        }else if(dir == 3){
            if(illegal(x, y-spd)) {
                y -= spd;
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }else{
                dir = randomdir(3);
            }
        }else if(dir == 4){
            if(illegal(x-spd/Math.sqrt(2), y-spd/Math.sqrt(2))) {
                y -= spd / Math.sqrt(2);
                x -= spd / Math.sqrt(2);
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }else{
                dir = randomdir(4);
            }
        }else if(dir == 5){
            if(illegal(x-spd, y)) {
                x -= spd;
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }else{
                dir = randomdir(5);
            }
        }else if(dir == 6){
            if(illegal(x-spd/Math.sqrt(2), y+spd/Math.sqrt(2))) {
                y += spd / Math.sqrt(2);
                x -= spd / Math.sqrt(2);
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }else{
                dir = randomdir(6);
            }
        }else if(dir == 7){
            if(illegal(x, y+spd)) {
                y += spd;
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }else{
                dir = randomdir(7);
            }
        }else if(dir == 8){
            if(illegal(x+spd/Math.sqrt(2), y+spd/Math.sqrt(2))) {
                x += spd / Math.sqrt(2);
                y += spd / Math.sqrt(2);
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }else{
                dir = randomdir(8);
            }
        }else{
            count = 0;
            if(!alive && !realDie) {
                dieChange();
                countDie++;
                if(countDie == 7) realDie = true;
            }
            if(alive) {
                imageMap.put("walk",new Image(Player.class.getResource(chaURL[characterid]).toExternalForm()));//video 7 diffrent
                cnt++;
                if(cnt == 12){
                    cnt = 0;
                    dir = randomdir(0);
                }
            }
        }

        if(x < 0) x = 0;
        if(y < 0) y = 0;
        if(x > Director.WIDTH - width) x = Director.WIDTH - width;
        if(y > Director.HEIGHT - height) y = Director.HEIGHT - height;
    }

    public void paintHP(GraphicsContext graphicsContext){
        for(int i = 1; i <= hp;++i){
            Image image1 = new Image(Enemy.class.getResource("/com/example/biubiu/image/hp.png").toExternalForm());
            graphicsContext.drawImage(image1,x-32 + 16 * (i-1),y-20,16,16);
        }
    }

    @Override
    public void paint(GraphicsContext graphicsContext) {
        image = imageMap.get("walk");
        weaponImage = imageMap.get("weapon");
        super.paint(graphicsContext);
        paintHP(graphicsContext);
        if(alive){
            if(numOfwudi > 0){
                Image image1 = new Image(Player.class.getResource("/com/example/biubiu/image/shield.png").toExternalForm());
                graphicsContext.drawImage(image1,x,y,32,32);
            }
            graphicsContext.save();
            graphicsContext.translate(x+16,y+16);
            graphicsContext.rotate(Math.toDegrees(-weaponDir));
            Image image1 = weaponImage;

            if((weaponDir) > Math.PI/2 && (weaponDir) < Math.PI * 1.5){
//                Scale horizontalScale = new Scale(-1, 1);
                // 创建一个WritableImage，大小与原始图片相同
                WritableImage mirroredImage = new WritableImage((int) weaponImage.getWidth(), (int) weaponImage.getHeight());

                // 获取PixelReader和PixelWriter
                PixelReader pixelReader = weaponImage.getPixelReader();
                PixelWriter pixelWriter = mirroredImage.getPixelWriter();

                // 镜像原始图片的像素并写入新的WritableImage
                int width = (int) weaponImage.getWidth();
                int height = (int) weaponImage.getHeight();
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int sourceX = width - x - 1; // 水平镜像
                        int color = pixelReader.getArgb(sourceX, y);
                        pixelWriter.setArgb(x, y, color);
                    }
                }

                // 创建一个ImageView来显示镜像后的图片
                ImageView imageView = new ImageView(mirroredImage);
                image1 = imageView.getImage();
                graphicsContext.rotate(Math.toDegrees(Math.PI));
            }

            graphicsContext.drawImage(image1,-16,-16,32,32);
            graphicsContext.restore();

            graphicsContext.setFill(Color.GRAY);
            graphicsContext.setFont(javafx.scene.text.Font.font("幼圆", FontWeight.BOLD,16));
            graphicsContext.fillText(this.username, x, y + 50);

            if(NPCflag == 0) {
                move();
            }else {
                NPCmove();
            }
        }
    }
    public void speedchange(double bx,double by,int weaponid){
        Bullet bullet = new Bullet(bx,by, bulletspeed[weaponid],48,25,weaponDir,mapChoose,gameScene);
        bullet.NPCflag = 0;
        gameScene.bullets.add(bullet);
    }

    public void newspeedchange(double bx,double by,int weaponid){
        Bullet bullet = new Bullet(bx,by, bulletspeed[weaponid],48,25,weaponDir,mapChoose,computerGameScene);
        bullet.NPCflag = idd;
        bullet.idd = idd;
        computerGameScene.bullets.add(bullet);
    }

    public void openFire(){
        SoundEffect.play("/com/example/biubiu/mp3/gun.mp3");
        double bx = x + width/2;
        double by = y + height/2;
        if(NPCflag == 0){
            speedchange(bx, by, weaponid);
        }else {
            newspeedchange(bx, by,weaponid);
        }

    }

    @Override
    public boolean impactChecking(Sprite sprite) {
        return false;
    }

    public void clicked() {
        openFire();
    }
}
