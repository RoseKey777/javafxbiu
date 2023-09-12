package com.example.biubiu.sprite;

import com.example.biubiu.Director;
import com.example.biubiu.controller.LoginController;
import com.example.biubiu.scene.ComputerGameScene;
import com.example.biubiu.scene.GameScene;
import com.example.biubiu.util.SoundEffect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.Struct;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Player extends Role{

    public int timer;
    public int NPCflag;//人机flag 为0则这把游戏是人机
    public int numOfwudi;
    public int mapChoose;
    public boolean realDie;

    public String username;
    Image weaponImage;
    private int count = 0;

    public int characterid;

    public int numOfbullet;

    public int weaponid = 0;
    private int countDie = 0;
    private int MOD = 7;
    public double hp;
    private int [][][]block={
            {},
            {
                    {  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10},
                    {  10,   0,   0,   0,  10,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,  10},
                    {  10,   0,   0,   0,  10,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   5,   0,   0,   0,   0,   5,   0,   0,   0,   0,  10,   0,   0,   0,  10},
                    {  10,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   5,   5,   5,   5,   5,   0,   0,   5,   5,   5,   5,   0,   0,   0,   5,   5,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,  10,   0,   0,   0,   5,   0,   0,   0,   0,   5,   0,   0,   0,   0,   0,   5,   5,   0,  10},
                    {  10,  10,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,  10,   0,   0,   5,   0,   0,   0,   0,   0,   0,   0,   0,   0,   5,   5,   0,   0,  10},
                    {  10,   0,  10,   0,   0,   0,   0,   0,  10,  10,  10,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   5,   5,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   5,   5,   5,  10,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   5,   5,   5,   0,   0,   0,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,  10,  10,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10},
                    {  10,  10,  10,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,  10,  10,   0,   0,   0,   0,  10,  10,   0,   0,   0,   0,  10},
                    {  10,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   5,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,  10,   0,   0,   0,  10},
                    {  10,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   5,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,  10,  10,  10,   0,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   5,   0,   0,   0,   0,   5,   0,   0,   0,  10,  10,  10,   0,   0,  10,   0,   0,   0,   0,   0,  10,  10,  10},
                    {  10,   0,   0,   0,   0,   0,   5,   0,   0,   0,   0,   0,   5,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,  10},
                    {  10,  10,  10,   0,   0,   0,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,   0,   0,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,   0,   0,   0,   0,   0,  10},
                    {  10,   0,   0,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,   0,   0,   5,   5,   5,  10},
                    {  10,   0,   0,   0,   0,   0,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,   0,   0,   0,   0,   0,  10},
                    {  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10},
                    {  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10}
            },
            {
                    {  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,  10,  10,  10,  10,  10,  10,   0,   0,   0,  10,  10,  10,  10,   0,   0,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,  10},
                    {  10,   0,  10,  10,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,  10,  10,   0,   0,  10,   0,  10,  10,  10,   0,   0,   0,   0,   0,   0,  10,   0,   0,  10},
                    {  10,   0,   0,  10,   0,   0,   0,   0,  10,   0,   0,   0,  10,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10},
                    {  10,   0,   0,  10,  10,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,  10,  10,  10,   0,   0,   0,   0,  10},
                    {  10,   0,   0,   0,  10,   0,   0,  10,  10,  10,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,  10,  10,   0,   0,   0,  10,   0,   0,   0,   0,   0,  10},
                    {  10,   0,  10,  10,  10,   0,   0,   0,   0,   0,   0,  10,  10,  10,   0,   0,   0,  10,  10,   0,   0,   0,   0,  10,   0,   0,   0,  10,   0,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,  10,   0,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,  10},
                    {  10,   0,   0,  10,  10,   0,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,   0,  10,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,   0,  10,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,   0,  10,   0,   0,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,   0,   0,   0,   0,   0,  10},
                    {  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10},
                    {  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10}
            },
            {
                    {  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,  10},
                    {  10,  10,  10,  10,  10,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,  10,  10,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,  10,  10,  10,  10,   0,   0,  10,   0,   0,   0,   0,  10,  10,   0,   0,   0,   0,  10},
                    {  10,   0,   0,   0,  10,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,  10,  10,   0,   0,   0,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,  10,  10,   0,  10,  10},
                    {  10,  10,  10,  10,  10,   0,   0,   0,   0,  10,  10,  10,  10,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,  10,  10},
                    {  10,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,  10,  10,  10,  10,   0,   0,   0,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,  10,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10},
                    {  10,  10,  10,   0,   0,   0,  10,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,  10,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,   0,   0,  10,   0,   0,   0,   0,   0,  10,  10,  10},
                    {  10,   0,   0,   0,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,   0,   0,   0,   0,  10,  10},
                    {  10,   0,   0,   0,   0,   0,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,   0,   0,  10,  10},
                    {  10,  10,  10,   0,   0,   0,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,   0,   0,   0,   0,   0,  10},
                    {  10,   0,   0,   0,   0,   0,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,   0,   0,  10,   0,   0,  10},
                    {  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10},
                    {  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10,  10}
            }
    };

    private static Image[][] images = new Image[][] {
            {},
            {
                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run1_0.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run1_1.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run1_2.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run1_3.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run1_4.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run1_5.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run1_6.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run1_7.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run1_8.gif").toExternalForm()),
            },
            {
                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run2_0.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run2_1.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run2_2.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run2_3.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run2_4.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run2_5.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run2_6.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run2_7.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run2_8.gif").toExternalForm()),
            },
            {
                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run3_0.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run3_1.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run3_2.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run3_3.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run3_4.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run3_5.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run3_6.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run3_7.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/Run3_8.gif").toExternalForm()),
            }
    };

    private static Image[][] imagesMir = new Image[][] {
            {},
            {
                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror1_0.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror1_1.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror1_2.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror1_3.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror1_4.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror1_5.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror1_6.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror1_7.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror1_8.gif").toExternalForm()),
            },
            {
                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror2_0.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror2_1.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror2_2.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror2_3.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror2_4.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror2_5.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror2_6.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror2_7.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror2_8.gif").toExternalForm()),
            },
            {
                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror3_0.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror3_1.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror3_2.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror3_3.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror3_4.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror3_5.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror3_6.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror3_7.gif").toExternalForm()),

                    new Image(Player.class.getResource("/com/example/biubiu/image/run/RunMirror3_8.gif").toExternalForm()),
            }
    };

    private static Image[] dieImages = new Image[] {
//            new Image(Player.class.getResource("/com/example/biubiu/image/moverole1-0.gif").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die1.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die2.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die3.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die4.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die5.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die6.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die7.png").toExternalForm()),

            new Image(Player.class.getResource("/com/example/biubiu/image/die8.png").toExternalForm()),

    };

    public double weaponDir;//武器方向

    boolean keyup, keydown, keyleft, keyright;

    double sceneX,sceneY;

    private String[] weaURL = {"","/com/example/biubiu/image/ak47.png","/com/example/biubiu/image/awm.png",
            "/com/example/biubiu/image/M1A1 Thompson.png"};

    private int []numbullet = {0,50, 40, 60};

    private double spd[] = {0,1, 2, 0.75};
    private double hps[] = {0,10, 8, 12};
    private double bulletspeed[] = {0,5, 7, 8};

    public int weaponCD[] = {0, 20, 40, 30};

    public boolean openfireflag;

    public double lastdir;

    public void dressup(int chaID,int weaID){
        speed = spd[chaID];
        hp = hps[chaID];
        imageMap.put("walk",images[characterid][0]);
        imageMap.put("weapon",new Image(Player.class.getResource(weaURL[weaID]).toExternalForm()));
    }

    public Player(double x, double y,int chaID,int weaID, double dir, double weaponDir, int mapchoose, GameScene gameScene) {
        super(x, y, 32, 32, dir, gameScene);
        this.weaponDir = weaponDir;
        characterid = chaID;
        weaponid = weaID;
        numOfbullet = numbullet[weaID];
        mapChoose = mapchoose;
        numOfwudi = 0;
        NPCflag = 0;
        timer = 0;
        openfireflag = true;

        realDie = false;
        dressup(chaID,weaID);
    }

    public Player(double x, double y,int chaID,int weaID, double dir, double weaponDir, int mapchoose, ComputerGameScene gameScene) {
        super(x, y, 32, 32, dir, gameScene);
        this.weaponDir = weaponDir;
        characterid = chaID;
        weaponid = weaID;
        numOfbullet = numbullet[weaID];
        mapChoose = mapchoose;
        numOfwudi = 0;
        NPCflag = 1;
        timer = 0;
        openfireflag = true;

        realDie = false;
        dressup(chaID,weaID);
    }

    public void pressed(KeyCode keyCode){
        switch (keyCode){
            case W:
                keyup = true;
                break;
            case S:
                keydown = true;
                break;
            case A:
                keyleft = true;
                break;
            case D:
                keyright = true;
        }
        //每次键盘状态改变，调用重定向
        redirect();
    }

    private double calc(){
//        double tmp = Math.atan((sceneY - y - 16)/(sceneX - x - 16));
        double tmp = Math.atan((sceneY - y )/(sceneX - x ));
        if(sceneX < x){
            return Math.PI - tmp;
        }else {
//            double tmp1 = Math.atan((y - sceneY + 16)/(sceneX - x - 16));
            double tmp1 = Math.atan((y - sceneY )/(sceneX - x ));
            return tmp1;
        }
//        return Math.atan((sceneY - y)/(sceneX - x));
    }

    public void released(KeyCode keyCode){
        switch (keyCode){
//            case SPACE:
//                openFire();
//                break;
            case W:
                keyup = false;
                break;
            case S:
                keydown = false;
                break;
            case A:
                keyleft = false;
                break;
            case D:
                keyright = false;
        }
        redirect();
    }

    public void direct(double sx, double sy){
        sceneX = sx;
        sceneY = sy;
        weaponDir = calc();
    }

    public void redirect(){
        if(keyup && !keydown && !keyleft && !keyright){
            dir = 3.0;
            lastdir = dir;
        }
        else if(!keyup && keydown && !keyleft && !keyright){
            dir = 7.0;
            lastdir = dir;
        }
        else if(!keyup && !keydown && keyleft && !keyright){
            dir = 5.0;
            lastdir = dir;
        }
        else if(!keyup && !keydown && !keyleft && keyright){
            dir = 1.0;
            lastdir = dir;
        }
        else if(keyup && !keydown && !keyleft && keyright){
            dir = 2.0;
            lastdir = dir;
        }
        else if(keyup && !keydown && keyleft && !keyright){
            dir = 4.0;
            lastdir = dir;
        }
        else if(!keyup && keydown && keyleft && !keyright){
            dir = 6.0;
            lastdir = dir;
        }
        else if(!keyup && keydown && !keyleft && keyright){
            dir = 8.0;
            lastdir = dir;
        }
        else if(!keyup && !keydown && !keyleft && !keyright){//啥方向都没按 停
            dir = 0.0;
        }
        if(dir != 0){
            weaponDir = calc();//todo: 目前是武器和人物一个方向，需要修改武器360度转向
        }
    }
    public void wakeChange(){
        imageMap.put("walk",images[characterid][count]);//video 7 diffrent
    }

    public void MirwakeChange(){
        imageMap.put("walk",imagesMir[characterid][count]);//video 7 diffrent
    }

    public void dieChange(){
        imageMap.put("walk",dieImages[countDie]);
    }

    public boolean illegal(double xx,double yy){
        int realX = (int)Math.floor((xx+5)/32.0);
        int realY = (int)Math.floor((yy+10)/32.0);
        int realXX = (int)Math.floor((xx+24)/32.0);
        int realYY = (int)Math.floor((yy+32)/32.0);
        int realXXX = (int)Math.floor((xx+5)/32.0);
        int realYYY = (int)Math.floor((yy+32)/32.0);
        int realXXXX = (int)Math.floor((xx+24)/32.0);
        int realYYYY = (int)Math.floor((yy+10)/32.0);

        if(block[mapChoose][realY][realX] == 10){//左上
            return false;
        }
        if(block[mapChoose][realYY][realXX] == 10){//右下
            return false;
        }
        if(block[mapChoose][realYYY][realXXX] == 10){//左下
            return false;
        }
        if(block[mapChoose][realYYYY][realXXXX] == 10){//右上
            return false;
        }
        if(block[mapChoose][realY][realX] == 5){//左上
            return false;
        }
        if(block[mapChoose][realYY][realXX] == 5){//右下
            return false;
        }
        if(block[mapChoose][realYYY][realXXX] == 5){//左下
            return false;
        }
        if(block[mapChoose][realYYYY][realXXXX] == 5){//右上
            return false;
        }
        return true;
    }

    public int impact(List<Drop> drops){
        Random random = new Random();
        for(Drop drop:drops){
            if(drop.alive && getContour().intersects(drop.getContour())){
                if(drop.type == 0){
                    hp ++;
                    SoundEffect.play("/com/example/biubiu/mp3/heal.mp3");
                }else if(drop.type == 1){
                    numOfbullet += 10;
                    SoundEffect.play("/com/example/biubiu/mp3/bullet.mp3");
                }else if(drop.type == 2){
                    numOfwudi += 3;
                    SoundEffect.play("/com/example/biubiu/mp3/dun.mp3");
                }else if(drop.type == 3){
                    SoundEffect.play("/com/example/biubiu/mp3/boom.mp3");
                    hp --;
                }else if(drop.type == 4){
                    int ii = random.nextInt(4);
                    if(ii == 0){
                        hp ++;
                        SoundEffect.play("/com/example/biubiu/mp3/heal.mp3");
                    }else if(ii == 1){
                        numOfbullet += 10;
                        SoundEffect.play("/com/example/biubiu/mp3/bullet.mp3");
                    }else if(ii == 2){
                        numOfwudi += 3;
                        SoundEffect.play("/com/example/biubiu/mp3/dun.mp3");
                    }else if(ii == 3){
                        SoundEffect.play("/com/example/biubiu/mp3/boom.mp3");
                        hp --;
                    }
                }
                drop.alive = false;

                if(gameScene!= null) gameScene.newdropid ++;
                if(gameScene!= null) gameScene.Tim[gameScene.newdropid] = 500;

                return drop.type;
            }
        }
        return -1;
    }

    @Override
    public void move() {
        if(dir == 1.0){
            if(illegal(x+speed,y)){
                x += speed;
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if(MOD== 0){
                    wakeChange();
                }
            }
        }else if(dir == 2.0){
            if(illegal(x+speed/Math.sqrt(2), y-speed/Math.sqrt(2))){
                x += speed/Math.sqrt(2);
                y -= speed/Math.sqrt(2);
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if(MOD== 0){
                    wakeChange();
                }
            }
        }else if(dir == 3.0){
            if(illegal(x, y-speed)) {
                y -= speed;
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else if(dir == 4.0){
            if(illegal(x-speed/Math.sqrt(2), y-speed/Math.sqrt(2))) {
                y -= speed / Math.sqrt(2);
                x -= speed / Math.sqrt(2);
                count = (count + 1) % imagesMir[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    MirwakeChange();
                }
            }
        }else if(dir == 5.0){
            if(illegal(x-speed, y)) {
                x -= speed;
                count = (count + 1) % imagesMir[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    MirwakeChange();
                }
            }
        }else if(dir == 6.0){
            if(illegal(x-speed/Math.sqrt(2), y+speed/Math.sqrt(2))) {
                y += speed / Math.sqrt(2);
                x -= speed / Math.sqrt(2);
                count = (count + 1) % imagesMir[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    MirwakeChange();
                }
            }
        }else if(dir == 7.0){
            if(illegal(x, y+speed)) {
                y += speed;
                count = (count + 1) % imagesMir[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    MirwakeChange();
                }
            }
        }else if(dir == 8.0){
            if(illegal(x+speed/Math.sqrt(2), y+speed/Math.sqrt(2))) {
                x += speed / Math.sqrt(2);
                y += speed / Math.sqrt(2);
                count = (count + 1) % images[characterid].length;
                MOD = (MOD + 1) % 6;
                if (MOD == 0) {
                    wakeChange();
                }
            }
        }else{
            count = 0;
            if(!alive && !realDie) {
                dieChange();
                countDie++;
                if(countDie == 7) realDie = true;
            }
            if(alive) {
                if(lastdir == 1.0 || lastdir == 2.0 || lastdir == 3.0 || lastdir == 4.0) {
                    imageMap.put("walk",images[characterid][0]);
//                    imageMap.put("walk", new Image(Player.class.getResource(chaURL[characterid]).toExternalForm()));//video 7 diffrent
                }else {
                    imageMap.put("walk",imagesMir[characterid][count]);
//                    imageMap.put("walk", new Image(Player.class.getResource(chaURL[characterid]).toExternalForm()));//video 7 diffrent
                }
            }
        }
        weaponDir = calc();//todo: 目前是武器和人物一个方向，需要修改武器360度转向
        if(x < 0) x = 0;
        if(y < 0) y = 0;
        if(x > Director.WIDTH - width) x = Director.WIDTH - width;
        if(y > Director.HEIGHT - height) y = Director.HEIGHT - height;
    }

    public void paintHP(GraphicsContext graphicsContext){
        for(int i = 1; i <= hp;++i){
            Image image1 = new Image(Player.class.getResource("/com/example/biubiu/image/hp.png").toExternalForm());
            graphicsContext.drawImage(image1,x-32 + 16 * (i-1),y-20,16,16);
        }
    }

    @Override
    public void paint(GraphicsContext graphicsContext) {
        image = imageMap.get("walk");
        weaponImage = imageMap.get("weapon");
//        super.paint(graphicsContext);
        paintHP(graphicsContext);
//        if(weaponDir == -Math.PI/2)
        if(alive){
            if(numOfwudi > 0){
                Image image1 = new Image(Player.class.getResource("/com/example/biubiu/image/shield.png").toExternalForm());
                graphicsContext.drawImage(image1,x,y,32,32);
            }
            graphicsContext.save();
            graphicsContext.translate(x+16,y+16);
            graphicsContext.rotate(Math.toDegrees(-weaponDir));
            Image image1 = weaponImage;

            if((weaponDir) > Math.PI/2 && (weaponDir) < Math.PI * 1.5){
//                Scale horizontalScale = new Scale(-1, 1);
                // 创建一个WritableImage，大小与原始图片相同
                WritableImage mirroredImage = new WritableImage((int) weaponImage.getWidth(), (int) weaponImage.getHeight());

                // 获取PixelReader和PixelWriter
                PixelReader pixelReader = weaponImage.getPixelReader();
                PixelWriter pixelWriter = mirroredImage.getPixelWriter();

                // 镜像原始图片的像素并写入新的WritableImage
                int width = (int) weaponImage.getWidth();
                int height = (int) weaponImage.getHeight();
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int sourceX = width - x - 1; // 水平镜像
                        int color = pixelReader.getArgb(sourceX, y);
                        pixelWriter.setArgb(x, y, color);
                    }
                }

                // 创建一个ImageView来显示镜像后的图片
                ImageView imageView = new ImageView(mirroredImage);
                image1 = imageView.getImage();
                graphicsContext.rotate(Math.toDegrees(Math.PI));
            }

            graphicsContext.drawImage(image1,-16,-16,32,32);
            graphicsContext.restore();

            super.paint(graphicsContext);

            graphicsContext.setFill(Color.GRAY);
            String fontPath = LoginController.class.getResource("/com/example/biubiu/zpix.ttf").toExternalForm();
            Font font = Font.loadFont(fontPath, 16);
            graphicsContext.setFont(font);
            graphicsContext.fillText(this.username, x, y + 50);

            move();
        }
    }
    public void speedchange(double bx,double by,int weaponid){
        Bullet bullet = new Bullet(bx,by, bulletspeed[weaponid],48,25,weaponDir,mapChoose,gameScene);
        bullet.NPCflag = 0;
        bullet.bullettype = weaponid;
        gameScene.bullets.add(bullet);
    }

    public void newspeedchange(double bx,double by,int weaponid){
        Bullet bullet = new Bullet(bx,by, bulletspeed[weaponid],48,25,weaponDir,mapChoose,computerGameScene);
        bullet.NPCflag = 1;
        bullet.idd = 0;
        bullet.bullettype = weaponid;
        computerGameScene.bullets.add(bullet);
    }

    public void openFire(){
        SoundEffect.play("/com/example/biubiu/mp3/gun.mp3");
        double bx = x + width/2;
        double by = y + height/2;
        weaponDir = calc();
        if(NPCflag == 0 && openfireflag){
            speedchange(bx, by, weaponid);
            openfireflag = false;
            timer = 0;
        }else if(openfireflag){
            newspeedchange(bx, by, weaponid);
            openfireflag = false;
            timer = 0;
        }
    }

    @Override
    public boolean impactChecking(Sprite sprite) {
        return false;
    }

    public void clicked() {
        if(alive && numOfbullet > 0) {
            if(openfireflag) numOfbullet--;
            openFire();
        }
    }
}
