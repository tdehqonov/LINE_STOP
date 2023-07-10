package com.example.DY_PDT;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import com.example.DY_PDT.model.Item;
import com.example.DY_PDT.model.MyDataHisobot;
import com.example.DY_PDT.model.shop;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class HotOrNotLineStop {


    public static final String KEY_ROWID="_id";
    public static final String KEY_ROWID_LS="_idLS";
    public static final String KEY_shop="shop";
    public static final String KEY_area="area";
    public static final String KEY_department="department";
    public static final String KEY_created_at="created_at";
    public static final String KEY_description="description";
    public static final String KEY_from="fromLS";
    public static final String KEY_to="toLS";
    public static final String KEY_minutes="minutes";
    public static final String KEY_alarm="alarm";
    public static final String KEY_korildi="korildi";
    public static final String DATABASE_NAME="LINESTOP";
    public static final String DATABASE_TABLE="linestop";
    public static final int DATABASE_VERSION=31;

    public static final String KEY_EtScanner1="EtScanner1";
    public static final String KEY_EtDetalNomeri="EtDetalNomeri";
    public static final String KEY_EtDetalSoni="EtDetalSoni";
    public static final String KEY_EtAdres="EtAdres";
    public static final String KEY_EtEO="EtEO";
    public static final String KEY_EtIzox="EtIzox";
    public static final String KEY_EtSeksiya="EtSeksiya";
    public static final String KEY_SCAN_DATA="scan_data";
    public static final String KEY_SCAN_TIME="scan_time";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_LOCATSIYA = "locatsiya";
    public static final String KEY_ACCESS_TOKEN = "access_token";


    //Area
    public static final String DATABASE_TABLE_SHOP="lsshop";
    public static final String DATABASE_TABLE_HISOBOT="hisobot";
    public static final String UserInfo_TABLE = "UserInfo";

    public static final String KEY_FILTER="filter";



    private DbHelper ourHelper;
    private Context ourContext;
    private SQLiteDatabase ourDatabase;

    public long createEntry(String  EtScanner1,String  EtDetalNomeri,String  EtDetalSoni,String EtAdres,String EtEO,String EtIzox,String scan_data,String scan_time) {
        ContentValues cv=new ContentValues();
        cv.put(KEY_EtScanner1,EtScanner1);
        cv.put(KEY_EtDetalNomeri,EtDetalNomeri);
        cv.put(KEY_EtDetalSoni,EtDetalSoni);
        cv.put(KEY_EtAdres,EtAdres);
        cv.put(KEY_EtEO,EtEO);
        cv.put(KEY_EtIzox,EtIzox);
        cv.put(KEY_SCAN_DATA,scan_data);
        cv.put(KEY_SCAN_TIME,scan_time);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }


    public boolean getForExcel(String EexcelFileName  ) {

        String[] columns=new String[]{KEY_ROWID,KEY_EtScanner1,KEY_EtIzox,KEY_EtSeksiya,KEY_SCAN_DATA,KEY_SCAN_TIME};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);

//////////////////////////////////////////////////////
        File sd = Environment.getExternalStorageDirectory();
        String csvFile = EexcelFileName;

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }

        try {

            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("userList", 0);
            // column and row
            sheet.addCell(new Label(0, 0, "EtScanner1"));
            sheet.addCell(new Label(1, 0, "EtIzox"));
            sheet.addCell(new Label(2, 0, "Soni"));
            sheet.addCell(new Label(3, 0, "Olchami"));
            sheet.addCell(new Label(4, 0, "Seksiya"));
            sheet.addCell(new Label(5, 0, "Scan_data"));
            sheet.addCell(new Label(6, 0, "Scan_time"));
            if (c.moveToFirst()) {


                do {
                    String EtScanner1 = c.getString(1);
                    String EtIzox = c.getString(2);
                    String EtSeksiya = c.getString(3);
                    String scan_data = c.getString(4);
                    String scan_time = c.getString(5);
                    int i = c.getPosition() + 1;
                    sheet.addCell(new Label(0, i, EtScanner1));
                    sheet.addCell(new Label(1, i, EtIzox));
                    sheet.addCell(new Label(2, i, "1"));
                    sheet.addCell(new Label(3, i, "SHTUK"));
                    sheet.addCell(new Label(4, i, EtSeksiya));
                    sheet.addCell(new Label(5, i, scan_data));
                    sheet.addCell(new Label(6, i, scan_time));

                } while (c.moveToNext());
            }

            c.close();
            workbook.write();
            workbook.close();
            Toast.makeText(ourContext.getApplicationContext(),
                    "Saqlandi  ("+csvFile.toString()+") ", Toast.LENGTH_LONG).show();
            return true;

        } catch(IOException e){
            e.printStackTrace();
            return false;
        } catch (WriteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Item> getDataForListView() {

        String[] columns=new String[]{KEY_ROWID,KEY_ROWID_LS,KEY_shop,KEY_area,KEY_department,KEY_description,KEY_created_at,KEY_from,KEY_to,KEY_minutes,KEY_alarm};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,KEY_ROWID_LS);


        int ishop=c.getColumnIndex(KEY_shop);
        int iarea=c.getColumnIndex(KEY_area);
        int idepartment=c.getColumnIndex(KEY_department);
        int idescription=c.getColumnIndex(KEY_description);
        int icreated_at=c.getColumnIndex(KEY_created_at);
        int ifrom=c.getColumnIndex(KEY_from);
        int ito=c.getColumnIndex(KEY_to);
        int iminutes=c.getColumnIndex(KEY_minutes);
        int ialarm=c.getColumnIndex(KEY_alarm);

        List<Item> malumot=new ArrayList<>();
        for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious())
        {
            String   created_at = c.getString(icreated_at).replace(" UTC", "");
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(created_at);
                created_at=new SimpleDateFormat("dd.MM.yyyy").format(date);
                malumot.add(new Item(c.getString(idescription),c.getString(ishop),c.getString(iarea),c.getString(idepartment),created_at,c.getString(ifrom),c.getString(ito),c.getString(iminutes),c.getString(ialarm)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return malumot;
    }

    public ArrayList<shop> getDataForListViewShop() {
        String[] columns=new String[]{KEY_ROWID,KEY_shop,KEY_FILTER};
        Cursor c=null;
        c=ourDatabase.query(DATABASE_TABLE_SHOP,columns,null ,null,null,null,KEY_shop);
        int ishop=c.getColumnIndex(KEY_shop);
        int isFILTER=c.getColumnIndex(KEY_FILTER);

        ArrayList<shop> malumot=new ArrayList<>();
        for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious())
        {
            malumot.add(new shop(c.getString(ishop),c.getString(isFILTER)));
        }
        c.close();
        return malumot;
    }

    public List<Item> getDataForListViewWithDate(String create_atFrom,String create_atTo,String IDlvHisobotFilter,String DATABASE_TABLE) {
        String[] columns=new String[]{KEY_ROWID,KEY_ROWID_LS,KEY_shop,KEY_area,KEY_department,KEY_description,KEY_created_at,KEY_from,KEY_to,KEY_minutes,KEY_alarm};
        Cursor c=null;

        String filter="";
        if(IDlvHisobotFilter.contains(Constants.HAMMASI)){
             c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"'" ,null,null,null,KEY_ROWID_LS);

        } else if(IDlvHisobotFilter.contains(Constants.SHOP)){

            filter=IDlvHisobotFilter.substring(6);
             c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"' AND "+KEY_shop+"='"+filter+"'" ,null,null,null,KEY_ROWID_LS);
        }else if(IDlvHisobotFilter.contains(Constants.AREA)){

            filter=IDlvHisobotFilter.substring(6);
            c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"' AND "+KEY_area+"='"+filter+"'" ,null,null,null,KEY_ROWID_LS);
        }else if(IDlvHisobotFilter.contains(Constants.DEPA)){

            filter=IDlvHisobotFilter.substring(6);
            c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"' AND "+KEY_department+"='"+filter+"'" ,null,null,null,KEY_ROWID_LS);
        }else if(IDlvHisobotFilter.contains(Constants.DESC)){

            filter=IDlvHisobotFilter.substring(6);
            c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"' AND "+KEY_description+"='"+filter+"'" ,null,null,null,KEY_ROWID_LS);
    }

        int ishop=c.getColumnIndex(KEY_shop);
        int iarea=c.getColumnIndex(KEY_area);
        int idepartment=c.getColumnIndex(KEY_department);
        int idescription=c.getColumnIndex(KEY_description);
        int icreated_at=c.getColumnIndex(KEY_created_at);
        int ifrom=c.getColumnIndex(KEY_from);
        int ito=c.getColumnIndex(KEY_to);
        int iminutes=c.getColumnIndex(KEY_minutes);
        int ialarm=c.getColumnIndex(KEY_alarm);

        List<Item> malumot=new ArrayList<>();
        for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious())
        {
            String   created_at = c.getString(icreated_at).replace(" UTC", "");
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(created_at);
                created_at=new SimpleDateFormat("dd.MM.yyyy").format(date);
                malumot.add(new Item(c.getString(idescription),c.getString(ishop),c.getString(iarea),c.getString(idepartment),created_at,c.getString(ifrom),c.getString(ito),c.getString(iminutes),c.getString(ialarm)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return malumot;
    }

//    public List<Item> getDataForListViewWithDateFilter(boolean alarmFilter) {
//        String[] columns=new String[]{KEY_ROWID,KEY_ROWID_LS,KEY_shop,KEY_area,KEY_department,KEY_description,KEY_created_at,KEY_from,KEY_to,KEY_minutes,KEY_alarm};
//        Cursor c=null;
//
//   //    String filter="";
//     //   if(IDlvHisobotFilter.contains(Constants.HAMMASI)){
//            c=ourDatabase.query(DATABASE_TABLE,columns,null ,null,null,null,KEY_ROWID_LS);
//
////        } else if(IDlvHisobotFilter.contains(Constants.SHOP)){
////
////            filter=IDlvHisobotFilter.substring(6);
////            c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"' AND "+KEY_shop+"='"+filter+"'" ,null,null,null,KEY_ROWID_LS);
////        }else if(IDlvHisobotFilter.contains(Constants.AREA)){
////
////            filter=IDlvHisobotFilter.substring(6);
////            c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"' AND "+KEY_area+"='"+filter+"'" ,null,null,null,KEY_ROWID_LS);
////        }else if(IDlvHisobotFilter.contains(Constants.DEPA)){
////
////            filter=IDlvHisobotFilter.substring(6);
////            c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"' AND "+KEY_department+"='"+filter+"'" ,null,null,null,KEY_ROWID_LS);
////        }else if(IDlvHisobotFilter.contains(Constants.DESC)){
////
////            filter=IDlvHisobotFilter.substring(6);
////            c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"' AND "+KEY_description+"='"+filter+"'" ,null,null,null,KEY_ROWID_LS);
////        }else if(IDlvHisobotFilter.contains(Constants.FILTER)){
////            c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"' AND "+KEY_alarm+"='1'" ,null,null,null,KEY_ROWID_LS);
////        }
//
//        int ishop=c.getColumnIndex(KEY_shop);
//        int iarea=c.getColumnIndex(KEY_area);
//        int idepartment=c.getColumnIndex(KEY_department);
//        int idescription=c.getColumnIndex(KEY_description);
//        int icreated_at=c.getColumnIndex(KEY_created_at);
//        int ifrom=c.getColumnIndex(KEY_from);
//        int ito=c.getColumnIndex(KEY_to);
//        int iminutes=c.getColumnIndex(KEY_minutes);
//        int ialarm=c.getColumnIndex(KEY_alarm);
//
//        List<Item> malumot=new ArrayList<>();
//        for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious())
//        {
//            String   created_at = c.getString(icreated_at).replace(" UTC", "");
//            Date date = null;
//            try {
//                date = new SimpleDateFormat("yyyy-MM-dd").parse(created_at);
//                created_at=new SimpleDateFormat("dd.MM.yyyy").format(date);
//
//                if (chekAlarm(c.getString(idepartment)).equals("1") && alarmFilter==true) {
//                    malumot.add(new Item(c.getString(idescription), c.getString(ishop), c.getString(iarea), c.getString(idepartment), created_at, c.getString(ifrom), c.getString(ito), c.getString(iminutes), c.getString(ialarm)));
//                } else if(alarmFilter==false)
//                {
//                    malumot.add(new Item(c.getString(idescription), c.getString(ishop), c.getString(iarea), c.getString(idepartment), created_at, c.getString(ifrom), c.getString(ito), c.getString(iminutes), c.getString(ialarm)));
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        c.close();
//        return malumot;
//    }

    public List<Item> getDataForListViewWithDateFilter2(String create_atFrom, String create_atTo, boolean alarmFilter) {

            String[] columns=new String[]{KEY_ROWID,KEY_ROWID_LS,KEY_shop,KEY_area,KEY_department,KEY_description,KEY_created_at,KEY_from,KEY_to,KEY_minutes,KEY_alarm};
            Cursor c=null;
            c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"'" ,null,null,null,KEY_ROWID_LS);

        int ishop=c.getColumnIndex(KEY_shop);
        int iarea=c.getColumnIndex(KEY_area);
        int idepartment=c.getColumnIndex(KEY_department);
        int idescription=c.getColumnIndex(KEY_description);
        int icreated_at=c.getColumnIndex(KEY_created_at);
        int ifrom=c.getColumnIndex(KEY_from);
        int ito=c.getColumnIndex(KEY_to);
        int iminutes=c.getColumnIndex(KEY_minutes);
        int ialarm=c.getColumnIndex(KEY_alarm);

        List<Item> malumot=new ArrayList<>();
        for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious())
        {
            String   created_at = c.getString(icreated_at).replace(" UTC", "");
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(created_at);
                created_at=new SimpleDateFormat("dd.MM.yyyy").format(date);

                if (chekAlarm(c.getString(idepartment)).equals("1") && alarmFilter==true) {
                    malumot.add(new Item(c.getString(idescription), c.getString(ishop), c.getString(iarea), c.getString(idepartment), created_at, c.getString(ifrom), c.getString(ito), c.getString(iminutes), c.getString(ialarm)));
                } else if(alarmFilter==false)
                {
                    malumot.add(new Item(c.getString(idescription), c.getString(ishop), c.getString(iarea), c.getString(idepartment), created_at, c.getString(ifrom), c.getString(ito), c.getString(iminutes), c.getString(ialarm)));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return malumot;
    }


    public List<Item> getDataForListViewWithDateFilter2Hisobot(String create_atFrom, String create_atTo) {

        String[] columns=new String[]{KEY_ROWID,KEY_ROWID_LS,KEY_shop,KEY_area,KEY_department,KEY_description,KEY_created_at,KEY_from,KEY_to,KEY_minutes};
        Cursor c=null;
        c=ourDatabase.query(DATABASE_TABLE_HISOBOT,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"'" ,null,null,null,KEY_ROWID_LS);

        int ishop=c.getColumnIndex(KEY_shop);
        int iarea=c.getColumnIndex(KEY_area);
        int idepartment=c.getColumnIndex(KEY_department);
        int idescription=c.getColumnIndex(KEY_description);
        int icreated_at=c.getColumnIndex(KEY_created_at);
        int ifrom=c.getColumnIndex(KEY_from);
        int ito=c.getColumnIndex(KEY_to);
        int iminutes=c.getColumnIndex(KEY_minutes);

        List<Item> malumot=new ArrayList<>();
        for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious())
        {
            String   created_at = c.getString(icreated_at).replace(" UTC", "");
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(created_at);
                created_at=new SimpleDateFormat("dd.MM.yyyy").format(date);
                malumot.add(new Item(c.getString(idescription), c.getString(ishop), c.getString(iarea), c.getString(idepartment), created_at, c.getString(ifrom), c.getString(ito), c.getString(iminutes)));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return malumot;
    }
    public long updateUserInfo(String userName,String password,String access_token) {
        ContentValues cv = new ContentValues();
        //     cv.put(KEY_USERNAME, userName);
        cv.put(KEY_PASSWORD, password);
        cv.put(KEY_ACCESS_TOKEN, access_token);
        return ourDatabase.update(UserInfo_TABLE, cv, KEY_USERNAME + "='" + userName+"'" , null);
    }
    public Boolean setUserInfo(String userName,String password,String access_token) {
        String[] columns = new String[]{KEY_USERNAME};
        Cursor c = ourDatabase.query(UserInfo_TABLE, columns, KEY_USERNAME + "='" + userName+"' AND "+KEY_PASSWORD+"='"+password+"'", null, null, null, null);

        if (c.getCount()!=0) {
            updateUserInfo(userName,password,access_token);
            return true;
        } else {
            createEntryUserInfo(userName,password,access_token);
            return true;
        }
    }

    public ArrayList<MyDataHisobot> getDataForListViewHisobot(String create_atFrom, String create_atTo,String DATABASE_TABLE) {
        String[] columns=new String[]{KEY_ROWID_LS,KEY_shop,"count(" + KEY_shop + ") AS key_shop_count","sum(" + KEY_minutes + ") AS key_minutss_sum" };
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"'" ,null,KEY_shop,null,KEY_ROWID_LS);
            ArrayList<MyDataHisobot> malumot=new ArrayList<MyDataHisobot> ();

        int soniJami=0;
        int minutjami=0;

        for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious())
        {
           soniJami+=c.getInt(2);
           minutjami+=c.getInt(3);
        }
        malumot.add(new MyDataHisobot("HAMMASI",soniJami,minutjami,Constants.HAMMASI));
        malumot.add(new MyDataHisobot("<<<SHOP>>> ",0,0,""));

        for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious())
        {
                malumot.add(new MyDataHisobot(c.getString(1),c.getInt(2),c.getInt(3),Constants.SHOP));
        }

        malumot.add(new MyDataHisobot("<<<DEPARTAMENT>>> ",0,0,""));


        columns=new String[]{KEY_ROWID_LS,KEY_department,"count(" + KEY_department + ") AS key_shop_count","sum(" + KEY_minutes + ") AS key_minutss_sum" };

        c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"'" ,null,KEY_department,null,KEY_ROWID_LS);
        for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious())
        {
            malumot.add(new MyDataHisobot(c.getString(1),c.getInt(2),c.getInt(3),Constants.DEPA));
            //   malumot.add("<AREA>"+c.getString(1)+"- soni: "+c.getString(2)+" - minut: "+c.getString(3) );
        }
        malumot.add(new MyDataHisobot("<<<<< AREA >>>>> ",0,0,""));
        columns=new String[]{KEY_ROWID_LS,KEY_area,"count(" + KEY_area + ") AS key_shop_count","sum(" + KEY_minutes + ") AS key_minutss_sum" };
        c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"'" ,null,KEY_area,null,KEY_ROWID_LS);

        for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious())
        {
            malumot.add(new MyDataHisobot(c.getString(1),c.getInt(2),c.getInt(3),Constants.AREA));
         //   malumot.add("<AREA>"+c.getString(1)+"- soni: "+c.getString(2)+" - minut: "+c.getString(3) );
        }

        malumot.add(new MyDataHisobot("<<<MUAMMOLAR>>> ",0,0,""));
        columns=new String[]{KEY_ROWID_LS,KEY_description,"count(" + KEY_description + ") AS key_shop_count","sum(" + KEY_minutes + ") AS key_minutss_sum" };
        c=ourDatabase.query(DATABASE_TABLE,columns,KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"'" ,null,KEY_description,null,KEY_ROWID_LS);

        for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious())
        {
            malumot.add(new MyDataHisobot(c.getString(1),c.getInt(2),c.getInt(3),Constants.DESC));
        }
        c.close();
        return malumot;
    }

    public String getData() {

        String[] columns=new String[]{KEY_ROWID,KEY_EtScanner1,KEY_EtIzox,KEY_SCAN_DATA,KEY_SCAN_TIME};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,KEY_ROWID);

        String result="";

        int iRow=c.getColumnIndex(KEY_ROWID);
        int i_EtScanner1=c.getColumnIndex(KEY_EtScanner1);
        int iEtDetalNomeri=c.getColumnIndex(KEY_EtDetalNomeri);
        int iScan_Data=c.getColumnIndex(KEY_SCAN_DATA);
        int iScan_Time=c.getColumnIndex(KEY_SCAN_TIME);

        for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious())
        {
            result=result+c.getString(iRow)+" "+c.getString(i_EtScanner1)+" "+c.getString(iEtDetalNomeri)+" "+c.getString(iScan_Data)+" "+c.getString(iScan_Time)+"\n";
        }
        return result;
    }

    public String getName(long l) {
        String[] columns=new String[]{KEY_ROWID,KEY_EtScanner1,KEY_EtIzox,KEY_SCAN_DATA,KEY_SCAN_TIME};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,KEY_ROWID+"="+l,null,null,null,null);

        if(c!=null)
        {
            c.moveToFirst();
            String name=c.getString(1);
            return name;
        }
        return null;
    }

    public long getDataForListViewWithDateAlarmCount(String create_atFrom,String create_atTo) {
        String[] columns=new String[]{KEY_alarm};
        Cursor c=null;
            c=ourDatabase.query(DATABASE_TABLE,new String[]{"COUNT(" +KEY_alarm + ") AS ALARMCOUNT"},KEY_created_at +" BETWEEN '"+create_atFrom+"' AND '"+ create_atTo+"' AND "+KEY_alarm+"='1'" ,null,null,null,null);

        if(c!=null)
        {
            c.moveToFirst();
            Long IDCOUNT=c.getLong(0);
            if (IDCOUNT==0L){
                IDCOUNT=0L;
            }
            return IDCOUNT;
        }
        return 0L;
    }

    public Long getIDMax() {
        Cursor c=ourDatabase.query(DATABASE_TABLE,new String[]{"MAX(" +KEY_ROWID_LS + ") AS MAX"},null,null,null,null,null);

        if(c!=null)
        {
            c.moveToFirst();
            Long IDMAX=c.getLong(0);
            System.out.println("ID MAX "+IDMAX);
//
//            if (IDMAX==0L){
//            //IDMAX=130000L
//                IDMAX=114040L;
//            }
//            System.out.println("ID MAX "+IDMAX);
            return IDMAX;
        }
      //  return 130000L;
        return 114040L;
    }



    public Long getIDMaxHisobot() {

        Cursor c=ourDatabase.query(DATABASE_TABLE_HISOBOT,new String[]{"MAX(" +KEY_ROWID_LS + ") AS MAX"},null,null,null,null,null);

        if(c!=null)
        {
            c.moveToFirst();
            Long IDMAX=c.getLong(0);
            System.out.println("ID MAX "+IDMAX);
            return IDMAX;
        }
        //  return 130000L;
        return 114040L;

    }

    public String getHotness() {
        String[] columns=new String[]{KEY_ROWID,KEY_EtScanner1,KEY_EtDetalNomeri,KEY_SCAN_DATA,KEY_SCAN_TIME};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,KEY_ROWID+">0",null,null,null,KEY_ROWID);

        if(c!=null)
        {
            c.moveToLast();
            String hotness=c.getString(2);
            return hotness;
        }
        return "Yangi Kriting";
    }

    public String chekAlarm(String department) {

        String[] columns=new String[]{KEY_ROWID,KEY_shop,KEY_FILTER};
        Cursor c=ourDatabase.query(DATABASE_TABLE_SHOP,columns,KEY_shop+"='"+department.trim()+"'",null,null,null,KEY_ROWID);

        if(c.getCount()>0)
        {
            c.moveToLast();
            String filter=c.getString(2);
            return filter;
        }
        return "0";
    }

    public void updateEntry(long lRow, String mEtScanner1, String mEtDetalNomeri, String mScan_Data, String mScan_Time) {
        ContentValues cvUpdate=new ContentValues();
        cvUpdate.put(KEY_EtScanner1,mEtScanner1);
        cvUpdate.put(KEY_EtDetalNomeri,mEtDetalNomeri);
        cvUpdate.put(KEY_SCAN_DATA,mScan_Data);
        cvUpdate.put(KEY_SCAN_DATA,mScan_Time);
        ourDatabase.update(DATABASE_TABLE,cvUpdate,KEY_ROWID+"="+lRow,null);
    }

    public void updateEntryShop(String shop_title, String shop_filter) {
        ContentValues cvUpdate=new ContentValues();
        cvUpdate.put(KEY_FILTER,shop_filter);

        ourDatabase.update(DATABASE_TABLE_SHOP,cvUpdate,KEY_shop+"='"+shop_title+"'",null);
    }

    public void updateEntryShopAll(String all) {
        ContentValues cvUpdate=new ContentValues();
        cvUpdate.put(KEY_FILTER,all);
        ourDatabase.update(DATABASE_TABLE_SHOP,cvUpdate,null,null);
    }

    public int updateEntrySeksiyaMAX(int seksiyaMAX, String etIzox) {
        ContentValues cvUpdate=new ContentValues();
        cvUpdate.put(KEY_EtSeksiya,seksiyaMAX);
        cvUpdate.put(KEY_EtIzox,etIzox);
         int chek=ourDatabase.update(DATABASE_TABLE,cvUpdate,KEY_EtSeksiya+"=0",null);
        return chek;
    }

    public void deleteItemID(String deleteItemID) {
        ourDatabase.delete(DATABASE_TABLE,KEY_ROWID+"="+deleteItemID,null);
    }

    public void deleteEntry() {
        ourDatabase.delete(DATABASE_TABLE,KEY_ROWID+">0",null);
    }

    public void deleteEntryShop() {
        ourDatabase.delete(DATABASE_TABLE_SHOP,KEY_ROWID+">0",null);
    }

    public long createEntryLineStopShop(String shop, String filter) {
        ContentValues cv=new ContentValues();
        cv.put(KEY_shop,shop);
        cv.put(KEY_FILTER,filter);
        return ourDatabase.insert(DATABASE_TABLE_SHOP,null,cv);
    }

