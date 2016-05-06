package com.kapok.schoolcar;

import android.content.Context;
import java.util.Date;

/**
 * Created by WangChangan on 2015/4/23 0023.
 */
public class TimeManager {

    private static int timePerHour=60*60000;
    private static int nearTime=1;
    private static int farTime=9;

    public static String ifAskTime(String string,Context context){

        Date dateNear = new Date();
        Date dateFar = new Date();
        Date date = new Date();
        date.setHours(Integer.parseInt(string.substring(0, 2)));
        date.setMinutes(Integer.parseInt(string.substring(3, 5)));
        date.setSeconds(0);
        Date nextDay=new Date(date.getTime()+24*timePerHour);

        long sec=dateNear.getTime();
        sec+=nearTime*timePerHour;
        dateNear=new Date(sec);         //未来一小时

        sec=dateFar.getTime();
        sec+=farTime*timePerHour;
        dateFar=new Date(sec);          //未来九小时

        if(SearchPage.day_char.equals("S")){
            if(date.before(dateFar) && date.after(dateNear)) {
                if ((date.getDay() == 0 || date.getDay() == 6)) {
                    return date.getMonth()+"."+date.getDate();
                } else{
                    ToastHelper.makeText(context,"请正确选择工作日和非工作日");
                }
            }else {
                if (nextDay.before(dateFar) && nextDay.after(dateNear)) {
                    if ((date.getDay() == 6 || date.getDay() == 5)) {
                        return nextDay.getMonth()+"."+nextDay.getDate();
                    } else{
                        ToastHelper.makeText(context,"请正确选择工作日和非工作日");
                    }
                } else {
                    ToastHelper.makeText(context, "请在校车开车前" + nearTime + "-" + farTime + "小时进行预约");
                }
            }
        }else{
            if(date.before(dateFar) && date.after(dateNear)) {
                if (!(date.getDay() == 0 || date.getDay() == 6)) {
                    return date.getMonth()+"."+date.getDate();
                } else{
                    ToastHelper.makeText(context,"请正确选择工作日和非工作日");
                }
            }else {
                if (nextDay.before(dateFar) && nextDay.after(dateNear)) {
                    if (!(date.getDay() == 6 || date.getDay() == 5)) {
                        return nextDay.getMonth()+"."+nextDay.getDate();
                    } else{
                        ToastHelper.makeText(context,"请正确选择工作日和非工作日");
                    }
                } else {
                    ToastHelper.makeText(context, "请在校车开车前" + nearTime + "-" + farTime + "小时进行预约");
                }
            }
        }
        return "false";
    }

}
