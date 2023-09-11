package com.example.biubiu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DButil {
    public static Connection getconnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://rm-cn-uqm3dopp4001aggo.rwlb.cn-chengdu.rds.aliyuncs.com:3306/biubiunew?&serverTimezone=Asia/Shanghai";

        Connection connection = DriverManager.getConnection(url,"root","123456789Mcl");

        return connection;
    }
}