//    public long createEntryLineStopArea(String area, String filter) {
//        ContentValues cv=new ContentValues();
//        cv.put(KEY_area,area);
//        cv.put(KEY_FILTER,filter);
//        return ourDatabase.insert(DATABASE_TABLE_AREA,null,cv);
//    }

    public boolean getUserInfo(String userName, String password) {

        String[] columns = new String[]{KEY_USERNAME};
        Cursor c = ourDatabase.query(UserInfo_TABLE, columns, KEY_USERNAME + "='" + userName+"' AND "+KEY_PASSWORD+"='"+password+"'", null, null, null, null);
        if (c.getCount()!=0) {
            return true;
        }
        return false;
    }

    public Boolean setUserInfo(String userName,String password) {
        String[] columns = new String[]{KEY_USERNAME};
        Cursor c = ourDatabase.query(UserInfo_TABLE, columns, KEY_USERNAME + "='" + userName+"' AND "+KEY_PASSWORD+"='"+password+"'", null, null, null, null);
        if (c.getCount()!=0) {
            return true;
        } else {
            createEntryUserInfo(userName,password,"Asaka");
            return true;
        }
    }
    public long createEntryUserInfo(String userName,String password,String locatsiya) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_USERNAME, userName);
        cv.put(KEY_PASSWORD, password);
        cv.put(KEY_LOCATSIYA, locatsiya);
        return ourDatabase.insert(UserInfo_TABLE, null, cv);
    }

    public long createEntryLineStop(String idLS, String shop, String area, String department, String description,String created_at, String from, String to,int minutes,String alarm) {

        ContentValues cv=new ContentValues();
        cv.put(KEY_ROWID_LS,Integer.valueOf(idLS));
        cv.put(KEY_shop,shop);
        cv.put(KEY_area,area);
        cv.put(KEY_department,department);
        cv.put(KEY_description,description.replace("'",""));
        cv.put(KEY_created_at,created_at);
        cv.put(KEY_from,from);
        cv.put(KEY_to,to);
        cv.put(KEY_minutes,minutes);
        cv.put(KEY_alarm,alarm);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public long createEntryLineStopHisobot(String idLS, String shop, String area, String department, String description,String created_at, String from, String to,int minutes) {
        ContentValues cv=new ContentValues();
        cv.put(KEY_ROWID_LS,Integer.valueOf(idLS));
        cv.put(KEY_shop,shop);
        cv.put(KEY_area,area);
        cv.put(KEY_department,department);
        cv.put(KEY_description,description.replace("'",""));
        cv.put(KEY_created_at,created_at);
        cv.put(KEY_from,from);
        cv.put(KEY_to,to);
        cv.put(KEY_minutes,minutes);
        return ourDatabase.insert(DATABASE_TABLE_HISOBOT,null,cv);
    }

    public void deleteEntryHisobot() {
        ourDatabase.delete(DATABASE_TABLE_HISOBOT,KEY_ROWID+">0",null);
    }


    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context contex)
        {
            super(contex,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
try {


    db.execSQL("CREATE TABLE " +DATABASE_TABLE + " ("+
            KEY_ROWID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_ROWID_LS+" INTEGER NOT NULL UNIQUE, " +
            KEY_shop+" TEXT, " +
            KEY_area+" TEXT, " +
            KEY_department+" TEXT, " +
            KEY_description+" TEXT, " +
            KEY_created_at+" TEXT, " +
            KEY_from+" TEXT, " +
            KEY_to+" TEXT, " +
            KEY_minutes+" INTEGER , " +
            KEY_alarm+" TEXT, " +
            KEY_korildi+ " TEXT);"
    );

    db.execSQL("CREATE TABLE " +DATABASE_TABLE_HISOBOT + " ("+
            KEY_ROWID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_ROWID_LS+" INTEGER NOT NULL UNIQUE, " +
            KEY_shop+" TEXT, " +
            KEY_area+" TEXT, " +
            KEY_department+" TEXT, " +
            KEY_description+" TEXT, " +
            KEY_created_at+" TEXT, " +
            KEY_from+" TEXT, " +
            KEY_to+" TEXT, " +
            KEY_minutes+" INTEGER , " +
            KEY_alarm+" TEXT, " +
            KEY_korildi+ " TEXT);"
    );

    db.execSQL("CREATE TABLE " +DATABASE_TABLE_SHOP + " ("+
            KEY_ROWID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_shop+" TEXT NOT NULL UNIQUE , " +
            KEY_FILTER+" TEXT NOT NULL );"
    );

    db.execSQL("CREATE TABLE " + UserInfo_TABLE + " (" +
            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_USERNAME + " TEXT NOT NULL, " +
            KEY_PASSWORD + " TEXT NOT NULL, " +
            KEY_LOCATSIYA + " TEXT NOT NULL);"
    );



}catch (Exception e){
    System.out.println(">>>>>>>>>>"+e);
}
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_TABLE_SHOP);
            db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_TABLE_HISOBOT);
            db.execSQL("DROP TABLE IF EXISTS " + UserInfo_TABLE);

            onCreate(db);
        }
    }

    public HotOrNotLineStop(Context c)
    {
        ourContext=c;
    }

    public HotOrNotLineStop open() throws SQLException
    {
        ourHelper=new DbHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();

        return this;
    }
    public void close()
    {
        ourHelper.close();
    }
}
