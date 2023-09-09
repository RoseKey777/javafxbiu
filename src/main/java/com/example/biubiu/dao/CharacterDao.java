package com.example.biubiu.dao;

import com.example.biubiu.domain.Character;
import com.example.biubiu.domain.Weapon;
import com.example.biubiu.util.DButil;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class CharacterDao {
    public Character getChatacterById(int id) {
        Connection cn = null;
        try {
            cn = DButil.getconnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from `character` where id=?";
        ResultSetHandler<Character> h = new BeanHandler<Character>(Character.class);
        try {
            Character p = queryRunner.query(cn,sql, h, id);
            if(p!=null) {
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @SneakyThrows
    public int findIdByCharacterName(String charactername){
        try (Connection conn = DButil.getconnection()) {
            QueryRunner queryRunner = new QueryRunner();
            String sql = "SELECT id FROM `character` WHERE charactername = ?";
            Character character = queryRunner.query(conn, sql, new BeanHandler<>(Character.class), charactername);
            return character.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
