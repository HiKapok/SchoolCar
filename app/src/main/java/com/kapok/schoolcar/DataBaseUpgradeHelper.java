package com.kapok.schoolcar;

/**
 * Created by WangChangan on 2015/4/24 0024.
 */
public class DataBaseUpgradeHelper {
    public static class oldVersion1 {
        public static String CREATE_appData = "create table appData(Ask_Time INTEGER,beOnNetAlways INTEGER,beNight INTEGER,beUseful INTEGER,add_beUseful INTEGER,num INTEGER,start INTEGER,end INTEGER,USER varchar(20),Number char(12),port INTEGER,ip varchar(25),dataDate varchar(25),debug varchar(5),author char(12))";
        public static String CREATE_TEMP_appData = "alter table appData rename to _temp_appData";
        public static String appData_INSERT_DATA = "insert into appData select *,'' from _temp_appData";
        public static String DROP_appData = "drop table _temp_appData";

        public static String CREATE_myAsk = "create table myAsk(ID varchar(4),user varchar(20),useful varchar(2),userType varchar(2),type varchar(8),start graphic(40),end graphic(40),whentime varchar(8),time varchar(8),daytype varchar(8))";
        public static String CREATE_TEMP_myAsk = "alter table myAsk rename to _temp_myAsk";
        public static String myAsk_INSERT_DATA = "insert into myAsk select *,'' from _temp_myAsk";
        public static String DROP_myAsk = "drop table _temp_myAsk";

        public static String CREATE_myRing = "create table myRing(ID varchar(4),user varchar(20),useful varchar(2),userType varchar(2),type varchar(8),start graphic(40),end graphic(40),whentime varchar(8),time varchar(8),daytype varchar(8))";
        public static String CREATE_TEMP_myRing = "alter table myRing rename to _temp_myRing";
        public static String myRing_INSERT_DATA = "insert into myRing select *,'' from _temp_myRing";
        public static String DROP_myRing = "drop table _temp_myRing";

        public static String addAppDataRingTime="ALTER TABLE "+"appData"+" ADD COLUMN Ask_Time INTEGER";
        public static String setAppDataRingTime="UPDATE appData SET Ask_Time = 3 WHERE author = 'kapok'";

        public static String addAppDataRingnum="ALTER TABLE "+"appData"+" ADD COLUMN ringnum INTEGER";
        public static String setAppDataRingnum="UPDATE appData SET ringnum = 0 WHERE author = 'kapok'";

    }
}
