package com.kyald.keretaapi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.kyald.keretaapi.ui.LoginActivity;
import com.kyald.keretaapi.utils.PreferenceUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final String token = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.TOKEN);

        Handler hdh = new Handler();

        hdh.postDelayed(new Runnable() {
            @Override
            public void run() {

                new Thread(new Runnable() {
                    public void run() {

                        if(token == null || token.equals("")){
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }

                    }
                }).start();

            }
        },1500);


    }
}
