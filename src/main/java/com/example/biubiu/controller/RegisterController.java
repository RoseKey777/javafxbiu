package com.example.biubiu.controller;

import com.example.biubiu.HelloApplication;
import com.example.biubiu.domain.Request;
import com.example.biubiu.scene.Login;
import com.example.biubiu.util.DButil;
import com.example.biubiu.util.SendMailUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.text.Font;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private String fontPath = LoginController.class.getResource("/com/example/biubiu/zpix.ttf").toExternalForm();

    @FXML
    private Label usernameLb;
    @FXML
    private Label passwordLb;
    @FXML
    private Label emailLb;
    @FXML
    private Label codeLb;

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

        Font font = Font.loadFont(fontPath, 15);
        usernameLb.setFont(font);
        passwordLb.setFont(font);
        emailLb.setFont(font);
        codeLb.setFont(font);
        font = Font.loadFont(fontPath, 13);
        t_name.setFont(font);
        t_password.setFont(font);
        t_email.setFont(font);
        t_code.setFont(font);

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
                Map<String, Object> data = new HashMap<>();
                data.put("username", username);
                data.put("password", password);
                Request request = new Request("signup", data);
                String result = HelloApplication.sendRequest(request);
                if(result.equals("注册成功")){
                    System.out.println("注册成功");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("information");
                        alert.setHeaderText(null);
                        alert.setContentText("注册成功!");
                        alert.showAndWait();
                }

//                sql = "insert into user (username,password) values(?,?)";
//                try {
//                    Connection connection = DButil.getconnection();
//                    pstmt = connection.prepareStatement(sql);
//                    pstmt.setString(1, username);
//                    pstmt.setString(2, password);
//                    int i = pstmt.executeUpdate();
//                    if (i > 0) {
//                        System.out.println("注册成功");
//                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                        alert.setTitle("information");
//                        alert.setHeaderText(null);
//                        alert.setContentText("注册成功!");
//                        alert.showAndWait();
//                    }
//                } catch (SQLException | ClassNotFoundException ex) {
//                    throw new RuntimeException(ex);
//                }
            }

        });
        this.email.setOnMouseClicked(e->{
            String str=t_email.getText();
            SendMailUtil.sendQQEmail(str,code);
        });
    }
}
