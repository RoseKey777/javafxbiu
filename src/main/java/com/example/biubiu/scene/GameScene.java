package com.example.biubiu.scene;

import com.example.biubiu.sprite.Background;
import com.example.biubiu.sprite.Bullet;
import com.example.biubiu.sprite.Enemy;
import com.example.biubiu.sprite.Player;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameScene {
    public double mouseX,mouseY;
    private Canvas canvas = new Canvas(1024,1024);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    private KeyProcess keyProcess = new KeyProcess();

    private MouseProcess mouseProcess = new MouseProcess();
    private Refresh refresh = new Refresh();

    private boolean running  = false;//为false则停止刷新，为true则启动刷新

    private Background background = new Background();
    private Player selfPlayer = new Player(400,500,0, 0.0,this);

    private Enemy enemy = new Enemy(400,500,0, 0.0,this);

    public List<Bullet> bullets = new ArrayList<>();

    private void paint(){
        background.paint(graphicsContext);
        selfPlayer.paint(graphicsContext);
        enemy.paint(graphicsContext);
        for(Bullet bullet:bullets){
            bullet.paint(graphicsContext);
        }
    }

    public void init(Stage stage){
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        stage.getScene().setOnKeyReleased(keyProcess);
        stage.getScene().setOnKeyPressed(keyProcess);
        stage.getScene().setOnMouseClicked(mouseProcess);
        stage.getScene().setOnMouseMoved(mouseProcess);
        running=true;
        refresh.start();
    }

    public void clear(Stage stage){
        stage.getScene().removeEventHandler(KeyEvent.KEY_RELEASED,keyProcess);
        refresh.stop();
    }

    private class Refresh extends AnimationTimer{

        @Override
        public void handle(long l) {//每一帧的刷新会调用paint
            if(running){
                paint();
            }
        }
    }

    private class KeyProcess implements EventHandler<KeyEvent>{

        @Override
        public void handle(KeyEvent keyEvent) {
            KeyCode keyCode = keyEvent.getCode();
//            if(keyCode.equals(KeyCode.SPACE)){//按下空格键暂停
//                pauseOrContinue();
//            }
            if(keyEvent.getEventType() == KeyEvent.KEY_RELEASED){
                selfPlayer.released(keyCode);
            }else if(keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
                selfPlayer.pressed(keyCode);
            }
        }
    }

    private class MouseProcess implements EventHandler<MouseEvent>{

        @Override
        public void handle(MouseEvent mouseEvent) {
            double sx = mouseEvent.getSceneX();
            double sy = mouseEvent.getSceneY();
            selfPlayer.direct(sx,sy);
            if(mouseEvent.getEventType() == mouseEvent.MOUSE_CLICKED){
                selfPlayer.clicked();
            }
        }
    }

    public void pauseOrContinue(){
        if(running){
            running = false;
        }else{
            running = true;
        }
    }

}
