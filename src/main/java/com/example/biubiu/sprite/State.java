package com.example.biubiu.sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class State extends Sprite{

    public double hp;
    public int numOfbullet;
    public double speed;
    public int ChaID;
    public int WeaID;
    public State() {
//        super(new Image(State.class.getResource("/com/example/biubiu/image/购物车.png").toExternalForm()),380, 600 ,200 ,40);
        super(new Image(State.class.getResource("/com/example/biubiu/image/state.png").toExternalForm()),262, 872 ,500 ,152);
    }

    private String[] chaURL = {"","/com/example/biubiu/image/moverole1-0.gif","/com/example/biubiu/image/moverole1-0.gif",
            "/com/example/biubiu/image/moverole1-0.gif"};

    private String[] weaURL = {"","/com/example/biubiu/image/ak47.png","/com/example/biubiu/image/awm.png",
            "/com/example/biubiu/image/Kar98k.png"};

    private int []numbullet = {0, 50, 40, 50};

    private double spd[] = {0, 2, 4, 1.5};

    private double bulletspeed[] = {0, 5, 7, 8};
    @Override
    public void paint(GraphicsContext graphicsContext){
        super.paint(graphicsContext);
        for(int i = 1; i <= hp;++i){
            Image image1 = new Image(Player.class.getResource("/com/example/biubiu/image/hp.png").toExternalForm());
            graphicsContext.drawImage(image1,x + 50 + 32 * (i-1),y + 24,32,32);
        }
        Image imageB = new Image(State.class.getResource(chaURL[ChaID]).toExternalForm());
        Image imageC = new Image(State.class.getResource(weaURL[WeaID]).toExternalForm());

        double tmpbulletspeed = bulletspeed[WeaID];

        graphicsContext.drawImage(imageB,x + 8,y + 8,64,64);
        graphicsContext.drawImage(imageC,x + 8,y + 80,64,64);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(javafx.scene.text.Font.font("Arial", 16));
        graphicsContext.fillText("速度: " + speed, x + 100, y + 96);
        graphicsContext.fillText("弹药: " + numOfbullet, x + 200, y + 96);
        graphicsContext.fillText("子弹速度: " + tmpbulletspeed, x + 300, y + 96);
    }

}
