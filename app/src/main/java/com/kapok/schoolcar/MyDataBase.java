package com.kapok.schoolcar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.kapok.schoolcar.DatabaseHelper;

public class MyDataBase {
    public static MyDataBase mydatabase;
    public static boolean isInit=true;
    public static Context dataContext;
    private SQLiteDatabase db = null;
    public String resultTable;
    public DatabaseHelper database;
    MyDataBase(){
        database = new DatabaseHelper(dataContext,"school_car_db");
        database.initData();
    }
    public static MyDataBase getDataBase(){
        if(mydatabase==null){
            mydatabase=new MyDataBase();
        }
        mydatabase.getMyData();
        mydatabase.getMyAskTBL();
        mydatabase.getMyRingTBL();
        return mydatabase;
    }

    public void updateMyData(){
        db =database.getWritableDatabase();
        ContentValues temp = new ContentValues();
        temp.put("Ask_Time",DataDetail.AskRingTimeBefore);
        temp.put("beOnNetAlways",DataDetail.beOnNetAlways);
        temp.put("beNight",DataDetail.beNight);
        temp.put("beUseful",DataDetail.ID_Useful);
        temp.put("USER",DataDetail.User);
        temp.put("Number",DataDetail.ID);
        temp.put("add_beUseful",DataDetail.common_add_Useful);
        temp.put("start",DataDetail.start);
        temp.put("num",DataDetail.num);
        temp.put("end",DataDetail.end);
        temp.put("ringnum",DataDetail.ringnum);
        temp.put("port",DataDetail.port);
        temp.put("dataDate",CheckUpdate.versionDay);
        temp.put("ip",DataDetail.ip);
        temp.put("debug",DataDetail.debug);
        //第一个参数是要更新的表名
        //第二个参数是一个ContentValeus对象
        //第三个参数是where子句
        db.update("appData", temp, "author=?", new String[]{"kapok"});

    }
    public void updateMyAskTBL(){
        db =database.getWritableDatabase();
        ContentValues temp = new ContentValues();
        deleteMyAskTBL();
        for(String [] element: DataDetail.collects_ask)
        {
            System.out.println(element[5]);
            temp.put("ID",""+DataDetail.ID);
            temp.put("useful",element[0]);
            temp.put("userType",element[1]);
            temp.put("type",element[2]);
            temp.put("start",element[3]);
            temp.put("end",element[4]);
            temp.put("whentime",element[5]);
            temp.put("time",element[6]);
            temp.put("daytype",element[7]);
            db.insert("myAsk",null,temp);
            temp.clear();
        }
    }
    public void deleteMyAskItem(String [] temps){
        db =database.getWritableDatabase();
        ContentValues temp = new ContentValues();
        DataDetail.collects_ask.removeElement(temps);
        updateMyAskTBL();
    }
    public void insertMyAsk(String [] temps){
        db =database.getWritableDatabase();
        ContentValues temp = new ContentValues();
        DataDetail.collects_ask.add(temps);
        temp.put("ID",""+DataDetail.ID);
        temp.put("useful",temps[0]);
        temp.put("userType",temps[1]);
        temp.put("type",temps[2]);
        temp.put("start",temps[3]);
        temp.put("end",temps[4]);
        temp.put("whentime",temps[5]);
        temp.put("time",temps[6]);
        temp.put("daytype",temps[7]);
        db.insert("myAsk",null,temp);
        temp.clear();
        //System.out.println("insert=");
        //DataDetail.num=0;
    }
    public void deleteMyAskTBL(){
        db =database.getWritableDatabase();
        db.execSQL("DELETE FROM myAsk WHERE ID = '"+DataDetail.ID+"'");
       // db.delete("myAsk","ID=?", new String[]{DataDetail.ID});
    }
    public int getMyAskTBL(){
        db=database.getReadableDatabase();
        Cursor cursor;
        DataDetail.collects_ask.removeAllElements();

        //	System.out.println(i+DataDetail.ID+"");
        //cursor = db.query("myAsk", null, "ID=?", new String[]{""+i+DataDetail.ID}, null, null, null, null);
        cursor=db.rawQuery("SELECT * FROM myAsk WHERE ID =?",new String []{""+DataDetail.ID});
        while(cursor.moveToNext()){
            String [] temp = new String[8];
            temp[0]=cursor.getString(cursor.getColumnIndex("useful"));
            temp[1]=cursor.getString(cursor.getColumnIndex("userType"));
            temp[2]=cursor.getString(cursor.getColumnIndex("type"));
            temp[3]=cursor.getString(cursor.getColumnIndex("start"));
            temp[4]=cursor.getString(cursor.getColumnIndex("end"));
            temp[5]=cursor.getString(cursor.getColumnIndex("whentime"));
            temp[6]=cursor.getString(cursor.getColumnIndex("time"));
            temp[7]=cursor.getString(cursor.getColumnIndex("daytype"));
            System.out.println("ssss"+temp[5]);
            DataDetail.collects_ask.add(temp);
        }
        for(String [] element: DataDetail.collects_ask) {
            System.out.println("WW"+element[5]);
        }
        return DataDetail.collects_ask.size();
    }

