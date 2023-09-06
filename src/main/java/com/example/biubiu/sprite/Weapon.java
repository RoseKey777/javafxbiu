package com.example.biubiu.sprite;

import com.example.biubiu.scene.GameScene;
import javafx.scene.image.Image;

public class Weapon extends Role{
    public Weapon(double x, double y, double width, double height, double dir, GameScene gameScene) {
        super(x, y, 32, 32, dir, gameScene);
        speed = 2;
        imageMap.put("walk",new Image(Player.class.getResource("/com/example/biubiu/image/role1.png").toExternalForm()));
    }

    @Override
    public void move() {

    }

    @Override
    public boolean impactChecking(Sprite sprite) {
        return false;
    }
}
