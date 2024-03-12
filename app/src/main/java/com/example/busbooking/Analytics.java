package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Analytics extends AppCompatActivity {

    AnyChartView anyChartView;
    FirebaseDatabase database;
    String[] months = {"Jan","Feb","Mar","Apr"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        anyChartView=findViewById(R.id.anyChartView);
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Bookings");

        String to1 = "Clane Garda Station";
        AtomicInteger count = new AtomicInteger(0);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User").child(firebaseUser.getUid()).child("Bookings");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren() ){
                    String toCGS = dataSnapshot.child("to").getValue(String.class);
                    System.out.println("Value of 'to': " + toCGS);
                    //String data = dataSnapshot.getValue(String.class);
                    if(toCGS!=null && toCGS.equals("Clane Garda Station")){
                        count.incrementAndGet();
                    }
                }
                int totalCount = count.get();
                System.out.println("Total count of 'Clane Garda Station': " + totalCount); // Print count for verification
                populateUI(count.get());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        }

        private void populateUI(int count){
            int[] salary = {count,2,3,5};
            Pie pie = AnyChart.pie();
            List<DataEntry> dataEntries = new ArrayList<>();

            for(int i=0; i<months.length;i++){
                dataEntries.add(new ValueDataEntry(months[i],salary[i]));
            }
            pie.data(dataEntries);
            pie.title("Salary");
            anyChartView.setChart(pie);
        }



}