    public void updateMyRingTBL(){
        db =database.getWritableDatabase();
        ContentValues temp = new ContentValues();
        deleteMyRingTBL();
        for(String [] element: DataDetail.collects_ring)
        {
            temp.put("ID",""+DataDetail.ID);
            temp.put("useful",element[0]);
            temp.put("userType",element[1]);
            temp.put("type",element[2]);
            temp.put("start",element[3]);
            temp.put("end",element[4]);
            temp.put("whentime",element[5]);
            temp.put("time",element[6]);
            temp.put("daytype",element[7]);
            db.insert("myRing",null,temp);
            temp.clear();
        }
    }
    public void deleteMyRingItem(String [] temps){
        db =database.getWritableDatabase();
        ContentValues temp = new ContentValues();
        DataDetail.collects_ring.removeElement(temps);
        updateMyRingTBL();
    }
    public void insertMyRing(String [] temps){
        db =database.getWritableDatabase();
        ContentValues temp = new ContentValues();
        DataDetail.collects_ring.add(temps);
        temp.put("ID",""+DataDetail.ID);
        temp.put("useful",temps[0]);
        temp.put("userType",temps[1]);
        temp.put("type",temps[2]);
        temp.put("start",temps[3]);
        temp.put("end",temps[4]);
        temp.put("whentime",temps[5]);
        temp.put("time",temps[6]);
        temp.put("daytype",temps[7]);
        db.insert("myRing",null,temp);
        temp.clear();
        //System.out.println("insert=");
        //DataDetail.num=0;
    }
    public void deleteMyRingTBL(){
        db =database.getWritableDatabase();
        db.execSQL("DELETE FROM myRing WHERE ID = '"+DataDetail.ID+"'");
        // db.delete("myAsk","ID=?", new String[]{DataDetail.ID});
    }
    public int getMyRingTBL(){
        db=database.getReadableDatabase();
        Cursor cursor;
        DataDetail.collects_ring.removeAllElements();
        //	System.out.println(i+DataDetail.ID+"");
        //cursor = db.query("myAsk", null, "ID=?", new String[]{""+i+DataDetail.ID}, null, null, null, null);
        cursor=db.rawQuery("SELECT * FROM myRing WHERE ID =?",new String []{""+DataDetail.ID});
        while(cursor.moveToNext()){
            String [] temp = new String[8];
            temp[0]=cursor.getString(cursor.getColumnIndex("useful"));
            temp[1]=cursor.getString(cursor.getColumnIndex("userType"));
            temp[2]=cursor.getString(cursor.getColumnIndex("type"));
            temp[3]=cursor.getString(cursor.getColumnIndex("start"));
            temp[4]=cursor.getString(cursor.getColumnIndex("end"));
            temp[5]=cursor.getString(cursor.getColumnIndex("whentime"));
            temp[6]=cursor.getString(cursor.getColumnIndex("time"));
            temp[7]=cursor.getString(cursor.getColumnIndex("daytype"));
            DataDetail.collects_ring.add(temp);
        }
        return DataDetail.collects_ring.size();
    }


