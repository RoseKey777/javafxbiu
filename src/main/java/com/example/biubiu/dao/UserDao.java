package com.example.biubiu.dao;

import com.example.biubiu.domain.User;
import com.example.biubiu.util.DButil;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDao {

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
                String insertQuery = "INSERT INTO user (username, password, coins, score) VALUES (?, ?, 0, 0)";
                int rowsInserted = queryRunner.update(conn, insertQuery, username, password);

                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            // 处理数据库异常
            e.printStackTrace();
            return false;
        }
    }
}
