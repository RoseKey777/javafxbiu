package com.example.biubiu.dao;

import com.example.biubiu.domain.Request;
import com.example.biubiu.domain.User;
import com.example.biubiu.domain.Weapon;
import com.example.biubiu.util.DButil;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao {

    public List<Map<String, Object>> getAllUser(){
        try {
            List<Map<String, Object>> users = new ArrayList<>();
            Statement statement = DButil.getconnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user ORDER BY score DESC");
            while(resultSet.next()){
                Map<String, Object> map = new HashMap<>();
                map.put("username", resultSet.getString("username"));
                map.put("score", resultSet.getDouble("score"));
                users.add(map);
            }
            return users;
        } catch (ClassNotFoundException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateAvatar(String username, String avatar){
        try{
            String sql = "UPDATE user SET avatar = ? WHERE username = ?";
            PreparedStatement preparedStatement = DButil.getconnection().prepareStatement(sql);
            preparedStatement.setString(1, avatar);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User getUserByUsernameAndPassword(String username,String password) {
        Connection cn = null;
        try {
            cn = DButil.getconnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from user where username=? and password=?";
        ResultSetHandler<User> h = new BeanHandler<User>(User.class);
        try {
            User p = queryRunner.query(cn,sql, h, username, password);
            if(p!=null) {
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public User getUserByUsername(String username) {
        Connection cn = null;
        try {
            cn = DButil.getconnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from user where username=?";
        ResultSetHandler<User> h = new BeanHandler<User>(User.class);
        try {
            User p = queryRunner.query(cn,sql, h, username);
            if(p!=null) {
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @SneakyThrows
    public boolean register(String username, String password) {
        try (Connection conn = DButil.getconnection()) {
            QueryRunner queryRunner = new QueryRunner();

            // 检查用户名是否已存在
            String checkQuery = "SELECT COUNT(*) FROM user WHERE username = ?";
            Long existingUsers = queryRunner.query(conn, checkQuery, new ScalarHandler<Long>(), username);

            if (existingUsers > 0) {
                // 用户名已存在，无法注册
                return false;
            } else {
                // 插入新用户信息
                String insertQuery = "INSERT INTO user (username, password, coins, score) VALUES (?, ?, 30, 0)";
                int rowsInserted = queryRunner.update(conn, insertQuery, username, password);

                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            // 处理数据库异常
            e.printStackTrace();
            return false;
        }
    }

    @SneakyThrows
    public boolean addCoinsAndScore(String username, double coins, int score){
        try (Connection conn = DButil.getconnection()) {
            QueryRunner queryRunner = new QueryRunner();
            String sql = "UPDATE user SET coins = coins + ?, score = score + ? WHERE username = ?";
            int n = queryRunner.update(conn, sql, coins, score, username);
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
