package com.example.biubiu.dao;

import com.example.biubiu.domain.Weapon;
import com.example.biubiu.util.DButil;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class WeaponDao {
    public Weapon getWeaponById(int id) {
        Connection cn = null;
        try {
            cn = DButil.getconnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from weapon where id=?";
        ResultSetHandler<Weapon> h = new BeanHandler<Weapon>(Weapon.class);
        try {
            Weapon p = queryRunner.query(cn,sql, h, id);
            if(p!=null) {
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Weapon> getAllWeapon() {
        Connection cn = null;
        try {
            cn = DButil.getconnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from weapon";
        try {
            List<Weapon> p = queryRunner.query(cn,sql, new BeanListHandler<>(Weapon.class));
            if(p!=null) {
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Weapon> getPlayerAllWeapon(String username){
        Connection cn = null;
        try {
            cn = DButil.getconnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        QueryRunner queryRunner = new QueryRunner();
        String sql = "SELECT weapon.id,weaponname,damage,weapon.bulletspeed,price,filepath " +
                "FROM weapon,playercharacter " +
                "WHERE playercharacter.username = ? " +
                "AND playercharacter.weaponid = weapon.id";
        try {
            List<Weapon> p = queryRunner.query(cn,sql, new BeanListHandler<>(Weapon.class), username);
            if(p!=null) {
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @SneakyThrows
    public int findIdByWeaponName(String weaponname){
        try (Connection conn = DButil.getconnection()) {
            QueryRunner queryRunner = new QueryRunner();
            String sql = "SELECT id FROM weapon WHERE weaponname = ?";
            Weapon weapon = queryRunner.query(conn, sql, new BeanHandler<>(Weapon.class), weaponname);
            return weapon.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
