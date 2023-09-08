package com.example.biubiu.sprite;

import com.example.biubiu.scene.GameScene;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public abstract class Role extends Sprite{
    public boolean alive = true;
    double dir;
    double speed;
    public Map<String, Image> imageMap  =new HashMap<>();

    public Role(double x, double y, double width, double height, double dir,GameScene gameScene) {
        super(null, x, y, width, height, gameScene);
        this.dir = dir;
    }

    public abstract void move();

    public abstract boolean impactChecking(Sprite sprite);//碰撞检测

}
