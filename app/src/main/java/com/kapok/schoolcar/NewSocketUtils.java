package com.kapok.schoolcar;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import android.os.StrictMode;

public class NewSocketUtils {
    private boolean state=true;
    public SocketChannel socket=null;
    private InputStream is;
    private BufferedReader br;
    NewSocketUtils() {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            state=true;

            socket = SocketChannel.open();
            socket.configureBlocking(false);
            SocketAddress socketAddress = new InetSocketAddress(DataDetail.ip, DataDetail.port);
            socket.connect(socketAddress);

            socket.socket().setSoTimeout(2000);
            while(!socket.isConnected());
        } catch (Exception ex) {
            ex.printStackTrace();
            state=false;
        } finally {
            if(state==false)
                closeSocket();
        }
    }
    public void closeSocket()
    {
        try {
            if(socket!=null)
                socket.close();
            socket=null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            socket=null;
            e.printStackTrace();
        }
    }
    public void sendMessage(String string) {
        byte[] bytes = string.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        try {
            socket.write(buffer);
            socket.socket().shutdownOutput();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String readInternet() throws IOException {

        is = socket.socket().getInputStream();
        // dis = new DataInputStream(is);
        br = new BufferedReader(new InputStreamReader(is,"utf-8"));

        String temp= new String("");
        try {
            //temp+=dis.readUTF();

            //br.read(pBuff, 0, 2);
            //temp=new String(pBuff);
            temp+=br.readLine();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return temp;
    }
}
