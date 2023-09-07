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
