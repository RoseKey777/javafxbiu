package com.example.biubiu.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;


public class MusicPlayer {
    private static MediaPlayer mediaPlayer = null;
    private static double volume = 0.5; // 默认音量

    private static boolean cycleValue = true; // 默认无限循环

    private static String url1 = "/com/example/biubiu/mp3/BackgroundMusic/Music1.mp3";//默认初始音乐


    public static void changeUrl(String boxText){
        if ("Music1".equals(boxText)) {
            url1 = "/com/example/biubiu/mp3/BackgroundMusic/Music1.mp3";
        } else if ("Music2".equals(boxText)) {
            url1 = "/com/example/biubiu/mp3/BackgroundMusic/Music2.mp3";
        } else if ("Music3".equals(boxText)) {
            url1 = "/com/example/biubiu/mp3/BackgroundMusic/Music3.mp3";
        }
    }
    public static void Musicplay() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == Status.PLAYING) {
            MusicStop();
        }

        Media media = new Media(MusicPlayer.class.getResource(url1).toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(volume); // 设置音量
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // 设置循环次数
        mediaPlayer.play();
    }

    public static void MusicStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
    //获取当前音量
    public static double getMusicVolume(){
        return volume;
    }
    //设置背景音乐音量
    public static void setMusicVolume(double newVolume) {
        if (newVolume >= 0.0 && newVolume <= 1.0) {
            volume = newVolume; // 设置新音量
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(volume); // 更新音量
            }
        } else {
            throw new IllegalArgumentException("音量必须在范围 [0.0, 1.0] 内。");
        }
    }

    public static void autoPlayMusic(boolean cycleValue) {
        if (cycleValue) {
            if (mediaPlayer != null) {
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // 开始循环
            }else {
                mediaPlayer.setCycleCount(1);
            }
        }
    }

    public static boolean isPlayingMusic() {
        return mediaPlayer != null && mediaPlayer.getStatus() == Status.PLAYING;
    }
}
