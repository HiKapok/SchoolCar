package com.kapok.schoolcar;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SetPage extends ActionBarActivity {
    private Context setContext;
    private ListView myList;
    private static ProgressDialog pd=null;
    public static MyDataBase myDataBase;
    private UpdateCarTbl updateCarTbl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_page);
        SetActivityBackground.set(this);
        CheckUpdate.context=SetPage.this;
        myDataBase.getMyData();
        setContext=getApplicationContext();
        //myList=(ListView)getListView();
        myList=(ListView)findViewById(R.id.set_lists);
        myList.setAdapter(new MySetListAdapter());
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        intent.setClass(SetPage.this, CommAddr.class);
                        SetPage.this.startActivity(intent);
                        break;
                    case 2:

                        intent.setClass(SetPage.this, ServerSetPage.class);
                        SetPage.this.startActivity(intent);
                        break;
                    case 3:
                        final Integer bak=DataDetail.AskRingTimeBefore;
                        LayoutInflater inflater = getLayoutInflater();
                        final View layout = inflater.inflate(R.layout.set_ask_ring_time,(ViewGroup)findViewById(R.id.dialog));
                        AlertDialog.Builder builder = new AlertDialog.Builder(SetPage.this);
                        builder.setView(layout);
                        builder.setTitle("请选择提醒预约的时间");
                        NumberPicker numPicker = (NumberPicker)layout.findViewById(R.id.numberPicker);
                        numPicker.setMinValue(1);
                        numPicker.setMaxValue(9);
                        numPicker.setValue(DataDetail.AskRingTimeBefore);
                        numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                DataDetail.AskRingTimeBefore=newVal;
                            }
                        });
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                DataDetail.AskRingTimeBefore=bak;
                            }
                        });
                        builder.create().show();
                        break;
                    case 5:
                        break;
                    case 7:
                        intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                        intent.putExtra(Intent.EXTRA_TEXT, "Duang~~简单又实用的校车查询APP来了！还有预约功能哦！再也不用担心坐不上校车了，快来下载吧");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent, "分享软件给好友"));
                        break;
                    case 8:
                        intent.setClass(SetPage.this, FeedBackPage.class);
                        SetPage.this.startActivity(intent);
                        break;
                    case 9:
                        pd = ProgressDialog.show(SetPage.this, "更新中，请勿操作", "正在更新数据库...", true, false);
                       // updateCarTbl=new UpdateCarTbl("http://www.dxb.sdu.edu.cn/content.php?id=45",SetPage.this, false,pd,"更新数据库");
                       // updateCarTbl.execute();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                new CheckUpdate("http://www.dxb.sdu.edu.cn/content.php?id=45", false);
                                pd.dismiss();
                            }
                        }, 500);
                        break;
                }
                myDataBase.updateMyData();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println(item.getItemId()+"");
        switch(item.getItemId()) {
            case 0x0102002C:
                SetPage.this.finish();
                break;
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true); android.R.id.home
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        SetActivityBackground.set(this);
        myList.setAdapter(new MySetListAdapter());
        myDataBase.updateMyData();
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();

        myDataBase.updateMyData();
    }
    public class MySetListAdapter extends BaseAdapter {
        TextView itemText;
        public MySetListAdapter() {
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Holder holder=new Holder();
            final Holder_2 holder_2=new Holder_2();
            if(position==1||position==2||position==7||position==8){
                convertView=LayoutInflater.from(setContext).inflate(R.layout.set_list_item_arrow, null);
                itemText=(TextView)convertView.findViewById(R.id.itemname_2);
            }
            if(position==4||position==5||position==6){
                convertView=LayoutInflater.from(setContext).inflate(R.layout.set_list_item_button, null);
                holder.myToggleButton=(ToggleButton)convertView.findViewById(R.id.myswtich);
                holder.myText=(TextView) convertView.findViewById(R.id.itemname_1);
            }
            if(position==0){
                convertView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.login_list_item, null);
                holder_2.myButton=(Button) convertView.findViewById(R.id.name_3);
                holder_2.myText1=(TextView) convertView.findViewById(R.id.name_1);
                holder_2.myText2=(TextView) convertView.findViewById(R.id.name_2);
            }
            if(position==9||position==3){
                convertView=LayoutInflater.from(setContext).inflate(R.layout.set_list_item_comm, null);
                itemText=(TextView)convertView.findViewById(R.id.itemname_2);
            }
            switch(position){
                case 0:{
                    if(DataDetail.ID_Useful==0){
                        holder_2.myText1.setText("登陆管理");
                        holder_2.myText2.setText("尚未登录");
                        holder_2.myButton.setText("登陆");
                    }else{
                        holder_2.myText1.setText("登陆管理");
                        holder_2.myText2.setText("已登录："+DataDetail.ID);
                        holder_2.myButton.setText("注销");
                    }
                    holder_2.myButton.setOnClickListener(new ButtonLis(position));
                    break;
                }
                case 1:{
                    itemText.setText("常用乘车路线管理");
                    break;
                }
                case 2:{
                    itemText.setText("服务器设置");
                    break;
                }
                case 3:{
                    itemText.setText("设置预约提醒时间");
                    break;
                }
                case 4:{
                    if(DataDetail.beOnNetAlways==1){
                        holder.myToggleButton.setChecked(true);
                    }else{
                        holder.myToggleButton.setChecked(false);
                    }
                    holder.myText.setText("是否使用数据网络");
                    holder.myToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener(){
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                            // TODO Auto-generated method stub
                            if(buttonView==holder.myToggleButton){
                                if(isChecked){
                                    //选中
                                    DataDetail.beOnNetAlways=1;
                                    ToastHelper.makeText(setContext, "始终连接网络", Toast.LENGTH_SHORT);
                                }else{
                                    //未选中
                                    DataDetail.beOnNetAlways=0;
                                    ToastHelper.makeText(setContext, "仅WIFI下联网", Toast.LENGTH_SHORT);
                                }
                                myDataBase.updateMyData();
                            }}});
                    break;
                }
                case 5:{
                    if(DataDetail.beNight==1){
                        holder.myToggleButton.setChecked(true);
                    }else{
                        holder.myToggleButton.setChecked(false);
                    }
                    holder.myText.setText("开启夜间模式");
                    convertView.setTag(holder);
                    holder.myToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener(){
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                        // TODO Auto-generated method stub
                        if(buttonView==holder.myToggleButton){
                            if(isChecked){
                                //选中
                                DataDetail.beNight=1;
                                ToastHelper.makeText(setContext, "夜间模式", Toast.LENGTH_SHORT);
                            }else{
                                //未选中
                                DataDetail.beNight=0;
                                ToastHelper.makeText(setContext, "正常模式", Toast.LENGTH_SHORT);
                            }
                            myDataBase.updateMyData();
                            SetPage.this.onResume();
                        }}});
                    break;
                }
                case 6:{
                    if(DataDetail.debug.equals("0")){
                        holder.myToggleButton.setChecked(true);
                    }else{
                        holder.myToggleButton.setChecked(false);
                    }
                    holder.myText.setText("使用调试模式");
                    convertView.setTag(holder);

                    holder.myToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener(){
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                        // TODO Auto-generated method stub
                        if(buttonView==holder.myToggleButton){
                            if(isChecked){
                                //选中
                                DataDetail.debug="0";
                                ToastHelper.makeText(setContext, "调试模式(该模式下程序可能会运行不正常)", Toast.LENGTH_SHORT);
                            }else{
                                //未选中
                                DataDetail.debug="1";
                                ToastHelper.makeText(setContext, "正常模式(自动切换冬夏时刻表，在线获取预约列表，在线身份验证)", Toast.LENGTH_SHORT);
                            }
                            myDataBase.updateMyData();
                        }}});
                    break;
                }
                case 7:{
                    itemText.setText("我要分享");
                    break;
                }
                case 8:{
                    itemText.setText("我要反馈");
                    break;
                }
                case 9:{
                    itemText.setText("数据库升级");
                    break;
                }
            }
            return convertView;
        }
        public class ButtonLis implements OnClickListener{
            private int index;
            ButtonLis(int index){
                this.index=index;
            }
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent();
                if(DataDetail.ID_Useful==0){
                    intent.putExtra("type","login");
                    intent.setClass(SetPage.this, LoginLogout.class);
                    SetPage.this.startActivity(intent);
                }else{
                    intent.putExtra("type","logout");
                    intent.setClass(SetPage.this, LoginLogout.class);
                    SetPage.this.startActivity(intent);
                }
            }

        }
        class Holder_2{
            public TextView myText1;
            public TextView myText2;
            public Button myButton;
        }
        class Holder{
            public TextView myText;
            public ToggleButton myToggleButton;
        }
        @Override
        public int getCount() {
            return 10;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
