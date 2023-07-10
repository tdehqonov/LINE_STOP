package com.example.DY_PDT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.DY_PDT.model.Item;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    List<Item> items;

    public MyAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false))  ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

  //      holder.title.setText(items.get(position).getTitle());
        holder.description.setText(items.get(position).getDescription());
        holder.shop.setText(items.get(position).getShop());
        holder.area.setText(items.get(position).getArea());
        holder.department.setText(items.get(position).getDepartment());
        holder.created_at.setText(items.get(position).getCreated_at());
        holder.time.setText(items.get(position).getFrom()+"-"+items.get(position).getTo()+"( minutes "+items.get(position).getMinutes()+")");
    }




    @Override
    public int getItemCount() {
        return items.size();
    }
}
