package com.kapok.schoolcar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.widget.Toast;

import org.apache.http.conn.ConnectTimeoutException;

public class CheckUpdate {
    public String stringSummerDayExt = new String("");
    public String stringSummerSunExt = new String("");
    public String stringWinterDayExt = new String("");
    public String stringWinterSunExt = new String("");
    public static MyDataBase myDataBase;
    private Pattern first_pattern = Pattern.compile("([.[^R]]{20,})");
    private Pattern second_pattern = Pattern.compile("([\u4e00-\u9fa5[、]]+|[0-9]{1,2}:[0-9]{2})");
    public static String versionDay = new String("2014-05-14 17:06:24");
    public static Context context;
    private boolean update=false;
    CheckUpdate(String url,boolean creat) {
        // TODO Auto-generated method stub
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Pattern time_pattern = Pattern.compile("([0-9[-: ]]{15,})");
        boolean state=true;
        String tempBuff = new String("");

        String stringSummerDay = new String("");
        String stringSummerSun = new String("");
        String stringWinterDay = new String("");
        String stringWinterSun = new String("");
        int index=0;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo!=null){
            if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                if(DataDetail.beOnNetAlways==0){
                    ToastHelper.makeText(context,"没有检测到合适的网络连接",Toast.LENGTH_SHORT);
                    state=false;
                }
            }else{
            }
        }else{
            ToastHelper.makeText(context,"没有检测到网络连接",Toast.LENGTH_SHORT);
            state=false;
        }
        if(state==true){
            try {
                //FileInputStream fis = new FileInputStream("F://test.txt");
                //FileReader fr = new FileReader(url);
				/*http://www.dxb.sdu.edu.cn/content.php?id=45*/
                URL Url = new URL(url);
                // 每个 HttpURLConnection 实例都可用于生成单个请求，
                //但是其他实例可以透明地共享连接到 HTTP 服务器的基础网络
                HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
                //设置 URL 请求的方法
                conn.setRequestMethod("GET");
                //设置一个指定的超时值（以毫秒为单位），
                //该值将在打开到此 URLConnection 引用的资源的通信链接时使用。
                conn.setConnectTimeout(5 * 1000);
                // conn.getInputStream()返回从此打开的连接读取的输入流
                //conn.connect();
                conn.setDoInput(true);
                // conn.setDoOutput(true); //允许输出流，即允许上传
                InputStream inStream = conn.getInputStream();// 通过输入流获取html数据
                InputStreamReader isr=new InputStreamReader(inStream,"gb2312");
                BufferedReader br = new BufferedReader(isr);

                while((tempBuff=br.readLine())!=null){

                    if(tempBuff.contains("Authentication is required.")){
                        ToastHelper.makeText(context,"该网络可能需要认证，请认证后重试",Toast.LENGTH_LONG);
                        Uri uri = Uri.parse("http://www.baidu.com/");
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(it);
                        break;
                    }
                    if(tempBuff.contains(("<p class=\"publish\">"))){
                        Matcher mat = time_pattern.matcher(tempBuff);
                        while(mat.find()){
                            myDataBase.getMyData();
                            String temps= mat.group().trim();
                            //System.out.println(temps);
                            if(creat){
                                versionDay = temps;
                                update=true;
                                myDataBase.updateMyData();

                            }else{
                                if(versionDay.compareTo(temps)>0){
                                    versionDay = temps;
                                    Builder alert = new AlertDialog.Builder(context).setTitle("发现新版本数据库").setMessage("确定要更新数据库吗？");
                                    alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            myDataBase.updateMyData();
                                            update=true;
                                        }
                                    });
                                    alert.setNegativeButton("取消",  new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    alert.show();
                                    myDataBase.getMyData();
                                }else{
                                    ToastHelper.makeText(context,"当前版本"+versionDay+"已是最新",Toast.LENGTH_LONG);

                                }
                            }
                        }
                    }

                    if(update){

                        if(tempBuff.contains(("<TBODY>"))){
                            index++;
                        }
                        switch(index){
                            case 1:
                                stringWinterDay+=tempBuff.trim();
                                break;
                            case 2:
                                stringWinterSun+=tempBuff.trim();
                                break;
                            case 3:
                                stringSummerDay+=tempBuff.trim();
                                break;
                            case 4:
                                stringSummerSun+=tempBuff.trim();
                                break;
                        }
                    }
                }
                conn.disconnect();
                inStream.close();
                isr.close();
                br.close();
            }
            catch (ConnectTimeoutException e){
                ToastHelper.makeText(context,"网络连接超时，请确定该网络可以连接到Internet");
            }
            catch (ConnectException e){
                ToastHelper.makeText(context,"网络连接错误，请确定该网络可以连接到Internet");
            }
            catch (UnknownHostException e){
                ToastHelper.makeText(context,"网络连接错误，请确定该网络可以连接到Internet");
            }
            catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //System.out.println(stringWinterDay);
            if(update){

                stringSummerDayExt=doJob(stringSummerDay,"W");
                stringSummerSunExt=doJob(stringSummerSun,"S");
                stringWinterDayExt=doJob(stringWinterDay,"W");
                stringWinterSunExt=doJob(stringWinterSun,"S");
                myDataBase.refreshCarTbl(this);
                ToastHelper.makeText(context,"更新成功，"+"当前版本"+versionDay,Toast.LENGTH_SHORT);
            }
			
			/*System.out.println(stringSummerDayExt);
			System.out.println(stringSummerSunExt);
			System.out.println(stringWinterDayExt);
			System.out.println(stringWinterSunExt);*/
			/*System.out.println(stringWinterSun);
			System.out.println(stringSummerDay);
			System.out.println(stringSummerSun);*/
        }
    }

    public String doJob(String s1,String s3) {
        Matcher mat=first_pattern.matcher(s1);
        String [] old = new String[5];
        String temptemp = new String("");
        while(mat.find()){
            //System.out.println(mat.group().trim());
            //mat.find();
            int cnt=0;
            String [] newStr = new String[5];

            String str=mat.group().trim();
            if(str.contains("运行时刻表")||str.contains("发车时间")||str.contains("日")||str.contains("版权所有")){
                continue;
            }

            Matcher matcher=second_pattern.matcher(str);

            while(matcher.find()){
                String tempStr=matcher.group().trim();
                if(tempStr.contains(":")){
                    if(tempStr.charAt(1)==':'){
                        tempStr='0'+tempStr;
                    }
                }
                //System.out.println(tempStr);
                newStr[cnt]=tempStr;
                cnt++;
            }
            if(cnt==5){
                for(int n=0;n<5;n++){
                    old[n]=newStr[n];
                }
            }else{
                if(cnt==3){
                    if(newStr[0].contains(":")){
                        newStr[4]=newStr[2];
                        newStr[2]=newStr[0];
                        newStr[3]=newStr[1];
                        newStr[0]=old[0];
                        newStr[1]=old[1];
                    }else{
                        newStr[3]=newStr[2];
                        newStr[2]=newStr[1];
                        newStr[1]=newStr[0];
                        newStr[0]=old[0];
                        newStr[4]=old[4];
                    }
                }else{
                    if(cnt==2){
                        newStr[2]=newStr[0];
                        newStr[3]=newStr[1];
                        newStr[0]=old[0];
                        newStr[1]=old[1];
                        newStr[4]=old[4];
                    }else{
                        if(cnt==4){
                            if(newStr[1].contains(":")){
                                newStr[4]=newStr[3];
                                newStr[3]=newStr[2];
                                newStr[2]=newStr[1];
                                newStr[1]=newStr[0];
                                newStr[0]=old[0];
                            }else{
                                newStr[4]=old[4];
                            }
                        }else{
                            continue;
                        }
                    }
                }
            }
            for(int n=0;n<5;n++){
                old[n]=newStr[n];
            }
            temptemp=temptemp+"/"+s3;
            if(newStr[0].contains("兴隆山校区")||newStr[0].contains("南山小区")){
                temptemp+="X";
            }else{
                if(newStr[0].contains("千佛山校区")){
                    temptemp+="Q";
                }else{
                    if(newStr[0].contains("中心校区")){
                        temptemp+="Z";
                    }else{
                        if(newStr[0].contains("趵突泉校区")){
                            temptemp+="B";
                        }else{
                            if(newStr[0].contains("洪家楼校区")||newStr[0].contains("五宿舍")){
                                temptemp+="H";
                            }else{
                                temptemp+="R";
                            }
                        }
                    }
                }
            }
            if((newStr[3].contains("兴隆山校区")||newStr[3].contains("南山小区"))&&!(newStr[0].contains("兴隆山校区")||newStr[0].contains("南山小区"))&&!(newStr[4].contains("兴隆山校区")||newStr[4].contains("南山小区"))){
                temptemp+="X";
            }else{
                if(newStr[3].contains("千佛山校区")&&!newStr[0].contains("千佛山校区")&&!newStr[4].contains("千佛山校区")){
                    temptemp+="Q";
                }else{
                    if(newStr[3].contains("中心校区")&&!newStr[0].contains("中心校区")&&!newStr[4].contains("中心校区")){
                        temptemp+="Z";
                    }else{
                        if(newStr[3].contains("趵突泉校区")&&!newStr[0].contains("趵突泉校区")&&!newStr[4].contains("趵突泉校区")){
                            temptemp+="B";
                        }else{
                            if((newStr[3].contains("洪家楼校区")||newStr[3].contains("五宿舍"))&&!(newStr[0].contains("洪家楼校区")||newStr[4].contains("五宿舍"))&&!(newStr[0].contains("洪家楼校区")||newStr[0].contains("五宿舍"))){
                                temptemp+="H";
                            }else{
                                if(newStr[3].contains("软件园"))
                                    temptemp+="R";
                            }
                        }
                    }
                }
            }
            if(newStr[4].contains("兴隆山校区")||newStr[4].contains("南山小区")){
                temptemp+="X";
            }else{
                if(newStr[4].contains("千佛山校区")){
                    temptemp+="Q";
                }else{
                    if(newStr[4].contains("中心校区")){
                        temptemp+="Z";
                    }else{
                        if(newStr[4].contains("趵突泉校区")){
                            temptemp+="B";
                        }else{
                            if(newStr[4].contains("洪家楼校区")||newStr[4].contains("五宿舍")){
                                temptemp+="H";
                            }else{
                                temptemp+="R";
                            }
                        }
                    }
                }
            }
            for(int n=0;n<5;n++){
                temptemp+="**";
                temptemp+=newStr[n];
            }
        }
        return temptemp;
    }
}
