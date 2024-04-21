package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
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

public class RouteAnalyticsFrom extends AppCompatActivity {
    AnyChartView anyChartView;
    FirebaseDatabase database;
    Button select , clear;
    TextView count;
    String[] months = {"Clane Garda Station","Bachelors Walk","Edenderry Town Hall","Colonel Perry Street","Carbury","Derrinturn","Allenwood","Coill Dubh","Prosperous Church","Straffan","Barberstown Cross","St Wolstans School","Tandys Lane","Liffey Valley SC","Heuston Station","Connolly Station"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_analytics_from);
        anyChartView = findViewById(R.id.anyChartView);
        database = FirebaseDatabase.getInstance();
        select = findViewById(R.id.select);
        clear = findViewById(R.id.clear);
        count = findViewById(R.id.count);
        count.setText("* Click clear before selecting a new day");
        Spinner mySpinner = findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(RouteAnalyticsFrom.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.days));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
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

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference userRef = database.getReference("User");

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySpinner.getSelectedItem().toString().equalsIgnoreCase("Monday")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int maxBookingCount = 0;
                            String routeWithMaxBookings = "";
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                for (DataSnapshot bookingsSnapshot : dataSnapshot.child("Bookings").getChildren()) {
                                    String toCGS = bookingsSnapshot.child("from").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Monday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Clane Garda Station")) {
                                            count.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Edenderry Town Hall")) {
                                            countET.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Colonel Perry Street")) {
                                            countCPS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Carbury")) {
                                            countCar.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Derrinturn")) {
                                            countDer.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Allenwood")) {
                                            countAll.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Coill Dubh")) {
                                            countCD.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Prosperous Church")) {
                                            countPC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Straffan")) {
                                            countStr.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Barberstown Cross")) {
                                            countBC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("St Wolstans School")) {
                                            countSWS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley SC")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {count.get(), countBW.get(), countET.get(), countCPS.get(), countCar.get(), countDer.get(), countAll.get(), countCD.get(), countPC.get(), countStr.get(), countBC.get(), countSWS.get(), countTL.get(), countLV.get(), countHS.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < months.length; i++) {
                                dataEntries.add(new ValueDataEntry(months[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics");
                            anyChartView.setChart(chart);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }if (mySpinner.getSelectedItem().toString().equalsIgnoreCase("Tuesday")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int maxBookingCount = 0;
                            String routeWithMaxBookings = "";
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                for (DataSnapshot bookingsSnapshot : dataSnapshot.child("Bookings").getChildren()) {
                                    String toCGS = bookingsSnapshot.child("from").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Tuesday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Clane Garda Station")) {
                                            count.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Edenderry Town Hall")) {
                                            countET.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Colonel Perry Street")) {
                                            countCPS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Carbury")) {
                                            countCar.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Derrinturn")) {
                                            countDer.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Allenwood")) {
                                            countAll.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Coill Dubh")) {
                                            countCD.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Prosperous Church")) {
                                            countPC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Straffan")) {
                                            countStr.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Barberstown Cross")) {
                                            countBC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("St Wolstans School")) {
                                            countSWS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley SC")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {count.get(), countBW.get(), countET.get(), countCPS.get(), countCar.get(), countDer.get(), countAll.get(), countCD.get(), countPC.get(), countStr.get(), countBC.get(), countSWS.get(), countTL.get(), countLV.get(), countHS.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < months.length; i++) {
                                dataEntries.add(new ValueDataEntry(months[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics");
                            anyChartView.setChart(chart);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }if (mySpinner.getSelectedItem().toString().equalsIgnoreCase("Wednesday")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int maxBookingCount = 0;
                            String routeWithMaxBookings = "";
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                for (DataSnapshot bookingsSnapshot : dataSnapshot.child("Bookings").getChildren()) {
                                    String toCGS = bookingsSnapshot.child("from").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Wednesday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Clane Garda Station")) {
                                            count.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Edenderry Town Hall")) {
                                            countET.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Colonel Perry Street")) {
                                            countCPS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Carbury")) {
                                            countCar.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Derrinturn")) {
                                            countDer.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Allenwood")) {
                                            countAll.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Coill Dubh")) {
                                            countCD.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Prosperous Church")) {
                                            countPC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Straffan")) {
                                            countStr.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Barberstown Cross")) {
                                            countBC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("St Wolstans School")) {
                                            countSWS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley SC")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {count.get(), countBW.get(), countET.get(), countCPS.get(), countCar.get(), countDer.get(), countAll.get(), countCD.get(), countPC.get(), countStr.get(), countBC.get(), countSWS.get(), countTL.get(), countLV.get(), countHS.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < months.length; i++) {
                                dataEntries.add(new ValueDataEntry(months[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics");
                            anyChartView.setChart(chart);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }if (mySpinner.getSelectedItem().toString().equalsIgnoreCase("Thursday")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int maxBookingCount = 0;
                            String routeWithMaxBookings = "";
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                for (DataSnapshot bookingsSnapshot : dataSnapshot.child("Bookings").getChildren()) {
                                    String toCGS = bookingsSnapshot.child("from").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Thursday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Clane Garda Station")) {
                                            count.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Edenderry Town Hall")) {
                                            countET.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Colonel Perry Street")) {
                                            countCPS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Carbury")) {
                                            countCar.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Derrinturn")) {
                                            countDer.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Allenwood")) {
                                            countAll.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Coill Dubh")) {
                                            countCD.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Prosperous Church")) {
                                            countPC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Straffan")) {
                                            countStr.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Barberstown Cross")) {
                                            countBC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("St Wolstans School")) {
                                            countSWS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley SC")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {count.get(), countBW.get(), countET.get(), countCPS.get(), countCar.get(), countDer.get(), countAll.get(), countCD.get(), countPC.get(), countStr.get(), countBC.get(), countSWS.get(), countTL.get(), countLV.get(), countHS.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < months.length; i++) {
                                dataEntries.add(new ValueDataEntry(months[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics");
                            anyChartView.setChart(chart);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }if (mySpinner.getSelectedItem().toString().equalsIgnoreCase("Friday")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int maxBookingCount = 0;
                            String routeWithMaxBookings = "";
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                for (DataSnapshot bookingsSnapshot : dataSnapshot.child("Bookings").getChildren()) {
                                    String toCGS = bookingsSnapshot.child("from").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Friday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Clane Garda Station")) {
                                            count.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Edenderry Town Hall")) {
                                            countET.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Colonel Perry Street")) {
                                            countCPS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Carbury")) {
                                            countCar.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Derrinturn")) {
                                            countDer.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Allenwood")) {
                                            countAll.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Coill Dubh")) {
                                            countCD.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Prosperous Church")) {
                                            countPC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Straffan")) {
                                            countStr.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Barberstown Cross")) {
                                            countBC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("St Wolstans School")) {
                                            countSWS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley SC")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {count.get(), countBW.get(), countET.get(), countCPS.get(), countCar.get(), countDer.get(), countAll.get(), countCD.get(), countPC.get(), countStr.get(), countBC.get(), countSWS.get(), countTL.get(), countLV.get(), countHS.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < months.length; i++) {
                                dataEntries.add(new ValueDataEntry(months[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics");
                            anyChartView.setChart(chart);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }if (mySpinner.getSelectedItem().toString().equalsIgnoreCase("Saturday")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int maxBookingCount = 0;
                            String routeWithMaxBookings = "";
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                for (DataSnapshot bookingsSnapshot : dataSnapshot.child("Bookings").getChildren()) {
                                    String toCGS = bookingsSnapshot.child("from").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Saturday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Clane Garda Station")) {
                                            count.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Edenderry Town Hall")) {
                                            countET.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Colonel Perry Street")) {
                                            countCPS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Carbury")) {
                                            countCar.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Derrinturn")) {
                                            countDer.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Allenwood")) {
                                            countAll.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Coill Dubh")) {
                                            countCD.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Prosperous Church")) {
                                            countPC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Straffan")) {
                                            countStr.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Barberstown Cross")) {
                                            countBC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("St Wolstans School")) {
                                            countSWS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley SC")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {count.get(), countBW.get(), countET.get(), countCPS.get(), countCar.get(), countDer.get(), countAll.get(), countCD.get(), countPC.get(), countStr.get(), countBC.get(), countSWS.get(), countTL.get(), countLV.get(), countHS.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < months.length; i++) {
                                dataEntries.add(new ValueDataEntry(months[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics");
                            anyChartView.setChart(chart);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }if (mySpinner.getSelectedItem().toString().equalsIgnoreCase("Sunday")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int maxBookingCount = 0;
                            String routeWithMaxBookings = "";
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                for (DataSnapshot bookingsSnapshot : dataSnapshot.child("Bookings").getChildren()) {
                                    String toCGS = bookingsSnapshot.child("from").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Sunday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Clane Garda Station")) {
                                            count.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Edenderry Town Hall")) {
                                            countET.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Colonel Perry Street")) {
                                            countCPS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Carbury")) {
                                            countCar.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Derrinturn")) {
                                            countDer.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Allenwood")) {
                                            countAll.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Coill Dubh")) {
                                            countCD.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Prosperous Church")) {
                                            countPC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Straffan")) {
                                            countStr.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Barberstown Cross")) {
                                            countBC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("St Wolstans School")) {
                                            countSWS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley SC")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {count.get(), countBW.get(), countET.get(), countCPS.get(), countCar.get(), countDer.get(), countAll.get(), countCD.get(), countPC.get(), countStr.get(), countBC.get(), countSWS.get(), countTL.get(), countLV.get(), countHS.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < months.length; i++) {
                                dataEntries.add(new ValueDataEntry(months[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics");
                            anyChartView.setChart(chart);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                startActivity(i);
            }
        });
    }
}