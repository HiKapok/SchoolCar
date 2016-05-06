package com.kapok.schoolcar;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class MyAlarmReceiver extends BroadcastReceiver {
    public static MyDataBase myDataBase;

    @Override
    public void onReceive(Context context, Intent intent) {
        //ToastHelper.makeText(context, "你设置的闹钟时间到了", Toast.LENGTH_LONG);
        intent.setClass(context, AlarmPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
       // String id = intent.getStringExtra("id");
        /*String id=intent.getStringExtra("id");
        String start=intent.getStringExtra("start");
        String end=intent.getStringExtra("end");
        String time=intent.getStringExtra("time");
        String delay=intent.getStringExtra("delay");
        new AlertDialog.Builder(context).setTitle("预约提醒")
                .setMessage("快去预约"+delay+"小时后从"+start+"到"+end+"发车时间为"+time+"的校车吧")
        .setPositiveButton("我知道了",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();*/
        //System.out.println(DataDetail.collects_ring.size()+"");
        //访问不到
        /*for(String [] element: DataDetail.collects_ring)
        {
            System.out.println(element[0]);
            if(element[0].equals(id)){
                DataDetail.collects_ring.removeElement(element);
                DataDetail.ringnum--;
                myDataBase.updateMyRingTBL();
                myDataBase.updateMyData();
                break;
            }
        }*/

    }
}