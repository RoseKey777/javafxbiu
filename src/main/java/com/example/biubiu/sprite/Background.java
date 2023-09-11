package com.example.biubiu.sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Background extends Sprite{

    public Background() {
        super(new Image(Background.class.getResource("/com/example/biubiu/image/desert.png").toExternalForm()),0, 0 ,960 ,640);
    }

}
