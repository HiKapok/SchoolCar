package com.kapok.schoolcar;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginLogout extends ActionBarActivity {
    public static MyDataBase myDataBase;
    public String type;
    public EditText idEditText;
    public EditText keyEditText;
    public Button okButton;
    private Handler mHandler;
    public BackSendRunnable run;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_logout);
        idEditText=(EditText)findViewById(R.id.idEdit);
        keyEditText=(EditText)findViewById(R.id.keyEdit);
        okButton=(Button)findViewById(R.id.okButton);

        okButton.setOnClickListener(new myButtonLis());
        Intent intent= getIntent();
        type=intent.getStringExtra("type");
        if(type.equals("login"))
            okButton.setText("登陆");
        else{
            idEditText.setText(DataDetail.ID);
            keyEditText.requestFocus();
            //keyEditText.requestFocusFromTouch();
            okButton.setText("注销");
        }
        SetActivityBackground.set(this);
        myDataBase.getMyData();
    }
    class LoginoutFailed implements MyTask{
        private String type;
        LoginoutFailed(String type){
            this.type=type;
        }
        @Override
        public void task(String back) {
           ToastHelper.makeText(getApplicationContext(), type+"失败，请稍后重试", Toast.LENGTH_SHORT);
        }
    }
    class LoginoutSuccess implements MyTask{
        private String type;
        private String id;
        private String key;
        LoginoutSuccess(String type,String id,String key){
            this.type=type;
            this.key=key;
            this.id=id;
        }
        @Override
        public void task(String back) {
            if(back.contains("OK"))
            {
                ToastHelper.makeText(getApplicationContext(), type+"成功", Toast.LENGTH_SHORT);
                if(type.equals("登陆")){
                    DataDetail.ID_Useful = 1;
                    DataDetail.ID = id;
                    DataDetail.User = key;
                    DataDetail.num = myDataBase.getMyAskTBL();
                }else{
                    DataDetail.ID_Useful = 0;
                    DataDetail.num = 0;
                }
                myDataBase.updateMyData();
                LoginLogout.this.finish();
            }else{
                ToastHelper.makeText(getApplicationContext(), type+"失败，请核对账号或密码", Toast.LENGTH_SHORT);
           }
        }
    }

    public class myButtonLis implements OnClickListener {
        private String id;
        private String key;
        @Override
        public void onClick(View v) {
            id=idEditText.getText().toString();
            key=keyEditText.getText().toString();
            if(id.equals("")||key.equals("")){
                ToastHelper.makeText(getApplicationContext(), "输入不合法，请重新输入", Toast.LENGTH_SHORT);
            }else{
                if(DataDetail.debug.equals("0")) {
                    if (type.equals("login")) {
                        DataDetail.ID_Useful = 1;
                        DataDetail.ID = id;
                        DataDetail.User = key;
                        DataDetail.num = myDataBase.getMyAskTBL();
                        myDataBase.updateMyData();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                LoginLogout.this.finish();
                            }
                        }, 500); //800 for release
                        ToastHelper.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT);

                    } else {
                        if (DataDetail.ID.equals(id) && DataDetail.User.equals(key)) {
                            DataDetail.ID_Useful = 0;
                            DataDetail.num = 0;
                            myDataBase.updateMyData();
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    LoginLogout.this.finish();
                                }
                            }, 500); //800 for release
                            ToastHelper.makeText(getApplicationContext(), "注销成功", Toast.LENGTH_SHORT);

                        } else {
                            ToastHelper.makeText(getApplicationContext(), "密码或账号输入错误，请重新输入", Toast.LENGTH_SHORT);
                        }
                    }
                }else{//正常模式
                    if (type.equals("login")) {
                        run = new BackSendRunnable(true, "正在验证身份，请勿操作", "正在登陆...", LoginLogout.this, "Login."+id+"."+key, new LoginoutSuccess("登陆",id,key), new LoginoutFailed("登陆"));
                        mHandler = new Handler();
                        mHandler.postDelayed(run, 200);
                    }else{
                        run = new BackSendRunnable(true, "正在验证身份，请勿操作", "正在注销...", LoginLogout.this, "Logout."+id+"."+key, new LoginoutSuccess("注销",id,key), new LoginoutFailed("注销"));
                        mHandler = new Handler();
                        mHandler.postDelayed(run, 200);
                    }
                }

            }

        }
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
                LoginLogout.this.finish();
                break;
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true); android.R.id.home
        }
        return super.onOptionsItemSelected(item);
    }
}
