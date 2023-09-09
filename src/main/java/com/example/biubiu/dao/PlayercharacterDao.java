package com.example.biubiu.dao;

import com.alibaba.fastjson.JSON;
import com.example.biubiu.domain.Character;
import com.example.biubiu.domain.Playercharacter;
import com.example.biubiu.domain.Weapon;
import com.example.biubiu.util.DButil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
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


}
