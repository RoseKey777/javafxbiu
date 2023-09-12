package com.example.biubiu.controller;

import com.example.biubiu.util.MusicPlayer;
import com.example.biubiu.util.SoundEffect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SettingController implements Initializable {

    private String fontPath = LoginController.class.getResource("/com/example/biubiu/zpix.ttf").toExternalForm();

    @FXML
    private Label settingLb;
    @FXML
    private Label volumeLb;
    @FXML
    private Label effectLb;
    @FXML
    private Label bgmLb;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider soundEffectSlider;

    @FXML
    private ComboBox<String> backgroundMusicComboBox;

    @FXML
    private RadioButton loopRadioButton;

    @FXML
    private Button MusicPlay = new Button();


    private String url1 = "/com/example/biubiu/mp3/BackgroundMusic/Music1.mp3";//初始值



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Font font = Font.loadFont(fontPath, 20);
        settingLb.setFont(font);
        font = Font.loadFont(fontPath, 15);
        volumeLb.setFont(font);
        effectLb.setFont(font);
        bgmLb.setFont(font);
        backgroundMusicComboBox.setStyle("-fx-font-family: 'Zpix';");

        // 在界面加载时执行的初始化逻辑

//        gun1.setAutoPlay(true);
//        SoundEffect.play("/com/example/biubiu/mp3/gun.mp3");
        Double MusicVolume = MusicPlayer.getMusicVolume();
        Double SoundEffectVolume = SoundEffect.getVolume();

        volumeSlider.setValue(MusicVolume);
        soundEffectSlider.setValue(SoundEffectVolume);

        //combobox的显示
        ObservableList<String> items = FXCollections.observableArrayList();

        // 向 ComboBox 中添加文本项
        items.add("Music1");
        items.add("Music2");
        items.add("Music3");

        // 将 ObservableList 设置为 ComboBox 的项列表
        backgroundMusicComboBox.setItems(items);
        backgroundMusicComboBox.setValue("Music1");

        backgroundMusicComboBox.setOnAction(event -> {
            String selectedMusic = backgroundMusicComboBox.getValue();
            MusicPlayer.changeUrl(selectedMusic);
//            // 根据选择的音乐设置 URL
//            if ("Music1".equals(selectedMusic)) {
//                url1 = "/com/example/biubiu/mp3/BackgroundMusic/Music1.mp3";
//            } else if ("Music2".equals(selectedMusic)) {
//                url1 = "/com/example/biubiu/mp3/BackgroundMusic/Music2.mp3";
//            } else if ("Music3".equals(selectedMusic)) {
//                url1 = "/com/example/biubiu/mp3/BackgroundMusic/Music3.mp3";
//            }
        });


//
    }

    @FXML
    void playingMusic(MouseEvent mouseEvent){
        MusicPlayer.Musicplay();
    }

    @FXML
    void stoppingMusic(MouseEvent mouseEvent){
        MusicPlayer.MusicStop();
    }


    @FXML
    void onVolumeSliderDragged(MouseEvent mouseEvent){
        double volume = volumeSlider.getValue();
        MusicPlayer.setMusicVolume(volume);
    }

    @FXML
    void onSoundEffectSliderDragged(MouseEvent mouseEvent){
        double soundEffect = soundEffectSlider.getValue();
        SoundEffect.setVolume(soundEffect);
    }

    @FXML
    void onLoopRadioButtonSelected(){
        boolean selecteValue = loopRadioButton.isSelected();
        MusicPlayer.autoPlayMusic(selecteValue);
        System.out.println(selecteValue);
    }
    // 添加其他事件处理程序和功能
}
