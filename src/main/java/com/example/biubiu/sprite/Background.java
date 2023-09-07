package com.example.biubiu.sprite;

import com.example.biubiu.scene.GameScene;
import com.example.biubiu.scene.Index;
import javafx.scene.image.Image;

public class Background extends Sprite{
    public Background() {
        super(new Image(Background.class.getResource("/com/example/biubiu/image/test01.png").toExternalForm()),0, 0 ,1024 ,1024);
    }

}
