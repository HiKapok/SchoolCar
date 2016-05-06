package com.kapok.schoolcar;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.MainColor);
        this.getWindow().setBackgroundDrawable(drawable);
        new Handler().postDelayed(new Runnable() {
            public void run() {

                MyDataBase.dataContext=getApplicationContext();
                MyDataBase myDataBase=new MyDataBase();
                MyDataBase.getDataBase();
                SearchResPage.myDataBase=myDataBase;
                SearchPage.myDataBase=myDataBase;
                CommAddr.myDataBase=myDataBase;
                SetPage.myDataBase=myDataBase;
                MyAskTbl.myDataBase=myDataBase;
                MyRingTbl.myDataBase=myDataBase;
                LoginLogout.myDataBase=myDataBase;
                BaseExpandListViewAdapter.myDataBase=myDataBase;
                ServerSetPage.myDataBase=myDataBase;
                CheckUpdate.myDataBase=myDataBase;
                MyAlarmReceiver.myDataBase=myDataBase;
                // myDataBase.updateMyData();
            }
        }, 200);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, 2000); //2000 for release

    }
}
