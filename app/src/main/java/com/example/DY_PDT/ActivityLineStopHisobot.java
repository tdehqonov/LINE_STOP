package com.example.DY_PDT;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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

public class ActivityLineStopHisobot extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ArrayList arHisobot=new ArrayList<String>();
    MyAdapterHisobot lvarrayAdapter;
    String IDlvHisobotFilter=Constants.HAMMASI;

    List<Item> items =new ArrayList<Item>();
    MyAdapter myAdapter;
    RecyclerView rvHisobot;
    int day=0;
    int month = 0;
    int year = 0;

    String Day1 = "";
    String Month1 = "";
    String Year1 = "";

    String Day2 = "";
    String Month2 = "";
    String Year2 = "";

    Button etDate1;
    Button etDate2;
    String et = "etDate1";
    ListView IDlvHisobot;
    Boolean FromServer=false;
    Long IDMAX=0L;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_stop_hisobot);
        intView();
        pickDate();

        etDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate1("etDate1");
            }
        });

        etDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate1("etDate2");
            }
        });

        final View[] updateview = {null};// above oncreate method
        IDlvHisobot.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        IDlvHisobot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              TextView tvFilterHisobot=(TextView) view.findViewById(R.id.tvFilterHisobot);
              TextView tvShop=(TextView) view.findViewById(R.id.tvShop);


                if (updateview[0] != null)

                    updateview[0].setBackgroundColor(Color.parseColor("#DBF4F4"));
                    updateview[0] = view;

                view.setBackgroundColor(Color.LTGRAY);

                IDlvHisobotFilter=tvFilterHisobot.getText().toString()+tvShop.getText().toString();
                if(!tvFilterHisobot.getText().toString().isEmpty()) {

 //                   if (FromServer==false){
                        ubdateRVWithData1();
//                    }else {
//                        ubdateRVWithData1Hisobot();
//                    }

                }
            }
        });

   //     if (FromServer==false){
            ubdateRVWithData();
