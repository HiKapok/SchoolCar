package com.kapok.schoolcar;

import android.content.Context;


/**
 * Created by WangChangan on 2015/4/23 0023.
 */
public class ToastHelper {
    static String text;
    static void makeText(Context context,String text,int time){
        DrawerToast myToast=DrawerToast.getInstance(context);
        ToastHelper.text=text;
        myToast.show(text);
    }
    static void makeText(Context context,String text){
        ToastHelper.makeText(context,text,0);
    }
}
