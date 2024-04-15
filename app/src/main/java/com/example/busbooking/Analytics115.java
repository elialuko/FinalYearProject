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

public class Analytics115 extends AppCompatActivity {
    AnyChartView anyChartView;
    FirebaseDatabase database;
    String[] stops = {"Enfield", "Cloncurry Cross","Kelleghers Cross","Ferrans Lock","Kilcock","Kilcock Square","Millerstown","Maynooth University","Old Greenfield","Tandys Lane","Liffey Valley Sc","Kennelsfort Road","Heuston Station","Bachelors Walk","Connolly Station"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics115);
        anyChartView=findViewById(R.id.anyChartView);
        database = FirebaseDatabase.getInstance();

        AtomicInteger countE = new AtomicInteger(0);
        AtomicInteger countCC = new AtomicInteger(0);
        AtomicInteger countKC = new AtomicInteger(0);
        AtomicInteger countFL = new AtomicInteger(0);
        AtomicInteger countK = new AtomicInteger(0);
        AtomicInteger countKS = new AtomicInteger(0);
        AtomicInteger countM = new AtomicInteger(0);
        AtomicInteger countMU = new AtomicInteger(0);
        AtomicInteger countOG = new AtomicInteger(0);
        AtomicInteger countTL = new AtomicInteger(0);
        AtomicInteger countLV = new AtomicInteger(0);
        AtomicInteger countKR = new AtomicInteger(0);
        AtomicInteger countHS = new AtomicInteger(0);
        AtomicInteger countBW = new AtomicInteger(0);
        AtomicInteger countCS = new AtomicInteger(0);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User").child(firebaseUser.getUid()).child("Bookings");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren() ){
                    String toCGS = dataSnapshot.child("to").getValue(String.class);
                    System.out.println("Value of 'to': " + toCGS);
                    if(toCGS!=null && toCGS.equals("Enfield")){
                        countE.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Cloncurry Cross")){
                        countCC.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Kelleghers Cross")){
                        countKC.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Ferrans Lock")){
                        countFL.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Kilcock")){
                        countK.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Kilcock Square")){
                        countKS.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Millerstown")){
                        countM.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Maynooth University")){
                        countMU.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Old Greenfield")){
                        countOG.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Tandys Lane")){
                        countTL.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Liffey Valley Sc")){
                        countLV.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Kennelsfort Road")){
                        countKR.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Heuston Station")){
                        countHS.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Bachelors Walk")){
                        countBW.incrementAndGet();
                    }if(toCGS!=null && toCGS.equals("Connolly Station")){
                        countCS.incrementAndGet();
                    }
                }
                int[] salary = {countE.get(),countCC.get(),countKC.get(),countFL.get(),countK.get(),countKS.get(),countM.get(),countMU.get(),countOG.get(),countTL.get(),countLV.get(),countKR.get(),countHS.get(),countBW.get(),countCS.get()};
                Pie pie = AnyChart.pie();
                List<DataEntry> dataEntries = new ArrayList<>();
                for(int i=0; i<stops.length;i++){
                    dataEntries.add(new ValueDataEntry(stops[i],salary[i]));
                }
                pie.data(dataEntries);
                pie.title("Where Your Buying Your Tickets To On the 115 Route");
                anyChartView.setChart(pie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}