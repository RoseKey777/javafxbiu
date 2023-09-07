package com.example.biubiu.sprite;

import com.example.biubiu.Director;
import com.example.biubiu.scene.GameScene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet extends Role{
    public Bullet(double x, double y, double width, double height, double dir, GameScene gameScene) {
        super(x, y, width, height, dir, gameScene);
        speed = 5;
        image = new Image(Bullet.class.getResource("/com/example/biubiu/image/missile.png").toExternalForm());
    }

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

    public boolean illegal(double xx,double yy){
        int realX = (int)Math.floor((xx+5)/32.0);
        int realY = (int)Math.floor((yy+5)/32.0);
        int realXX = (int)Math.floor((xx+10)/32.0);
        int realYY = (int)Math.floor((yy+10)/32.0);
        int realXXX = (int)Math.floor((xx+5)/32.0);
        int realYYY = (int)Math.floor((yy+10)/32.0);
        int realXXXX = (int)Math.floor((xx+10)/32.0);
        int realYYYY = (int)Math.floor((yy+5)/32.0);
//        int realXX = (int)Math.floor((xx+40)/32.0);
//        int realYY = (int)Math.floor((yy+15)/32.0);
//        int realXXX = (int)Math.floor((xx+5)/32.0);
//        int realYYY = (int)Math.floor((yy+15)/32.0);
//        int realXXXX = (int)Math.floor((xx+40)/32.0);
//        int realYYYY = (int)Math.floor((yy+5)/32.0);

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
        if(!illegal(x,y)){
            alive = false;
            return;
        }

        x += speed * Math.cos(dir);
        y -= speed * Math.sin(dir);


//        todo:子弹越出边界后应该删除，还未写
//        if(x < 0) x = 0;
//        if(y < 0) y = 0;
//        if(x > Director.WIDTH - width) x = Director.WIDTH - width;
//        if(y > Director.HEIGHT - height) y = Director.HEIGHT - height;
    }

    @Override
    public void paint(GraphicsContext graphicsContext) {

//        graphicsContext.drawImage(image,x,y,width,height);
        if(!alive){
            gameScene.bullets.remove(this);
            return;
        }

        graphicsContext.save();
        graphicsContext.translate(x,y);
        graphicsContext.rotate(Math.toDegrees(-dir));
        graphicsContext.drawImage(image,-24,-12.5,width,height);
        graphicsContext.restore();
        move();
    }

    @Override
    public boolean impactChecking(Sprite sprite) {
        return false;
    }
}
