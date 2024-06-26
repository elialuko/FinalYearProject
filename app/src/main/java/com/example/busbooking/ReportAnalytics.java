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
import com.anychart.core.Chart;
import com.anychart.data.Mapping;
import com.anychart.data.Table;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ReportAnalytics extends AppCompatActivity {

    AnyChartView anyChartView;
    FirebaseDatabase database;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_analytics);
        anyChartView = findViewById(R.id.anyChartView);
        AtomicInteger countB = new AtomicInteger(0);
        AtomicInteger countS = new AtomicInteger(0);
        database = FirebaseDatabase.getInstance();
        next = findViewById(R.id.next);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User").child(firebaseUser.getUid()).child("Reports");
        DatabaseReference userRef2 = database.getReference("User");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportAnalytics.this, ReportAnalytics115.class);
                startActivity(i);
            }
        });

        userRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> dateList = new ArrayList<>();
                Map<String, Integer> biggerBusMap = new HashMap<>();
                Map<String, Integer> smallerBusMap = new HashMap<>();
                //long sevenDaysAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000);
                long eightDaysAgo = System.currentTimeMillis() - (8 * 24 * 60 * 60 * 1000);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        for(DataSnapshot reportSnapshot : dataSnapshot.child("Reports").getChildren()) {
                            String route = reportSnapshot.child("route").getValue(String.class);
                            if (route != null && route.equals("120")) {
                                String date = reportSnapshot.child("date").getValue(String.class);
                                long reportTimestamp = getTimestampFromString(date);
                                if (reportTimestamp >= eightDaysAgo) {
                                    String bus = reportSnapshot.child("report").getValue(String.class);
                                    dateList.add(date);
                                    if (date != null && bus != null) {
                                        if (bus.equals("Bigger Bus Needed")) {
                                            biggerBusMap.put(date, biggerBusMap.getOrDefault(date, 0) + 1);
                                        }
                                        if (bus.equals("Smaller Bus Needed")) {
                                            smallerBusMap.put(date, smallerBusMap.getOrDefault(date, 0) + 1);
                                        }
                                    }
                                }
                            }
                        }


                }
                int[] biggerBus = new int[dateList.size()];
                int[] smallerBus = new int[dateList.size()];
                //Arrays.fill(biggerBus, countB.get());
                //Arrays.fill(smallerBus, countS.get());
                for (int i = 0; i < dateList.size(); i++) {
                    String date = dateList.get(i);
                    biggerBus[i] = biggerBusMap.getOrDefault(date, 0);
                    smallerBus[i] = smallerBusMap.getOrDefault(date, 0);
                }
                String[] dates= dateList.toArray(new String[0]);
                Chart chart = AnyChart.bar();
                List<DataEntry> dataEntries = new ArrayList<>();

                for(int i=0; i<dates.length;i++){
                    dataEntries.add(new CustomDataEntry(dates[i],biggerBus[i],smallerBus[i]));
                }
                ((Cartesian) chart).data(dataEntries);
                chart.title("Bus Capacity Reports For The 120 Route");

                ((Cartesian) chart).xAxis(0).title("Dates");
                ((Cartesian) chart).yAxis(0).title("Amount");

                ((Cartesian) chart).getSeries(0).name("Bigger Bus Needed");
                ((Cartesian) chart).getSeries(1).name("Smaller Bus Needed");

                anyChartView.setChart(chart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number biggerBus, Number smallerBus) {
            super(x, biggerBus);
            setValue("smallerBus", smallerBus);
        }
    }

    private long getTimestampFromString(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date dateS = dateFormat.parse(date);
            if (dateS != null) {
                return dateS.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}