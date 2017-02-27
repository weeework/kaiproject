package com.kyald.keretaapi.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyald.keretaapi.R;
import com.kyald.keretaapi.adapters.PenumpangAdapter;
import com.kyald.keretaapi.endpoints.RestAPI;
import com.kyald.keretaapi.models.Passengger;
import com.kyald.keretaapi.models.Train;
import com.kyald.keretaapi.responses.PassangerResponse;
import com.kyald.keretaapi.responses.PassangerSearchResponse;
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

public class PassangerSearchActivity extends AppCompatActivity {


    Toolbar toolbar;
    private RecyclerView rv;
    //    private MakananAdapter dishAdapter;
    private RecyclerView.Adapter dishAdapter;
    private List<Passengger> passenggerList = new ArrayList<>();
    TextView txtTrain,txtdate;

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    ProgressDialog progressDialog;

    RelativeLayout rl_status, rl_gerbong, rl_chair;
    String token;
    int kereta;
    EditText editText;
    Button search;
    public TextView id_tiket,id_status,id_name,id_coach,id_chair,id_train;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passanger_search);

        editText = (EditText)findViewById(R.id.edt_search);
        search = (Button)findViewById(R.id.btn_search);
        rl_status = (RelativeLayout)findViewById(R.id.rl_status);
        id_tiket=(TextView) findViewById((R.id.id_tiket));
        id_status=(TextView) findViewById((R.id.id_status));
        id_name=(TextView) findViewById((R.id.id_name));
        id_coach=(TextView) findViewById((R.id.id_coach));
        id_chair=(TextView) findViewById((R.id.id_chair));
        id_train=(TextView) findViewById((R.id.id_train));

        initToolBar();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");


        token = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.TOKEN);



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_status.setVisibility(View.GONE);
                String code = editText.getText().toString();
                getData(code);

            }
        });


    }



    private void initToolBar() { toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("KAI cari Penumpang");


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

    private void getData(String code){
        //ketika aplikasi sedang mengambil data kita akan melihat progress dialog muncul
        final ProgressDialog loading = ProgressDialog.show(this, "Fetching Data","Please wait..",false,false);
        final String token = PreferenceUtils.getInstance().loadDataString(PassangerSearchActivity.this, PreferenceUtils.TOKEN);

        RestAPI service = Utils.getClient().create(RestAPI.class);
        Call<PassangerSearchResponse> call = service. getPassangerSearch(token,code);
        call.enqueue(new Callback<PassangerSearchResponse>() { //Asyncronous Request
                         @Override
                         public void onResponse(Call<PassangerSearchResponse> call, Response<PassangerSearchResponse> response) {
                             try {
                                 loading.dismiss(); //progress dialog dihentikan
                                 PassangerSearchResponse passangerSearchResponse = response.body();
                                 if(passangerSearchResponse.getStatus()){
                                     rl_status.setVisibility(View.VISIBLE);
                                     Passengger sts=passangerSearchResponse.getData();

                                     id_tiket.setText("Tiket : "+sts.getTicket() );
                                     id_name.setText("Name : "+String.valueOf(sts.getName()) );
                                     id_coach.setText("Gerbong : "+String.valueOf(sts.getCoach()) );
                                     id_chair.setText("Kursi : "+String.valueOf(sts.getChair()) );
                                     id_train.setText("Kereta : "+String.valueOf(sts.getTrain()) );

                                 } else {
                                     rl_status.setVisibility(View.GONE);
                                     Toast.makeText(getApplicationContext(), "Data tidak ditemukan",Toast.LENGTH_SHORT).show();
                                 }




                             }catch (Exception e){
                                 e.printStackTrace();
                             }
                         }

                         @Override
                         public void onFailure(Call<PassangerSearchResponse> call, Throwable t) {

                             Toast.makeText(getApplicationContext(), "Data tidak ditemukan",Toast.LENGTH_SHORT).show();
                             loading.dismiss(); //progress dialog dihentikan
                             rl_status.setVisibility(View.GONE);
                         }

                     }
        );
    }

}
