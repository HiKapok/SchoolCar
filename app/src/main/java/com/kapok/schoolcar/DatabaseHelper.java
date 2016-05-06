package com.kapok.schoolcar;

import java.util.regex.Pattern;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

//DatabaseHelper作为一个访问SQLite的助手类，提供两个方面的功能，
//第一，getReadableDatabase(),getWritableDatabase()可以获得SQLiteDatabse对象，通过该对象可以对数据库进行操作
//第二，提供了onCreate()和onUpgrade()两个回调函数，允许我们在创建和升级数据库时，进行自己的操作

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    final String carTimeTable_1 = new String("/WHZX**五宿舍**东北门**07:00**洪家楼校区南门外、中心校区北门内、信息楼**兴隆山校区教学五楼南侧/WHZX**五宿舍**东北门**12:50**洪家楼校区南门外、中心校区北门内、信息楼**兴隆山校区教学五楼南侧/WZHR**中心校区**体育馆北**07:20**中心校区信息楼、洪家楼校区南门对面、五宿舍东北门**软件园校区旗杆处/WZHR**中心校区**体育馆北**12:55**中心校区信息楼、洪家楼校区南门对面、五宿舍东北门**软件园校区旗杆处/WZX**中心校区**体育馆北**17:00**直达**南山小区/WHZX**洪家楼校区**南门内**09:20**中心校区信息楼**兴隆山校区教学五楼南侧/WHZX**洪家楼校区**南门内**10:10**中心校区信息楼**兴隆山校区教学五楼南侧/WHZX**洪家楼校区**南门内**14:50**中心校区信息楼**兴隆山校区教学五楼南侧/WHX**洪家楼校区**南门内**17:00**直达**南山小区/WHZX**洪家楼校区**南门内**21:00**中心校区信息楼**兴隆山校区教学五楼南侧/WBQR**趵突泉校区**停车场**07:15**千佛山校区南院北门**软件园校区旗杆处/WBQR**趵突泉校区**停车场**09:15**千佛山校区南院北门**软件园校区旗杆处/WBQR**趵突泉校区**停车场**12:50**千佛山校区南院北门**软件园校区旗杆处/WBQX**趵突泉校区**停车场**07:00**千佛山校区南院北门、冶金宾馆、马家庄**兴隆山校区教学五楼南侧/WBQX**趵突泉校区**停车场**09:15**千佛山校区南院北门**兴隆山校区教学五楼南侧/WBQX**趵突泉校区**停车场**12:50**千佛山校区南院北门**兴隆山校区教学五楼南侧/WBX**趵突泉校区**停车场**17:00**直达**南山小区/WBQX**趵突泉校区**停车场**21:00**千佛山校区南院北门**兴隆山校区教学五楼南侧/WQX**千佛山校区**南院北门**07:00**阳光舜城转盘南侧、山东省社会科学院、兴隆山校区工程训练中心**兴隆山校区教学五楼南侧/WQX**千佛山校区**南院北门**10:10**直达**兴隆山校区教学五楼南侧/WQX**千佛山校区**南院北门**14:40**直达**兴隆山校区教学五楼南侧/WQX**千佛山校区**南院北门**15:30**直达**兴隆山校区教学五楼南侧/WQX**千佛山校区**北院南门**17:00**直达**南山小区/WXZH**兴隆山校区**教学五楼南侧**10:00**中心校区信息楼**洪家楼校区南门/WXZH**兴隆山校区**教学五楼南侧**11:10**中心校区信息楼**洪家楼校区南门/WXZH**兴隆山校区**教学五楼南侧**15:30**中心校区信息楼**洪家楼校区南门/WXZH**兴隆山校区**教学五楼南侧**16:30**中心校区信息楼**洪家楼校区南门/WXZH**兴隆山校区**教学五楼南侧**12:05**中心校区信息楼、洪家楼校区南门对面**五宿舍东北门/WXZH**兴隆山校区**教学五楼南侧**17:20**中心校区信息楼、洪家楼校区南门对面**五宿舍东北门/WXZH**兴隆山校区**教学五楼南侧**21:40**中心校区信息楼**洪家楼校区南门/WXQ**兴隆山校区**教学五楼南侧**10:00**直达**千佛山校区南院北门/WXQ**兴隆山校区**教学五楼南侧**11:10**玉函立交桥东南**千佛山校区南院北门/WXQ**兴隆山校区**教学五楼南侧**12:05**玉函立交桥东南**千佛山校区南院北门/WXQ**兴隆山校区**教学五楼南侧**15:30**直达**千佛山校区南院北门/WXQ**兴隆山校区**教学五楼南侧**16:30**直达**千佛山校区南院北门/WXQ**兴隆山校区**教学五楼南侧**17:20**玉函立交桥东南**千佛山校区南院北门/WXQ**兴隆山校区**教学五楼南侧**21:40**玉函立交桥东南**千佛山校区南院北门/WXQ**兴隆山校区**工程训练中心**16:20**阳光舜城转盘南侧**千佛山校区南院北门/WRHZ**软件园校区**旗杆处**12:10**五宿舍东北门、洪家校区南门外**中心校区体育馆北/WRHZ**软件园校区**旗杆处**17:00**五宿舍东北门、洪家校区南门外**中心校区体育馆北/WRQB**软件园校区**旗杆处**11:00**千佛山校区北院南门**趵突泉校区停车场/WRQB**软件园校区**旗杆处**12:10**千佛山校区北院南门**趵突泉校区停车场/WRQB**软件园校区**旗杆处**17:00**千佛山校区北院南门**趵突泉校区停车场/WXQ**南山小区**北门**07:00**直达**千佛山校区北院南门/WXH**南山小区**北门**07:00**直达**洪家楼校区南门/WXQH**南山小区**北门**09:00**玉函立交桥东南、千佛山校区南院北门、中心校区信息楼**洪家楼校区南门/WXB**南山小区**北门**07:00**直达**趵突泉校区停车场/WXZ**南山小区**北门**07:00**直达**中心校区体育馆北");
    final String carTimeTable_2 = new String("/SHZX**洪家楼校区**南门内**07:10**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**08:00**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**09:00**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**10:00**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**11:10**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**12:10**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**12:50**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**15:00**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**16:00**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**17:10**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**19:00**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**21:00**中心校区信息楼**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**07:10**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**08:00**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**09:00**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**10:00**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**11:10**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**12:10**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**12:50**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**15:00**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**16:00**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**17:10**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**19:00**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**21:00**千佛山校区南院北门**兴隆山校区教学五楼南侧/SXZH**兴隆山校区**教学五楼南侧**07:10**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**08:00**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**09:00**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**10:00**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**11:10**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**12:10**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**12:50**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**15:00**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**16:00**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**17:10**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**19:40**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**21:40**中心校区信息楼**洪家楼校区南门/SXQB**兴隆山校区**教学五楼南侧**07:10**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**08:00**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**09:00**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**10:00**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**11:10**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**12:10**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**12:50**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**15:00**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**16:00**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**17:10**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**19:40**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**21:40**千佛山校区北院南门**趵突泉校区停车场");
    final String carTimeTable_3 = new String("/WHZX**五宿舍**东北门**07:00**洪家楼校区南门外、中心校区北门内、信息楼**兴隆山校区教学五楼南侧/WHZX**五宿舍**东北门**13:20**洪家楼校区南门外、中心校区北门内、信息楼**兴隆山校区教学五楼南侧/WZHR**中心校区**体育馆北**07:20**中心校区信息楼、洪家楼校区南门对面、五宿舍东北门**软件园校区旗杆处/WZHR**中心校区**体育馆北**13:25**中心校区信息楼、洪家楼校区南门对面、五宿舍东北门**软件园校区旗杆处/WZX**中心校区**体育馆北**17:30**直达**南山小区/WHZX**洪家楼校区**南门内**09:20**中心校区信息楼**兴隆山校区教学五楼南侧/WHZX**洪家楼校区**南门内**10:10**中心校区信息楼**兴隆山校区教学五楼南侧/WHZX**洪家楼校区**南门内**15:20**中心校区信息楼**兴隆山校区教学五楼南侧/WHX**洪家楼校区**南门内**17:30**直达**南山小区/WHZX**洪家楼校区**南门内**21:30**中心校区信息楼**兴隆山校区教学五楼南侧/WBQR**趵突泉校区**停车场**07:15**千佛山校区南院北门**软件园校区旗杆处/WBQR**趵突泉校区**停车场**09:15**千佛山校区南院北门**软件园校区旗杆处/WBQR**趵突泉校区**停车场**13:20**千佛山校区南院北门**软件园校区旗杆处/WBQX**趵突泉校区**停车场**07:00**千佛山校区南院北门、冶金宾馆、马家庄**兴隆山校区教学五楼南侧/WBQX**趵突泉校区**停车场**09:15**千佛山校区南院北门**兴隆山校区教学五楼南侧/WBQX**趵突泉校区**停车场**13:20**千佛山校区南院北门**兴隆山校区教学五楼南侧/WBX**趵突泉校区**停车场**17:30**直达**南山小区/WBQX**趵突泉校区**停车场**21:30**千佛山校区南院北门**兴隆山校区教学五楼南侧/WQX**千佛山校区**南院北门**07:00**阳光舜城转盘南侧、山东省社会科学院、兴隆山校区工程训练中心**兴隆山校区教学五楼南侧/WQX**千佛山校区**南院北门**10:10**直达**兴隆山校区教学五楼南侧/WQX**千佛山校区**南院北门**15:10**直达**兴隆山校区教学五楼南侧/WQX**千佛山校区**南院北门**16:00**直达**兴隆山校区教学五楼南侧/WQX**千佛山校区**北院南门**17:30**直达**南山小区/WXZH**兴隆山校区**教学五楼南侧**10:00**中心校区信息楼**洪家楼校区南门/WXZH**兴隆山校区**教学五楼南侧**11:10**中心校区信息楼**洪家楼校区南门/WXZH**兴隆山校区**教学五楼南侧**16:00**中心校区信息楼**洪家楼校区南门/WXZH**兴隆山校区**教学五楼南侧**17:00**中心校区信息楼**洪家楼校区南门/WXZH**兴隆山校区**教学五楼南侧**12:05**中心校区信息楼、洪家楼校区南门对面**五宿舍东北门/WXZH**兴隆山校区**教学五楼南侧**17:50**中心校区信息楼、洪家楼校区南门对面**五宿舍东北门/WXZH**兴隆山校区**教学五楼南侧**22:10**中心校区信息楼**洪家楼校区南门/WXQ**兴隆山校区**教学五楼南侧**10:00**直达**千佛山校区南院北门/WXQ**兴隆山校区**教学五楼南侧**11:10**玉函立交桥东南**千佛山校区南院北门/WXQ**兴隆山校区**教学五楼南侧**12:05**玉函立交桥东南**千佛山校区南院北门/WXQ**兴隆山校区**教学五楼南侧**16:00**直达**千佛山校区南院北门/WXQ**兴隆山校区**教学五楼南侧**17:00**直达**千佛山校区南院北门/WXQ**兴隆山校区**教学五楼南侧**17:50**玉函立交桥东南**千佛山校区南院北门/WXQ**兴隆山校区**教学五楼南侧**22:10**玉函立交桥东南**千佛山校区南院北门/WXQ**兴隆山校区**工程训练中心**16:50**阳光舜城转盘南侧**千佛山校区南院北门/WRHZ**软件园校区**旗杆处**12:10**五宿舍东北门、洪家校区南门外**中心校区体育馆北/WRHZ**软件园校区**旗杆处**17:30**五宿舍东北门、洪家校区南门外**中心校区体育馆北/WRQB**软件园校区**旗杆处**11:00**千佛山校区北院南门**趵突泉校区停车场/WRQB**软件园校区**旗杆处**12:10**千佛山校区北院南门**趵突泉校区停车场/WRQB**软件园校区**旗杆处**17:30**千佛山校区北院南门**趵突泉校区停车场/WXQ**南山小区**北门**07:00**直达**千佛山校区北院南门/WXH**南山小区**北门**07:00**直达**洪家楼校区南门/WXQH**南山小区**北门**09:00**玉函立交桥东南、千佛山校区南院北门、中心校区信息楼**洪家楼校区南门/WXB**南山小区**北门**07:00**直达**趵突泉校区停车场/WXZ**南山小区**北门**07:00**直达**中心校区体育馆北");
    final String carTimeTable_4 = new String("/SHZX**洪家楼校区**南门内**07:10**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**08:00**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**09:00**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**10:00**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**11:10**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**12:10**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**13:20**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**15:30**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**16:30**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**17:40**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**19:30**中心校区信息楼**兴隆山校区教学五楼南侧/SHZX**洪家楼校区**南门内**21:30**中心校区信息楼**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**07:10**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**08:00**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**09:00**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**10:00**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**11:10**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**12:10**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**13:20**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**15:30**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**16:30**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**17:40**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**19:30**千佛山校区南院北门**兴隆山校区教学五楼南侧/SBQX**趵突泉校区**停车场**21:30**千佛山校区南院北门**兴隆山校区教学五楼南侧/SXZH**兴隆山校区**教学五楼南侧**07:10**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**08:00**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**09:00**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**10:00**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**11:10**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**12:10**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**13:20**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**15:30**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**16:30**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**17:40**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**20:10**中心校区信息楼**洪家楼校区南门/SXZH**兴隆山校区**教学五楼南侧**22:10**中心校区信息楼**洪家楼校区南门/SXQB**兴隆山校区**教学五楼南侧**07:10**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**08:00**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**09:00**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**10:00**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**11:10**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**12:10**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**13:20**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**15:30**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**16:30**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**17:40**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**20:10**千佛山校区北院南门**趵突泉校区停车场/SXQB**兴隆山校区**教学五楼南侧**22:10**千佛山校区北院南门**趵突泉校区停车场");
    static final String [] constTable = {new String("type"),new String("start"),new String("start_2"),new String("whentime"),new String("mid"),new String("end")};
    //在SQLiteOepnHelper的子类当中，必须有该构造函数
    public DatabaseHelper(Context context, String name, CursorFactory factory,
                          int version) {
        //必须通过super调用父类当中的构造函数
        super(context, name, factory, version);

        // TODO Auto-generated constructor stub
    }
    public DatabaseHelper(Context context, String name) {
        this(context,name,VERSION);
    }
    public DatabaseHelper(Context context,String name,int version){
        this(context, name,null,version);
    }
    public void initData(){

        SQLiteDatabase db;
        db=DatabaseHelper.this.getWritableDatabase();
        if(MyDataBase.isInit==true)
            return;
        ContentValues temp = new ContentValues();//实例化一个ContentValues用来装载待插入的数据cv.put("username","Jack Johnson");//添加用户名
        temp.put("Ask_Time",DataDetail.AskRingTimeBefore);
        temp.put("beOnNetAlways",DataDetail.beOnNetAlways);
        temp.put("beNight",DataDetail.beNight);
        temp.put("beUseful",DataDetail.ID_Useful);
        temp.put("USER",DataDetail.User);
        temp.put("Number",DataDetail.ID);
        temp.put("add_beUseful",DataDetail.common_add_Useful);
        temp.put("start",DataDetail.start);
        temp.put("end",DataDetail.end);
        temp.put("num",DataDetail.num);
        temp.put("ringnum",DataDetail.ringnum);
        temp.put("ip",DataDetail.ip);
        temp.put("dataDate",CheckUpdate.versionDay);
        temp.put("port",DataDetail.port);
        temp.put("debug",DataDetail.debug);
        temp.put("author","kapok");
        db.insert("appData",null,temp);//执行插入操作
        temp.clear();
        Pattern quoteRegex = Pattern.compile("[/]+");
        String [] result=quoteRegex.split(carTimeTable_1);
        //System.out.println(result.length+"");
        for (int i=0; i<result.length; i++) {
            Pattern pattern = Pattern.compile("[**]+");
            String [] subresult=pattern.split(result[i]);
            //System.out.println(subresult.length+"");
            for (int j=0; j<subresult.length; j++) {
                temp.put(constTable[j],subresult[j]);
            }
            db.insert("WinterCarTable",null,temp);//执行插入操作
            temp.clear();
        }
        result=quoteRegex.split(carTimeTable_2);
        for (int i=0; i<result.length; i++) {
            Pattern pattern = Pattern.compile("[**]+");
            String [] subresult=pattern.split(result[i]);
            //System.out.println(subresult.length+"");
            for (int j=0; j<subresult.length; j++) {
                temp.put(constTable[j],subresult[j]);
            }
            db.insert("WinterCarTable",null,temp);//执行插入操作
            temp.clear();
        }
        temp.clear();
        result=quoteRegex.split(carTimeTable_3);
        //System.out.println(result.length+"");
        for (int i=0; i<result.length; i++) {
            Pattern pattern = Pattern.compile("[**]+");
            String [] subresult=pattern.split(result[i]);
            //System.out.println(subresult.length+"");
            for (int j=0; j<subresult.length; j++) {
                temp.put(constTable[j],subresult[j]);
            }
            db.insert("SummerCarTable",null,temp);//执行插入操作
            temp.clear();
        }
        result=quoteRegex.split(carTimeTable_4);
        for (int i=0; i<result.length; i++) {
            Pattern pattern = Pattern.compile("[**]+");
            String [] subresult=pattern.split(result[i]);
            //System.out.println(subresult.length+"");
            for (int j=0; j<subresult.length; j++) {
                temp.put(constTable[j],subresult[j]);
            }
            db.insert("SummerCarTable",null,temp);//执行插入操作
            temp.clear();
        }
        temp.clear();
    /*	for (int i=0; i<DataDetail.askTbl.length; i++) {
    		temp.put("ID",""+i); 
    		temp.put("useful",DataDetail.askTbl[i][0]); 
    		temp.put("userType",DataDetail.askTbl[i][1]);
    		temp.put("type",DataDetail.askTbl[i][2]);
    		temp.put("start",DataDetail.askTbl[i][3]); 
    		temp.put("end",DataDetail.askTbl[i][4]);
    		temp.put("whentime",DataDetail.askTbl[i][5]);
    		temp.put("time",DataDetail.askTbl[i][6]);
    		temp.put("daytype",DataDetail.askTbl[i][7]);
        	db.insert("myAsk",null,temp);//执行插入操作
    		temp.clear();
    	} */
        MyDataBase.isInit=true;
    }
    //该函数是在第一次创建数据库的时候执行,实际上是在第一次得到SQLiteDatabse对象的时候，才会调用这个方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //execSQL函数用于执行SQL语句
        //没有发现数据库，则创建，并建立三张表
        db.execSQL("create table WinterCarTable(type varchar(8),start graphic(30),start_2 graphic(20),whentime varchar(8),mid graphic(30),end graphic(30))");
        db.execSQL("create table SummerCarTable(type varchar(8),start graphic(30),start_2 graphic(20),whentime varchar(8),mid graphic(30),end graphic(30))");
        db.execSQL("create table appData(ringnum INTEGER,Ask_Time INTEGER,beOnNetAlways INTEGER,beNight INTEGER,beUseful INTEGER,add_beUseful INTEGER,num INTEGER,start INTEGER,end INTEGER,USER varchar(20),Number char(12),port INTEGER,ip varchar(25),dataDate varchar(25),debug varchar(5),author char(12))");
        db.execSQL("create table myAsk(ID varchar(4),useful varchar(2),userType varchar(2),type varchar(8),start graphic(40),end graphic(40),whentime varchar(8),time varchar(10),daytype varchar(8))");
        db.execSQL("create table myRing(ID varchar(4),useful varchar(2),userType varchar(2),type varchar(8),start graphic(40),end graphic(40),whentime varchar(8),time varchar(20),daytype varchar(8))");

        MyDataBase.isInit=false;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        switch(oldVersion){
                case 1:
                    /*db.execSQL(DataBaseUpgradeHelper.oldVersion1.CREATE_TEMP_appData);
                    db.execSQL(DataBaseUpgradeHelper.oldVersion1.CREATE_appData);
                    db.execSQL(DataBaseUpgradeHelper.oldVersion1.appData_INSERT_DATA);
                    db.execSQL(DataBaseUpgradeHelper.oldVersion1.DROP_appData);
                    db.execSQL(DataBaseUpgradeHelper.oldVersion1.CREATE_myRing);*/
                    db.execSQL(DataBaseUpgradeHelper.oldVersion1.CREATE_myRing);
                    db.execSQL(DataBaseUpgradeHelper.oldVersion1.addAppDataRingTime);
                    db.execSQL(DataBaseUpgradeHelper.oldVersion1.setAppDataRingTime);
                    db.execSQL(DataBaseUpgradeHelper.oldVersion1.addAppDataRingnum);
                    db.execSQL(DataBaseUpgradeHelper.oldVersion1.setAppDataRingnum);
                    break;
                default:
                    break;
            }
    }

}