//        }else {
//            ubdateRVWithDataHisobot();
//        }

        myAdapter=new MyAdapter(ActivityLineStopHisobot.this, items);
        rvHisobot.setLayoutManager(new LinearLayoutManager(this));
        rvHisobot.setAdapter(myAdapter);
    }

    private void ubdateRVWithData() {

        HotOrNotLineStop malumot = new HotOrNotLineStop(ActivityLineStopHisobot.this);
        malumot.open();
        items = malumot.getDataForListViewWithDate(Year1+"-"+Month1+"-"+Day1,Year2+"-"+Month2+"-"+Day2,IDlvHisobotFilter,malumot.DATABASE_TABLE);
        arHisobot=malumot.getDataForListViewHisobot(Year1+"-"+Month1+"-"+Day1,Year2+"-"+Month2+"-"+Day2,malumot.DATABASE_TABLE);
        malumot.close();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAdapter=new MyAdapter(ActivityLineStopHisobot.this, items);
                rvHisobot.setAdapter(myAdapter);
                lvarrayAdapter=new MyAdapterHisobot(ActivityLineStopHisobot.this,arHisobot);
                IDlvHisobot.setAdapter(lvarrayAdapter);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    private void ubdateRVWithDataHisobot() {
        HotOrNotLineStop malumot = new HotOrNotLineStop(ActivityLineStopHisobot.this);
        malumot.open();
        items = malumot.getDataForListViewWithDate(Year1+"-"+Month1+"-"+Day1,Year2+"-"+Month2+"-"+Day2,IDlvHisobotFilter,malumot.DATABASE_TABLE_HISOBOT);
        arHisobot=malumot.getDataForListViewHisobot(Year1+"-"+Month1+"-"+Day1,Year2+"-"+Month2+"-"+Day2,malumot.DATABASE_TABLE_HISOBOT);
        malumot.close();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAdapter=new MyAdapter(ActivityLineStopHisobot.this, items);
                rvHisobot.setAdapter(myAdapter);
                lvarrayAdapter=new MyAdapterHisobot(ActivityLineStopHisobot.this,arHisobot);
                IDlvHisobot.setAdapter(lvarrayAdapter);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    private void pickDate1(String etin) {
        et=etin;
        new DatePickerDialog(this,ActivityLineStopHisobot.this, year, month, day).show();
        FromServer=true;
//        HotOrNotLineStop entry1 = new HotOrNotLineStop(ActivityLineStopHisobot.this);
//        entry1.open();
//        entry1.deleteEntryHisobot();
//        entry1.close();
//    //    FromServer();

//        ubdateRVWithData();
    }

 //   activityLineStop activityLineStop=new activityLineStop();

    private void FromServer() {

        JSONObject jsonData=new JSONObject();

        try {
            jsonData.put("data1",Year1+"-"+Month1+"-"+Day1);
            jsonData.put("data2",Year2+"-"+Month2+"-"+Day2);
            jsonData.put("IDMAX",IDMAX);

        } catch (JSONException e) {
            e.printStackTrace();
        }

       getLineStopData(Constants.URLSEND_LINE_STOPDATA,jsonData.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("actionBarMessage=\"Aloqa yo'q\"");
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
                                HotOrNotLineStop entry1 = new HotOrNotLineStop(ActivityLineStopHisobot.this);
                                entry1.open();

                                //      entry1.deleteEntry();
                                for (int i = 0; i < jsonArray.length(); i++) {
//                                        String title="⚠️  Warning: LINE STOP"; //     jsonArray.getJSONObject(i).optString("title");
                                    String idLS = jsonArray.getJSONObject(i).optString("id");
                                    String shop = jsonArray.getJSONObject(i).optString("shop");
                                    String description = "ℹ️  " + jsonArray.getJSONObject(i).optString("description");
                                    String area = jsonArray.getJSONObject(i).optString("area").trim();
                                    String department = jsonArray.getJSONObject(i).optString("department");
                                    String created_at = jsonArray.getJSONObject(i).optString("created_at").substring(0, 10);

                                    String from = jsonArray.getJSONObject(i).optString("from");
                                    from = from.replace(" UTC", "");
                                    Date dateFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(from);
                                    from = new SimpleDateFormat("HH:mm").format(dateFrom);

                                    String to = jsonArray.getJSONObject(i).optString("to");
                                    to = to.replace(" UTC", "");
                                    Date dateTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(to);
                                    to = new SimpleDateFormat("HH:mm").format(dateTo);

                                    LocalTime timeFrom = LocalTime.parse(from);
                                    LocalTime timeTo = LocalTime.parse(to);
                                    int minutes = (int) ChronoUnit.MINUTES.between(timeFrom, timeTo);
                                    entry1.createEntryLineStopHisobot(idLS, shop, area, department.trim(), description, created_at, from, to, minutes);
                                }
                                IDMAX=entry1.getIDMaxHisobot();
                                entry1.close();
                                FromServer();
                            }else {
                                ubdateRVWithDataHisobot();
                            }
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

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





    @RequiresApi(Build.VERSION_CODES.N)

    private void pickDate() {
        getDate();
        if (day < 10) {
            Day1 = "0"+day;
            Day2 = "0"+day;
        } else {
            Day1 = ""+day;
            Day2 = ""+day;
        }

        if (month < 10) {
            Month1 = "0"+(month + 1);
            Month2 = "0"+(month + 1);

        } else {
            Month1 = ""+(month + 1);
            Month2 = ""+(month + 1);
        }
        Year1 = ""+year;
        Year2 = ""+year;

        etDate1.setText(Day1+"."+Month1+"."+Year1);
        etDate2.setText(Day2+"."+Month2+"."+Year2);
    }

    private void getDate() {
        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

    }

    private void intView() {
        rvHisobot=findViewById(R.id.rvHisobot);
        etDate1=findViewById(R.id.etDate1H);
        etDate2=findViewById(R.id.etDate2H);
        IDlvHisobot=findViewById(R.id.lvHisobot);
        ActionBarHelper.enableBackButton(this);
    }


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
        ubdateRVWithData();
    }

//    private void ubdateRVWithData1() {
//        HotOrNotLineStop malumot = new HotOrNotLineStop(activityLineStop.this);
//        malumot.open();
//        items = malumot.getDataForListViewWithDate(Year1+"-"+Month1+"-"+Day1,Year2+"-"+Month2+"-"+Day2,IDlvHisobotFilter);
//        //       arHisobot=malumot.getDataForListViewHisobot(Year1+"-"+Month1+"-"+Day1,Year2+"-"+Month2+"-"+Day2);
//        malumot.close();
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                myAdapter=new MyAdapter(activityLineStop.this, items);
//                recyclerView.setAdapter(myAdapter);
//                //               lvarrayAdapter=new ArrayAdapter<Integer>(activityLineStop.this, android.R.layout.simple_list_item_1,arHisobot);
//                //               IDlvHisobot.setAdapter(lvarrayAdapter);
//                myAdapter.notifyDataSetChanged();
//            }
//        });
//
//    }


    private void ubdateRVWithData1() {
        HotOrNotLineStop malumot = new HotOrNotLineStop(ActivityLineStopHisobot.this);
        malumot.open();
        items.clear();
        items = malumot.getDataForListViewWithDate(Year1+"-"+Month1+"-"+Day1,Year2+"-"+Month2+"-"+Day2,IDlvHisobotFilter,malumot.DATABASE_TABLE);
        malumot.close();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAdapter=new MyAdapter(ActivityLineStopHisobot.this, items);
                rvHisobot.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    private void ubdateRVWithData1Hisobot() {
        HotOrNotLineStop malumot = new HotOrNotLineStop(ActivityLineStopHisobot.this);
        malumot.open();
        items.clear();
        items = malumot.getDataForListViewWithDate(Year1+"-"+Month1+"-"+Day1,Year2+"-"+Month2+"-"+Day2,IDlvHisobotFilter,malumot.DATABASE_TABLE);
        malumot.close();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAdapter=new MyAdapter(ActivityLineStopHisobot.this, items);
                rvHisobot.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }
        });
    }


