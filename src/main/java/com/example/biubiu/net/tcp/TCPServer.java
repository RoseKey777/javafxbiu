package com.example.biubiu.net.tcp;

import com.alibaba.fastjson.JSON;
import com.example.biubiu.domain.Room;
import com.example.biubiu.domain.User;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import javax.swing.*;
import java.awt.*;

public class TCPServer extends JFrame{

    private JTextArea m_display=new JTextArea();

    private ServerSocket serverSocket;

    /**
     * 创建线程池来管理客户端的连接线程
     * 避免系统资源过度浪费
     */
    private ExecutorService exec;

    //客户端列表
    Map<String, UserClient> userClients = new HashMap<>();

    //房间列表
    Room[] rooms = new Room[8];

    //可用端口列表
    ArrayList<Integer> portList = new ArrayList<>();

    // 存放客户端之间私聊的信息
    private Map<String,PrintWriter> storeInfo;

    public TCPServer() {
        super("服务器端");
        for(int i = 0; i < 8; i++){
            rooms[i] = new Room();
            rooms[i].id = i + 1;
        }
        for(int i = 0; i < 4000; i++){
            portList.add(8888 + i * 5);
        }
        Container c=getContentPane();
        c.add(new JScrollPane(m_display),BorderLayout.CENTER);
        try {

            serverSocket = new ServerSocket(6666);
            storeInfo = new HashMap<String, PrintWriter>();
            exec = Executors.newCachedThreadPool();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 将客户端的信息以Map形式存入集合中
    private void putIn(String key,PrintWriter value) {
        synchronized(this) {
            storeInfo.put(key, value);
        }
    }

    // 将给定的输出流从共享集合中删除
    private synchronized void remove(String  key) {
        storeInfo.remove(key);
//        m_display.append("当前在线人数为："+ storeInfo.size());
        //for(String name: storeInfo.key)
    }

    // 将给定的消息转发给所有客户端
    private synchronized void sendToAll(String message) {
        for(PrintWriter out: storeInfo.values()) {
            out.println(message);
            // m_display.append("已经发送了");
        }
    }

    // 将给定的消息转发给指定客户端
    private synchronized void sendToSomeone(String ip,String message) {
        System.out.println("To:" + ip + ": " + message);
        PrintWriter pw = storeInfo.get(ip); //获取对应输出流
        if(pw != null) pw.println(message);
    }

    public void start() {
        try {
            m_display.setVisible(true);
            //m_display.append("mayanshuo");
            while(true) {

                m_display.append("等待客户端连接... ... \n");

                Socket socket = serverSocket.accept();

                // 获取客户端的ip地址
                InetAddress address = socket.getInetAddress();
                String ip = address.getHostAddress();
                if(ip.equals("127.0.0.1"))
                    ip = "192.168.43.144";
                m_display.append("客户端：“" + ip + "”连接成功！ ");
                /*
                 * 启动一个线程，由线程来处理客户端的请求，这样可以再次监听
                 * 下一个客户端的连接
                 */
                exec.execute(new ListenrClient(socket)); //通过线程池来分配线程
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 该线程体用来处理给定的某一个客户端的消息，循环接收客户端发送
     * 的每一个字符串，并输出到控制台
     */
    class ListenrClient implements Runnable {

        private Socket socket;
        private String name;//ip
        private UserClient userClient;
        private RequestHandler requestHandler = new RequestHandler();//请求处理器
        private int currentRoom;    //当前房间号

        public ListenrClient(Socket socket) {
            this.socket = socket;
        }

        // 创建内部类来获取昵称(IP)
//        private String getName() throws Exception {
//            try {
//                //服务端的输入流读取客户端发送来的昵称输出流
//                BufferedReader bReader = new BufferedReader(
//                        new InputStreamReader(socket.getInputStream(), "UTF-8"));
//                //服务端将昵称验证结果通过自身的输出流发送给客户端
//                PrintWriter ipw = new PrintWriter(
//                        new OutputStreamWriter(socket.getOutputStream(), "UTF-8"),true);
//
//                requestHandler.output = ipw;//输出流
//                //requestHandler.output.println("wrrwerwr");
//
//                //读取客户端发来的昵称
//                while(true) {
//                    String nameString = bReader.readLine();
//                    if ((nameString.trim().length() == 0) || storeInfo.containsKey(nameString)) {
//                        ipw.println("FAIL");
//                    } else {
//                        ipw.println("OK");
//                        return nameString;
//                    }
//                }
//            } catch(Exception e) {
//                throw e;
//            }
//        }

        @Override
        public void run() {
            try {
                /*
                 * 通过服务器端的socket分配给每一个
                 * 用来将消息发送给客户端
                 */
                PrintWriter pw = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

                /*
                 * 将客户昵称和其所说的内容存入共享集合HashMap中
                 */
                name = socket.getInetAddress().getHostAddress();
                if(name.equals("127.0.0.1"))
                    name = "192.168.43.144";
                requestHandler.output = pw;//输出流
                putIn(name, pw);
                Thread.sleep(100);

                // 服务端通知所有客户端，某用户上线
//                sendToAll("*系统消息* “" + name + "”已上线");

                /*
                 * 通过客户端的Socket获取输入流
                 * 读取客户端发送来的信息
                 */
                BufferedReader bReader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), "UTF-8"));
                String msgString = null;


                //根据用户的请求进行分类处理
                while((msgString = bReader.readLine()) != null) {
                    Map<String, Object> request = JSON.parseObject(msgString);//转成json对象
                    System.out.println(request);
                    String type = (String)(request.get("type"));
                    Object dataObj = request.get("data");
                    Map<String, Object> data = JSON.parseObject(dataObj.toString());

                    m_display.append(name + ": \n");
                    m_display.append("    请求类型: " + type + "\n");
                    m_display.append("    请求数据: " + data + "\n");

                    //登录请求
                    if("login".equals(type)){
                        User user = requestHandler.login(data);
                        if (user != null) {
                            userClient = new UserClient(user, name);
                            String[] state = requestHandler.getUserState(user).split(",");
                            userClient.characterid = Integer.parseInt(state[0]);
                            userClient.weapenid = Integer.parseInt(state[1]);
                            System.out.println(userClient);
                            userClients.put(name, userClient);
                        }
                    }
                    //注册请求
                    else if("signup".equals(type)){
                        requestHandler.signup(data);
                    }
                    //获取用户信息
                    else if("getuserinfo".equals(type)){
                        Map<String, Object> req = new HashMap<>();
                        req.put("username", userClient.user.getUsername());
                        requestHandler.getuserinfo(req);
                    }
                    //获取所有房间人数
                    else if("getRoomNum".equals(type)){
                        String res = "";
                        for(int i = 0; i < 8; i++){
                            res += rooms[i].num;
                        }
                        pw.println(res);
                    }
                    //加入房间
                    else if("joinRoom".equals(type)){
                        int roomid = ((int)(data.get("roomid"))) - 1;
                        if(rooms[roomid].num <= 3){
                            rooms[roomid].userClients.add(userClient);
                            System.out.println(rooms[roomid].num);
                            rooms[roomid].num++;
                            currentRoom = roomid;
                            pw.println("加入成功");
                            System.out.println(123123);
                            //向房间中的其他人发送信息
                            for (int i = 0; i < rooms[currentRoom].num; i++) {
                                System.out.println(rooms[currentRoom].userClients.get(i).ip);
                                if(i != rooms[currentRoom].num - 1)
                                    sendToSomeone(rooms[currentRoom].userClients.get(i).ip, JSON.toJSONString(rooms[roomid]));
                            }
                        }
                        else
                            pw.println("人数已满");
                    }
                    //刷新房间
                    else if("refreshRoom".equals(type)){
                        int roomid = ((int)(data.get("roomid"))) - 1;
                        System.out.println(JSON.toJSONString(rooms[roomid]));
                        pw.println(JSON.toJSONString(rooms[roomid]));
                    }
                    //获取玩家所在房间号
                    else if("getCurrentRoom".equals(type)){
                        pw.println(currentRoom + 1);
                    }
                    //获取玩家在房间中的序号
                    else if("getPlayerNum".equals(type)){
                        for(int i = 0; i < rooms[currentRoom].num; i++)
                            if(rooms[currentRoom].userClients.get(i).ip.equals(name)){
                                pw.println(i);
                                System.out.println("num is " + i);
                            }

                    }
                    //开始游戏
                    else if("gameStart".equals(type)){
                        int gamePort = portList.remove(0);//这局游戏使用的端口
                        m_display.append("房间" + currentRoom + "开始游戏, " + "使用的端口为" + gamePort + '\n');
                        for (int i = 0; i < rooms[currentRoom].num; i++) {
                            if(!rooms[currentRoom].userClients.get(i).ip.equals(name)){
                                System.out.println(rooms[currentRoom].userClients.get(i).ip);
                                sendToSomeone(rooms[currentRoom].userClients.get(i).ip, "游戏开始|" + gamePort);
                            }
                        }
                        pw.println(gamePort);
                    }
                    //切换地图
                    else if("nextmap".equals(type)){
                        rooms[currentRoom].mapid = (int) data.get("mapid");
                        for(int i = 0; i < rooms[currentRoom].num; i++){
                            if(!rooms[currentRoom].userClients.get(i).ip.equals(name))
                                sendToSomeone(rooms[currentRoom].userClients.get(i).ip, JSON.toJSONString(rooms[currentRoom]));
                        }
                        pw.println("1");
                    }
                    //退出房间
                    else if("leaveRoom".equals(type)){
                        System.out.println(rooms[currentRoom].num);
                        for(int i = 0; i < rooms[currentRoom].num; i++)
                            if(rooms[currentRoom].userClients.get(i).ip.equals(name)){
                                rooms[currentRoom].userClients.remove(i);
                                break;
                            }
                        rooms[currentRoom].num--;
                        for (int i = 0; i < rooms[currentRoom].num; i++) {
                            System.out.println(rooms[currentRoom].userClients.get(i).ip);
                            sendToSomeone(rooms[currentRoom].userClients.get(i).ip, JSON.toJSONString(rooms[currentRoom]));
                        }
                        pw.println("退出");
                    }
                    //获取玩家的背包
                    else if("getuserbag".equals(type)){
                        requestHandler.getuserbag(data);
                    }
                    //获取所有武器信息
                    else if("getAllWeapon".equals(type)){
                        requestHandler.getAllWeapon(data);
                    }
                    //获取所有角色信息
                    else if("getAllCharacter".equals(type)){
                        requestHandler.getAllCharacter();
                    }
                    //更新武器状态
                    else if("updateWeapon".equals(type)){
                        requestHandler.updateWeapon(data);
                    }
                    //获取玩家已拥有的武器信息
                    else if("getPlayerAllWeapon".equals(type)){
                        requestHandler.getPlayerAllWeapon(data);
                    }
                    //更新角色状态
                    else if("updateCharacter".equals(type)){
                        requestHandler.updateCharacter(data);
                    }
                    //获取玩家登录状态信息
                    else if("getUserClient".equals(type)){
                        String[] state = requestHandler.getUserState(userClient.user).split(",");
                        userClient.characterid = Integer.parseInt(state[0]);
                        userClient.weapenid = Integer.parseInt(state[1]);
                        System.out.println(userClient.characterid);
                        System.out.println(userClient.weapenid);
                        pw.println(JSON.toJSONString(userClient));
                    }
                    //获取所有玩家信息
                    else if("getalluser".equals(type)){
                        requestHandler.getAllUser();
                    }
                    //更新头像
                    else if("updateavatar".equals(type)){
                        requestHandler.updateAvatar(data);
                    }
                    //增加金钱和积分
                    else if("addcoinsandscore".equals(type)){
                        String username = userClient.user.getUsername();
                        requestHandler.addCoinsAndScore(data, username);
                    }
                    //获得玩家所有武器
                    else if("getPlayerAllWeapon".equals(type)){
                        requestHandler.getPlayerAllWeapon(data);
                    }
                    //获得玩家所有角色
                    else if("getPlayerAllCharacter".equals(type)){
                        requestHandler.getPlayerAllCharacter(data);
                    }
                    //购买武器
                    else if("insertweapon".equals(type)){
                        requestHandler.insertWeapon(data);
                    }
                    //购买角色
                    else if("insertCharacter".equals(type)){
                        requestHandler.insertCharacter(data);
                    }
//                    else if("")
//                    // 检验是否为私聊（格式：@昵称：内容）
//                    if(msgString.startsWith("@")) {
//                        int index = msgString.indexOf("：");
//                        if(index >= 0) {
//                            //获取昵称
//                            String theName = msgString.substring(1, index);
//                            String info = msgString.substring(index+1, msgString.length());
//                            info =  name + "："+ info;
//                            //将私聊信息发送出去
//                            sendToSomeone(theName, info);
//
//                            sendToSomeone(name,info);
//
//                            continue;
//                        }
//                    }
//                    // 遍历所有输出流，将该客户端发送的信息转发给所有客户端
//                    m_display.append(name+"："+ msgString+"\n");
//                    sendToAll(name+"："+ msgString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                remove(name);
                // 通知所有客户端，某某客户已经下线
//                sendToAll("*系统消息* "+name + "已经下线了。\n");
                m_display.append(name + "已经下线了。\n");

                if(socket!=null) {
                    try {
                        socket.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        TCPServer server = new TCPServer();
        server.setSize(400,400);
        server.setVisible(true);
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        server.start();
    }
}