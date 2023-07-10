package com.example.DY_PDT;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.DY_PDT.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class activityLineStop extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button btnReadLineStop;
    Button btnAlarm;
    RecyclerView recyclerView;
  //  ListView IDlvHisobot;
    List<Item> items =new ArrayList<Item>();
    MyAdapter myAdapter;
 //   ArrayList arHisobot=new ArrayList<String>();
  //  ArrayAdapter<Integer> lvarrayAdapter;

    String IDlvHisobotFilter=Constants.HAMMASI;

    long IDMAX;

    long IDNEW;
    int day=0;
    int month = 0;
    int year = 0;

    int day1=0;
    int month1 = 0;
    int year1 = 0;

    long alarmCount=0L;
//
    String Day1 = "";
    String Month1 = "";
    String Year1 = "";

    String Day2 = "";
    String Month2 = "";
    String Year2 = "";

    Button etDate1;
    Button etDate2;
    String et = "etDate1";
    Spinner IDspMuddat;
    Integer[] muddatArray;

    SharedPreferences prefs;
    int shpMuddat;
    int shpNechiKunlik=3;
    Long shpIDMAX;

    CountDownTimer countDownTimer;
    ActionBar actionBar;
    String actionBarMessage="LS";
    Thread thread;
    Boolean shpsoundONOFF;

    CheckBox chbFilter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filter, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent launchNewIntent;
        switch(item.getItemId()){

            case R.id.IdFilter:
               countDownTimer.cancel();
                launchNewIntent = new Intent(activityLineStop.this,activity_sozlash.class);
                startActivity(launchNewIntent);
                break;
            case R.id.IdHisobot:
                countDownTimer.cancel();
                launchNewIntent = new Intent(activityLineStop.this,ActivityLineStopHisobot.class);
                startActivity(launchNewIntent);
                break;
            case R.id.home:
                finish();
            break;

        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_stop);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_qaytish);
        upArrow.setColorFilter(Color.parseColor("#0C0C0C"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        ////////////
//        HotOrNotLineStop entry1 = new HotOrNotLineStop(activityLineStop.this);
//        entry1.open();
//        entry1.deleteEntry();
//        //IDMAX=entry1.getIDMax();
//        //    btnReadLineStop.setText("LS:"+IDMAX);
//        //     btnReadLineStop.setEnabled(false);
//        entry1.close();
//        //////////////
        initView();
        pickDate();
        btnReadLineStop.callOnClick();
        setCountDownTime(shpMuddat);

//        IDspMuddat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                prefs = getSharedPreferences("MY_PREFS_NAME", Activity.MODE_PRIVATE);
//              //  shpMuddat = prefs.getInt("shpMuddat", 1);
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.putInt("shpMuddat", muddatArray[position]);
//                editor.commit();
//                shpMuddat=muddatArray[position];
//                countDownTimer.cancel();
//                if(shpMuddat!=0)
//                setCountDownTime(shpMuddat);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmCount=0;
                btnAlarm.setText("0");
            }
        });

