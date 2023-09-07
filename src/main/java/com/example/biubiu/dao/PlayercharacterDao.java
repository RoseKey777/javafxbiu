package com.example.biubiu.dao;

import com.example.biubiu.domain.Playercharacter;
import com.example.biubiu.util.DButil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class PlayercharacterDao {
    public Playercharacter getPlayercharacterByUserId(int userid) {
        Connection cn = null;
        try {
            cn = DButil.getconnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from playercharacter where userid=?";
        ResultSetHandler<Playercharacter> h = new BeanHandler<Playercharacter>(Playercharacter.class);
        try {
            Playercharacter p = queryRunner.query(cn,sql, h, userid);
            if(p!=null) {
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
