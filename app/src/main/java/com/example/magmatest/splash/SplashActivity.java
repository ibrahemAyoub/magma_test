package com.example.magmatest.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.magmatest.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class SplashActivity extends AppCompatActivity {
    private AdView adView;
    ProgressBar progressBar;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar=findViewById(R.id.progress);
        textView=findViewById(R.id.textProgress);
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        runProgress();
    }
    void runProgress(){
        ProgressAnimation progressAnimation=new ProgressAnimation(this,progressBar,textView,0f,1000f);
        progressAnimation.setDuration(12000);
        progressBar.setAnimation(progressAnimation);
    }
}