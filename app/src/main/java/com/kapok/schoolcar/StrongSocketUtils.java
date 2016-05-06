package com.kapok.schoolcar;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;

public class StrongSocketUtils {
    private final int Connect_Time_Out=5000;
    private final int Read_Time_Out=2000;
    public Socket socket=null;
    private PrintStream output;
    private BufferedReader br;
    private Context context;
    private boolean show;
    StrongSocketUtils(Context context, boolean show){
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.context=context;
        this.show=show;
        initClientSocket();
    }
    public void handleException(Exception e, String prefix)
    {
        e.printStackTrace();
    }

    public void closeSocket()
    {
        releaseSocket();
        if(output!=null){
            output.close();
            output=null;
        }
        releaseInput();
    }
    public void releaseSocket() {
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
            handleException(e, "close exception: ");
        }
    }
    public void releaseInput() {
        try {
            if(br!=null){
                br.close();
                br=null;
            }
        } catch (IOException e) {
            handleException(e, "close exception: ");
        }
    }
    private void initClientSocket()
    {
        try
        {
	        /* 连接服务器 */
            socket=new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getByName(DataDetail.ip),DataDetail.port),Connect_Time_Out);
            //socket = new Socket(DataDetail.ip, DataDetail.port);
            socket.setSoTimeout(Read_Time_Out);
	   
	        /* 获取输出流 */
            output = new PrintStream(socket.getOutputStream(), true, "utf-8");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
        }
        catch (UnknownHostException e)
        {
            if(show)
                ToastHelper.makeText(context,"发生异常UnknownHostException，请稍后重试");
            socket=null;
        }
        catch (SocketTimeoutException e)
        {
            if(show)
                ToastHelper.makeText(context, "服务器连接超时，请检查网络连接、服务器设置或直接与管理员联系");
            socket=null;
        }
        catch (IOException e)
        {
            releaseSocket();
            if(output!=null){
                output.close();
                output=null;
            }
            releaseInput();
            if(show)
                ToastHelper.makeText(context, "无法获取网络输入流，请稍后重试");
        }
    }

    public boolean sendMessage(String msg)
    {
        if(socket!=null && output!=null) {
            output.print(msg);
            output.flush();
            return true;
        }else{
            if(show)
                ToastHelper.makeText(context, "网络IO异常，请求未发送");
            return false;
        }
            /* try {
            out.writeUTF(msg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }
    public String readInternet()
    {

        if(socket!=null) {
            String temp = new String("");
            try {
                //temp+=dis.readUTF();

                //br.read(pBuff, 0, 2);
                //temp=new String(pBuff);
                temp = br.readLine();
                return temp;
            } catch (SocketTimeoutException e) {
                if(show)
                    ToastHelper.makeText(context, "接收数据超时，请稍后重试");
                return null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            // temp+=br.readLine();
            // System.out.println(temp);
        }
        return null;
    }
}
