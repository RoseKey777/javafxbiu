package com.example.biubiu.util;

import javafx.scene.media.AudioClip;

public class SoundEffect {

    public static AudioClip currentAudioClip = null;
    private static double volume = 0.5; // 默认音量


    public static void play(String src){
        AudioClip currentAudioClip = new AudioClip(SoundEffect.class.getResource(src).toString());
        currentAudioClip.setVolume(volume);
        currentAudioClip.play();
    }

    public static void stop(){
        if (currentAudioClip != null){
            currentAudioClip.stop();
            currentAudioClip = null;
        }
    }

    public static double getVolume(){
        return volume;
    }
    //设置音量大小
    public static void setVolume(double newVolume) {
        if (newVolume >= 0.0 && newVolume <= 1.0) {
            volume = newVolume; // 设置新音量
            if (currentAudioClip != null) {
                currentAudioClip.setVolume(volume); // 更新正在播放的音频剪辑的音量
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    //循环播放
    public static void autoPlay(boolean cycleValue) {
        if (cycleValue) {
            if (currentAudioClip != null) {
                currentAudioClip.setCycleCount(AudioClip.INDEFINITE); // 循环播放
            }else {
                currentAudioClip.setCycleCount(1);
            }
        }
    }

}
