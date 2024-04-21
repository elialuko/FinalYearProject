package com.example.busbooking.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busbooking.Bookings;
import com.example.busbooking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    List<Bookings>list;

    public MyAdapter(Context context, List<Bookings> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.entry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bookings bookings = list.get(position);
        holder.name.setText(bookings.getName());
        holder.email.setText(bookings.getEmail());
        holder.from.setText(bookings.getFrom());
        holder.to.setText(bookings.getTo());

        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog(bookings);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, email, from, to;
        CardView cardView;
        public  MyViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.textName);
            email = itemView.findViewById(R.id.textEmail);
            from = itemView.findViewById(R.id.textFrom);
            to = itemView.findViewById(R.id.textTo);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    private void showDialog(Bookings bookings){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ticket");

        String message = "Name: " + bookings.getName() + "\n" +
                "From: " + bookings.getFrom() + "\n" +
                "To: " + bookings.getTo() + "\n" +
                "Date" + bookings.getDate() + "\n" +
                "Price: €" + bookings.getPrice() + "\n" +
                "Ticket Valid Only For Date Purchased" + "\n" +
                "Expires After Activation";

        builder.setMessage(message);

        builder.setPositiveButton("Activate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activate(bookings.getId());
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void activate(String bookingId) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
        DatabaseReference bookingsRef = userRef.child("Bookings");

        bookingsRef.child(bookingId).child("status").setValue("not active");


    }
}
