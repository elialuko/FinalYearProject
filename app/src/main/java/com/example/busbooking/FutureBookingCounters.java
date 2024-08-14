package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FutureBookingCounters extends AppCompatActivity {

    private Spinner daySpinner;
    private HorizontalBarChart barChart;
    private RequestQueue requestQueue;
    private Map<String, List<BarEntry>> dayEntriesMap = new HashMap<>();
    private Map<Integer, String> destinationMap = new HashMap<>();
    private List<String> allDestinations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_booking_counters);

        daySpinner = findViewById(R.id.daySpinner);
        barChart = findViewById(R.id.barChart);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        loadBarChartData();

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDay = parent.getItemAtPosition(position).toString();
                updateChartForDay(selectedDay);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadBarChartData() {
        String url = "http://192.168.1.11:5000/future_stops_from";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject bookings = response.getJSONObject("bookings");
                            JSONObject days = response.getJSONObject("day");
                            JSONObject destinations = response.getJSONObject("from");

                            for (int i = 0; i < bookings.length(); i++) {
                                int bookingCount = bookings.getInt(String.valueOf(i));
                                String day = days.getString(String.valueOf(i));
                                String destination = destinations.getString(String.valueOf(i));

                                int destinationIndex;
                                if (!destinationMap.containsValue(destination)) {
                                    destinationIndex = allDestinations.size();
                                    allDestinations.add(destination);
                                    destinationMap.put(destinationIndex, destination);
                                } else {
                                    destinationIndex = getKeyByValue(destinationMap, destination);
                                }

                                if (!dayEntriesMap.containsKey(day)) {
                                    dayEntriesMap.put(day, new ArrayList<>());
                                }
                                dayEntriesMap.get(day).add(new BarEntry(destinationIndex, bookingCount));
                            }

                            String defaultDay = daySpinner.getSelectedItem().toString();
                            updateChartForDay(defaultDay);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API Error", "Error fetching data from API", error);
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void updateChartForDay(String selectedDay) {
        List<BarEntry> entries = dayEntriesMap.getOrDefault(selectedDay, new ArrayList<>());

        BarDataSet dataSet = new BarDataSet(entries, "Bookings");
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(allDestinations));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelRotationAngle(-90);
        //
        xAxis.setTextSize(10f);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawGridLines(false);

        barChart.invalidate();
    }

    private int getKeyByValue(Map<Integer, String> map, String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return -1;
    }
}