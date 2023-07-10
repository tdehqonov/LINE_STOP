package com.example.DY_PDT;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.DY_PDT.model.MyDataHisobot;

import java.util.ArrayList;

public class MyAdapterHisobot extends BaseAdapter {
    private Context context;
    private ArrayList<MyDataHisobot> arrayList;
    private TextView tvShop, tvSoni, tvMinut,tvFilterHisobot;

    public MyAdapterHisobot(Context context, ArrayList<MyDataHisobot> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
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
        convertView = LayoutInflater.from(context).inflate(R.layout.row_hisobot, parent, false);
        tvShop = convertView.findViewById(R.id.tvShop);
        tvSoni = convertView.findViewById(R.id.tvSoni);
        tvMinut = convertView.findViewById(R.id.tvMinut);
        tvFilterHisobot=convertView.findViewById(R.id.tvFilterHisobot);

        tvFilterHisobot.setText(arrayList.get(position).getFilterHisobot());
        if(arrayList.get(position).getSoni()>0) {

            tvShop.setTextColor(Color.BLACK);
            if(arrayList.get(position).getShop().contains("HAMMASI"))
            {
                tvSoni.setText("" + arrayList.get(position).getSoni());
                tvMinut.setText("" + arrayList.get(position).getMinut()
);

            }else {
                tvSoni.setText("" + arrayList.get(position).getSoni());
                tvMinut.setText("" + arrayList.get(position).getMinut());
            }
            tvShop.setText(arrayList.get(position).getShop());
        } else  {
            tvShop.setTextColor(Color.parseColor("#0066a0"));
            tvShop.setText(arrayList.get(position).getShop());
        }

        return convertView;
    }
}
