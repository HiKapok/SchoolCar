package com.kapok.schoolcar;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class ServerSetPage extends ActionBarActivity {
    public static MyDataBase myDataBase;
    private EditText ip_edit;
    private EditText port_edit;
    Button setButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_page);
        SetActivityBackground.set(this);
        myDataBase.getMyData();
        ip_edit = (EditText) findViewById(R.id.ip);
        port_edit = (EditText) findViewById(R.id.port);
        setButton=(Button) findViewById(R.id.setIpButton);
        ip_edit.setText(DataDetail.ip);
        port_edit.setText(DataDetail.port.toString());

        setButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String ip=ip_edit.getText().toString();
                int port=Integer.parseInt(port_edit.getText().toString());
                //Pattern ips=Pattern.compile("");
                //System.out.println(ip.matches("[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}"));
                if(ip.matches("[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}")==true){
                    Pattern pattern = Pattern.compile("[.]");
                    String [] temp=pattern.split(ip);
                    //String [] temp = ip.split(".");
                    //System.out.println(ip+"");
                    //System.out.println(temp.length+"");
                    boolean flag=true;
                    for(int i=0;i<temp.length;i++){
                        int t=Integer.parseInt(temp[i]);
                        //System.out.println(t+"");
                        if(t<0||t>255){
                            flag=false;
                        }
                    }
                    if(flag==true){
                        if(port<2000||port>9998){
                            ToastHelper.makeText(getApplicationContext(), "请将端口号设置在2000-9999之间", Toast.LENGTH_SHORT);

                        }else{
                            DataDetail.ip=ip;
                            DataDetail.port=port;
                            myDataBase.updateMyData();
                            ToastHelper.makeText(getApplicationContext(), "设置成功", Toast.LENGTH_SHORT);
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    ServerSetPage.this.finish();
                                }
                            }, 500); //800 for release
                        }
                    }else
                        ToastHelper.makeText(getApplicationContext(), "设置失败，IP地址设置有误", Toast.LENGTH_SHORT);


                }else{
                    ToastHelper.makeText(getApplicationContext(), "设置失败，IP地址设置有误", Toast.LENGTH_SHORT);
                }
            }
        });
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        SetActivityBackground.set(this);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println(item.getItemId()+"");
        switch(item.getItemId()) {
            case 0x0102002C:
                ServerSetPage.this.finish();
                break;
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true); android.R.id.home
        }
        return super.onOptionsItemSelected(item);
    }
}