    public void getCarTable(String value){
        db=database.getReadableDatabase();
        Date dateNow = new Date();
        Date dateBegin = new Date();
        Date dateEnd = new Date();

        dateBegin.setHours(0);
        dateBegin.setMinutes(0);
        dateBegin.setSeconds(0);
        dateBegin.setDate(1);
        dateBegin.setMonth(4);

        dateEnd.setHours(0);
        dateEnd.setMinutes(0);
        dateEnd.setSeconds(0);
        dateEnd.setDate(7);
        dateEnd.setMonth(9);
        Cursor cursor;
        if(DataDetail.debug.equals("1")){
            cursor = db.query("WinterCarTable", new String[]{"type","start","start_2","whentime","mid","end"}, "type=?", new String []{value}, null, null, null);

        }else{
            if(dateNow.before(dateEnd)&&dateNow.after(dateBegin)){
                cursor = db.query("WinterCarTable", new String[]{"type","start","start_2","whentime","mid","end"}, "type=?", new String []{value}, null, null, null);
            }else{
                cursor = db.query("WinterCarTable", new String[]{"type","start","start_2","whentime","mid","end"}, "type=?", new String []{value}, null, null, null);
            }
        }

        while(cursor.moveToNext()){
            //System.out.println(""+cursor.getCount());
            resultTable+="/";
            resultTable+="**";
            resultTable+=cursor.getString(cursor.getColumnIndex("start"));
            resultTable+=" ";
            resultTable+=cursor.getString(cursor.getColumnIndex("start_2"));
            resultTable+="**";
            resultTable+=cursor.getString(cursor.getColumnIndex("mid"));
            resultTable+="**";
            resultTable+=cursor.getString(cursor.getColumnIndex("end"));
            resultTable+="**";
            resultTable+=cursor.getString(cursor.getColumnIndex("type"));
            resultTable+="**";
            resultTable+=cursor.getString(cursor.getColumnIndex("whentime"));
        }
        if(!cursor.isClosed())
            cursor.close();

    }
    public void getMyData(){

        db=database.getReadableDatabase();
        if(isInit==false){
            database.initData();
        }
        Cursor cursor = db.query("appData", new String[]{"ringnum","Ask_Time","beOnNetAlways","beNight","beUseful","USER","Number","num","add_beUseful","start","end","ip","port","dataDate","debug"}, "author=?", new String[]{"kapok"}, null, null, null, null);
        //cursor.moveToNext();
        while(cursor.moveToNext()){
            DataDetail.ringnum=cursor.getInt(cursor.getColumnIndex("ringnum"));
            DataDetail.AskRingTimeBefore=cursor.getInt(cursor.getColumnIndex("Ask_Time"));
            DataDetail.beNight=cursor.getInt(cursor.getColumnIndex("beNight"));
            DataDetail.beOnNetAlways=cursor.getInt(cursor.getColumnIndex("beOnNetAlways"));
            DataDetail.ID_Useful=cursor.getInt(cursor.getColumnIndex("beUseful"));
            DataDetail.ID=cursor.getString(cursor.getColumnIndex("Number"));
            DataDetail.User=cursor.getString(cursor.getColumnIndex("USER"));
            DataDetail.common_add_Useful=cursor.getInt(cursor.getColumnIndex("add_beUseful"));
            DataDetail.start=cursor.getInt(cursor.getColumnIndex("start"));
            DataDetail.end=cursor.getInt(cursor.getColumnIndex("end"));
            DataDetail.num=cursor.getInt(cursor.getColumnIndex("num"));
            DataDetail.ip=cursor.getString(cursor.getColumnIndex("ip"));
            DataDetail.port=cursor.getInt(cursor.getColumnIndex("port"));
            CheckUpdate.versionDay=cursor.getString(cursor.getColumnIndex("dataDate"));
            DataDetail.debug=cursor.getString(cursor.getColumnIndex("debug"));

        }

        if(!cursor.isClosed())
            cursor.close();

    }
    public void refreshCarTbl(CheckUpdate check){
        SQLiteDatabase db;
        db=database.getWritableDatabase();
        //CheckUpdate check = new CheckUpdate("http://www.dxb.sdu.edu.cn/content.php?id=45",true);

        db.delete("WinterCarTable",null, null);
        db.delete("SummerCarTable",null, null);
        ContentValues temp = new ContentValues();//实例化一个ContentValues用来装载待插入的数据cv.put("username","Jack Johnson");//添加用户名
        Pattern quoteRegex = Pattern.compile("[/]+");
        String [] result=quoteRegex.split(check.stringWinterDayExt);

        for (int i=0; i<result.length; i++) {
            Pattern pattern = Pattern.compile("[**]+");
            String [] subresult=pattern.split(result[i]);

            for (int j=0; j<subresult.length; j++) {
                temp.put(DatabaseHelper.constTable[j],subresult[j]);
            }
            db.insert("WinterCarTable",null,temp);//执行插入操作
            temp.clear();
        }
        result=quoteRegex.split(check.stringWinterSunExt);
        for (int i=0; i<result.length; i++) {
            Pattern pattern = Pattern.compile("[**]+");
            String [] subresult=pattern.split(result[i]);

            for (int j=0; j<subresult.length; j++) {
                temp.put(DatabaseHelper.constTable[j],subresult[j]);
            }
            db.insert("WinterCarTable",null,temp);//执行插入操作
            temp.clear();
        }
        temp.clear();
        result=quoteRegex.split(check.stringSummerDayExt);

        for (int i=0; i<result.length; i++) {
            Pattern pattern = Pattern.compile("[**]+");
            String [] subresult=pattern.split(result[i]);

            for (int j=0; j<subresult.length; j++) {
                temp.put(DatabaseHelper.constTable[j],subresult[j]);
            }
            db.insert("SummerCarTable",null,temp);//执行插入操作
            temp.clear();
        }
        result=quoteRegex.split(check.stringSummerSunExt);
        for (int i=0; i<result.length; i++) {
            Pattern pattern = Pattern.compile("[**]+");
            String [] subresult=pattern.split(result[i]);
            //System.out.println(subresult.length+"");
            for (int j=0; j<subresult.length; j++) {
                temp.put(DatabaseHelper.constTable[j],subresult[j]);
            }
            db.insert("SummerCarTable",null,temp);//执行插入操作
            temp.clear();
        }
        temp.clear();
    }
}
