module com.example.biubiu {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires mail;
    requires lombok;
    requires commons.dbutils;
    requires fastjson;
    requires java.desktop;


    opens com.example.biubiu to javafx.fxml;
    exports com.example.biubiu;
    exports com.example.biubiu.controller;
    exports com.example.biubiu.domain;
    exports com.example.biubiu.net.tcp;
    opens com.example.biubiu.domain to fastjson;
    opens com.example.biubiu.controller to javafx.fxml;
}
