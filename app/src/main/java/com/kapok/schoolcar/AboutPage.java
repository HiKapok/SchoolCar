package com.kapok.schoolcar;

import com.kapok.schoolcar.SetPage.MySetListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;


public class AboutPage extends ActionBarActivity {

    private final static String TAG = "AboutPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        SetActivityBackground.set(this);
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
                AboutPage.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
