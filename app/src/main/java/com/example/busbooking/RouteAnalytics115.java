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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RouteAnalytics115 extends AppCompatActivity {

    AnyChartView anyChartView;
    FirebaseDatabase database;
    Button next, clear,select, home;
    TextView count;

    String[] stops = {"Enfield", "Cloncurry Cross","Kelleghers Cross","Ferrans Lock","Kilcock","Kilcock Square","Millerstown","Maynooth University","Old Greenfield","Tandys Lane","Liffey Valley Sc","Kennelsfort Road","Heuston Station","Bachelors Walk","Connolly Station"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_analytics115);
        anyChartView = findViewById(R.id.anyChartView);
        next = findViewById(R.id.next);
        clear = findViewById(R.id.clear);
        select = findViewById(R.id.select);
        home = findViewById(R.id.home);
        Spinner mySpinner = findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(RouteAnalytics115.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.days));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        count = findViewById(R.id.count);
        count.setText("* Click clear before selecting a new day");
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

        DatabaseReference userRef = database.getReference("User");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RouteAnalytics115.this, RouteAnalytics115From.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RouteAnalytics115.this, AdminWelcome.class);
                startActivity(i);
            }
        });

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
                                    String toCGS = bookingsSnapshot.child("to").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Monday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Enfield")) {
                                            countE.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Cloncurry Cross")) {
                                            countCC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kelleghers Cross")) {
                                            countKC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Ferrans Lock")) {
                                            countFL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock")) {
                                            countK.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock Square")) {
                                            countKS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Millerstown")) {
                                            countM.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Maynooth University")) {
                                            countMU.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Old Greenfield")) {
                                            countOG.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley Sc")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kennelsfort Road")) {
                                            countKR.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {countE.get(), countCC.get(), countKC.get(), countFL.get(), countK.get(), countKS.get(), countM.get(), countMU.get(), countOG.get(), countTL.get(), countLV.get(), countKR.get(), countHS.get(), countBW.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < stops.length; i++) {
                                dataEntries.add(new ValueDataEntry(stops[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics For The 115 Route");
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
                                    String toCGS = bookingsSnapshot.child("to").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Tuesday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Enfield")) {
                                            countE.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Cloncurry Cross")) {
                                            countCC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kelleghers Cross")) {
                                            countKC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Ferrans Lock")) {
                                            countFL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock")) {
                                            countK.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock Square")) {
                                            countKS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Millerstown")) {
                                            countM.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Maynooth University")) {
                                            countMU.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Old Greenfield")) {
                                            countOG.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley Sc")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kennelsfort Road")) {
                                            countKR.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {countE.get(), countCC.get(), countKC.get(), countFL.get(), countK.get(), countKS.get(), countM.get(), countMU.get(), countOG.get(), countTL.get(), countLV.get(), countKR.get(), countHS.get(), countBW.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < stops.length; i++) {
                                dataEntries.add(new ValueDataEntry(stops[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics For The 115 Route");
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
                                    String toCGS = bookingsSnapshot.child("to").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Wednesday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Enfield")) {
                                            countE.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Cloncurry Cross")) {
                                            countCC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kelleghers Cross")) {
                                            countKC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Ferrans Lock")) {
                                            countFL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock")) {
                                            countK.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock Square")) {
                                            countKS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Millerstown")) {
                                            countM.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Maynooth University")) {
                                            countMU.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Old Greenfield")) {
                                            countOG.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley Sc")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kennelsfort Road")) {
                                            countKR.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {countE.get(), countCC.get(), countKC.get(), countFL.get(), countK.get(), countKS.get(), countM.get(), countMU.get(), countOG.get(), countTL.get(), countLV.get(), countKR.get(), countHS.get(), countBW.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < stops.length; i++) {
                                dataEntries.add(new ValueDataEntry(stops[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics For The 115 Route");
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
                                    String toCGS = bookingsSnapshot.child("to").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Thursday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Enfield")) {
                                            countE.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Cloncurry Cross")) {
                                            countCC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kelleghers Cross")) {
                                            countKC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Ferrans Lock")) {
                                            countFL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock")) {
                                            countK.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock Square")) {
                                            countKS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Millerstown")) {
                                            countM.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Maynooth University")) {
                                            countMU.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Old Greenfield")) {
                                            countOG.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley Sc")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kennelsfort Road")) {
                                            countKR.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {countE.get(), countCC.get(), countKC.get(), countFL.get(), countK.get(), countKS.get(), countM.get(), countMU.get(), countOG.get(), countTL.get(), countLV.get(), countKR.get(), countHS.get(), countBW.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < stops.length; i++) {
                                dataEntries.add(new ValueDataEntry(stops[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics For The 115 Route");
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
                                    String toCGS = bookingsSnapshot.child("to").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Friday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Enfield")) {
                                            countE.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Cloncurry Cross")) {
                                            countCC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kelleghers Cross")) {
                                            countKC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Ferrans Lock")) {
                                            countFL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock")) {
                                            countK.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock Square")) {
                                            countKS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Millerstown")) {
                                            countM.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Maynooth University")) {
                                            countMU.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Old Greenfield")) {
                                            countOG.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley Sc")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kennelsfort Road")) {
                                            countKR.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {countE.get(), countCC.get(), countKC.get(), countFL.get(), countK.get(), countKS.get(), countM.get(), countMU.get(), countOG.get(), countTL.get(), countLV.get(), countKR.get(), countHS.get(), countBW.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < stops.length; i++) {
                                dataEntries.add(new ValueDataEntry(stops[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics For The 115 Route");
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
                                    String toCGS = bookingsSnapshot.child("to").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Saturday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Enfield")) {
                                            countE.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Cloncurry Cross")) {
                                            countCC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kelleghers Cross")) {
                                            countKC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Ferrans Lock")) {
                                            countFL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock")) {
                                            countK.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock Square")) {
                                            countKS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Millerstown")) {
                                            countM.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Maynooth University")) {
                                            countMU.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Old Greenfield")) {
                                            countOG.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley Sc")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kennelsfort Road")) {
                                            countKR.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {countE.get(), countCC.get(), countKC.get(), countFL.get(), countK.get(), countKS.get(), countM.get(), countMU.get(), countOG.get(), countTL.get(), countLV.get(), countKR.get(), countHS.get(), countBW.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < stops.length; i++) {
                                dataEntries.add(new ValueDataEntry(stops[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics For The 115 Route");
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
                                    String toCGS = bookingsSnapshot.child("to").getValue(String.class);
                                    String day = bookingsSnapshot.child("day").getValue(String.class);
                                    if ("Sunday".equals(day)) {
                                        if (toCGS != null && toCGS.equals("Enfield")) {
                                            countE.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Cloncurry Cross")) {
                                            countCC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kelleghers Cross")) {
                                            countKC.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Ferrans Lock")) {
                                            countFL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock")) {
                                            countK.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kilcock Square")) {
                                            countKS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Millerstown")) {
                                            countM.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Maynooth University")) {
                                            countMU.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Old Greenfield")) {
                                            countOG.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Tandys Lane")) {
                                            countTL.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Liffey Valley Sc")) {
                                            countLV.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Kennelsfort Road")) {
                                            countKR.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Heuston Station")) {
                                            countHS.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Bachelors Walk")) {
                                            countBW.incrementAndGet();
                                        }
                                        if (toCGS != null && toCGS.equals("Connolly Station")) {
                                            countCS.incrementAndGet();
                                        }
                                    }
                                }
                            }
                            int[] salary = {countE.get(), countCC.get(), countKC.get(), countFL.get(), countK.get(), countKS.get(), countM.get(), countMU.get(), countOG.get(), countTL.get(), countLV.get(), countKR.get(), countHS.get(), countBW.get(), countCS.get()};
                            Chart chart = AnyChart.bar();
                            List<DataEntry> dataEntries = new ArrayList<>();
                            for (int i = 0; i < stops.length; i++) {
                                dataEntries.add(new ValueDataEntry(stops[i], salary[i]));
                            }
                            ((Cartesian) chart).data(dataEntries);
                            ((Cartesian) chart).getSeries(0).name("Total");
                            chart.title("Route Analytics For The 115 Route");
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