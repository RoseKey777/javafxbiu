module com.example.biubiu {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires mail;
    requires java.desktop;
    requires fastjson;


    opens com.example.biubiu to javafx.fxml;
    exports com.example.biubiu;
    exports com.example.biubiu.controller;
    opens com.example.biubiu.controller to javafx.fxml;
}
