package com.example.biubiu.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class TalkReceive implements Runnable {

    DatagramSocket datagramSocket =null;

    private int formPort;
    private String user;


    public TalkReceive(int formPort,String user){
        this.formPort = formPort;
        this.user = user;

        try {
            datagramSocket=new DatagramSocket(this.formPort);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        while (true){
            try {
                byte[] bytes = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(bytes, 0, bytes.length);
                datagramSocket.receive(datagramPacket);
                byte[] data = datagramPacket.getData();
                String s = new String(data, 0, datagramPacket.getLength());
//                System.out.println(this.user+":"+s.trim());
                handleData(s);
                if (s.contains("bye")){
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        datagramSocket.close();
    }

    public void handleData(String data){
        String[] dataList = data.split("|");
        if("loc".equals(dataList[0])){  //位置信息数据包

        }else if("atk".equals(dataList[0])){   //开枪数据包

        }
    }
}
