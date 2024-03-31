package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Fares extends AppCompatActivity implements View.OnClickListener{
    Button timetable1, timetable2;
    CardView onetwenty, oneonefive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fares);
        //timetable1 = findViewById(R.id.timetable1);
        //timetable2 = findViewById(R.id.timetable2);
        onetwenty = findViewById(R.id.onetwenty_card);
        oneonefive = findViewById(R.id.oneonefive_card);

        onetwenty.setOnClickListener(this);
        oneonefive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent i;
        String tag = (String) v.getTag();

        switch (tag){
            case "onetwenty" : i = new Intent(getApplicationContext(),Timetable120.class);
                i.putExtra("pdf_120","https://www.transportforireland.ie/wp-content/uploads/2019/12/GAI_TFI-120-120C-1.pdf");
                startActivity(i);
                break;
                case "oneonefive" : i = new Intent(getApplicationContext(),Timetable115.class);
                i.putExtra("pdf_115","https://www.buseireann.ie/timetables/115-1700571622.pdf");
                startActivity(i);
                break;
    }
}
}