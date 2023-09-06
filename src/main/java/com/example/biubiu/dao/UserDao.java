package com.example.biubiu.dao;

import com.example.biubiu.domain.User;
import com.example.biubiu.util.DButil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDao {

    public int checkUser(String username,String password) {
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
        int n = -1;
        try {
            User p = queryRunner.query(cn,sql, h, username, password);
            if(p!=null) {
                n = 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return n;
    }

//    public boolean signup(String username,String password){
//
//    }
}
