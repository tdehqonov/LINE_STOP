package com.example.DY_PDT;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView title,description,shop,area,department,created_at,time;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageView);
  //      title=itemView.findViewById(R.id.title);
        shop=itemView.findViewById(R.id.shop);
        area=itemView.findViewById(R.id.area);
        department=itemView.findViewById(R.id.department);
        description=itemView.findViewById(R.id.description);
        created_at=itemView.findViewById(R.id.created_at);
        time=itemView.findViewById(R.id.time);
    }
}
