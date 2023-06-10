package com.practice.gmusic.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.practice.gmusic.MainActivity;
import com.practice.gmusic.R;
import com.practice.gmusic.others.Const;

public class SplashActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        SharedPreferences preferences = getSharedPreferences("User Data", MODE_PRIVATE);
        boolean isRemember = preferences.getBoolean("IsRemember", false);
        splashScreenHandler(isRemember);
    }

    private void splashScreenHandler(boolean isRemember) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRemember) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, Const.delayTime);
    }

}