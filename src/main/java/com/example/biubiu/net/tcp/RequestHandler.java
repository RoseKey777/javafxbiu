package com.example.biubiu.net.tcp;


import com.alibaba.fastjson.JSON;
import com.example.biubiu.dao.*;
import com.example.biubiu.domain.Character;
import com.example.biubiu.domain.User;
import com.example.biubiu.domain.Weapon;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//用于处理客户端的请求
public class RequestHandler {
    private UserDao userDao = new UserDao();
    private PlayercharacterDao playercharacterDao = new PlayercharacterDao();
    private PlayerweaponDao playerweaponDao = new PlayerweaponDao();
    private WeaponDao weaponDao = new WeaponDao();
    private CharacterDao characterDao = new CharacterDao();
    public PrintWriter output;//客户端

    //登录请求
    public User login(Map<String, Object> data){
        String username = (String)(data.get("username"));
        String password = (String)(data.get("password"));
        System.out.println("登录的用户名为" + username);
        System.out.println("登录的密码为" + password);
        System.out.println(userDao);
        User user = userDao.getUserByUsernameAndPassword("123", "123");
        System.out.println(user);
        if(userDao.getUserByUsernameAndPassword(username, password) != null) {
            output.println("登录成功");
            return userDao.getUserByUsernameAndPassword(username, password);
        }
        else{
            output.println("登录失败");
            return null;
        }
    }

    //注册请求
    public void signup(Map<String, Object> data){
        String username = (String)(data.get("username"));
        String password = (String)(data.get("password"));
        System.out.println("注册的用户名为" + username);
        System.out.println("注册的密码为" + password);
        if(userDao.register(username, password))
            output.println("注册成功");
        else
            output.println("用户名已存在，请重新注册");
    }

    //获取用户信息
    public void getuserinfo(Map<String, Object> data){
        String username = (String)(data.get("username"));
        User user = userDao.getUserByUsername(username);
        System.out.println(JSON.toJSONString(user));
        output.println(JSON.toJSONString(user));
    }

    //获取玩家背包
    public void getuserbag(Map<String, Object> data){
        String username = (String)(data.get("username"));
        String bagList = playercharacterDao.getUserBag(username);
        System.out.println(bagList);
        output.println(bagList);
    }

    //获取所有武器
    public void getAllWeapon(Map<String, Object> data){
        List<Weapon> tmp = weaponDao.getAllWeapon();
        ArrayList<String> weaponList = new ArrayList<>();
        for(int i = 0; i < tmp.size(); i++){
            System.out.println(tmp.get(i));
            weaponList.add(JSON.toJSONString(tmp.get(i)));
        }
        output.println(weaponList);
    }

    //获取玩家已拥有的武器
    public void getPlayerAllWeapon(Map<String, Object> data){
        String username = (String)(data.get("username"));
        List<Weapon> tmp = weaponDao.getAllWeapon();
        List<Weapon> weaponList = weaponDao.getPlayerAllWeapon(username);
        List<Integer> weaponIdList = new ArrayList<>();//玩家已拥有的武器id列表
        for(int i = 1; i <= tmp.size(); i++){
            if(weaponList.indexOf(tmp.get(i - 1)) != -1)
                weaponIdList.add(i);
        }
        output.println(weaponIdList);
    }

    //更新武器状态
    public void updateWeapon(Map<String, Object> data){
        String username = (String)(data.get("username"));
        String weaponname = (String)(data.get("weapon"));
        output.println(playerweaponDao.updateWeapon(username, weaponname));
    }

    //更新角色状态
    public void updateCharacter(Map<String, Object> data){
        String username = (String)(data.get("username"));
        String charactername = (String)(data.get("character"));
        output.println(playercharacterDao.updateCharacter(username, charactername));
    }

    //获取登录玩家使用的的武器和角色id
    public String getUserState(User user){
        return playercharacterDao.getUserState(user.getUsername());
    }

    //获取所有玩家信息
    public void getAllUser(){
        output.println(JSON.toJSONString(userDao.getAllUser()));
    }

    //更新头像
    public void updateAvatar(Map<String, Object> data){
        String username = (String)(data.get("username"));
        String avatar = (String)(data.get("avatar"));
        userDao.updateAvatar(username, avatar);
        output.println("更新成功");
    }

    //创建房间
    public void createroom(Map<String, Object> data){

    }

    //更新金钱和积分
    public void addCoinsAndScore(Map<String, Object> data, String username){
        double coins = Double.valueOf(data.get("coins").toString());
        int score = (int) data.get("score");
        userDao.addCoinsAndScore(username, coins, score);
        output.println("1");
    }

    public void insertWeapon(Map<String, Object> data){
        String username = (String) data.get("username");
        int weaponid = (int) data.get("weaponid");
        playerweaponDao.insertWeapon(username, weaponid);
        output.println("1");
    }

    public void insertCharacter(Map<String, Object> data){
        String username = (String) data.get("username");
        int characterid = (int) data.get("characterid");
        playercharacterDao.insertCharacter(username, characterid);
        output.println("1");
    }

    //获取所有角色
    public void getAllCharacter(){
        List<Character> tmp = characterDao.getAllCharacter();
        ArrayList<String> characterList = new ArrayList<>();
        for(int i = 0; i < tmp.size(); i++){
            characterList.add(JSON.toJSONString(tmp.get(i)));
        }
        output.println(characterList);
    }

    public void getPlayerAllCharacter(Map<String, Object> data){
        String username = (String)(data.get("username"));
        List<Character> tmp = characterDao.getAllCharacter();
        List<Character> characterList = characterDao.getPlayerAllCharacter(username);
        List<Integer> characterIdList = new ArrayList<>();//玩家已拥有的角色id列表
        for(int i = 1; i <= tmp.size(); i++){
            if(characterList.indexOf(tmp.get(i - 1)) != -1)
                characterIdList.add(i);
        }
        output.println(characterIdList);
    }
}
