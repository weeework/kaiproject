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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyald.keretaapi.R;
import com.kyald.keretaapi.adapters.PenumpangAdapter;
import com.kyald.keretaapi.endpoints.RestAPI;
import com.kyald.keretaapi.models.Passengger;
import com.kyald.keretaapi.models.Train;
import com.kyald.keretaapi.models.TrainValidate;
import com.kyald.keretaapi.responses.CleaningResponse;
import com.kyald.keretaapi.responses.PassangerResponse;
import com.kyald.keretaapi.responses.TrainResponse;
import com.kyald.keretaapi.responses.TrainValidateResponse;
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

public class KebersihanUpdate extends AppCompatActivity {
    Toolbar toolbar;
    private RecyclerView rv;
    //    private MakananAdapter dishAdapter;
    private RecyclerView.Adapter dishAdapter;
    private List<Passengger> passenggerList = new ArrayList<>();
    TextView txtTrain,txtdate,txtgerbong;

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    ProgressDialog progressDialog;

    RelativeLayout rl_train, rl_clean, rl_gerbong;
    String token,date;
    int kereta,gerbong;
    Button search;
    String code;
    CheckBox chk_coach,chk_chair,chk_toilet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kebersihan_update);
        txtTrain = (TextView)findViewById(R.id.txtTraing);
        txtdate = (TextView) findViewById(R.id.txt_date);
//        txtChair = (TextView)findViewById(R.id.txtchair);
        rl_train = (RelativeLayout)findViewById(R.id.rl_kereta);
        rl_clean= (RelativeLayout)findViewById(R.id.rl_clean);
        search = (Button)findViewById(R.id.btn_search);
        chk_chair = (CheckBox)findViewById(R.id.chk_chair);
        chk_toilet = (CheckBox)findViewById(R.id.chk_toilet);
        chk_coach = (CheckBox)findViewById(R.id.chk_coach);
        txtgerbong = (TextView) findViewById(R.id.txt_gerbong);
        rl_gerbong = (RelativeLayout)findViewById(R.id.rl_gerbong);


        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        initToolBar();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

//        rv=(RecyclerView) findViewById(R.id.id_ac);
//                rv.setVisibility(View.GONE);


        token = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.TOKEN);
        code = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.CODE);




//        getData(code);
//        rv.setVisibility(View.VISIBLE);




        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepick();
                rl_train.setVisibility(View.GONE);
//                rv.setVisibility(View.GONE);
//                Toast.makeText(OrderTicketActivity.this, "asdsad", Toast.LENGTH_LONG).show();

            }
        });

        txtTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTrain();
//                Toast.makeText(OrderTicketActivity.this, "asdsad", Toast.LENGTH_LONG).show();

            }
        });


        txtgerbong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCarriage(kereta);


            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
//                rv.setVisibility(View.VISIBLE);

            }
        });

    }



    private void dialogCarriage(Integer integer) {
//        btn_order.setVisibility(View.GONE);

//        Toast.makeText(OrderTicketActivity.this, txtdate.getText().toString() + " + "+integer.toString(),Toast.LENGTH_LONG).show();

        RestAPI apiEndpoint = Utils.getClient().create(RestAPI.class);
        Call<TrainValidateResponse> call = apiEndpoint.postTrainValidate(token, integer.toString(),txtdate.getText().toString());

        call.enqueue(new Callback<TrainValidateResponse>() {
            @Override
            public void onResponse(Call<TrainValidateResponse> call, Response<TrainValidateResponse> response) {

                TrainValidateResponse trainResponse = response.body();

//                Toast.makeText(OrderTicketActivity.this, trainResponse.getMessage(),Toast.LENGTH_LONG).show();
//                txtstatus.setText(trainResponse.getMessage());

                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(KebersihanUpdate.this);
                builderSingle.setTitle("Pilih gerbong");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        KebersihanUpdate.this,
                        android.R.layout.simple_list_item_1);

                final List<TrainValidate> trains = trainResponse.getData();

                for (int i = 0; i < trains.size(); i++) {

                    arrayAdapter.add(trains.get(i).getName());


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

//                                rl_chair.setVisibility(View.VISIBLE);
//                                total_chair = trains.get(which).getTotalChair();
//                                rv.setVisibility(View.VISIBLE);
                                gerbong = trains.get(which).getId();
                                ((TextView)findViewById(R.id.txt_gerbong)).setText(trains.get(which).getName());
//                                edtCountryCode.setTag("+" + countryCodeEvent.getData().get(which).getPhoneCode());
                                dialog.dismiss();
                                search.setVisibility(View.VISIBLE);
                                rl_clean.setVisibility(View.VISIBLE);
//                                getData();

                            }
                        });
                builderSingle.show();

            }

            @Override
            public void onFailure(Call<TrainValidateResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan, silahkan kontak developer", Toast.LENGTH_LONG).show();

            }
        });
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
                date = dateFormatter.format(newDate.getTime());
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

                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(KebersihanUpdate.this);
                builderSingle.setTitle("Pilih kereta");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        KebersihanUpdate.this,
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
                                rl_gerbong.setVisibility(View.VISIBLE);



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
        toolbar.setTitle("KAI Cleaning Service");


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

    private void updateData(){

        if(!chk_chair.isChecked() || !chk_coach.isChecked() ||!chk_toilet.isChecked()){

            Toast.makeText(this, "Pastikan semua tempat diperiksa", Toast.LENGTH_LONG).show();

        } else {

        //ketika aplikasi sedang mengambil data kita akan melihat progress dialog muncul
        final ProgressDialog loading = ProgressDialog.show(this, "Fetching Data","Please wait..",false,false);
        final String token = PreferenceUtils.getInstance().loadDataString(KebersihanUpdate.this, PreferenceUtils.TOKEN);

        RestAPI service = Utils.getClient().create(RestAPI.class);
        Call<CleaningResponse> call = service. postClean(token,date,kereta,gerbong,1,1,1);
        call.enqueue(new Callback<CleaningResponse>() { //Asyncronous Request
                         @Override
                         public void onResponse(Call<CleaningResponse> call, Response<CleaningResponse> response) {
                             try {
                                 loading.dismiss(); //progress dialog dihentikan

                                 Toast.makeText(KebersihanUpdate.this, "Semua tempat telah diperiksa!", Toast.LENGTH_LONG).show();

                                 finish();


                             }catch (Exception e){
                                 e.printStackTrace();
                             }
                         }

                         @Override
                         public void onFailure(Call<CleaningResponse> call, Throwable t) {
//                             passenggerList.clear();
//                             dishAdapter.notifyDataSetChanged();
                             Toast.makeText(getApplicationContext(), "Terjadi kesalahan, silahkan kontak developer",Toast.LENGTH_SHORT).show();
                             loading.dismiss(); //progress dialog dihentikan
                         }

                     }
                    );


        }
    }

}
