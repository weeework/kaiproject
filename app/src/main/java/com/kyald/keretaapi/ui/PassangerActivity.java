package com.kyald.keretaapi.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.kyald.keretaapi.R;
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

public class PassangerActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_passanger);
        txtTrain = (TextView)findViewById(R.id.txtTraing);
        txtdate = (TextView) findViewById(R.id.txt_date);
//        txtChair = (TextView)findViewById(R.id.txtchair);
        rl_train = (RelativeLayout)findViewById(R.id.rl_kereta);
        search = (Button)findViewById(R.id.btn_search);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        initToolBar();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        rv=(RecyclerView) findViewById(R.id.id_ac);
//                rv.setVisibility(View.GONE);


        token = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.TOKEN);
        String code = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.CODE);




        getData(code);
        rv.setVisibility(View.VISIBLE);




//        txtdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                datepick();
//                rl_train.setVisibility(View.GONE);
//                rv.setVisibility(View.GONE);
////                Toast.makeText(OrderTicketActivity.this, "asdsad", Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//        txtTrain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogTrain();
////                Toast.makeText(OrderTicketActivity.this, "asdsad", Toast.LENGTH_LONG).show();
//
//            }
//        });

//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getData();
//                rv.setVisibility(View.VISIBLE);
//
//            }
//        });

    }


    private void datepick(){

//        txtstatus.setVisibility(View.GONE);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txtdate.setText(dateFormatter.format(newDate.getTime()));
                rl_train.setVisibility(View.VISIBLE);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog.show();


    }

    private void dialogTrain() {

        RestAPI apiEndpoint = Utils.getClient().create(RestAPI.class);
        Call<TrainResponse> call = apiEndpoint.getTrain(token);
//        btn_order.setVisibility(View.GONE);

        call.enqueue(new Callback<TrainResponse>() {
            @Override
            public void onResponse(Call<TrainResponse> call, Response<TrainResponse> response) {

                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(PassangerActivity.this);
                builderSingle.setTitle("Pilih kereta");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        PassangerActivity.this,
                        android.R.layout.simple_list_item_1);

                TrainResponse trainResponse = response.body();
                final List<Train> trains = trainResponse.getData();

                for (int i = 0; i < trains.size(); i++) {

                    arrayAdapter.add(trains.get(i).getName() + " | "+ trains.get(i).getLocStart()
                            + "-"+trains.get(i).getLocEnd()+" ("+trains.get(i).getSchStart()+"-"+trains.get(i).getSchEnd()+")" );

                }

                builderSingle.setNegativeButton(
                        "cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builderSingle.setAdapter(
                        arrayAdapter,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                rl_gerbong.setVisibility(View.VISIBLE);
//                                search.setVisibility(View.VISIBLE);
                                kereta = trains.get(which).getId();
                                ((TextView)findViewById(R.id.txtTraing)).setText(trains.get(which).getName() + " | "+ trains.get(which).getLocStart()
                                        + "-"+trains.get(which).getLocEnd()+" ("+trains.get(which).getSchStart()+"-"+trains.get(which).getSchEnd()+")" );
//                                edtCountryCode.setTag("+" + countryCodeEvent.getData().get(which).getPhoneCode());
                                dialog.dismiss();

//                                getData();
//                                rv.setVisibility(View.VISIBLE);



                            }
                        });
                builderSingle.show();

            }

            @Override
            public void onFailure(Call<TrainResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan, silahkan kontak developer", Toast.LENGTH_LONG).show();

            }
        });
    }




    private void initToolBar() { toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("KAI Penumpang");


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
        final String token = PreferenceUtils.getInstance().loadDataString(PassangerActivity.this, PreferenceUtils.TOKEN);

        RestAPI service = Utils.getClient().create(RestAPI.class);
        Call<PassangerResponse> call = service. getPassanger(token,code);
        call.enqueue(new Callback<PassangerResponse>() { //Asyncronous Request
                         @Override
                         public void onResponse(Call<PassangerResponse> call, Response<PassangerResponse> response) {
                             try {
                                 loading.dismiss(); //progress dialog dihentikan


                                 //dapatkan hasil parsing dari method response.body()
                                 PassangerResponse dishResponse=response.body();
//                                 Penumpang dishKategory=dishResponse.getData();
                                 List<Passengger> passenggers=dishResponse.getData();
//                                 rv.setHasFixedSize(true);
                                 passenggerList.clear();
//                                 for(int i=0;i<dishResponse.getData().)
                                 for (int i=0; i<=passenggers.size();i++){
//                                     Toast.makeText(getApplicationContext(), passenggers.get(i).getName(),Toast.LENGTH_SHORT).show();
//                                     makananList.add(new Makanan())
                                     passenggerList.add(passenggers.get(i));
//                                   makananList.add(new Makanan(1,"rdytrdy"));
                                     dishAdapter = new PenumpangAdapter(PassangerActivity.this,passenggerList);

                                     RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PassangerActivity.this);
                                     rv.setLayoutManager(layoutManager);
                                     rv.setItemAnimator(new DefaultItemAnimator());
                                     rv.setAdapter(dishAdapter);
                                     dishAdapter.notifyDataSetChanged();
                                 }

//                                 RecyclerView.LayoutManager layoutManager;
//                                 layoutManager = new LinearLayoutManager(MakananActivity.this);
//                                 rv.setLayoutManager(layoutManager);
//                                 dishAdapter = new MakananAdapter(MakananActivity.this,makanen);
//                                 rv.setAdapter(dishAdapter);
//                                 dishAdapter.notifyDataSetChanged();


                             }catch (Exception e){
                                 e.printStackTrace();
                             }
                         }

                         @Override
                         public void onFailure(Call<PassangerResponse> call, Throwable t) {
                             passenggerList.clear();
                             dishAdapter.notifyDataSetChanged();
                             Toast.makeText(getApplicationContext(), "Data tidak ditemukan",Toast.LENGTH_SHORT).show();
                             loading.dismiss(); //progress dialog dihentikan
                         }

                     }
        );
    }

}
