package com.example.biubiu.sprite;

import com.example.biubiu.controller.LoginController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

public class State extends Sprite{

    public double hp;
    public int numOfbullet;
    public double speed;
    public int ChaID;
    public int WeaID;
    public State() {
//        super(new Image(State.class.getResource("/com/example/biubiu/image/购物车.png").toExternalForm()),380, 600 ,200 ,40);
        super(new Image(State.class.getResource("/com/example/biubiu/image/state.png").toExternalForm()),224, 480 ,500 ,152);
    }

    private String[] chaURL = {"","/com/example/biubiu/image/run/Run1_0.gif","/com/example/biubiu/image/run/Run2_0.gif",
            "/com/example/biubiu/image/run/Run3_0.gif"};

    private String[] weaURL = {"","/com/example/biubiu/image/ak47.png","/com/example/biubiu/image/awm.png",
            "/com/example/biubiu/image/Kar98k.png"};

    private int []numbullet = {0, 50, 40, 50};

    private double spd[] = {0, 2, 4, 1.5};

    private double bulletspeed[] = {0, 5, 7, 8};

    private int bulletdamage[] = {0,1,3,2};
    @Override
    public void paint(GraphicsContext graphicsContext){
        super.paint(graphicsContext);
        for(int i = 1; i <= hp;++i){
            Image image1 = new Image(Player.class.getResource("/com/example/biubiu/image/hp.png").toExternalForm());
            graphicsContext.drawImage(image1,x + 80 + 32 * (i-1),y + 24,32,32);
        }
        Image imageB = new Image(State.class.getResource(chaURL[ChaID]).toExternalForm());
        Image imageC = new Image(State.class.getResource(weaURL[WeaID]).toExternalForm());

        double tmpbulletspeed = bulletspeed[WeaID];

        int tmpbulletdamage = bulletdamage[WeaID];

        graphicsContext.drawImage(imageB,x + 38,y + 8,64,64);
        graphicsContext.drawImage(imageC,x + 38,y + 80,64,64);

        graphicsContext.setFill(Color.WHITE);
        String fontPath = LoginController.class.getResource("/com/example/biubiu/zpix.ttf").toExternalForm();
        Font font = Font.loadFont(fontPath, 16);
        graphicsContext.setFont(font);
        graphicsContext.fillText("速度: " + speed, x + 150, y + 80);
        graphicsContext.fillText("弹药: " + numOfbullet, x + 150, y + 112);
        graphicsContext.fillText("子弹速度: " + tmpbulletspeed, x + 300, y + 80);
        graphicsContext.fillText("子弹伤害: " + tmpbulletdamage, x + 300, y + 112);
    }

}
