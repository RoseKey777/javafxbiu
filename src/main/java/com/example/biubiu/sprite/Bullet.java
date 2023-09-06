package com.example.biubiu.sprite;

import com.example.biubiu.Director;
import com.example.biubiu.scene.GameScene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet extends Role{
    public Bullet(double x, double y, double width, double height, double dir, GameScene gameScene) {
        super(x, y, width, height, dir, gameScene);
        speed = 10;
        image = new Image(Bullet.class.getResource("/com/example/biubiu/image/missile.png").toExternalForm());
    }

    @Override
    public void move() {
//
//        if(dir >= 0 && dir <= Math.PI/2){
            x += speed * Math.cos(dir);
            y -= speed * Math.sin(dir);
//        }else if(dir >= Math.PI/2 && dir <= Math.PI){
//            x -= speed * Math.cos(dir);
//            y -= speed * Math.sin(dir);
//        }else if(dir >= Math.PI && dir <= Math.PI*1.5){
//            x -= speed * Math.cos(dir);
//            y += speed * Math.sin(dir);
//        }else{
//            x += speed * Math.cos(dir);
//            y += speed * Math.sin(dir);
//        }



//        if(dir == 1.0){//这里暂时用的是角色角度
//            x += speed;
//        }else if(dir == 2.0){
//            x += speed;
//            y -= speed;
//        }else if(dir == 3.0){
//            y -= speed;
//        }else if(dir == 4.0){
//            y -= speed;
//            x -= speed;
//        }else if(dir == 5.0){
//            x -= speed;
//        }else if(dir == 6.0){
//            y += speed;
//            x -= speed;
//        }else if(dir == 7.0){
//            y += speed;
//        }else if(dir == 8.0){
//            x += speed;
//            y += speed;
//        }
//todo:子弹越出边界后应该删除，还未写
//        if(x < 0) x = 0;
//        if(y < 0) y = 0;
//        if(x > Director.WIDTH - width) x = Director.WIDTH - width;
//        if(y > Director.HEIGHT - height) y = Director.HEIGHT - height;
    }

    @Override
    public void paint(GraphicsContext graphicsContext) {

//        graphicsContext.drawImage(image,x,y,width,height);
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
