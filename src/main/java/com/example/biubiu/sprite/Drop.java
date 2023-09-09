package com.example.biubiu.sprite;

import javafx.scene.image.Image;

public class Drop extends Sprite{
    public int type;

    public int id;

    public boolean alive;
    public Drop(String url,double x,double y,int typ,int idd){
        super(new Image(Background.class.getResource(url).toExternalForm()),
                x, y ,32 ,32);
        type = typ;
        id = idd;
        alive = true;
    }
}
