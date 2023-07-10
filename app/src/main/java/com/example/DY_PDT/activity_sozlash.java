package com.example.DY_PDT;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.example.DY_PDT.model.area;
import com.example.DY_PDT.model.shop;

import java.util.ArrayList;

public class activity_sozlash extends AppCompatActivity {

    ArrayList<shop> arShop=new ArrayList<shop>();
    MyAdapterShop myAdapterShop;

    ArrayList<area> arArea=new ArrayList<area>();
  //  MyAdapterArea myAdapterArea;
    ListView lvShop,lvArea;
    ImageButton ibQaytish;
    CheckBox chBFilterAll;
    Spinner IDspMuddat1;
    Spinner IDspNechiKunlik;
    Integer[] muddatArray;
    Integer[] NechKunlikArray;
    SharedPreferences prefs;
    int shpMuddat;
    int shpNechiKunlik;
    ToggleButton tgbOvozliyHabar;
    Boolean shpsoundONOFF=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sozlash);

        lvShop = findViewById(R.id.lvShop);
        chBFilterAll=findViewById(R.id.chBFilterAll);
        IDspMuddat1=findViewById(R.id.IDspMuddat1);
        ActionBarHelper.enableBackButton(this);

        tgbOvozliyHabar=findViewById(R.id.tgbOvozliyHabar);
        IDspMuddat1 = findViewById(R.id.IDspMuddat1);
        IDspNechiKunlik=findViewById(R.id.IDspNechiKunlik);

        muddatArray = new Integer[]{0,5, 10, 15, 20, 25, 30, 60, 120, 180, 240,300};
        NechKunlikArray = new Integer[]{1,2, 3, 4, 5, 6, 7, 8, 9, 10};

        ArrayAdapter<Integer> MuddatAdapter = new ArrayAdapter<Integer>(this,  android.R.layout.simple_expandable_list_item_1, muddatArray );
        IDspMuddat1.setAdapter(MuddatAdapter);

        ArrayAdapter<Integer> NechaKunlikAdapter = new ArrayAdapter<Integer>(this,  android.R.layout.simple_expandable_list_item_1, NechKunlikArray );
        IDspNechiKunlik.setAdapter(NechaKunlikAdapter);

        prefs = getSharedPreferences("MY_PREFS_NAME", Activity.MODE_PRIVATE);
        shpMuddat = prefs.getInt("shpMuddat", 10);
        shpNechiKunlik = prefs.getInt("shpNechaKunlik", 3);
        shpsoundONOFF = prefs.getBoolean("shpsoundONOFF", false);

        tgbOvozliyHabar.setChecked(shpsoundONOFF);

        int spPosition=MuddatAdapter.getPosition(shpMuddat);
        int spPositionNechaKunlik=NechaKunlikAdapter.getPosition(shpNechiKunlik);

        IDspMuddat1.setSelection(spPosition);
        IDspNechiKunlik.setSelection(spPositionNechaKunlik);

        ubdateRVWithData();


        tgbOvozliyHabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tgbOvozliyHabar.isChecked()){
                    shpsoundONOFF=true;
                }else {
                    shpsoundONOFF=false;
                }
                prefs = getSharedPreferences("MY_PREFS_NAME", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("shpsoundONOFF", shpsoundONOFF);
                editor.commit();
            }
        });

        IDspMuddat1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs = getSharedPreferences("MY_PREFS_NAME", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("shpMuddat", muddatArray[position]);
                editor.commit();
                shpMuddat=muddatArray[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        IDspNechiKunlik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs = getSharedPreferences("MY_PREFS_NAME", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("shpNechaKunlik",NechKunlikArray[position]);
                editor.putLong("shpIDMAX", 0L);

                System.out.println("##############################>>>>>");
                editor.commit();
                shpNechiKunlik=NechKunlikArray[position];

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//
//        IDspNechiKunlik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                prefs = getSharedPreferences("MY_PREFS_NAME", Activity.MODE_PRIVATE);
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.putInt("shpNechaKunlik",NechKunlikArray[position]);
//                editor.putLong("shpIDMAX", 0L);
//
//                System.out.println("##############################>>>>>");
//                editor.commit();
//                shpNechiKunlik=NechKunlikArray[position];
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
        chBFilterAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               String all="0";

                if (isChecked==true){
                    all="1";
                }else {
                    all="0";
                }
                HotOrNotLineStop malumotShop = new HotOrNotLineStop(activity_sozlash.this);
                malumotShop.open();
                malumotShop.updateEntryShopAll(all);
                malumotShop.close();
                ubdateRVWithData();
            }
        });

//        ibQaytish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    finish();
//      //              System.exit(0);
//                } catch (Throwable e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
    private void ubdateRVWithData() {
        HotOrNotLineStop malumotShop = new HotOrNotLineStop(activity_sozlash.this);
        malumotShop.open();
        arShop=malumotShop.getDataForListViewShop();
        malumotShop.close();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAdapterShop=new MyAdapterShop(activity_sozlash.this, arShop);
                lvShop.setAdapter(myAdapterShop);
            }
        });
    }


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

//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//    OkHttpClient client = new OkHttpClient();
//    Call getLineStopArea(String url, Callback callback) {
//        Request request = new Request.Builder()
//                .url(url)
//                .get()
//                .header("Authorization", Credentials.basic("admin", "admin"))
//                .build();
//        Call call = client.newCall(request);
//        call.enqueue(callback);
//        return call;
//    }
}