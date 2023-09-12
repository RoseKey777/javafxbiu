package com.example.biubiu.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {

    private String fontPath = LoginController.class.getResource("/com/example/biubiu/zpix.ttf").toExternalForm();

    @FXML
    private TextArea text;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        Font font = Font.loadFont(fontPath, 15);
        text.setFont(font);
    }
}