//        etDate1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                countDownTimer.cancel();
//                pickDate1("etDate1");
//            }
//        });
//
//        etDate2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                countDownTimer.cancel();
//                pickDate1("etDate2");
//            }
//        });

        chbFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubdateRVWithDataFilter();
            }
        });

       // ubdateRVWithData();
          ubdateRVWithDataFilter();

        myAdapter=new MyAdapter(activityLineStop.this, items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

//        lvarrayAdapter=new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1,arHisobot);
//        IDlvHisobot.setAdapter(lvarrayAdapter);


//        IDlvHisobot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//              IDlvHisobotFilter=IDlvHisobot.getItemAtPosition(position).toString();
//                ubdateRVWithData1();
//
//                if(IDlvHisobotFilter.contains(Constants.HAMMASI)) {
//                    actionBarMessage = "HAMMASI";
//                }else
//                actionBarMessage=IDlvHisobotFilter.substring(6,IDlvHisobotFilter.indexOf("- soni:"));
//            }
//        });

        btnReadLineStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             new Thread(new Runnable() {
                    public void run() {
                        countDownTimer.cancel();
                    }
                });

                pickDate();
                HotOrNotLineStop entry1 = new HotOrNotLineStop(activityLineStop.this);
                entry1.open();

                if (IDMAX==0L){
                    entry1.deleteEntry();
                }else

                IDMAX=entry1.getIDMax();

                entry1.close();
                JSONObject jsonData=new JSONObject();


                try {
                    jsonData.put("data1",Year1+"-"+Month1+"-"+Day1);
                    jsonData.put("data2",Year2+"-"+Month2+"-"+Day2);
                    jsonData.put("IDMAX",IDMAX);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                getLineStop(Constants.URLSEND_LINE_STOP+"/"+IDMAX, new Callback() {
                   getLineStopData(Constants.URLSEND_LINE_STOPDATA,jsonData.toString(), new Callback() {

                        @Override
                    public void onFailure(Call call, IOException e) {

                        actionBarMessage="Aloqa yo'q";
//                        thread.start();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                countDownTimer.start();
                            }
                        });
                    }

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                String responseStr = response.body().string();
                                System.out.println("responseStr :" + responseStr);

                                try {

                                    if (!responseStr.isEmpty())
                                    {
  //                                      Toast.makeText(activityLineStop.this, "Malumot yuklanmoqda!!!", Toast.LENGTH_SHORT).show();
                                    JSONArray jsonArray = new JSONArray(responseStr);
                                    HotOrNotLineStop entry1 = new HotOrNotLineStop(activityLineStop.this);
                                    entry1.open();
                                        String idLS="";
                                        //      entry1.deleteEntry();
                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                      String title="⚠️  Warning: LINE STOP"; //     jsonArray.getJSONObject(i).optString("title");
                                        idLS = jsonArray.getJSONObject(i).optString("id");
                                        String shop = jsonArray.getJSONObject(i).optString("shop");
                                        String description = "ℹ️  " + jsonArray.getJSONObject(i).optString("description");
                                        String area = jsonArray.getJSONObject(i).optString("area").trim();
                                        String department = jsonArray.getJSONObject(i).optString("department");
                                        String created_at = jsonArray.getJSONObject(i).optString("created_at").substring(0, 10);

                                        String from = jsonArray.getJSONObject(i).optString("from");
                                        from = from.replace(" UTC", "");

                                        Date dateFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(from);
                                        from = new SimpleDateFormat("HH:mm").format(dateFrom);
//
//                                        String fromSoat= new SimpleDateFormat("HH").format(from);
//                                        String fromMinut= new SimpleDateFormat("mm").format(from);
//
//                                        int fromSoatMinut=Integer.parseInt(fromSoat)*60+Integer.parseInt(fromMinut);


                                        String to = jsonArray.getJSONObject(i).optString("to");
                                        to = to.replace(" UTC", "");
                                        Date dateTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(to);

                                        to = new SimpleDateFormat("HH:mm").format(dateTo);


                                  //      String toSoat= new SimpleDateFormat("HH").format(dateTo);
                                  //      String toMinut= new SimpleDateFormat("mm").format(dateTo);

                                  //      int toSoatMinut=Integer.parseInt(toSoat)*60+Integer.parseInt(toMinut);

                                        LocalTime timeFrom = LocalTime.parse(from);
                                        LocalTime timeTo = LocalTime.parse(to);

                                        int soat;
                                        int minut;


                                        if (timeFrom.isAfter(timeTo)) {
                                            soat=(int) (ChronoUnit.HOURS.between(timeTo, timeFrom) % 24);
                                            minut=(int) (ChronoUnit.MINUTES.between(timeTo, timeFrom) % 60);
                                            minut=soat*60+minut;
                                            minut=1440-minut;

                                        } else {
                                            soat=(int) (ChronoUnit.HOURS.between(timeFrom, timeTo) % 24);
                                            minut=(int) (ChronoUnit.MINUTES.between(timeFrom, timeTo) % 60);
                                            minut=soat*60+minut;
                                        }


                                        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHH>>>>>>"+IDMAX);


                                      if(  chekAlarm(department).equals("1")){
                                          entry1.createEntryLineStop(idLS, shop, area, department.trim(), description, created_at, from, to, minut,Constants.ALARM_1);
                                          if(IDMAX!=0L)
                                          alarmCount++;
                                      }else
                                      {
                                          entry1.createEntryLineStop(idLS, shop, area, department.trim(), description, created_at, from, to, minut,Constants.ALARM_0);
                                      }
                                    }
                                     //   IDMAX= entry1.getIDMax();
                                      if(!idLS.isEmpty()) {
                                          prefs = getSharedPreferences("MY_PREFS_NAME", Activity.MODE_PRIVATE);
                                          SharedPreferences.Editor editor = prefs.edit();
                                          IDMAX = Long.parseLong(idLS);
                                          editor.putLong("shpIDMAX", IDMAX);
                                    //      editor.putString("shpDAY2", Day2);
                                          editor.commit();
                                      }
                                        entry1.close();
                                }else {

                                        HotOrNotLineStop entry1 = new HotOrNotLineStop(activityLineStop.this);
                                        entry1.open();
                                        items = entry1.getDataForListViewWithDateFilter2(Year1 + "-" + Month1 + "-" + Day1, Year2 + "-" + Month2 + "-" + Day2,chbFilter.isChecked());
                                        // = entry1.getDataForListViewWithDateAlarmCount(Year1 + "-" + Month1 + "-" + Day1, Year2 + "-" + Month2 + "-" + Day2);

                                        if(shpsoundONOFF==true && IDMAX>0L && alarmCount>0) {
                                            btnAlarm.setText("" + alarmCount);
                                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), notification);
                                            mp.start();
                                        }

                                        entry1.close();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                myAdapter = new MyAdapter(activityLineStop.this, items);
                                                recyclerView.setAdapter(myAdapter);
                                                myAdapter.notifyDataSetChanged();
                                                recyclerView.scrollToPosition(0);
                                            }
                                        });
                                    }
                                } catch (JSONException | ParseException e) {
                                    e.printStackTrace();
                                }

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
//                                                btnReadLineStop.setEnabled(true);
                                                countDownTimer.start();
                                            }
                                        });
                            }
                        }
                    }
                });

            }
        });
    }

    private void ubdateRVWithDataFilter() {
        HotOrNotLineStop malumot = new HotOrNotLineStop(activityLineStop.this);
        malumot.open();
        items = malumot.getDataForListViewWithDateFilter2(Year1 + "-" + Month1 + "-" + Day1, Year2 + "-" + Month2 + "-" + Day2,chbFilter.isChecked());
        //      items = malumot.getDataForListViewWithDateFilter(chbFilter.isChecked());
        malumot.close();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAdapter=new MyAdapter(activityLineStop.this, items);
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    private String chekAlarm(String department) {
     HotOrNotLineStop malumotShop=new HotOrNotLineStop(activityLineStop.this);
     malumotShop.open();
     String filter=  malumotShop.chekAlarm(department);
     malumotShop.close();
     return filter;
    }

    @Override
    protected void onRestart() {
        ubdateRVWithDataFilter();
    //    ubdateRVWithData();
 //       thread.start();
        countDownTimer.start();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
   //     thread.stop();
       countDownTimer.cancel();
    }

    @Override
    protected void onStart() {
        super.onStart();
   //     ubdateRVWithDataFilter();
   //     ubdateRVWithData();
    }

    private void setCountDownTime(int shpMuddat) {

            countDownTimer = new CountDownTimer(shpMuddat *1000, 1000) {
                @Override public void onTick(long millisUntilFinished) {
                    //getSupportActionBar()
                          getSupportActionBar().setTitle(actionBarMessage+"    "+millisUntilFinished/1000  );
                    btnAlarm.setText(""+alarmCount);
                }
                @Override public void onFinish() {

//                    actionBar.setTitle("" );
                    btnReadLineStop.callOnClick();
                }
            };
            thread= new Thread(new Runnable() {
                public void run() {
                    countDownTimer.start();
                }
            });
            thread.start();
    }

//    private void pickDate1(String etin) {
//        et=etin;
////        IDMAX=0L;
//         new DatePickerDialog(this,activityLineStop.this, year, month, day).show();
// //       btnReadLineStop.callOnClick();
//    }

    @RequiresApi(Build.VERSION_CODES.N)
    private void pickDate() {
        getDate();

        if (day1 < 10) {
            Day1 = "0"+day1;
        } else {
            Day1 = ""+day1;
        }

        if (month1 < 10) {
            Month1 = "0"+(month1);
        } else {
            Month1 = ""+(month1);
        }


        if (day < 10) {
 //           Day1 = "0"+day;
            Day2 = "0"+day;
        } else {
//            Day1 = "0"+day;
            Day2 = ""+day;
        }

        if (month < 10) {
 //           Month1 = "0"+(month + 1);
            Month2 = "0"+(month + 1);
        } else {
//            Month1 = ""+(month + 1);
            Month2 = ""+(month + 1);
        }

        Year1 = ""+year1;
        Year2 = ""+year;
        etDate1.setText(Day1+"."+Month1+"."+Year1);
        etDate2.setText(Day2+"."+Month2+"."+Year2);
    }

    private void getDate() {
        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        cal.add(Calendar.DATE, -shpNechiKunlik);
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
        String result = s.format(new Date(cal.getTimeInMillis()));
   //     System.out.println("rEsault:::::" + result+"sdfsdfsdfsdf"+result.substring(4,6));
        year1=Integer.parseInt(result.substring(0,4));
        month1=Integer.parseInt(result.substring(4,6));
        day1=Integer.parseInt(result.substring(6));

    }



//    private void ubdateRVWithData() {
//
//        HotOrNotLineStop malumot = new HotOrNotLineStop(activityLineStop.this);
//        malumot.open();
//
//        if (chbFilter.isChecked()){
//            IDlvHisobotFilter=Constants.FILTER;
//        }else {
//            IDlvHisobotFilter=Constants.HAMMASI;
//        }
//        items = malumot.getDataForListViewWithDateFilter(chbFilter.isChecked());
//        malumot.close();
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                myAdapter=new MyAdapter(activityLineStop.this, items);
//                recyclerView.setAdapter(myAdapter);
//                myAdapter.notifyDataSetChanged();
//            }
//        });
//
//    }

//    private void ubdateRVWithData1() {
//        HotOrNotLineStop malumot = new HotOrNotLineStop(activityLineStop.this);
//        malumot.open();
//
//        if (chbFilter.isChecked()){
//            IDlvHisobotFilter=Constants.FILTER;
//        }else {
//            IDlvHisobotFilter=Constants.HAMMASI;
//        }
//        items = malumot.getDataForListViewWithDateFilter2(Year1 + "-" + Month1 + "-" + Day1, Year2 + "-" + Month2 + "-" + Day2,chbFilter.isChecked());
//        malumot.close();
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                myAdapter=new MyAdapter(activityLineStop.this, items);
//                recyclerView.setAdapter(myAdapter);
//                myAdapter.notifyDataSetChanged();
//            }
//        });
//
//    }

    private void initView() {

        btnReadLineStop=findViewById(R.id.btnReadLineStop);
        btnAlarm=findViewById(R.id.btnAlarm);
        recyclerView=findViewById(R.id.recycleview);
        etDate1=findViewById(R.id.etDate1);
        etDate2=findViewById(R.id.etDate2);
        chbFilter=findViewById(R.id.chbFilter);
        //  IDlvHisobot=findViewById(R.id.IDlvHisobot);
        IDspMuddat = findViewById(R.id.IDspMuddat);
        muddatArray = new Integer[]{0,5, 10, 15, 20, 25, 30, 60, 120, 180, 240,300};

        ArrayAdapter<Integer> MuddatAdapter = new ArrayAdapter<Integer>(this,  android.R.layout.simple_expandable_list_item_1, muddatArray );
        IDspMuddat.setAdapter(MuddatAdapter);

        prefs = getSharedPreferences("MY_PREFS_NAME", Activity.MODE_PRIVATE);
        shpMuddat = prefs.getInt("shpMuddat", 10);
        shpNechiKunlik=prefs.getInt("shpNechaKunlik", 3);
        shpIDMAX=prefs.getLong("shpIDMAX", 0L);
        shpsoundONOFF = prefs.getBoolean("shpsoundONOFF", false);


        int spPosition=MuddatAdapter.getPosition(shpMuddat);
        IDspMuddat.setSelection(spPosition);
        IDMAX=shpIDMAX;

        System.out.println("<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>"+IDMAX);
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    Call getLineStopData(String url,String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                //.get(body)
                .header("Authorization", Credentials.basic("admin", "admin"))
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

//
//    Call getLineStop(String url, Callback callback) {
//        Request request = new Request.Builder()
//                .url(url)
//                .get()
//                .header("Authorization", Credentials.basic("admin", "admin"))
//                .build();
//        Call call = client.newCall(request);
//        call.enqueue(callback);
//        return call;
//    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if (et.equals("etDate1")) {
            if (dayOfMonth < 10) {
                Day1 = "0" + dayOfMonth;
            } else
                Day1 = ""+dayOfMonth;


            if (month + 1 < 10) {
                Month1 = "0" + (month + 1);
            } else
                Month1 = ""+(month + 1);

            Year1 = ""+year;

            etDate1.setText(Day1+"."+Month1+"."+Year1);
        } else {
            if (dayOfMonth < 10) {
                Day2 = "0" + dayOfMonth;
            } else
                Day2 = ""+dayOfMonth;


            if (month + 1 < 10) {
                Month2 = "0" + (month + 1);
            } else
                Month2 = ""+(month + 1);

            Year2 = ""+year;
            etDate2.setText(Day2+"."+Month2+"."+Year2);
        }
        ubdateRVWithDataFilter();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}


