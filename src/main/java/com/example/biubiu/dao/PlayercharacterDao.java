package com.example.biubiu.dao;

import com.alibaba.fastjson.JSON;
import com.example.biubiu.domain.Character;
import com.example.biubiu.domain.Playercharacter;
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
    WeaponDao weaponDao = new WeaponDao();
    CharacterDao characterDao = new CharacterDao();

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

    public ArrayList<String> getUserBag(String username){
        List<Playercharacter> playercharacterList = getPlayercharacterByUsername(username);
        int size = playercharacterList.size();
        ArrayList<String> resultList = new ArrayList<>();
        for(int i = 0; i < size; i++){
            Map<String, Object> map = new HashMap<>();
            Playercharacter playercharacter = playercharacterList.get(i);
            Weapon weapon = weaponDao.getWeaponById(playercharacter.getWeaponid());
            Character character = characterDao.getChatacterById(playercharacter.getCharacterid());

            String weaponPath = weapon.getFilepath();
            int weapon_state = playercharacter.getWeapon_state();
            String characterPath = character.getFilepath();
            int character_state = playercharacter.getCharacter_state();
            map.put("weapon", weaponPath);
            map.put("weapon_state", weapon_state);
            map.put("character", characterPath);
            map.put("character_state", character_state);
            resultList.add(JSON.toJSONString(map));
        }
        return resultList;
    }

    @SneakyThrows
    public int updateWeapon(String username, String weaponname){
        try (Connection conn = DButil.getconnection()) {
            QueryRunner queryRunner = new QueryRunner();
            String insertQuery = "UPDATE playercharacter "
                    + "SET weapon_state = (weapon_state + 1) % 2 "
                    + "WHERE username = ? AND weaponid IN "
                    + "(SELECT weaponid FROM weapon WHERE filepath = ?)";
            int rowsInserted = queryRunner.update(conn, insertQuery, username, weaponname);
            String selectQuery = "SELECT weapon_state "
                    + "FROM playercharacter "
                    + "WHERE username = ? AND weaponid IN "
                    + "(SELECT weaponid FROM weapon WHERE filepath = ?)";
            PreparedStatement preparedStatement = DButil.getconnection().prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, weaponname);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int weapon_state = resultSet.getInt(1);
            return weapon_state;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @SneakyThrows
    public int updateCharacter(String username, String charactername){
        try (Connection conn = DButil.getconnection()) {
            QueryRunner queryRunner = new QueryRunner();
            String insertQuery = "UPDATE playercharacter "
                    + "SET character_state = (character_state + 1) % 2 "
                    + "WHERE username = ? AND characterid IN "
                    + "(SELECT characterid FROM `character` WHERE filepath = ?)";
            int rowsInserted = queryRunner.update(conn, insertQuery, username, charactername);
            String selectQuery = "SELECT character_state "
                    + "FROM playercharacter "
                    + "WHERE username = ? AND characterid IN "
                    + "(SELECT characterid FROM `character` WHERE filepath = ?)";
            PreparedStatement preparedStatement = DButil.getconnection().prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, charactername);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int character_state = resultSet.getInt(1);
            return character_state;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
