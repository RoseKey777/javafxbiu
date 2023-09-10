package com.example.biubiu.controller;

import com.example.biubiu.Director;
import com.example.biubiu.scene.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GameOverController {

    public int npcflag;
    @FXML
    private ImageView flag;

    @FXML
    private ImageView toIndex;

    @FXML
    private ImageView back;

    @FXML
    void mouseClickedToIndex(MouseEvent event) {
//        SoundEffect.play("/sound/done.wav");
        if(npcflag == 0){
            Director.getInstance().waitingRoomStart();
        }else {
            Director.getInstance().toIndex();
        }

    }

    @FXML
    void mouseEnteredToIndex(MouseEvent event) {
        toIndex.setOpacity(0.8);
        back.setOpacity(0.3);
//        SoundEffect.play("/sound/button.wav");
    }

    @FXML
    void mouseExitedToIndex(MouseEvent event) {
        toIndex.setOpacity(1);
        back.setOpacity(0.3);
    }

    public void flagSuccess() {
        flag.setImage(new Image(GameOverController.class.getResource("/com/example/biubiu/Avatar/avatar35.png").toExternalForm()));
    }

}
