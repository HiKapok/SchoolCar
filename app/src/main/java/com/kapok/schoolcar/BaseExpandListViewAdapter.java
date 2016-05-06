package com.kapok.schoolcar;

import java.util.Calendar;
import java.util.Date;
import android.app.Activity;
import android.app.AlarmManager;
import java.util.regex.Pattern;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class BaseExpandListViewAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater;
    private String[] groupStrings;
    private String[][] childStrings;
    private Context context;
    public int ok;
    private Handler mHandler;
    public BackSendRunnable run;
    private String buff;
    public static MyDataBase myDataBase;
    public BaseExpandListViewAdapter(Context context, String[] groupStrings,
                                     String[][] childStrings) {
        ok=0;
        this.groupStrings = groupStrings;
        this.context = context;
        this.childStrings = childStrings;
        inflater = LayoutInflater.from(context);
        Date dateNowTemp = new Date();
        for(String [] element: DataDetail.collects_ring)
        {
            Date date = new Date();
            Pattern pattern = Pattern.compile("[日|月|时|分]+");
            String [] subresult=pattern.split(element[6]);
            //System.out.println(subresult.length);
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
        myDataBase.updateMyRingTBL();
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return groupStrings.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return childStrings[groupPosition].length-2;
    }

    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return groupStrings[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub

        return childStrings[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        //TODO Auto-generated method stub
        boolean has=false;
        View view;
        /*String [] temp = new String[8];
        temp[0]="1";
        temp[1]=SearchResPage.type;
        temp[2]=childStrings[groupPosition][3];
        temp[3]=childStrings[groupPosition][0];
        temp[4]=childStrings[groupPosition][2];
        temp[5]=groupStrings[groupPosition];
        temp[7]=SearchPage.day_char;*/
        for(String [] element: DataDetail.collects_ring)
        {
            if(element[2].equals(childStrings[groupPosition][3])
                    &&element[3].equals(childStrings[groupPosition][0])
                    &&element[4].equals(childStrings[groupPosition][2])
                    &&element[5].equals(groupStrings[groupPosition])
                    &&element[7].equals(SearchPage.day_char)) {
                    has=true;
                    break;
            }
        }
        if(!has) {
            view = inflater.inflate(R.layout.result_parent, null);
            TextView textView = (TextView) view.findViewById(R.id.name_1);
            Button myButton = (Button) view.findViewById(R.id.name_2);
            ImageButton myImageButton = (ImageButton) view.findViewById(R.id.ibuttom);
            textView.setText(groupStrings[groupPosition]);
            myButton.setOnClickListener(new myButtonLis(groupPosition));
            myImageButton.setOnClickListener(new myImageButtonLis(groupPosition));
        }else{
            view = inflater.inflate(R.layout.result_parent_noring, null);
            TextView textView = (TextView) view.findViewById(R.id.name_1);
            Button myButton = (Button) view.findViewById(R.id.name_2);
            textView.setText(groupStrings[groupPosition]);
            myButton.setOnClickListener(new myButtonLis(groupPosition));
        }
        return view;
    }
    public class myImageButtonLis implements OnClickListener {
        private int index;
        myImageButtonLis(int index){
            this.index=index;
        }
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            myDataBase.getMyRingTBL();
            myDataBase.getMyData();

            if(DataDetail.ringnum>DataDetail.ask_num_max){
                ToastHelper.makeText(context,"您最多只可以添加"+DataDetail.ask_num_max+"个预约提醒哦",Toast.LENGTH_SHORT);
            }else {
                if(DataDetail.ID_Useful==0){
                    ToastHelper.makeText(context,"对不起，您还没有登录，请登陆后重试",Toast.LENGTH_SHORT);
                }else {
                    final Calendar cal = Calendar.getInstance();
                    DatePickerDialog mDialog = new DatePickerDialog(context, new myDateLis(index),
                            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH));
                    mDialog.setTitle("请设置乘车日期");
                    mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new myDateLisNew(index,mDialog));
                    mDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"取消",new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialog, int which) {
                            //System.out.println("cancel~~cancel~~");
                        }
                    });
                    mDialog.show();
                }
            }
        }
    }
    private class myDateLisNew implements DialogInterface.OnClickListener{
        private int index;
        private DatePickerDialog mDialog;
        myDateLisNew(int index,DatePickerDialog mDialog){this.index=index;this.mDialog=mDialog;}

        @Override
        public void onClick(DialogInterface dialog, int which) {
            DatePicker datePicker = mDialog.getDatePicker();
            int year = datePicker.getYear();
            int monthOfYear = datePicker.getMonth();
            int dayOfMonth = datePicker.getDayOfMonth();
            String[] temp = new String[8];
            Date date = new Date();
            Date dateNow = new Date(date.getTime() + 1 * 60 * 60000);
            Date dateNowCpy = new Date(date.getTime() + 3 * 24 * 60 * 60000);
            date.setYear(year - 1900);
            date.setDate(dayOfMonth);
            date.setMonth(monthOfYear);
            date.setHours(Integer.parseInt(groupStrings[index].substring(0, 2)));
            date.setMinutes(Integer.parseInt(groupStrings[index].substring(3, 5)));
            date.setSeconds(0);
            // System.out.println(dateNow.getTime()+"cpy"+dateNowCpy.getTime()+"df"+date.getTime());
            if (date.after(dateNow) && date.before(dateNowCpy)) {
                boolean dayTrue = false;
                if (date.getDay() == 6 || date.getDay() == 0) {
                    if(SearchPage.day_char.equals("S")){
                        dayTrue=true;
                    }
                } else {
                    if(SearchPage.day_char.equals("W")){
                        dayTrue=true;
                    }
                }
                if (dayTrue) {
                    boolean hasDuty = false;
                    int cnt = 0;
                    for (; cnt < 6; cnt++) {
                        hasDuty = false;
                        for (String[] element : DataDetail.collects_ring) {
                            if (element[0].equals(cnt + "")) {
                                hasDuty = true;
                                break;
                            }
                        }
                        if (hasDuty == false) {
                            break;
                        }
                    }
                    dateNow.setTime(date.getTime() - DataDetail.AskRingTimeBefore * 60 * 60000);
                    temp[0] = "" + cnt;
                    temp[1] = SearchResPage.type;
                    temp[2] = childStrings[index][3];
                    temp[3] = childStrings[index][0];
                    temp[4] = childStrings[index][2];
                    temp[5] = groupStrings[index];
                    int tempMonth = dateNow.getMonth() + 1;
                    temp[6] = tempMonth + "月" + dateNow.getDate() + "日" + dateNow.getHours() + "时" + dateNow.getMinutes() + "分";
                    temp[7] = SearchPage.day_char;

                    DataDetail.collects_ring.add(temp);
                    DataDetail.ringnum=DataDetail.collects_ring.size();
                    myDataBase.updateMyRingTBL();
                    myDataBase.updateMyData();

                    Intent i = new Intent(context, MyAlarmReceiver.class);
                    //i.putExtra("id",Integer.valueOf(temp[0]));
                    i.putExtra("id",temp[0]);
                    i.putExtra("start",temp[3]);
                    i.putExtra("end",temp[4]);
                    i.putExtra("time",temp[5]);
                    i.putExtra("delay", DataDetail.AskRingTimeBefore.toString());
                    PendingIntent pi = PendingIntent.getBroadcast(context, Integer.valueOf(temp[0]), i, 0); //通过getBroadcast第二个参数区分闹钟，将查询得到的note的ID值作为第二个参数。

                    AlarmManager am;
                    am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, dateNow.getTime(), pi);

                    SearchResPage.baseExpandListViewAdapter.notifyDataSetChanged();
                    SearchResPage.expandableListView.setAdapter(SearchResPage.baseExpandListViewAdapter);
                    ToastHelper.makeText(context, "添加预约提醒成功，将在" + temp[6] + "提醒您进行预约", Toast.LENGTH_SHORT);
                } else {
                    ToastHelper.makeText(context, "请正确选择工作日非工作日", Toast.LENGTH_SHORT);
                }
            } else {
                ToastHelper.makeText(context, "您只可以添加距离开车时间1-72小时的预约提醒", Toast.LENGTH_SHORT);
            }
        }
    }
    private class myDateLis implements DatePickerDialog.OnDateSetListener{
        private int index;
        myDateLis(int index){this.index=index;}

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            /*String[] temp = new String[8];
            Date date = new Date();
            Date dateNow = new Date(date.getTime() + 1 * 60 * 60000);
            Date dateNowCpy = new Date(date.getTime() + 3 * 24 * 60 * 60000);
            date.setYear(year - 1900);
            date.setDate(dayOfMonth);
            date.setMonth(monthOfYear);
            date.setHours(Integer.parseInt(groupStrings[index].substring(0, 2)));
            date.setMinutes(Integer.parseInt(groupStrings[index].substring(3, 5)));
            date.setSeconds(0);
            // System.out.println(dateNow.getTime()+"cpy"+dateNowCpy.getTime()+"df"+date.getTime());
            if (date.after(dateNow) && date.before(dateNowCpy)) {
                boolean dayTrue = false;
                if (date.getDay() == 6 || date.getDay() == 0) {
                    if(SearchPage.day_char.equals("S")){
                        dayTrue=true;
                    }
                } else {
                    if(SearchPage.day_char.equals("W")){
                        dayTrue=true;
                    }
                }
                if (dayTrue) {
                    boolean hasDuty = false;
                    int cnt = 0;
                    for (; cnt < 6; cnt++) {
                        hasDuty = false;
                        for (String[] element : DataDetail.collects_ring) {
                            if (element[0].equals(cnt + "")) {
                                hasDuty = true;
                                break;
                            }
                        }
                        if (hasDuty == false) {
                            break;
                        }
                    }
                    dateNow.setTime(date.getTime() - DataDetail.AskRingTimeBefore * 60 * 60000);
                    temp[0] = "" + cnt;
                    temp[1] = SearchResPage.type;
                    temp[2] = childStrings[index][3];
                    temp[3] = childStrings[index][0];
                    temp[4] = childStrings[index][2];
                    temp[5] = groupStrings[index];
                    int tempMonth = dateNow.getMonth() + 1;
                    temp[6] = tempMonth + "月" + dateNow.getDate() + "日" + dateNow.getHours() + "时" + dateNow.getMinutes() + "分";
                    temp[7] = SearchPage.day_char;

                    DataDetail.collects_ring.add(temp);
                    DataDetail.ringnum=DataDetail.collects_ring.size();
                    myDataBase.updateMyRingTBL();
                    myDataBase.updateMyData();

                    Intent i = new Intent(context, MyAlarmReceiver.class);
                    //i.putExtra("id",Integer.valueOf(temp[0]));
                    i.putExtra("id",temp[0]);
                    i.putExtra("start",temp[3]);
                    i.putExtra("end",temp[4]);
                    i.putExtra("time",temp[5]);
                    i.putExtra("delay", DataDetail.AskRingTimeBefore.toString());
                    PendingIntent pi = PendingIntent.getBroadcast(context, Integer.valueOf(temp[0]), i, 0); //通过getBroadcast第二个参数区分闹钟，将查询得到的note的ID值作为第二个参数。

                    AlarmManager am;
                    am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, dateNow.getTime(), pi);

                    SearchResPage.baseExpandListViewAdapter.notifyDataSetChanged();
                    SearchResPage.expandableListView.setAdapter(SearchResPage.baseExpandListViewAdapter);
                    ToastHelper.makeText(context, "添加预约提醒成功，将在" + temp[6] + "提醒您进行预约", Toast.LENGTH_SHORT);
                } else {
                    ToastHelper.makeText(context, "请正确选择工作日非工作日", Toast.LENGTH_SHORT);
                }
            } else {
                ToastHelper.makeText(context, "您只可以添加距离开车时间1-72小时的预约提醒", Toast.LENGTH_SHORT);
            }*/
        }
    }

    public class myButtonLis implements OnClickListener {
        private int index;

        myButtonLis(int index){
            this.index=index;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int beSuccessful=1;
            ok=0;
            myDataBase.getMyAskTBL();
            myDataBase.getMyData();
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo!=null){
                if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                    if(DataDetail.beOnNetAlways==0){
                        ToastHelper.makeText(context,"没有检测到合适的网络连接",Toast.LENGTH_SHORT);
                        return;
                    }
                }else{ }
            }else{
                ToastHelper.makeText(context,"没有检测到网络连接",Toast.LENGTH_SHORT);
                return;
            }
            if(DataDetail.ID_Useful==0){
                ToastHelper.makeText(context,"对不起，您还没有登录，请登陆后重试",Toast.LENGTH_SHORT);
                return;
            }
            Date dateNow2 = new Date();
            for(String [] element: DataDetail.collects_ask)
            {
                Date date = new Date();
                Pattern pattern = Pattern.compile("[.]+");
                String [] subresult=pattern.split(element[6]);
                date.setDate(Integer.parseInt(subresult[1]));
                date.setMonth(Integer.parseInt(subresult[0]));
                date.setHours(Integer.parseInt(element[5].substring(0, 2)));
                date.setMinutes(Integer.parseInt(element[5].substring(3, 5)));
                date.setSeconds(0);

                if(date.before(dateNow2)){
                    DataDetail.collects_ask.removeElement(element);
                    DataDetail.num--;
                }
            }
            myDataBase.updateMyAskTBL();

          //  System.out.println(DataDetail.askTbl[j][0]+DataDetail.askTbl[j][2]+DataDetail.askTbl[j][5]+DataDetail.askTbl[j][1]+"");
            for(String [] element: DataDetail.collects_ask)
            {
                if((element[2].equals(childStrings[index][3]))
                        &&(element[5].equals(groupStrings[index]))
                        &&(element[1].equals(SearchResPage.type))
                        &&(element[3]==childStrings[index][0])
                        &&(element[4]==childStrings[index][2])
                        &&(element[7]==SearchPage.day_char)){
                    if(DataDetail.debug.equals("0")) {
                        ToastHelper.makeText(context, "对不起，您已预约过该班校车", Toast.LENGTH_SHORT);
                        beSuccessful = 0;
                    }
                    break;
                }
            }

            String [] temp = new String[8];
            if(beSuccessful==1){
                if(DataDetail.num>2 && DataDetail.debug.equals("0")){
                    ToastHelper.makeText(context,"对不起，您的当天预约数已超过最大限制",Toast.LENGTH_SHORT);

                }else{
                    if(DataDetail.debug.equals("0")) {
                        String returnStr = TimeManager.ifAskTime(groupStrings[index], context);
                        if (returnStr.equals("false")) {
                            ok = 0;
                        } else {
                            temp[6] = returnStr;
                            ok = 1;
                        }
                    }else{
                        ok=1;
                    }
                }
                if(ok==1){
                    buff = new String("");
                    buff+=1;
                    buff+=".";
                    buff+=SearchResPage.type;
                    buff+=".";
                    buff+=DataDetail.ID;
                    buff+=".";
                    buff+=Decoder.translate(childStrings[index][0]);
                    buff+=".";
                    buff+=Decoder.translate(childStrings[index][2]);
                    buff+=".";
                    buff+=groupStrings[index];
                    buff+=".";
                    buff+= SearchPage.day_char;
                   // buff+=".";
                   // buff+= temp[6];
                    temp[1]=SearchResPage.type;
                    temp[2]=childStrings[index][3];
                    temp[3]=childStrings[index][0];
                    temp[4]=childStrings[index][2];
                    temp[5]=groupStrings[index];
                    temp[7]=SearchPage.day_char;
                    run = new BackSendRunnable(true,"请稍后", "正在执行预约操作...", context, buff, new SuccessBack(temp), new FailedBack());
                    mHandler=new Handler();
                    mHandler.postDelayed(run,200);
                }
            }
        }
        class SuccessBack implements MyTask{
            private String [] temp;
            SuccessBack(String [] temp){
                this.temp=temp;
            }

            @Override
            public void task(String back) {
                try{
                    if (back.contains("OK")) {
                        int num = Integer.parseInt(back.substring(0, 1));
                        if (num != 0)
                            ToastHelper.makeText(context, "恭喜您预约成功，" + "目前违规记录为" + num + "次", Toast.LENGTH_SHORT);
                        else
                            ToastHelper.makeText(context, "恭喜您预约成功，" + "无违规记录", Toast.LENGTH_SHORT);
                        temp[0] = "1";
                        DataDetail.num++;
                        DataDetail.collects_ask.add(temp);
                        myDataBase.updateMyAskTBL();
                        myDataBase.updateMyData();
                    } else {
                        int num = Integer.parseInt(back.substring(0, 1));
                        if (num < 4)
                            ToastHelper.makeText(context, "对不起，预约失败，请稍后重试", Toast.LENGTH_SHORT);
                        else {
                            ToastHelper.makeText(context, "您的违规记录次数已达上限，请联系管理员", Toast.LENGTH_SHORT);
                        }
                    }
                }catch(NumberFormatException e){
                    ToastHelper.makeText(context,"服务器版本不一致，预约失败",Toast.LENGTH_SHORT);
                }
            }
        }
        class FailedBack implements MyTask{
            @Override
            public void task(String back){ }

        }
        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            mHandler.removeCallbacks(run);
            super.finalize();
        }
    }
    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.result_child, null);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // ToastHelper.makeText(context,childStrings[groupPosition][childPosition],Toast.LENGTH_SHORT);
            }
        });
        TextView textView = (TextView) view.findViewById(R.id.name_3);
        textView.setText(childStrings[groupPosition][childPosition]);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return false;
    }

}
