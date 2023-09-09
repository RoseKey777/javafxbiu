package com.example.biubiu.sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Background extends Sprite{

    String []mpURL = {"/com/example/biubiu/image/test01.png","/com/example/biubiu/image/test01.png","/com/example/biubiu/image/test01.png"};
    public Background() {
        super(new Image(Background.class.getResource("/com/example/biubiu/image/test01.png").toExternalForm()),0, 0 ,1024 ,1024);
    }

    public void paint(GraphicsContext graphicsContext,int mapchoose){
        image = new Image(Background.class.getResource(mpURL[mapchoose]).toExternalForm());
        graphicsContext.drawImage(image,x,y,width,height);
    }

}
