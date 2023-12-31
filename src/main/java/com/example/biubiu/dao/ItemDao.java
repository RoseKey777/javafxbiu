package com.example.biubiu.dao;

import com.example.biubiu.domain.Item;
import com.example.biubiu.util.DButil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class ItemDao {
    public Item getItemById(int id) {
        Connection cn = null;
        try {
            cn = DButil.getconnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from item where id=?";
        ResultSetHandler<Item> h = new BeanHandler<Item>(Item.class);
        try {
            Item p = queryRunner.query(cn,sql, h, id);
            if(p!=null) {
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
