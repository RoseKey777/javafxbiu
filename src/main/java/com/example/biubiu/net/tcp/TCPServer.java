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

    // 存放客户端之间私聊的信息
    private Map<String,PrintWriter> storeInfo;

    public TCPServer() {
        super("服务器端");
        for(int i = 0; i < 8; i++){
            rooms[i] = new Room();
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

    // 将给定的消息转发给私聊的客户端
    private synchronized void sendToSomeone(String name,String message) {
        PrintWriter pw = storeInfo.get(name); //将对应客户端的聊天信息取出作为私聊内容发送出去
        if(pw != null) pw.println("私聊:     "+message);
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
                m_display.append("客户端：“" + address.getHostAddress() + "”连接成功！ ");
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
        private String name;
        private UserClient userClient;
        private RequestHandler requestHandler = new RequestHandler();//请求处理器

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
                    String type = (String)(request.get("type"));
                    Object dataObj = request.get("data");
                    Map<String, Object> data = JSON.parseObject(dataObj.toString());
                    System.out.println(request);
                    m_display.append(name + ": \n");
                    m_display.append("    请求类型: " + type + "\n");
                    m_display.append("    请求数据: " + data + "\n");

                    //登录请求
                    if("login".equals(type)){
                        User user = requestHandler.login(data);
                        if (user != null) {
                            userClient = new UserClient(user, name);
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
                    //获取房间人数
                    else if("getRoomNum".equals(type)){
                        String res = "";
                        for(int i = 0; i < 8; i++){
                            res += rooms[i].num;
                        }
                        pw.println(res);
                    }

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