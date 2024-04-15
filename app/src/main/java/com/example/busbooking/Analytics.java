package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.Chart;
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
    Button next;
    String[] months = {"Clane Garda Station","Bachelors Walk","Edenderry Town Hall","Colonel Perry Street","Carbury","Derrinturn","Allenwood","Coill Dubh","Prosperous Church","Straffan","Barberstown Cross","St Wolstans School","Tandys Lane","Liffey Valley SC","Heuston Station","Connolly Station"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        anyChartView=findViewById(R.id.anyChartView);
        database = FirebaseDatabase.getInstance();
        next = findViewById(R.id.next);
        DatabaseReference reference = database.getReference("Bookings");

        String to1 = "Clane Garda Station";
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger countBW = new AtomicInteger(0);
        AtomicInteger countET = new AtomicInteger(0);
        AtomicInteger countCPS = new AtomicInteger(0);
        AtomicInteger countCar = new AtomicInteger(0);
        AtomicInteger countDer = new AtomicInteger(0);
        AtomicInteger countAll = new AtomicInteger(0);
        AtomicInteger countCD = new AtomicInteger(0);
        AtomicInteger countPC = new AtomicInteger(0);
        AtomicInteger countStr = new AtomicInteger(0);
        AtomicInteger countBC = new AtomicInteger(0);
        AtomicInteger countSWS = new AtomicInteger(0);
        AtomicInteger countTL = new AtomicInteger(0);
        AtomicInteger countLV = new AtomicInteger(0);
        AtomicInteger countHS = new AtomicInteger(0);
        AtomicInteger countCS = new AtomicInteger(0);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Analytics.this, Analytics115.class);
                startActivity(i);
            }
        });
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
                    }if(toCGS!=null && toCGS.equals("Bachelors Walk")){
                        countBW.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Edenderry Town Hall")){
                        countET.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Colonel Perry Street")){
                        countCPS.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Carbury")){
                        countCar.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Derrinturn")){
                        countDer.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Allenwood")){
                        countAll.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Coill Dubh")){
                        countCD.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Prosperous Church")){
                        countPC.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Straffan")){
                        countStr.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Barberstown Cross")){
                        countBC.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("St Wolstans School")){
                        countSWS.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Tandys Lane")){
                        countTL.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Liffey Valley SC")){
                        countLV.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Heuston Station")){
                        countHS.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Connolly Station")){
                        countCS.incrementAndGet();
                    }
                }
                int totalCount = count.get();
                System.out.println("Total count of 'Clane Garda Station': " + totalCount); // Print count for verification
                //populateUI(count.get());
                int[] salary = {count.get(),countBW.get(),countET.get(),countCPS.get(),countCar.get(),countDer.get(),countAll.get(),countCD.get(),countPC.get(),countStr.get(),countBC.get(),countSWS.get(),countTL.get(),countLV.get(),countHS.get(),countCS.get()};
                Pie pie = AnyChart.pie();
                List<DataEntry> dataEntries = new ArrayList<>();

                for(int i=0; i<months.length;i++){
                    dataEntries.add(new ValueDataEntry(months[i],salary[i]));
                }
                pie.data(dataEntries);
                pie.title("Where Your Buying Your Tickets To On The 120 Route");
                anyChartView.setChart(pie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        }

        private void populateUI(int count){
            //int[] salary = {count.get(),countBW.get(),countET.get(),countCPS.get(),countCar.get(),countDer.get(),countAll.get(),countCD.get(),countPC.get(),countStr.get(),countBC.get(),countSWS.get(),countTL.get(),countLV.get(),countHS.get(),countCS.get()};
            Chart chart = AnyChart.bar();
            List<DataEntry> dataEntries = new ArrayList<>();

            for(int i=0; i<months.length;i++){
                //dataEntries.add(new ValueDataEntry(months[i],salary[i]));
            }
            ((Cartesian) chart).data(dataEntries);
            chart.title("Where Your Buying Your Tickets To On The 120 Route");
            anyChartView.setChart(chart);
        }



}