//    private void ubdateRVWithData2(int position) {
//        HotOrNotLineStop malumot = new HotOrNotLineStop(ActivityLineStopHisobot.this);
//        malumot.open();
//        items = malumot.getDataForListViewWithDate(Year1+"-"+Month1+"-"+Day1,Year2+"-"+Month2+"-"+Day2,IDlvHisobotFilter);
//        arHisobot=malumot.getDataForListViewHisobot(Year1+"-"+Month1+"-"+Day1,Year2+"-"+Month2+"-"+Day2);
//        malumot.close();
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                myAdapter=new MyAdapter(ActivityLineStopHisobot.this, items);
//                rvHisobot.setAdapter(myAdapter);
//                myAdapter.notifyDataSetChanged();
//
//                lvarrayAdapter=new MyAdapterHisobot(ActivityLineStopHisobot.this, arHisobot);
//                IDlvHisobot.setAdapter(lvarrayAdapter);
//                lvarrayAdapter.notifyDataSetChanged();
//                IDlvHisobot.setSelection(position);
//
//            }
//        });
//    }

    public static final class ActionBarHelper {
        public static void enableBackButton(AppCompatActivity context) {
            if(context == null) return;

            ActionBar actionBar = context.getSupportActionBar();
            if (actionBar == null) return;
            actionBar.setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow =context.getResources().getDrawable(R.drawable.ic_qaytish);
            upArrow.setColorFilter(Color.parseColor("#0C0C0C"), PorterDuff.Mode.SRC_ATOP);
            context.getSupportActionBar().setHomeAsUpIndicator(upArrow);
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }






    }
}