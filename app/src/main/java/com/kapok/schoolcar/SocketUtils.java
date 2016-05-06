package com.kapok.schoolcar;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import android.os.Handler;
import android.os.StrictMode;

public class SocketUtils {
    public Socket socket=null;
    private PrintStream output;
    private DataInputStream dis;
    private InputStream is;
    private BufferedReader br;
    private DataOutputStream out;
    SocketUtils(){
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initClientSocket();
    }
    public void handleException(Exception e, String prefix)
    {
        e.printStackTrace();
    }

    public void closeSocket()
    {
        try
        {
            if(socket!=null){
                output.close();
                //dis.close();
                is.close();
                br.close();
                //  is.close();
                socket.close();
                socket=null;
            }
        }
        catch (IOException e)
        {
            handleException(e, "close exception: ");
        }
    }
    private void initClientSocket()
    {
        try
        {
	      /* 连接服务器 */

            socket = new Socket(DataDetail.ip, DataDetail.port);

            socket.setSoTimeout(2000);
	   
	      /* 获取输出流 */
            output = new PrintStream(socket.getOutputStream(), true, "utf-8");
            is = socket.getInputStream();
            // dis = new DataInputStream(is);
            br = new BufferedReader(new InputStreamReader(is,"utf-8"));
            //out = new DataOutputStream(socket.getOutputStream());
        }
        catch (UnknownHostException e)
        {
            handleException(e, "unknown host exception: " + e.toString());
        }
        catch (IOException e)
        {
            if(output!=null){
                output.close();
                output=null;
                socket=null;
            }
            if(is!=null){
                socket=null;
                is=null;
            }
            if(br!=null){
                socket=null;
                br=null;
            }
            handleException(e, "io exception: " + e.toString());
        }
    }

    public void sendMessage(String msg)
    {

        output.print(msg);
        output.flush();
		/* try {
		out.writeUTF(msg);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
    }
    public String readInternet()
    {
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
        // temp+=br.readLine();
        System.out.println(temp);
        return temp;
    }
}
