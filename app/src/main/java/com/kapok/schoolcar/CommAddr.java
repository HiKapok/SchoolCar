package com.kapok.schoolcar;

import android.app.Activity;
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
import android.widget.Spinner;
import android.widget.Toast;


public class CommAddr extends ActionBarActivity {
    private int start_char,end_char;
    private static final String[] add={"兴隆山校区","千佛山校区","趵突泉校区","中心校区","洪家楼校区","软件园校区"};
    public static MyDataBase myDataBase;
    private Spinner spinner_startadd;
    private Spinner spinner_endadd;
    private ArrayAdapter<String> start_adapter;
    private ArrayAdapter<String> end_adapter;
    Button setButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comm_addr);
        SetActivityBackground.set(this);
        myDataBase.getMyData();
        spinner_startadd = (Spinner) findViewById(R.id.startSpinner);
        spinner_endadd = (Spinner) findViewById(R.id.endSpinner);
        setButton=(Button) findViewById(R.id.setButton);
        //将可选内容与ArrayAdapter连接起来
        start_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,add);
        //设置下拉列表的风格
        start_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner_startadd.setAdapter(start_adapter);
        //添加事件Spinner事件监听  
        spinner_startadd.setOnItemSelectedListener(new StartSpinnerSelectedListener());
        //设置默认值
        spinner_startadd.setSelection(DataDetail.start);
        spinner_startadd.setVisibility(View.VISIBLE);
        //将可选内容与ArrayAdapter连接起来
        end_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,add);
        //设置下拉列表的风格
        end_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner_endadd.setAdapter(end_adapter);
        //添加事件Spinner事件监听  
        spinner_endadd.setOnItemSelectedListener(new EndSpinnerSelectedListener());
        //设置默认值
        spinner_endadd.setSelection(DataDetail.end);
        spinner_endadd.setVisibility(View.VISIBLE);

        setButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(start_char==end_char){
                    ToastHelper.makeText(getApplicationContext(), "选择不合法，请重新选择", Toast.LENGTH_SHORT);
                }else{
                    DataDetail.start=start_char;
                    DataDetail.common_add_Useful=1;
                    DataDetail.end=end_char;
                    myDataBase.updateMyData();
                    ToastHelper.makeText(getApplicationContext(), "设置成功", Toast.LENGTH_SHORT);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            CommAddr.this.finish();
                        }
                    }, 500); //800 for release

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
                CommAddr.this.finish();
                break;
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true); android.R.id.home
        }
        return super.onOptionsItemSelected(item);
    }
    //使用数组形式操作
    class StartSpinnerSelectedListener implements OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            String temp=spinner_startadd.getSelectedItem().toString();
            if(temp.equals("兴隆山校区")){
                start_char=0;
            }else{
                if(temp.equals("千佛山校区")){
                    start_char=1;

                }else{
                    if(temp.equals("中心校区")){
                        start_char=3;
                    }else{
                        if(temp.equals("趵突泉校区")){
                            start_char=2;
                        }else{
                            if(temp.equals("洪家楼校区")){
                                start_char=4;
                            }else{
                                start_char=5;
                            }
                        }
                    }
                }
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    //使用数组形式操作
    class EndSpinnerSelectedListener implements OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            String temp=spinner_endadd.getSelectedItem().toString();
            if(temp.equals("兴隆山校区")){
                end_char=0;
            }else{
                if(temp.equals("千佛山校区")){
                    end_char=1;
                }else{
                    if(temp.equals("中心校区")){
                        end_char=3;
                    }else{
                        if(temp.equals("趵突泉校区")){
                            end_char=2;
                        }else{
                            if(temp.equals("洪家楼校区")){
                                end_char=4;
                            }else{
                                end_char=5;
                            }
                        }
                    }
                }
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}
