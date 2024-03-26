package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_analytics);
        anyChartView=findViewById(R.id.anyChartView);
        database = FirebaseDatabase.getInstance();
        AtomicInteger countO = new AtomicInteger(0);
        AtomicInteger countB = new AtomicInteger(0);
        AtomicInteger countS = new AtomicInteger(0);
        List<String> reportEntry = new ArrayList<>();
        final ArrayList<String> datesList = new ArrayList<>();
        ArrayList<String> timesList = new ArrayList<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User").child(firebaseUser.getUid()).child("Reports");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Integer> reportCounts = new HashMap<>();
                Set<String> uniqueDates = new HashSet<>();


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String date = snapshot.child("date").getValue(String.class);
                    reportEntry.add(date);
                    if (!uniqueDates.contains(date)){

                        uniqueDates.add(date);
                        datesList.add(date);
                    }


                    String time = snapshot.child("time").getValue(String.class);
                    timesList.add(time);
                    String reportdb = dataSnapshot.child("report").getValue(String.class);
                    if (reportdb != null) {
                        // Increment the count for the corresponding report type
                        reportCounts.put(date, reportCounts.getOrDefault(date, 0) + 1);
                    }
                    if(reportdb!=null && reportdb.equals("Other")){
                        countO.incrementAndGet();
                    }
                    if(reportdb!=null && reportdb.equals("Bigger Bus Needed")){
                        countB.incrementAndGet();
                    }
                    if(reportdb!=null && reportdb.equals("Smaller Bus Needed")){
                        countB.incrementAndGet();
                    }

                    reportEntry.add(reportdb);
                }

                Collections.sort(datesList, new Comparator<String>() {
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                    @Override
                    public int compare(String date1, String date2) {
                        try {
                            Date d1 = dateFormat.parse(date1);
                            Date d2 = dateFormat.parse(date2);
                            return d1.compareTo(d2);
                        } catch (ParseException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
                Collections.sort(timesList, new Comparator<String>() {
                    DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

                    @Override
                    public int compare(String time1, String time2) {
                        try {
                            Date t1 = timeFormat.parse(time1);
                            Date t2 = timeFormat.parse(time2);
                            return t1.compareTo(t2);
                        } catch (ParseException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }

                });
                List[] data = {reportEntry};
                String[] dates = datesList.toArray(new String[0]);
                String[] times = timesList.toArray(new String[0]);
                List<Integer> minutesList = new ArrayList<>();
                for (String time : timesList) {
                    minutesList.add(convertTimeStringToMinutes(time));
                }
                Chart chart = AnyChart.bar();
                List<DataEntry> dataEntries = new ArrayList<>();
                int[] reportdatab ={countO.get(), countB.get(), countS.get()};

                for(String date: datesList){
                    int count = reportCounts.getOrDefault(date, 0);
                    dataEntries.add(new ValueDataEntry(date,count));
                    List<DataEntry> additionalDataEntries = new ArrayList<>();
                    for (int i = 0; i < datesList.size(); i++) {
                        additionalDataEntries.add(new ValueDataEntry(datesList.get(i), count));
                    }
                    com.anychart.data.Set set = com.anychart.data.Set.instantiate();
                    set.data(dataEntries);
                    com.anychart.data.Set additionalSet = com.anychart.data.Set.instantiate();
                    additionalSet.data(additionalDataEntries);
                    ((Cartesian) chart).addSeries(set);
                    ((Cartesian) chart).addSeries(additionalSet);
                }
                ((Cartesian) chart).data(dataEntries);
                chart.title("Where Your Buying Your Tickets To");
                anyChartView.setChart(chart);




            }

            private Integer convertTimeStringToMinutes(String timeString) {
                String[] parts = timeString.split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                return hours * 60 + minutes;
            }


            @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
    }

}