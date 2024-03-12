package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class Analytics extends AppCompatActivity {

    AnyChartView anyChartView;
    String[] months = {"Jan","Feb","Mar","Apr"};
    int[] salary = {100000,200000,300000,500000};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        anyChartView=findViewById(R.id.anyChartView);
        setUpChartView();
    }

    private void setUpChartView() {
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