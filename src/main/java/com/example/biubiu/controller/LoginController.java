package com.example.biubiu.controller;

import com.alibaba.fastjson.JSON;
import com.example.biubiu.Director;
import com.example.biubiu.HelloApplication;
import com.example.biubiu.domain.Request;
import com.example.biubiu.domain.User;
import com.example.biubiu.scene.Login;
import com.example.biubiu.util.DButil;
import com.example.biubiu.util.SendMailUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private String fontPath = LoginController.class.getResource("/com/example/biubiu/zpix.ttf").toExternalForm();

    @FXML
    private Label usernameLb;

    @FXML
    private Label passwordLb;

    @FXML
    private TextField t_name;

    @FXML
    private PasswordField t_password;

    @FXML
    private Button login;

    @FXML
    private Button register;

    @FXML
    private GridPane gr;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Font font = Font.loadFont(fontPath, 15);
        usernameLb.setFont(font);
        passwordLb.setFont(font);
        font = Font.loadFont(fontPath, 13);
        t_name.setFont(font);
        t_password.setFont(font);

        Image image1 = new Image(Login.class.getResource("/com/example/biubiu/images/login_button_01.gif").toExternalForm(),50,30,false,true);
        BackgroundImage backgroundImage1 =new BackgroundImage(image1,null,null,null,null);
        login.setBackground(new Background(backgroundImage1));

        image1 = new Image(Login.class.getResource("/com/example/biubiu/images/register_button_01.gif").toExternalForm(),50,30,false,true);
        backgroundImage1 =new BackgroundImage(image1,null,null,null,null);
        register.setBackground(new Background(backgroundImage1));


        this.login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = t_name.getText();
                String password = t_password.getText();
                String sql = "SELECT * FROM user WHERE username =? AND password =?";
                PreparedStatement pstmt;
                ResultSet rs;
                //System.out.println(HelloApplication.clientSocket.getPort());
                //发送登录请求
                Request request = new Request("login", new User(username, password));
                System.out.println(HelloApplication.sendRequest(request));


                try {
                    Connection connection = DButil.getconnection();
                    pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        System.out.println("登录成功！");
                        Map<String, String> map = new HashMap<>();
                        map.put("username",username);
                        map.put("password",password);
                        Stage stage = Director.getInstance().getStage();
                        Director.getInstance().Toindex(stage);


                    }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("用户名或密码错误!");
                        alert.showAndWait();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        this.register.setOnMouseClicked(e -> {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(HelloApplication.class.getResource("/com/example/biubiu/fxml/register-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 400, 300);
                //scene.getStylesheets().add("/com/example/biubiu/fusion-pixel-10px-monospaced-zh_hans.ttf");
                //scene.getStylesheets().add(HelloApplication.class.getResource("/com/example/biubiu/font.css").toExternalForm());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            Image image = new Image(HelloApplication.class.getResource("/com/example/biubiu/image/background.png").toExternalForm(),400,300,false,true);
            BackgroundImage backgroundImage =new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    null);
            GridPane gridPane=(GridPane) scene.getRoot();
            gridPane.setBackground(new Background(backgroundImage));

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("biubiu~");
            stage.show();
        });

    }

}