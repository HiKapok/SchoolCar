package com.kapok.schoolcar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAskTbl extends ActionBarActivity{
    public static MyDataBase myDataBase;
    MySetListAdapter mySetListAdapter;
    public ListView myList;
    private Handler mHandler;
    public BackSendRunnable run;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myask_page);

        SetActivityBackground.set(this);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDataBase.getMyData();
        myList = (ListView) findViewById(R.id.ask_list);
        if(DataDetail.debug.equals("0")) {
            myDataBase.getMyAskTBL();
            Date dateNow2 = new Date();
            try {
                for (String[] element : DataDetail.collects_ask) {
                    Date date = new Date();
                    Pattern pattern = Pattern.compile("[.]+");
                    String[] subresult = pattern.split(element[6]);
                    date.setDate(Integer.parseInt(subresult[1]));
                    date.setMonth(Integer.parseInt(subresult[0]));
                    date.setHours(Integer.parseInt(element[5].substring(0, 2)));
                    date.setMinutes(Integer.parseInt(element[5].substring(3, 5)));
                    date.setSeconds(0);

                    if (date.before(dateNow2)) {
                        DataDetail.collects_ask.removeElement(element);
                        DataDetail.num--;
                    }
                }
            }catch (ArrayIndexOutOfBoundsException e){
                ToastHelper.makeText(getApplicationContext(),"服务器返回的实际乘车时间格式有误");
            }
            myDataBase.updateMyAskTBL();

            if (DataDetail.num < 1) {
                ToastHelper.makeText(getApplicationContext(), "您还没有预约校车哦", Toast.LENGTH_SHORT);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        MyAskTbl.this.finish();
                    }
                }, 500); //800 for release
            }
            mySetListAdapter = new MySetListAdapter();
            myList.setAdapter(mySetListAdapter);
            myList.setOnItemClickListener(new ListLis());
        }else{
            run = new BackSendRunnable(true,"正在努力加载，请勿操作", "正在获取预约列表...",MyAskTbl.this,"GetTbl"+"."+DataDetail.ID,new GetAskTblTaskSuccess(),new GetAskTblTaskFailed());
            mHandler=new Handler();
            mHandler.postDelayed(run,200);
        }
    }
    class GetAskTblTaskSuccess implements MyTask{

        @Override
        public void task(String back) {
            Pattern quoteRegex = Pattern.compile("[/]+");
            String [] result=quoteRegex.split(back);
            DataDetail.collects_ask.removeAllElements();
            DataDetail.num=0;
            for (int i=0; i<result.length; i++) {
                String [] temp=new String[8];
                temp[0]="1";
                Pattern pattern = Pattern.compile("[**]+");
                String [] subresult=pattern.split(result[i]);
                for (int j=0; j<subresult.length; j++) {
                    temp[j+1]=subresult[j];
                }
                DataDetail.collects_ask.addElement(temp);
            }
            DataDetail.num=DataDetail.collects_ask.size();
            myDataBase.updateMyAskTBL();
            myDataBase.updateMyData();
           /*
            temp.put("userType",temp[1]);
            temp.put("type(缩写)",temp[2]);
            temp.put("start",temp[3]);
            temp.put("end",temp[4]);
            temp.put("whentime",temp[5]);
            temp.put("time",temp[6]);
            temp.put("daytype",temp[7]);*/

            mySetListAdapter = new MySetListAdapter();
            myList.setAdapter(mySetListAdapter);
            myList.setOnItemClickListener(new ListLis());

        }
    }
    class GetAskTblTaskFailed implements MyTask{

        @Override
        public void task(String back) {
            MyAskTbl.this.finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println(item.getItemId()+"");
        switch(item.getItemId()) {
            case 0x0102002C:
                MyAskTbl.this.finish();
                break;
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true); android.R.id.home
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        myDataBase.updateMyData();
        super.onPause();
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        SetActivityBackground.set(this);
    }
    public class ListLis implements OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            // TODO Auto-generated method stub
            switch(position){
                case 3:
                   break;
                case 4:
                   break;
            }
        }
    }

    public class MySetListAdapter extends BaseAdapter {
        public MySetListAdapter() {}

        class Holder{
            public TextView myText1;
            public TextView myText2;
            public ImageButton myButton;
        }

        @Override
        public int getCount() {
            return DataDetail.collects_ask.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            String [] temp=DataDetail.collects_ask.get(position);
            final Holder holder;
            if(convertView!=null)
            {
                holder=(Holder) convertView.getTag();
            }else {
                holder=new Holder();
                convertView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.my_ask_list, null);
                holder.myButton=(ImageButton) convertView.findViewById(R.id.name_3);

                holder.myText1=(TextView) convertView.findViewById(R.id.name_1);
                holder.myText2=(TextView) convertView.findViewById(R.id.name_2);
                holder.myText1.setText(temp[5]);
                holder.myText2.setText(temp[3]+" \r\n"+temp[4]);
                convertView.setTag(holder);
            }
            holder.myButton.setOnClickListener(new ButtonLis(position));

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
                final String [] temp=DataDetail.collects_ask.get(index);
                //System.out.println(""+this.index);
                ConnectivityManager connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if(networkInfo!=null){
                    if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                        if(DataDetail.beOnNetAlways==0){
                            ToastHelper.makeText(getApplicationContext(),"没有检测到合适的网络连接",Toast.LENGTH_SHORT);
                            return;
                        }
                    }else{

                    }
                }else{
                    ToastHelper.makeText(getApplicationContext(),"没有检测到网络连接",Toast.LENGTH_SHORT);
                    return;
                }

                Builder alert = new AlertDialog.Builder(MyAskTbl.this).setTitle("提示").setMessage("确定要删除该预约吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            String buff = new String("");

                            buff+=0;
                            buff+=".";
                            buff+=temp[1];
                            buff+=".";
                            buff+=DataDetail.ID;

                            buff+=".";
                            buff+=Decoder.translate(temp[3]);
                            buff+=".";
                            buff+=Decoder.translate(temp[4]);
                            buff+=".";
                            buff+=temp[5];
                            buff+=".";
                            buff+= temp[7];
                            run = new BackSendRunnable(true,"请稍后", "正在取消预约...",MyAskTbl.this,buff,new SuccessBack(index),new FailedBack(index));
                            mHandler=new Handler();
                            mHandler.postDelayed(run,200);
                            }
                        });
                alert.setNegativeButton("取消",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }

        }
        class SuccessBack implements MyTask{
            private int index;
            SuccessBack(int index){
                this.index=index;
            }

            @Override
            public void task(String back) {
                if (back.contains("OK")) {
                    DataDetail.collects_ask.removeElementAt(index);
                    DataDetail.num--;
                    myDataBase.updateMyAskTBL();

                    if (DataDetail.num < 1) {
                        ToastHelper.makeText(getApplicationContext(), "没有查到已预约校车哦", Toast.LENGTH_SHORT);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                MyAskTbl.this.finish();
                            }
                        }, 500); //500 for release
                    }
                    myDataBase.updateMyData();
                    mySetListAdapter.notifyDataSetChanged();
                    myList.setAdapter(mySetListAdapter);
                } else{
                    ToastHelper.makeText(getApplicationContext(), "遇到点小麻烦~请稍后重试", Toast.LENGTH_SHORT);
                }
            }
        }
        class FailedBack implements MyTask{
            private int index;
            FailedBack(int index){
                this.index=index;
            }

            @Override
            public void task(String back){
                ToastHelper.makeText(getApplicationContext(),"遇到点小麻烦~请稍后重试",Toast.LENGTH_SHORT);
            }

        }
    }

} 

