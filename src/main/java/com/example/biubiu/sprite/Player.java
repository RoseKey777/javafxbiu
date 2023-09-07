package com.example.biubiu.sprite;

import com.example.biubiu.Director;
import com.example.biubiu.scene.GameScene;
import com.example.biubiu.util.SoundEffect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Player extends Role{

    Image weaponImage;
    private int count = 0;
    private int MOD = 7;

    private   int [][]block={
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
    };

    private static Image[] images = new Image[] {
//            new Image(Player.class.getResource("/com/example/biubiu/image/moverole1-0.gif").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/moverole1-1.gif").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/moverole1-2.gif").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/moverole1-3.gif").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/moverole1-4.gif").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/moverole1-5.gif").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/moverole1-2.gif").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/moverole1-6.gif").toExternalForm()),

    };
    double weaponDir;//武器方向

    boolean keyup, keydown, keyleft, keyright;

    double sceneX,sceneY;

    public Player(double x, double y, double dir, double weaponDir, GameScene gameScene) {
        super(x, y, 32, 32, dir, gameScene);
        this.weaponDir = weaponDir;
        speed = 2;
        imageMap.put("walk",new Image(Player.class.getResource("/com/example/biubiu/image/moverole1-0.gif").toExternalForm()));//video 7 diffrent
        imageMap.put("weapon",new Image(Player.class.getResource("/com/example/biubiu/image/ak47.png").toExternalForm()));
    }

    public void pressed(KeyCode keyCode){
        switch (keyCode){
            case W:
                keyup = true;
                break;
            case S:
                keydown = true;
                break;
            case A:
                keyleft = true;
                break;
            case D:
                keyright = true;
        }
        //每次键盘状态改变，调用重定向
        redirect();
    }

    private double calc(){
//        System.out.println(x);
//        System.out.println(sceneX);
//        System.out.println(y);
//        System.out.println(sceneY);
//        System.out.println(Math.atan(Math.abs(sceneY - y)/Math.abs(sceneX - x)));
        double tmp = Math.atan((sceneY - y - 16)/(sceneX - x - 16));
        if(sceneX < x){
            return Math.PI - tmp;
        }else {
            double tmp1 = Math.atan((y - sceneY + 16)/(sceneX - x - 16));
            return tmp1;
        }
//        return Math.atan((sceneY - y)/(sceneX - x));
    }

    public void released(KeyCode keyCode){
        switch (keyCode){
//            case SPACE:
//                openFire();
//                break;
            case W:
                keyup = false;
                break;
            case S:
                keydown = false;
                break;
            case A:
                keyleft = false;
                break;
            case D:
                keyright = false;
        }
        redirect();
    }

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
        imageMap.put("walk",images[count]);//video 7 diffrent
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

        if(block[realY][realX] == 10){//左上
            return false;
        }
        if(block[realYY][realXX] == 10){//右下
            return false;
        }
        if(block[realYYY][realXXX] == 10){//左下
            return false;
        }
        if(block[realYYYY][realXXXX] == 10){//右上
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
                count = (count + 1) % images.length;
                MOD = (MOD + 1) % 6;
                if(MOD== 0){
                    wakeChange();
                }
            }
        }else if(dir == 2.0){
            if(illegal(x+speed/Math.sqrt(2), y-speed/Math.sqrt(2))){
                x += speed/Math.sqrt(2);
                y -= speed/Math.sqrt(2);
                count = (count + 1) % images.length;
                MOD = (MOD + 1) % 6;
                if(MOD== 0){
                    wakeChange();
                }
            }
        }else if(dir == 3.0){
            if(illegal(x, y-speed)) {
                y -= speed;
                count = (count + 1) % images.length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else if(dir == 4.0){
            if(illegal(x-speed/Math.sqrt(2), y-speed/Math.sqrt(2))) {
                y -= speed / Math.sqrt(2);
                x -= speed / Math.sqrt(2);
                count = (count + 1) % images.length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else if(dir == 5.0){
            if(illegal(x-speed, y)) {
                x -= speed;
                count = (count + 1) % images.length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else if(dir == 6.0){
            if(illegal(x-speed/Math.sqrt(2), y+speed/Math.sqrt(2))) {
                y += speed / Math.sqrt(2);
                x -= speed / Math.sqrt(2);
                count = (count + 1) % images.length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else if(dir == 7.0){
            if(illegal(x, y+speed)) {
                y += speed;
                count = (count + 1) % images.length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else if(dir == 8.0){
            if(illegal(x+speed/Math.sqrt(2), y+speed/Math.sqrt(2))) {
                x += speed / Math.sqrt(2);
                y += speed / Math.sqrt(2);
                count = (count + 1) % images.length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else{
            count = 0;
            imageMap.put("walk",new Image(Player.class.getResource("/com/example/biubiu/image/moverole1-0.gif").toExternalForm()));//video 7 diffrent
        }
        if(dir != 0){
            weaponDir = dir;//todo: 目前是武器和人物一个方向，需要修改武器360度转向
        }
        if(x < 0) x = 0;
        if(y < 0) y = 0;
        if(x > Director.WIDTH - width) x = Director.WIDTH - width;
        if(y > Director.HEIGHT - height) y = Director.HEIGHT - height;
    }

    @Override
    public void paint(GraphicsContext graphicsContext) {
        image = imageMap.get("walk");
        weaponImage = imageMap.get("weapon");
        super.paint(graphicsContext);
        graphicsContext.save();
        graphicsContext.translate(x+16,y+16);
        graphicsContext.rotate(Math.toDegrees(-weaponDir));
        graphicsContext.drawImage(weaponImage,-16,-16,32,32);
//        graphicsContext.drawImage(image,-24,-12.5,width,height);
        graphicsContext.restore();
//        graphicsContext.drawImage(weaponImage,x+5,y+5,32,32);
        move();
    }

    public void openFire(){
        SoundEffect.play("/com/example/biubiu/mp3/gun.mp3");
        double bx = x + width/2;
        double by = y + height/2;
        weaponDir = calc();
        Bullet bullet = new Bullet(bx,by,48,25,weaponDir,gameScene);
        gameScene.bullets.add(bullet);
    }

    @Override
    public boolean impactChecking(Sprite sprite) {
        return false;
    }

    public void clicked() {
        openFire();
    }
}
