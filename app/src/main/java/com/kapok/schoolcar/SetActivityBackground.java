package com.kapok.schoolcar;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by WangChangan on 2015/4/23 0023.
 */
public class SetActivityBackground {
    public static void set(ActionBarActivity activity) {
        Resources res = activity.getResources();
        Drawable drawable;
        if (DataDetail.beNight == 0) {
            drawable = res.getDrawable(R.drawable.MainColor);
            activity.getSupportActionBar().setBackgroundDrawable(res.getDrawable((R.drawable.MainColorTitle)));
        } else {
            drawable = res.getDrawable(R.drawable.MainBlackColor);
            activity.getSupportActionBar().setBackgroundDrawable(res.getDrawable((R.drawable.MainBlackColorTitle)));
        }
        activity.getWindow().setBackgroundDrawable(drawable);
    }
}
