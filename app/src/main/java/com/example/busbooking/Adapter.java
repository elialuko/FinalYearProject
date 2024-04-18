package com.example.busbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
    Context context;
    List<Bookings> list;

    public Adapter(Context context, List<Bookings> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.entry2,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bookings bookings = list.get(position);
        holder.date.setText(bookings.getDate());
        holder.price.setText(String.valueOf(bookings.getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date, price;
        public  MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textDate);
            price = itemView.findViewById(R.id.textPrice);

        }

    }
}
