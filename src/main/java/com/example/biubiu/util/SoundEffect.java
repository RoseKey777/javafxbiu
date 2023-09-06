package com.example.biubiu.util;

import javafx.scene.media.AudioClip;

public class SoundEffect {

    public static void play(String src){
        AudioClip audioClip = new AudioClip(SoundEffect.class.getResource(src).toString());
        audioClip.play();
    }

}
