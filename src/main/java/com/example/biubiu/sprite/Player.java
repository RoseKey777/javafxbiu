package com.example.biubiu.sprite;

import com.example.biubiu.Director;
import com.example.biubiu.scene.GameScene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Player extends Role{

    double weaponDir;//武器方向

    boolean keyup, keydown, keyleft, keyright;

    double sceneX,sceneY;

    public Player(double x, double y, double dir, double weaponDir, GameScene gameScene) {
        super(x, y, 32, 32, dir, gameScene);
        this.weaponDir = weaponDir;
        speed = 2;
        imageMap.put("walk",new Image(Player.class.getResource("/com/example/biubiu/image/role1.png").toExternalForm()));//video 7 diffrent
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

    @Override
    public void move() {
        if(dir == 1.0){
            x += speed;
        }else if(dir == 2.0){
            x += speed / Math.sqrt(2);
            y -= speed / Math.sqrt(2);
        }else if(dir == 3.0){
            y -= speed;
        }else if(dir == 4.0){
            y -= speed / Math.sqrt(2);
            x -= speed / Math.sqrt(2);
        }else if(dir == 5.0){
            x -= speed;
        }else if(dir == 6.0){
            y += speed / Math.sqrt(2);
            x -= speed / Math.sqrt(2);
        }else if(dir == 7.0){
            y += speed;
        }else if(dir == 8.0){
            x += speed / Math.sqrt(2);
            y += speed / Math.sqrt(2);
        }

        if(dir != 0){
            weaponDir = calc();//todo: 目前是武器和人物一个方向，需要修改武器360度转向
        }
        if(x < 0) x = 0;
        if(y < 0) y = 0;
        if(x > Director.WIDTH - width) x = Director.WIDTH - width;
        if(y > Director.HEIGHT - height) y = Director.HEIGHT - height;
    }

    @Override
    public void paint(GraphicsContext graphicsContext) {
        image = imageMap.get("walk");

        super.paint(graphicsContext);
        move();
    }

    public void openFire(){
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
