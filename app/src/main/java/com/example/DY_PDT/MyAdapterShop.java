package com.example.DY_PDT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.DY_PDT.model.shop;

import java.util.ArrayList;

public class MyAdapterShop extends BaseAdapter {

    private Context context;
    private ArrayList<shop> arrayListShop;
    private TextView tvShopTitle;
    private CheckBox chBFilter;

    public MyAdapterShop(Context context, ArrayList<shop> arrayListShop) {
        this.context = context;
        this.arrayListShop = arrayListShop;
    }

//    public MyAdapterShop(activity_sozlash context, ArrayList<shop> arShop) {
//    }

    @Override
    public int getCount() {
        return arrayListShop.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.item_shop,parent,false);
        tvShopTitle=convertView.findViewById(R.id.tvShopTitle);
        chBFilter=convertView.findViewById(R.id.chBFilter);
        tvShopTitle.setText(arrayListShop.get(position).getShop_title());

        if(arrayListShop.get(position).getShop_filter().equals("1"))
            chBFilter.setChecked(true);
        else
            chBFilter.setChecked(false);


        chBFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox temp=(CheckBox)v;
                if(temp.isChecked()){
                    arrayListShop.get(position).setShop_filter("1");
                }
                else{
                    arrayListShop.get(position).setShop_filter("0");
                }

                HotOrNotLineStop malumotShop = new HotOrNotLineStop(context);
                malumotShop.open();
                malumotShop.updateEntryShop(arrayListShop.get(position).getShop_title(),arrayListShop.get(position).getShop_filter());
                malumotShop.close();
            }
        });
        return convertView;
    }
}
