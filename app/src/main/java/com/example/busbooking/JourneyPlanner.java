package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JourneyPlanner extends AppCompatActivity {

    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_planner);
        webview = findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("https://www.transportforireland.ie/plan-a-journey/");

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack();
        }else{
            super.onBackPressed();
        }

    }
}