package com.example.biubiu.dao;

import com.example.biubiu.domain.Playerskill;
import com.example.biubiu.util.DButil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class PlayerskillDao {
    public Playerskill getPlayerskillByUserId(int userid) {
        Connection cn = null;
        try {
            cn = DButil.getconnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from playerskill where userid=?";
        ResultSetHandler<Playerskill> h = new BeanHandler<Playerskill>(Playerskill.class);
        try {
            Playerskill p = queryRunner.query(cn,sql, h, userid);
            if(p!=null) {
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
