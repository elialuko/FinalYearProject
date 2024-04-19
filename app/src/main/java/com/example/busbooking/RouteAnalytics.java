package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class RouteAnalytics extends AppCompatActivity {
    AnyChartView anyChartView;
    FirebaseDatabase database;
    TextView counttt;
    String[] months = {"Clane Garda Station","Bachelors Walk","Edenderry Town Hall","Colonel Perry Street","Carbury","Derrinturn","Allenwood","Coill Dubh","Prosperous Church","Straffan","Barberstown Cross","St Wolstans School","Tandys Lane","Liffey Valley SC","Heuston Station","Connolly Station"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_analytics);
        anyChartView = findViewById(R.id.anyChartView);
        counttt = findViewById(R.id.count);
        database = FirebaseDatabase.getInstance();
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

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int maxBookingCount = 0;
                String routeWithMaxBookings = "";
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for(DataSnapshot bookingsSnapshot : dataSnapshot.child("Bookings").getChildren()){
                        String toCGS = bookingsSnapshot.child("to").getValue(String.class);
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
                }
                int[] salary = {count.get(),countBW.get(),countET.get(),countCPS.get(),countCar.get(),countDer.get(),countAll.get(),countCD.get(),countPC.get(),countStr.get(),countBC.get(),countSWS.get(),countTL.get(),countLV.get(),countHS.get(),countCS.get()};
                Chart chart = AnyChart.bar();
                List<DataEntry> dataEntries = new ArrayList<>();
                for(int i=0; i<months.length;i++){
                    dataEntries.add(new ValueDataEntry(months[i],salary[i]));
                }
                ((Cartesian) chart).data(dataEntries);
                ((Cartesian) chart).getSeries(0).name("Total");
                chart.title("Route Analytics");
                anyChartView.setChart(chart);

                if (count.get() > maxBookingCount) {
                    maxBookingCount = count.get();
                    routeWithMaxBookings = "Clane Garda Station";
                }if (countBW.get() > maxBookingCount) {
                    maxBookingCount = countBW.get();
                    routeWithMaxBookings = "Bachelors Walk";
                }if (countET.get() > maxBookingCount) {
                    maxBookingCount = countET.get();
                    routeWithMaxBookings = "Edenderry Town Hall";
                }if (countCPS.get() > maxBookingCount) {
                    maxBookingCount = countCPS.get();
                    routeWithMaxBookings = "Colonel Perry Street";
                }if (countCar.get() > maxBookingCount) {
                    maxBookingCount = countCar.get();
                    routeWithMaxBookings = "Carbury";
                }if (countDer.get() > maxBookingCount) {
                    maxBookingCount = countDer.get();
                    routeWithMaxBookings = "Derrinturn";
                }if (countAll.get() > maxBookingCount) {
                    maxBookingCount = countAll.get();
                    routeWithMaxBookings = "Allenwood";
                }if (countCD.get() > maxBookingCount) {
                    maxBookingCount = countCD.get();
                    routeWithMaxBookings = "Coill Dubh";
                }if (countPC.get() > maxBookingCount) {
                    maxBookingCount = countPC.get();
                    routeWithMaxBookings = "Prosperous Church";
                }if (countStr.get() > maxBookingCount) {
                    maxBookingCount = countStr.get();
                    routeWithMaxBookings = "Straffan";
                }if (countBC.get() > maxBookingCount) {
                    maxBookingCount = countBC.get();
                    routeWithMaxBookings = "Barberstown Cross";
                }if (countSWS.get() > maxBookingCount) {
                    maxBookingCount = countSWS.get();
                    routeWithMaxBookings = "St Wolstans School";
                }if (countTL.get() > maxBookingCount) {
                    maxBookingCount = countTL.get();
                    routeWithMaxBookings = "Tandys Lane";
                }if (countLV.get() > maxBookingCount) {
                    maxBookingCount = countLV.get();
                    routeWithMaxBookings = "Liffey Valley SC";
                }if (countHS.get() > maxBookingCount) {
                    maxBookingCount = countHS.get();
                    routeWithMaxBookings = "Heuston Station";
                }if (countCS.get() > maxBookingCount) {
                    maxBookingCount = countCS.get();
                    routeWithMaxBookings = "Connolly Station";
                }
                counttt.setText("• Looks like " + routeWithMaxBookings + " is the route with the most bookings with " + maxBookingCount + " bookings");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}