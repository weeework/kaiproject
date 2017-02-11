package com.kyald.keretaapi.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyald.keretaapi.MainActivity;
import com.kyald.keretaapi.R;
import com.kyald.keretaapi.StatusKebersihan;
import com.kyald.keretaapi.adapters.PenumpangAdapter;
import com.kyald.keretaapi.endpoints.RestAPI;
import com.kyald.keretaapi.models.Passengger;
import com.kyald.keretaapi.models.Train;
import com.kyald.keretaapi.responses.PassangerResponse;
import com.kyald.keretaapi.responses.TrainResponse;
import com.kyald.keretaapi.utils.PreferenceUtils;
import com.kyald.keretaapi.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CleaningActivity extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView rv;
    //    private MakananAdapter dishAdapter;
    private RecyclerView.Adapter dishAdapter;
    private List<Passengger> passenggerList = new ArrayList<>();
    TextView txtTrain,txtdate;

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    ProgressDialog progressDialog;

    RelativeLayout rl_train, rl_gerbong, rl_chair;
    String token;
    int kereta;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning);
        initToolBar();

        ((Button)findViewById(R.id.btn_status)).setVisibility(View.GONE);
        ((Button)findViewById(R.id.btn_status)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CleaningActivity.this, StatusKebersihan.class));
//                PreferenceUtils.getInstance().deleteAll(MainActivity.this);
//                finish();

            }
        });

        ((Button)findViewById(R.id.btn_update)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CleaningActivity.this, KebersihanUpdate.class));
//                PreferenceUtils.getInstance().deleteAll(MainActivity.this);
//                finish();

            }
        });


    }


    private void initToolBar() { toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("KAI Cleaning");


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