package cn.edu.hcnu.client;

import com.sun.security.ntlm.Server;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * UDP传输
 * 接收端
 */
public class GetMessageThread extends Thread {
    DatagramSocket ds;
    JTextArea ta;
    JComboBox cb;

    public GetMessageThread(ChatThreadWindow ctw) {
        this.ds = ctw.ds;
        this.ta = ctw.ta;
        this.cb = ctw.cb;
    }

    public void run() {
        try {
            while (true) {
                //1.创建字节数组，接收发来的数据
                byte buff[] = new byte[1024];
                //2.创建创建数据包对象（集装箱）
                DatagramPacket dp = new DatagramPacket(buff, 200);
                //3.接收数据
                ds.receive(dp);
                //getLength:获取有效长度
                String message = new String(buff, 0, dp.getLength());
                //添加到聊天窗口中的发言中
                ta.append(message);

                //1、分割消息拿到用户名，例如wjl
                //判断字符串中是否有子字符串。如果有则返回true，如果没有则返回false。
                if (message.contains("进入了聊天室")) {
                    //字符串替换
                    message = message.replace("进入了聊天室", "");
                    System.out.println("处理后的消息：" + message);
                }
                //2、然后在使用JComboBox把用户名加入下拉框
                cb.addItem(message);


                System.out.println("UDP收的的消息：" + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}