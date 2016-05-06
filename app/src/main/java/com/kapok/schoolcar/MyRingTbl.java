package com.kapok.schoolcar;

import android.app.AlarmManager;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.regex.Pattern;

public class MyRingTbl extends ActionBarActivity{

    public static MyDataBase myDataBase;
    MySetListAdapter mySetListAdapter;
    public ListView myList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myask_page);

        SetActivityBackground.set(this);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //System.out.println(referTBL[0]+"-"+referTBL[1]+"-"+referTBL[2]+"num"+totalNum);

        myList=(ListView)findViewById(R.id.ask_list);
        mySetListAdapter=new MySetListAdapter();
        myList.setAdapter(mySetListAdapter);
        myList.setOnItemClickListener(new ListLis());

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println(item.getItemId()+"");
        switch(item.getItemId()) {
            case 0x0102002C:
                MyRingTbl.this.finish();
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
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {          }
    }


    public class MySetListAdapter extends BaseAdapter {
        public MySetListAdapter() {
        }


        class Holder{
            public TextView myText1;
            public TextView myText2;
            public ImageButton myButton;
        }

        @Override
        public int getCount() {
            return DataDetail.collects_ring.size();
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
            String [] temp=DataDetail.collects_ring.get(position);
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
                final String [] temp=DataDetail.collects_ring.get(index);
                //System.out.println(""+this.index);
                if(DataDetail.ID_Useful==0){
                    ToastHelper.makeText(getApplicationContext(),"对不起，您还没有登录，请登陆后重试",Toast.LENGTH_SHORT);
                    return;
                }
                Builder alert = new AlertDialog.Builder(MyRingTbl.this).setTitle("提示").setMessage("确定要删除该提醒吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i=new Intent(MyRingTbl.this,MyAlarmReceiver.class);
                                PendingIntent pi = PendingIntent.getBroadcast(MyRingTbl.this, Integer.valueOf(temp[0]), i, 0); //通过getBroadcast第二个参数区分闹钟，将查询得到的note的ID值作为第二个参数。

                                AlarmManager am;
                                am = (AlarmManager)getSystemService(MyRingTbl.this.ALARM_SERVICE);
                                am.cancel(pi);

                                DataDetail.collects_ring.removeElementAt(index);
                                //DataDetail.ringnum--;
                                DataDetail.ringnum=DataDetail.collects_ring.size();
                                myDataBase.updateMyRingTBL();
                                if(DataDetail.ringnum<1){
                                    ToastHelper.makeText(getApplicationContext(),"没有查询到已添加的有效预约提醒哦",Toast.LENGTH_SHORT);
                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            MyRingTbl.this.finish();
                                        }
                                    }, 500); //800 for release
                                }
                                myDataBase.updateMyData();
                                mySetListAdapter.notifyDataSetChanged();
                                myList.setAdapter(mySetListAdapter);
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
    }

} 

