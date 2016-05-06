package com.kapok.schoolcar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

public class AlarmPage extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent= getIntent();
        String id=intent.getStringExtra("id");
        String start=intent.getStringExtra("start");
        String end=intent.getStringExtra("end");
        String time=intent.getStringExtra("time");
        String delay=intent.getStringExtra("delay");
        new AlertDialog.Builder(this).setTitle("预约提醒")
                .setMessage("快去预约"+delay+"小时后"+start+"到"+end+"，发车时间为"+time+"的校车吧！")
                .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlarmPage.this.finish();
                    }
                }).show();
    }
}

