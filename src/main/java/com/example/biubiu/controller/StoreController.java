package com.example.biubiu.controller;//package com.example.biubiu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.biubiu.Director;
import com.example.biubiu.HelloApplication;
import com.example.biubiu.domain.Character;
import com.example.biubiu.domain.Request;
import com.example.biubiu.domain.Weapon;
import com.example.biubiu.scene.Login;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public class StoreController implements Initializable {

    @FXML
    private Button exitMenu;
    @FXML
    private Label cargo1;
    @FXML
    private Label cargo2;
    @FXML
    private Label cargo3;
    @FXML
    private Label cargo4;
    @FXML
    private Label cargo5;
    @FXML
    private Label cargo6;

    @FXML
    private Label cargo13;
    @FXML
    private Label cargo14;
    @FXML
    private Label cargo15;
    @FXML
    private Label cargo16;
    @FXML
    private Label cargo17;
    @FXML
    private Label cargo18;

    private ArrayList<Label> labelList = new ArrayList<Label>();

    private ArrayList<Label> labelList3 = new ArrayList<Label>();
    @FXML
    private Label coins;
    @FXML
    private Label coin_icon;
    @FXML
    private Label add_coin;


    @FXML
    private Button cargoButton1;

    @FXML
    private Button cargoButton2;
    @FXML
    private Button cargoButton3;
    @FXML
    private Button cargoButton4;
    @FXML
    private Button cargoButton5;
    @FXML
    private Button cargoButton6;


    @FXML
    private Button cargoButton13;

    @FXML
    private Button cargoButton14;
    @FXML
    private Button cargoButton15;
    @FXML
    private Button cargoButton16;
    @FXML
    private Button cargoButton17;
    @FXML
    private Button cargoButton18;
    private ArrayList<Button> buttonList = new ArrayList<Button>();

    private ArrayList<Button> buttonList3 = new ArrayList<Button>();


    @FXML
    private TextArea allOfMsg;

    private Double currentCoin;

    private List<Weapon> weaponList = new ArrayList<>();

    private List<Character> characterList = new ArrayList<>();

    private String username;



    //------------------------------------------
    //初始化的显示(武器)
    @FXML
    void displayAllOfWeapons(JSONArray array){
//        List<Weapon> weapons = weaponDao.getAllWeapon();
//        System.out.println(weapons);
        for(int i = 0; i < array.size(); i++){
            Map<String, Object> item = JSON.parseObject(array.get(i).toString());
            String weaponname = (String)item.get("weaponname");
            BigDecimal damage = (BigDecimal) item.get("damage");
            BigDecimal bulletspeed = (BigDecimal) item.get("bulletspeed");
            BigDecimal price = (BigDecimal) item.get("price");
            String filepath = (String)item.get("filepath");

//            System.out.println(weaponname + '+' + damage + '+' +bulletspeed);

            Label label = labelList.get(i);

            Image image = new Image(Login.class.getResource(filepath).toExternalForm(),40,40,false,true); // filepath是图片路径
            label.setGraphic(new ImageView(image));
            Weapon weapon = new Weapon();
            weapon.setId(i);
            weapon.setWeaponname(weaponname);
            double damageDouble = damage.doubleValue();
            weapon.setBulletspeed(damageDouble);
            double bulletspeedDouble = bulletspeed.doubleValue();
            weapon.setBulletspeed(bulletspeedDouble);
            double priceDouble = price.doubleValue();
            weapon.setPrice(priceDouble);

            //将价格显示在Button上
            Button button = buttonList.get(i);
            button.setText("$" + " " + priceDouble);

            weaponList.add(weapon);

        }
    }
    @FXML
    void displayPurchasedWeapon(JSONArray array) {
        for (int i = 0; i < array.size(); i++){
            int index = array.getInteger(i);
            System.out.println(index);
            Button button = buttonList.get(index-1);
            button.setText("已购买");
            button.setDisable(true);
        }
    }

    //初始化的显示（人物）
    @FXML
    void displayAllOfCharacters(JSONArray array) {
        System.out.println(array);
        System.out.println(labelList3);
        for(int i = 0; i < array.size(); i++){
            Map<String, Object> item = JSON.parseObject(array.get(i).toString());
            String charactername = (String)item.get("charactername");
            BigDecimal hp = (BigDecimal) item.get("hp");
            BigDecimal price = (BigDecimal) item.get("price");
            String filepath = (String)item.get("filepath");
            Label labelCharacter = labelList3.get(i);

            Image image = new Image(Login.class.getResource(filepath).toExternalForm(),40,40,false,true); // filepath是图片路径
            labelCharacter.setGraphic(new ImageView(image));
            Character character = new Character();
            character.setId(i);
            character.setCharactername(charactername);
            double hpDouble = hp.doubleValue();
            character.setHp(hpDouble);
            double priceDouble = price.doubleValue();
            character.setPrice(priceDouble);

            //将价格显示在Button上
            Button button = buttonList3.get(i);
            button.setText("$" + " " + priceDouble);

            characterList.add(character);

        }
    }

    @FXML
    void displayPurchasedCharacter(JSONArray array) {
        System.out.println(array);
        for (int i = 0; i < array.size(); i++){
            int index = array.getInteger(i);
            System.out.println(index);
            Button button = buttonList3.get(index-1);
            button.setText("已购买");
            button.setDisable(true);
        }
    }


    //--------------------------------------------------------
    //点击事件
    @FXML
    void displayInformation(MouseEvent event){
        Label label = (Label) event.getSource();
        String information = "您点击了Label：" + label.getText();

        // 获取Label的索引（示例中假设Label的索引与List中的索引一致）
        int labelIndex = labelList.indexOf(label);
        // 获取对应武器的信息
        Weapon weapon = weaponList.get(labelIndex);
        Double damage = weapon.getDamage();
        Double bulletspeed = weapon.getBulletspeed();

        // 添加damage和bulletspeed信息到显示文本
        String weaponMsg = "\n伤害：" + damage + "\n子弹速度：" + bulletspeed;
        allOfMsg.appendText(weaponMsg + "\n");

        allOfMsg.appendText("确认购买此物品吗？" + "\n");
    }

    //点击事件中的人物
    @FXML
    void displayInformationCharacter(MouseEvent event){
        Label label = (Label) event.getSource();
        String information = "您点击了Label：" + label.getText();

        // 获取Label的索引（示例中假设Label的索引与List中的索引一致）
        int labelIndex = labelList3.indexOf(label);
        // 获取对应武器的信息
        Character character = characterList.get(labelIndex);
        String characterName = character.getCharactername();
        Double hp = character.getHp();

        // 添加damage和bulletspeed信息到显示文本
        String characterMsg = "\n人物名称：" + characterName + "\nHP：" + hp;
        allOfMsg.appendText(characterMsg + "\n");

        allOfMsg.appendText("确认购买此人物吗？" + "\n");
    }

    //退出，返回主界面
    @FXML
    void exitToIndex(MouseEvent mouseEvent){
        Director.getInstance().toIndex();
    }


    @FXML
    public void cargoShopping(MouseEvent mouseEvent) {

        Button button = (Button) mouseEvent.getSource();
        String buttonText = button.getText();
        String labelInfo = "";

        // 获取与Button同在一个VBox中的Label
        if (button.getParent() instanceof VBox) {
            VBox vBox = (VBox) button.getParent();
            for (Node node : vBox.getChildren()) {
                if (node instanceof Label) {
                    labelInfo = ((Label) node).getText();
                    break; // 找到一个 Label 后跳出循环
                }
            }
        }


        String information = "您点击了Button：" + buttonText + "\n与Button同在一个VBox的Label：" + labelInfo;
        allOfMsg.appendText(information + "\n");

        allOfMsg.appendText("确认购买此物品吗？" + "\n");

        // 创建一个确认对话框
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("确认购买");
        confirmationDialog.setHeaderText("请确认您的购买");
        confirmationDialog.setContentText("您是否确认要购买此物品？");

        // 设置对话框的模态性，阻止用户与主窗口交互
        confirmationDialog.initModality(Modality.APPLICATION_MODAL);

        // 显示对话框并等待用户响应
        ButtonType userResponse = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);

        if (userResponse == ButtonType.OK) {
            // 用户点击了确认按钮，执行购买操作
            int buttonIndex = buttonList.indexOf(button);
            Double coins = weaponList.get(buttonIndex).getPrice();
            if (coins > currentCoin){
                // 金币不足，显示提示对话框
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("购买失败");
                alert.setHeaderText(null);
                alert.setContentText("您的金币不足！");

                // 创建一个按钮用于关闭对话框
                ButtonType buttonTypeOK = new ButtonType("确定", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(buttonTypeOK);

                // 显示对话框并等待用户关闭
                Optional<ButtonType> result = alert.showAndWait();
            }else {

                Map<String, Object> data = new HashMap<>();
                data.put("coins", -coins);
                data.put("score", 0);
                Request request5 = new Request("addcoinsandscore", data);
                HelloApplication.sendRequest(request5);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("购买成功");
                alert.setHeaderText(null);
                alert.setContentText("购买成功！");

                // 创建一个按钮用于关闭对话框
                ButtonType buttonTypeOK = new ButtonType("确定", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(buttonTypeOK);

                // 显示对话框并等待用户关闭
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == buttonTypeOK) {
                    currentCoin -= coins;
                    this.coins.setText(String.valueOf(currentCoin));

                    //武器购买放入数据库
                    Map<String, Object> data1 = new HashMap<>();
                    data1.put("username", username);
                    data1.put("weaponid", buttonIndex + 1);
                    Request request6 = new Request("insertweapon", data1 );
                    HelloApplication.sendRequest(request6);
                    button.setText("已购买");
                }
                allOfMsg.appendText(information + "\n");
                allOfMsg.appendText("购买成功！\n");
            }
        } else {
            // 用户点击了取消按钮，取消购买操作
            allOfMsg.appendText(information + "\n");
            allOfMsg.appendText("购买已取消。\n");
        }
    }


    @FXML
    public void cargoShoppingCharacter(MouseEvent mouseEvent) {

        Button button = (Button) mouseEvent.getSource();
        String buttonText = button.getText();
        String labelInfo = "";

        // 获取与Button同在一个VBox中的Label
        if (button.getParent() instanceof VBox) {
            VBox vBox = (VBox) button.getParent();
            for (Node node : vBox.getChildren()) {
                if (node instanceof Label) {
                    labelInfo = ((Label) node).getText();
                    break; // 找到一个 Label 后跳出循环
                }
            }
        }


        String information = "您点击了Button：" + buttonText + "\n与Button同在一个VBox的Label：" + labelInfo;
        allOfMsg.appendText(information + "\n");

        allOfMsg.appendText("确认购买此物品吗？" + "\n");

        // 创建一个确认对话框
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("确认购买");
        confirmationDialog.setHeaderText("请确认您的购买");
        confirmationDialog.setContentText("您是否确认要购买此人物？");

        // 设置对话框的模态性，阻止用户与主窗口交互
        confirmationDialog.initModality(Modality.APPLICATION_MODAL);

        // 显示对话框并等待用户响应
        ButtonType userResponse = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);

        if (userResponse == ButtonType.OK) {
            // 用户点击了确认按钮，执行购买操作
            int buttonIndex = buttonList3.indexOf(button);
            Double coins = characterList.get(buttonIndex).getPrice();
            if (coins > currentCoin){
                // 金币不足，显示提示对话框
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("购买失败");
                alert.setHeaderText(null);
                alert.setContentText("您的金币不足！");

                // 创建一个按钮用于关闭对话框
                ButtonType buttonTypeOK = new ButtonType("确定", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(buttonTypeOK);

                // 显示对话框并等待用户关闭
                Optional<ButtonType> result = alert.showAndWait();
            }else {

                Map<String, Object> data = new HashMap<>();
                data.put("coins", -coins);
                data.put("score", 0);
                Request request5 = new Request("addcoinsandscore", data);
                HelloApplication.sendRequest(request5);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("购买成功");
                alert.setHeaderText(null);
                alert.setContentText("购买成功！");

                // 创建一个按钮用于关闭对话框
                ButtonType buttonTypeOK = new ButtonType("确定", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(buttonTypeOK);

                // 显示对话框并等待用户关闭
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == buttonTypeOK) {
                    currentCoin -= coins;
                    this.coins.setText(String.valueOf(currentCoin));

                    //人物购买放入数据库
                    Map<String, Object> data1 = new HashMap<>();
                    data1.put("username", username);
                    data1.put("characterid", buttonIndex + 1);
                    Request request6 = new Request("insertweapon", data1 );
                    HelloApplication.sendRequest(request6);
                    button.setText("已购买");
                }
                allOfMsg.appendText(information + "\n");
                allOfMsg.appendText("购买成功！\n");
            }
        } else {
            // 用户点击了取消按钮，取消购买操作
            allOfMsg.appendText(information + "\n");
            allOfMsg.appendText("购买已取消。\n");
        }
    }

    //-------------------
    //商店初始化
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Label label : Arrays.asList(
                cargo1, cargo2, cargo3, cargo4, cargo5, cargo6)) {
            labelList.add(label);
        }

        for (Label label : Arrays.asList(
                cargo13, cargo14, cargo15, cargo16, cargo17, cargo18)) {
            labelList3.add(label);
        }

        for (Button button : Arrays.asList(
                cargoButton1, cargoButton2, cargoButton3, cargoButton4, cargoButton5, cargoButton6)) {
            buttonList.add(button);
        }

        for (Button button : Arrays.asList(
                cargoButton13, cargoButton14, cargoButton15, cargoButton16, cargoButton17, cargoButton18)) {
            buttonList3.add(button);
        }

        Image image = new Image(
                Login.class.getResource("/com/example/biubiu/image/gold.png").toExternalForm(),
                46,46,false,true);
        coin_icon.setGraphic(new ImageView(image));

        image = new Image(Login.class.getResource("/com/example/biubiu/image/加号.png").toExternalForm(),40,40,false,true);
        add_coin.setGraphic(new ImageView(image));



        Request request1 = new Request("getuserinfo", null);
        String str = HelloApplication.sendRequest(request1);
        Map<String, Object> user =  JSON.parseObject(str);
        username =(String) user.get("username");
        BigDecimal currentCoinBigDecimal = (BigDecimal) user.get("coins");
        currentCoin = currentCoinBigDecimal.doubleValue();
        this.coins.setText(String.valueOf(currentCoin));

        //显示所有武器
        Request request2 = new Request("getAllWeapon", null);
        JSONArray array = JSON.parseArray(HelloApplication.sendRequest(request2));
        System.out.println(array);
        displayAllOfWeapons(array);

        //显示用户已购买的武器
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        Request request3 = new Request("getPlayerAllWeapon", jsonObject);
        JSONArray array2 = JSON.parseArray(HelloApplication.sendRequest(request3));
        displayPurchasedWeapon(array2);

        //显示所有人物
        Request request7 = new Request("getAllCharacter", null);
        JSONArray arrayCharacter = JSON.parseArray(HelloApplication.sendRequest(request7));
        System.out.println(arrayCharacter);
        displayAllOfCharacters(arrayCharacter);

        //显示用户已拥有的人物
        JSONObject jsonObjectCharacter = new JSONObject();
        jsonObject.put("username", username);
        Request request8 = new Request("getPlayerAllCharacter", jsonObject);
        JSONArray arrayCharacter2 = JSON.parseArray(HelloApplication.sendRequest(request8));
        displayPurchasedCharacter(arrayCharacter2);

    }




    public void addMouseEntered(MouseEvent mouseEvent) {
        add_coin.setCursor(Cursor.HAND);
    }
}
