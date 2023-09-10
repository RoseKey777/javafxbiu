package com.example.biubiu.scene;

import com.example.biubiu.controller.GameOverController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOver {
    public static void load(Stage stage, boolean success,int npcflag) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Index.class.getResource("/com/example/biubiu/fxml/gameover-view.fxml"));
            Parent root = fxmlLoader.load();
            GameOverController overController = fxmlLoader.getController();
            overController.npcflag = npcflag;
            if(success) overController.flagSuccess();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
