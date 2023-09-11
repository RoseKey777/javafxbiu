package com.example.biubiu.dao;

import com.alibaba.fastjson.JSON;
import com.example.biubiu.domain.Character;
import com.example.biubiu.domain.Playercharacter;
import com.example.biubiu.domain.Playerweapon;
import com.example.biubiu.domain.Weapon;
import com.example.biubiu.util.DButil;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayercharacterDao {
    CharacterDao characterDao = new CharacterDao();
    WeaponDao weaponDao = new WeaponDao();
    PlayerweaponDao playerweaponDao = new PlayerweaponDao();

    public List<Playercharacter> getPlayercharacterByUsername(String username) {
        Connection cn = null;
        try {
            cn = DButil.getconnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from playercharacter where username=?";
        try {
            List<Playercharacter> p = queryRunner.query(cn,sql, new BeanListHandler<>(Playercharacter.class), username);
            if(p!=null) {
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String getUserBag(String username){
        Map<String, Object> result = new HashMap<>();

        List<Playercharacter> playercharacterList = getPlayercharacterByUsername(username);
        int size = playercharacterList.size();
        ArrayList<String> characterList = new ArrayList<>();
        for(int i = 0; i < size; i++){
            Map<String, Object> map = new HashMap<>();
            Playercharacter playercharacter = playercharacterList.get(i);
            Character character = characterDao.getChatacterById(playercharacter.getCharacterid());

            String characterPath = character.getFilepath();
            int character_state = playercharacter.getCharacter_state();
            map.put("character", characterPath);
            map.put("character_state", character_state);
            characterList.add(JSON.toJSONString(map));
        }

        List<Playerweapon> playerweaponList = playerweaponDao.getPlayerweaponByUsername(username);
        size = playerweaponList.size();
        ArrayList<String> weaponList = new ArrayList<>();
        for(int i = 0; i < size; i++){
            Map<String, Object> map = new HashMap<>();
            Playerweapon playerweapon = playerweaponList.get(i);
            Weapon weapon = weaponDao.getWeaponById(playerweapon.getWeaponid());

            String weaponPath = weapon.getFilepath();
            int weapon_state = playerweapon.getWeapon_state();
            map.put("weapon", weaponPath);
            map.put("weapon_state", weapon_state);
            weaponList.add(JSON.toJSONString(map));
        }

        result.put("characterList", characterList);
        result.put("weaponList", weaponList);
        return JSON.toJSONString(result);
    }

//    @SneakyThrows
//    public int updateCharacter(String username, String charactername){
//        try (Connection conn = DButil.getconnection()) {
//            QueryRunner queryRunner = new QueryRunner();
//            String insertQuery = "UPDATE playercharacter "
//                    + "SET character_state = (character_state + 1) % 2 "
//                    + "WHERE username = ? AND characterid IN "
//                    + "(SELECT characterid FROM `character` WHERE filepath = ?)";
//            int rowsInserted = queryRunner.update(conn, insertQuery, username, charactername);
//            String selectQuery = "SELECT character_state "
//                    + "FROM playercharacter "
//                    + "WHERE username = ? AND characterid IN "
//                    + "(SELECT characterid FROM `character` WHERE filepath = ?)";
//            PreparedStatement preparedStatement = DButil.getconnection().prepareStatement(selectQuery);
//            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, charactername);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//            int character_state = resultSet.getInt(1);
//            return character_state;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }

    @SneakyThrows
    public boolean updateCharacter(String username, String charactername){
        try (Connection conn = DButil.getconnection()) {
            int characterid = characterDao.findIdByCharacterPath(charactername);
            QueryRunner queryRunner = new QueryRunner();
            String sql = "UPDATE playercharacter " +
                    "SET character_state = 0 " +
                    "WHERE username = ?";
            queryRunner.update(conn, sql, username);
            sql = "UPDATE playercharacter " +
                    "SET character_state = 1 " +
                    "WHERE username = ? AND characterid = ?";
            int n = queryRunner.update(conn, sql, username, characterid);
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //获取登陆玩家的状态
    public String getUserState(String username) {

        try (Connection conn = DButil.getconnection()) {
            String sql1 = "SELECT characterid FROM playercharacter WHERE username = ? AND character_state = 1 ";
            PreparedStatement preparedStatement1 = conn.prepareStatement(sql1);
            preparedStatement1.setString(1, username);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            resultSet1.next();
            int character_id = resultSet1.getInt(1);
            String sql2 = "SELECT weaponid FROM playerweapon WHERE username = ? AND weapon_state = 1 ";
            PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
            preparedStatement2.setString(1, username);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            resultSet2.next();
            int weapon_id = resultSet2.getInt(1);

            return character_id + "," + weapon_id;
        }
        catch (SQLException e) {
                e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public boolean insertCharacter(String username, int characterid){
        try (Connection conn = DButil.getconnection()) {
            QueryRunner queryRunner = new QueryRunner();
            String sql = "INSERT INTO playercharacter " +
                    "(username, characterid, character_state) " +
                    "VALUES (?, ?, 0)";
            int n = queryRunner.update(conn, sql, username, characterid);
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
