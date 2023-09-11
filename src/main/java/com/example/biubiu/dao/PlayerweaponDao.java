package com.example.biubiu.dao;

import com.example.biubiu.domain.Playerweapon;
import com.example.biubiu.util.DButil;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlayerweaponDao {
    private WeaponDao weaponDao = new WeaponDao();

    @SneakyThrows
    public boolean updateWeapon(String username, String weaponname){
        try (Connection conn = DButil.getconnection()) {
            int weaponid = weaponDao.findIdByWeaponPath(weaponname);
            QueryRunner queryRunner = new QueryRunner();
            String sql = "UPDATE playerweapon " +
                    "SET weapon_state = 0 " +
                    "WHERE username = ?";
            queryRunner.update(conn, sql, username);
            sql = "UPDATE playerweapon " +
                    "SET weapon_state = 1 " +
                    "WHERE username = ? AND weaponid = ?";
            int n = queryRunner.update(conn, sql, username, weaponid);
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    @SneakyThrows
//    public int updateWeapon(String username, String weaponname){
//        try (Connection conn = DButil.getconnection()) {
//            QueryRunner queryRunner = new QueryRunner();
//            String insertQuery = "UPDATE playerweapon "
//                    + "SET weapon_state = (weapon_state + 1) % 2 "
//                    + "WHERE username = ? AND weaponid IN "
//                    + "(SELECT weaponid FROM weapon WHERE filepath = ?)";
//            int rowsInserted = queryRunner.update(conn, insertQuery, username, weaponname);
//            String selectQuery = "SELECT weapon_state "
//                    + "FROM playerweapon "
//                    + "WHERE username = ? AND weaponid IN "
//                    + "(SELECT weaponid FROM weapon WHERE filepath = ?)";
//            PreparedStatement preparedStatement = DButil.getconnection().prepareStatement(selectQuery);
//            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, weaponname);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//            int weapon_state = resultSet.getInt(1);
//            return weapon_state;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }

    public List<Playerweapon> getPlayerweaponByUsername(String username) {
        Connection cn = null;
        try {
            cn = DButil.getconnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from playerweapon where username=?";
        try {
            List<Playerweapon> p = queryRunner.query(cn,sql, new BeanListHandler<>(Playerweapon.class), username);
            if(p!=null) {
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @SneakyThrows
    public boolean insertWeapon(String username, int weaponid){
        try (Connection conn = DButil.getconnection()) {
            QueryRunner queryRunner = new QueryRunner();
            String sql = "INSERT INTO playerweapon " +
                    "(username, weaponid, weapon_state) " +
                    "VALUES (?, ?, 0)";
            int n = queryRunner.update(conn, sql, username, weaponid);
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
