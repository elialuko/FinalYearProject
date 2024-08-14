package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class BookingPrediction extends AppCompatActivity {

    HorizontalBarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_prediction);
        chart = findViewById(R.id.chart);
        new GetPredictions().execute();
    }

    private class GetPredictions extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... urls) {
            try {
                URL url = new URL("http://192.168.1.11:5000/future_bookings");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            try {
                JSONArray jsonArray = new JSONArray(response);
                ArrayList<BarEntry> entries = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    float bookings = (float) jsonObject.getDouble("predicted_bookings");
                    entries.add(new BarEntry(i, bookings));
                    labels.add(jsonObject.getString("date").substring(5, 12).replace("-", " "));
                }

                BarDataSet dataSet = new BarDataSet(entries, "Predicted Bookings");

                dataSet.setValueTextSize(10f);
                dataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return "" + (int) value;
                    }
                });

                BarData barData = new BarData(dataSet);
                chart.setData(barData);
                chart.setFitBars(true);

                XAxis xAxis = chart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(10f);

                YAxis yAxis = chart.getAxisLeft();
                yAxis.setGranularity(1f);
                yAxis.setGranularityEnabled(true);
                yAxis.setAxisMinimum(0f);
                yAxis.setAxisMaximum(3f);
                xAxis.setLabelRotationAngle(-90);
                chart.getAxisRight().setEnabled(false);
                chart.getDescription().setEnabled(false);
                chart.getLegend().setEnabled(false);
                chart.setExtraBottomOffset(10f);
                chart.setExtraRightOffset(30f);

                chart.invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}