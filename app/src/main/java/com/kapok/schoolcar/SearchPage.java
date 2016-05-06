package com.kapok.schoolcar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings.System;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class SearchPage extends ActionBarActivity {
    public static Context myContext;
    public static String day_char,start_char,end_char;
    String type;
    public static MyDataBase myDataBase;
    private static final String[] add={"兴隆山校区","千佛山校区","趵突泉校区","中心校区","洪家楼校区","软件园校区"};
    private static final String[] day_item={"双休日","工作日"};
    private Spinner spinner_startadd;
    private Spinner spinner_endadd;
    private ArrayAdapter<String> day_adapter;
    private Spinner spinner_day;
    private ArrayAdapter<String> start_adapter;
    private ArrayAdapter<String> end_adapter;
    Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        SetActivityBackground.set(this);
        myDataBase.getMyData();
        Intent intent= getIntent();
        type=intent.getStringExtra("type");
        spinner_startadd = (Spinner) findViewById(R.id.startSpinner);

        spinner_endadd = (Spinner) findViewById(R.id.endSpinner);
        spinner_day = (Spinner) findViewById(R.id.daypinner);
        searchButton=(Button) findViewById(R.id.searchButtons);
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
        //将可选内容与ArrayAdapter连接起来
        day_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,day_item);
        //设置下拉列表的风格
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner_day.setAdapter(day_adapter);
        //添加事件Spinner事件监听  
        spinner_day.setOnItemSelectedListener(new DaySpinnerSelectedListener());
        //设置默认值
        spinner_day.setVisibility(View.VISIBLE);
        searchButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(start_char.equals(end_char)){
                    ToastHelper.makeText(myContext, "选择不合法，请重新选择", Toast.LENGTH_SHORT);
                }else{
                    String temp_1=day_char;
                    String temp_2=start_char;
                    String temp_3=end_char;
                    //java.lang.System.out.println(temp);
                    Intent myIntent=new Intent();
                    myIntent.putExtra("type",type);
                    myIntent.putExtra("value_1",temp_1);
                    myIntent.putExtra("value_2",temp_2);
                    myIntent.putExtra("value_3",temp_3);
                    myIntent.setClass(SearchPage.this, SearchResPage.class);
                    SearchPage.this.startActivity(myIntent);
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
        java.lang.System.out.println(item.getItemId()+"");
        switch(item.getItemId()) {
            case 0x0102002C:
                SearchPage.this.finish();
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
                start_char="X";
            }else{
                if(temp.equals("千佛山校区")){
                    start_char="Q";

                }else{
                    if(temp.equals("中心校区")){
                        start_char="Z";
                    }else{
                        if(temp.equals("趵突泉校区")){
                            start_char="B";
                        }else{
                            if(temp.equals("洪家楼校区")){
                                start_char="H";
                            }else{
                                start_char="R";
                            }
                        }
                    }
                }
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    class DaySpinnerSelectedListener implements OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            String temp=spinner_day.getSelectedItem().toString();

            if(temp.equals("工作日")){
                day_char="W";
            }else{
                if(temp.equals("双休日")){
                    day_char="S";
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
                end_char="X";
            }else{
                if(temp.equals("千佛山校区")){
                    end_char="Q";
                }else{
                    if(temp.equals("中心校区")){
                        end_char="Z";
                    }else{
                        if(temp.equals("趵突泉校区")){
                            end_char="B";
                        }else{
                            if(temp.equals("洪家楼校区")){
                                end_char="H";
                            }else{
                                end_char="R";
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
