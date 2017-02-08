package com.kyald.keretaapi.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.kyald.keretaapi.R;

public class FoodActivity extends AppCompatActivity {
    private Button btn_makanan;
    private Button btn_minuman;
    private Button btn_status;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initToolBar();
        btn_makanan = (Button)findViewById(R.id.btnmakanan);
        btn_minuman =(Button)findViewById(R.id.btnminuman);
        btn_status =(Button)findViewById(R.id.btnstatus);

        btn_makanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (FoodActivity.this,MakananActivity.class);
                startActivity(intent);
            }
        });

        btn_minuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (FoodActivity.this,MinumanActivity.class);
                startActivity(intent);

            }
        });


        btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (FoodActivity.this,StatusActivity.class);
                startActivity(intent);

            }
        });

    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("KAI Order");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
