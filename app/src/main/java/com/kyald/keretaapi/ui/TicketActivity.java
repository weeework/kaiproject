package com.kyald.keretaapi.ui;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kyald.keretaapi.utils.PreferenceUtils;
import com.kyald.keretaapi.R;
import com.kyald.keretaapi.endpoints.RestAPI;
import com.kyald.keretaapi.utils.Utils;
import com.kyald.keretaapi.models.Ticket;
import com.kyald.keretaapi.models.Train;
import com.kyald.keretaapi.models.User;
import com.kyald.keretaapi.responses.TicketResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketActivity extends AppCompatActivity {
    TextView id_train,id_gerbong,id_chair,id_name,id_route,id_code;

    ProgressDialog progressDialog;
    Toolbar toolbar;
    String code,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        code = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.CODE);

        token = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.TOKEN);
        id_code = (TextView)findViewById(R.id.id_code);
        id_train = (TextView)findViewById(R.id.id_train);
        id_gerbong = (TextView)findViewById(R.id.id_gerbon);
        id_name = (TextView)findViewById(R.id.id_name);
        id_route = (TextView)findViewById(R.id.id_route);
        id_chair = (TextView)findViewById(R.id.id_kursi);

        initToolBar();
        getData();

    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("KAI Tiket");
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

    private void getData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

                RestAPI apiEndpoint = Utils.getClient().create(RestAPI.class);
                Call<TicketResponse> call = apiEndpoint.getTicket(token,code);

                call.enqueue(new Callback<TicketResponse>() {
                    @Override
                    public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {

                        progressDialog.dismiss();
                        TicketResponse authResponse = response.body();
                        User user = authResponse.getUser();
                        Ticket ticket = authResponse.getTicket();
                        Train train = authResponse.getTrain();
                        id_train.setText("Kereta : "+String.valueOf(train.getId()));
                        id_gerbong.setText("Gerbong : "+String.valueOf(ticket.getCoachId()));
                        id_chair.setText("Kursi : "+String.valueOf(ticket.getChair()));
                        id_name.setText("Nama : "+String.valueOf(user.getName()));
                        id_route.setText("Rute : "+String.valueOf(train.getLocStart() + " - "+ train.getLocEnd()));
                        id_code.setText("Kode tiket : "+String.valueOf(ticket.getCode()));

                    }

                    @Override
                    public void onFailure(Call<TicketResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Terjadi kesalahan, silahkan kontak developer", Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();

                    }
                });

    }
}
