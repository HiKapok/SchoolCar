package com.kapok.schoolcar;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Date;
import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {
	ImageButton im_teachers,im_students,setting,about_app,rignB,askB;
    Menu mymenu=null;
    private final static String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SearchPage.myContext=getApplicationContext();
        SetActivityBackground.set(this);

        rignB=(ImageButton)findViewById(R.id.ring);
        askB=(ImageButton)findViewById(R.id.ask);
		im_teachers=(ImageButton)findViewById(R.id.teacher);
		im_students=(ImageButton)findViewById(R.id.student);
		setting=(ImageButton)findViewById(R.id.setting);
		about_app=(ImageButton)findViewById(R.id.about);

		setting.setOnClickListener(new setting_lis());
		about_app.setOnClickListener(new about_app_lis());
		im_teachers.setOnClickListener(new teachers_search_lis());
		im_students.setOnClickListener(new students_search_lis());
        rignB.setOnClickListener(new ring_lis());
        askB.setOnClickListener(new ask_lis());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setLogo(R.drawable.ic_launcher_head);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.menu_log:
                intent=new Intent();
                if(DataDetail.ID_Useful==0){
                    intent.putExtra("type","login");
                    intent.setClass(MainActivity.this, LoginLogout.class);
                    MainActivity.this.startActivity(intent);
                }else{
                    intent.putExtra("type","logout");
                    intent.setClass(MainActivity.this, LoginLogout.class);
                    MainActivity.this.startActivity(intent);
                }
                break;
            case R.id.action_settings:
                intent=new Intent();
                intent.setClass(MainActivity.this, SetPage.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.action_help:
                intent=new Intent();
                intent.setClass(MainActivity.this, AboutPage.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.action_quit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(DataDetail.ID_Useful==0)
            menu.getItem(1).setIcon(R.drawable.login);
        else
            menu.getItem(1).setIcon(R.drawable.logout);
        mymenu=menu;
        return true;
    }
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        SetActivityBackground.set(this);
        if(mymenu!=null) {
            if (DataDetail.ID_Useful == 0)
                mymenu.getItem(1).setIcon(R.drawable.login);
            else
                mymenu.getItem(1).setIcon(R.drawable.logout);
        }
	}
	class teachers_search_lis implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

            Intent intent=new Intent();
			intent.putExtra("type","teacher");
			intent.setClass(MainActivity.this, SearchPage.class);
			MainActivity.this.startActivity(intent);
        }

	}
    class ask_lis implements OnClickListener{
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            if (DataDetail.ID_Useful == 0) {
                    ToastHelper.makeText(getApplicationContext(),"对不起，您还没有登录，请登陆后重试",Toast.LENGTH_SHORT);
            } else {
                if(DataDetail.debug.equals("0")) {
                    if (DataDetail.num < 1) {
                        ToastHelper.makeText(getApplicationContext(), "您还没有预约校车哦", Toast.LENGTH_SHORT);

                    } else {
                        intent.setClass(MainActivity.this, MyAskTbl.class);
                        MainActivity.this.startActivity(intent);
                    }
                }else {
                    boolean canEnter=true;
                    ConnectivityManager connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null) {
                        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            if (DataDetail.beOnNetAlways == 0) {
                                ToastHelper.makeText(getApplicationContext(), "没有检测到合适的网络连接", Toast.LENGTH_SHORT);
                                canEnter=false;
                            }
                        } else {

                        }
                    } else {
                        ToastHelper.makeText(getApplicationContext(), "没有检测到网络连接", Toast.LENGTH_SHORT);
                        canEnter=false;
                    }
                    if(canEnter==true){
                        intent.setClass(MainActivity.this, MyAskTbl.class);
                        MainActivity.this.startActivity(intent);
                    }
                }
            }
        }

    }
    class ring_lis implements OnClickListener{
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            MyRingTbl.myDataBase.getMyData();
            MyRingTbl.myDataBase.getMyRingTBL();
            Intent intent = new Intent();
            Date dateNowTemp = new Date();
            for(String [] element: DataDetail.collects_ring)
            {
                Date date = new Date();
                Pattern pattern = Pattern.compile("[日|月|时|分]+");
                String [] subresult=pattern.split(element[6]);
                date.setDate(Integer.parseInt(subresult[1]));
                date.setMonth(Integer.parseInt(subresult[0])-1);
                date.setHours(Integer.parseInt(subresult[2]));
                date.setMinutes(Integer.parseInt(subresult[3]));
                date.setSeconds(0);

                if(date.before(dateNowTemp)){
                    DataDetail.collects_ring.removeElement(element);
                    DataDetail.ringnum--;
                }
            }
            MyRingTbl.myDataBase.updateMyRingTBL();
            if(DataDetail.ringnum<1){
                ToastHelper.makeText(getApplicationContext(),"您还没有添加预约提醒哦",Toast.LENGTH_SHORT);
               /* new Handler().postDelayed(new Runnable() {
                    public void run() {
                        MyRingTbl.this.finish();
                    }
                }, 500); //800 for release*/
            }else {

                intent.setClass(MainActivity.this, MyRingTbl.class);
                MainActivity.this.startActivity(intent);
            }
        }
    }
	class students_search_lis implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.putExtra("type","student");
			intent.setClass(MainActivity.this, SearchPage.class);
			MainActivity.this.startActivity(intent);
		}
	}


	class about_app_lis implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, AboutPage.class);
			MainActivity.this.startActivity(intent);
		}
	}
	class setting_lis implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, SetPage.class);
			MainActivity.this.startActivity(intent);
		}
	}

}

