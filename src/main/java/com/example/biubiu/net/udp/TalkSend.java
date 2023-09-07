package com.example.biubiu.net.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class TalkSend implements Runnable {

    DatagramSocket datagramSocket =null;
    BufferedReader bufferedReader =null;
    DatagramPacket datagramPacket = null;

    private int fromPort;
    private String toIP;
    private int toPort;


    public TalkSend(int fromPort,String toIP,int toPort){
        this.fromPort=fromPort;
        this.toIP=toIP;
        this.toPort=toPort;

        try {
            datagramSocket = new DatagramSocket(this.fromPort);
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            //控制台读取数据
            while (true){
                String data = bufferedReader.readLine();
                sendData(data);
                if(data.equals("exit")){
                    datagramSocket.close();
                    return;
                }
            }

        } catch (SocketException e) {
            throw new RuntimeException("发送错误");
        } catch (IOException e) {
            throw new RuntimeException("信息读取错误");
        }

    }

    //发送数据
    public void sendData(String data){
        try {
            byte[] bytes = data.getBytes();
            datagramPacket = new DatagramPacket(bytes,0,bytes.length,new InetSocketAddress(this.toIP,this.toPort));
            datagramSocket.send(datagramPacket);
        }catch (SocketException e) {
            throw new RuntimeException("发送错误");
        } catch (IOException e) {
            throw new RuntimeException("信息读取错误");
        }

    }
}