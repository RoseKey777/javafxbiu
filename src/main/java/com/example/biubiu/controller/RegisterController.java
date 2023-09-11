package com.example.biubiu.controller;

import com.example.biubiu.scene.Login;
import com.example.biubiu.util.DButil;
import com.example.biubiu.util.SendMailUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private TextField t_name;

    @FXML
    private PasswordField t_password;
    @FXML
    private TextField t_email;
    @FXML
    private TextField t_code;

    @FXML
    private Button register;

    @FXML
    private Button email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image1 = new Image(Login.class.getResource("/com/example/biubiu/images/register_button_01.gif").toExternalForm(),50,30,false,true);
        BackgroundImage backgroundImage =new BackgroundImage(image1,null,null,null,null);
        register.setBackground(new Background(backgroundImage));

        image1 = new Image(Login.class.getResource("/com/example/biubiu/images/send_code_button_01.gif").toExternalForm(),50,30,false,true);
        backgroundImage =new BackgroundImage(image1,null,null,null,null);
        email.setBackground(new Background(backgroundImage));

        Random random =new Random();
        int code = random.nextInt(1000, 9999);
        String str_code= Integer.toString(code);

        this.register.setOnMouseClicked(e -> {
            String _code =t_code.getText();

            if(_code.equals(str_code)){
                String username = t_name.getText();
                String password = t_password.getText();
                String sql = "SELECT * FROM user WHERE username =? ";
                PreparedStatement pstmt;
                ResultSet rs;
                try {
                    Connection connection = DButil.getconnection();
                    pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, username);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        System.out.println("用户名已存在");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("用户名已存在!");
                        alert.showAndWait();
                        return;
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                sql = "insert into user (username,password) values(?,?)";
                try {
                    Connection connection = DButil.getconnection();
                    pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    int i = pstmt.executeUpdate();
                    if (i > 0) {
                        System.out.println("注册成功");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("information");
                        alert.setHeaderText(null);
                        alert.setContentText("注册成功!");
                        alert.showAndWait();
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
        this.email.setOnMouseClicked(e->{
            String str=t_email.getText();
            SendMailUtil.sendQQEmail(str,code);
        });
    }
}
