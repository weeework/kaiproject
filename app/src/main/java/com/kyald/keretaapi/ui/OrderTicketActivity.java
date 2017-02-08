package com.kyald.keretaapi.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyald.keretaapi.models.Order;
import com.kyald.keretaapi.utils.PreferenceUtils;
import com.kyald.keretaapi.R;
import com.kyald.keretaapi.endpoints.RestAPI;
import com.kyald.keretaapi.utils.Utils;
import com.kyald.keretaapi.models.Train;
import com.kyald.keretaapi.models.TrainValidate;
import com.kyald.keretaapi.responses.ChairResponse;
import com.kyald.keretaapi.responses.OrderResponse;
import com.kyald.keretaapi.responses.TrainResponse;
import com.kyald.keretaapi.responses.TrainValidateResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderTicketActivity extends AppCompatActivity {

    TextView txtTrain,txtGerbong,txtChair,txtdate,txtstatus;
    int kereta;
    int gerbong;
    int chair;
    int total_chair;
    RelativeLayout rl_train, rl_gerbong, rl_chair;
    Button btn_order;

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    ProgressDialog progressDialog;
    Toolbar toolbar;

    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ticket);
        txtTrain = (TextView)findViewById(R.id.txtTraing);
        txtGerbong = (TextView)findViewById(R.id.txtgerbong);
        txtChair = (TextView)findViewById(R.id.txtchair);
        rl_train = (RelativeLayout)findViewById(R.id.rl_kereta);
        rl_gerbong = (RelativeLayout)findViewById(R.id.rl_gerbong);
        rl_chair = (RelativeLayout)findViewById(R.id.rl_chair);
        txtstatus = (TextView)findViewById(R.id.txt_status);
        btn_order = (Button)findViewById(R.id.btn_order);

        txtdate = (TextView) findViewById(R.id.txt_date);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        initToolBar();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");


        rl_gerbong.setVisibility(View.GONE);
        rl_chair.setVisibility(View.GONE);
        rl_train.setVisibility(View.GONE);
        txtstatus.setVisibility(View.GONE);
        btn_order.setVisibility(View.GONE);


        token = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.TOKEN);


        txtTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTrain();
//                Toast.makeText(OrderTicketActivity.this, "asdsad", Toast.LENGTH_LONG).show();

            }
        });

        txtGerbong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCarriage(kereta);
//                Toast.makeText(OrderTicketActivity.this, "asdsad", Toast.LENGTH_LONG).show();

            }
        });

        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepick();
//                Toast.makeText(OrderTicketActivity.this, "asdsad", Toast.LENGTH_LONG).show();

            }
        });
//
        txtChair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTotalChair(total_chair);
//                Toast.makeText(OrderTicketActivity.this, String.valueOf(total_chair), Toast.LENGTH_LONG).show();

            }
        });

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userid = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.USERID);
                String date = txtdate.getText().toString();

                progressDialog.show();
                callOrder(Integer.parseInt(userid),chair,gerbong,kereta,date);
            }
        });


    }

    private void callOrder(int userid, int chair, int gerbong, int kereta, String date){

        Log.e("KUOU",String.valueOf(token)+" \n"+
                String.valueOf(userid)+" \n"+
                String.valueOf(chair)+" \n"+
                String.valueOf(gerbong)+" \n"+
                String.valueOf(kereta)+" \n"+
                String.valueOf(date)+" \n");

        RestAPI apiEndpoint = Utils.getClient().create(RestAPI.class);
        Call<OrderResponse> call = apiEndpoint.postOrder(token,userid,gerbong,kereta,chair,date);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    progressDialog.dismiss();

                OrderResponse orderResponse = response.body();
                Toast.makeText(OrderTicketActivity.this, orderResponse.getMessage(),Toast.LENGTH_LONG).show();
                if(orderResponse.getStatus()){

                    Order order = orderResponse.getData();
                    PreferenceUtils.getInstance().saveData(getApplicationContext(), PreferenceUtils.CODE, String.valueOf(order.getCode()));
                    PreferenceUtils.getInstance().saveData(getApplicationContext(), PreferenceUtils.TRAIN_ID, String.valueOf(order.getUserId()));

                    final AlertDialog.Builder builderSingle = new AlertDialog.Builder(OrderTicketActivity.this);
                    builderSingle.setTitle("Tiket berhasil dipesan");
                    builderSingle.setMessage("Kode : "+ order.getCode());
                    builderSingle.setPositiveButton(
                            "Done!",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });


                    builderSingle.show();

//                    Toast.makeText(OrderTicketActivity.this, "ORDER SUCCESS!",Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(OrderTicketActivity.this, orderResponse.getMessage(),Toast.LENGTH_LONG).show();

                }



            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t){
//                    progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan, silahkan kontak developer", Toast.LENGTH_LONG).show();

            }
        });


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


    private void datepick(){

        rl_gerbong.setVisibility(View.GONE);
        rl_chair.setVisibility(View.GONE);
        rl_train.setVisibility(View.GONE);
        txtstatus.setVisibility(View.GONE);
        btn_order.setVisibility(View.GONE);
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
        btn_order.setVisibility(View.GONE);

        call.enqueue(new Callback<TrainResponse>() {
            @Override
            public void onResponse(Call<TrainResponse> call, Response<TrainResponse> response) {

                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(OrderTicketActivity.this);
                builderSingle.setTitle("Pilih kereta");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        OrderTicketActivity.this,
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

                                rl_gerbong.setVisibility(View.VISIBLE);
                                kereta = trains.get(which).getId();
                                ((TextView)findViewById(R.id.txtTraing)).setText(trains.get(which).getName() + " | "+ trains.get(which).getLocStart()
                                        + "-"+trains.get(which).getLocEnd()+" ("+trains.get(which).getSchStart()+"-"+trains.get(which).getSchEnd()+")" );
//                                edtCountryCode.setTag("+" + countryCodeEvent.getData().get(which).getPhoneCode());
                                dialog.dismiss();



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

    private void dialogCarriage(Integer integer) {
        btn_order.setVisibility(View.GONE);

//        Toast.makeText(OrderTicketActivity.this, txtdate.getText().toString() + " + "+integer.toString(),Toast.LENGTH_LONG).show();

        RestAPI apiEndpoint = Utils.getClient().create(RestAPI.class);
        Call<TrainValidateResponse> call = apiEndpoint.postTrainValidate(token, integer.toString(),txtdate.getText().toString());

        call.enqueue(new Callback<TrainValidateResponse>() {
            @Override
            public void onResponse(Call<TrainValidateResponse> call, Response<TrainValidateResponse> response) {

                TrainValidateResponse trainResponse = response.body();

//                Toast.makeText(OrderTicketActivity.this, trainResponse.getMessage(),Toast.LENGTH_LONG).show();
                txtstatus.setText(trainResponse.getMessage());

                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(OrderTicketActivity.this);
                builderSingle.setTitle("Pilih gerbong");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        OrderTicketActivity.this,
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

                                rl_chair.setVisibility(View.VISIBLE);
                                total_chair = trains.get(which).getTotalChair();
                                gerbong = trains.get(which).getId();
                                ((TextView)findViewById(R.id.txtgerbong)).setText(trains.get(which).getName());
//                                edtCountryCode.setTag("+" + countryCodeEvent.getData().get(which).getPhoneCode());
                                dialog.dismiss();

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



    private void dialogTotalChair(final Integer total_chair) {
        btn_order.setVisibility(View.GONE);

                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(OrderTicketActivity.this);
                builderSingle.setTitle("Pilih kursi");
                final ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(
                        OrderTicketActivity.this,
                        android.R.layout.simple_list_item_1);

               final List<Integer> trains = new ArrayList<>();

                for (int i = 0; i < total_chair; i++) {

                    trains.add(i);


                }


                for (int i = 0; i < trains.size(); i++) {

                    arrayAdapter.add(trains.get(i)+1);


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
//                                total_chair = trains.get(which).getTotalChair();
                                chair = trains.get(which)+1;
                                ((TextView)findViewById(R.id.txtchair)).setText(String.valueOf(chair));
//                                edtCountryCode.setTag("+" + countryCodeEvent.getData().get(which).getPhoneCode());

                                dialog.dismiss();

                                dialogValidateChair(chair, gerbong);

                            }
                        });
                builderSingle.show();

            }




    private void dialogValidateChair(Integer chair, Integer carriage) {

//        Toast.makeText(OrderTicketActivity.this, chair.toString() + " + "+carriage.toString(),Toast.LENGTH_LONG).show();


        RestAPI apiEndpoint = Utils.getClient().create(RestAPI.class);
        Call<ChairResponse> call = apiEndpoint.postChairValidate(token, carriage.toString(),txtdate.getText().toString(),chair.toString());

        call.enqueue(new Callback<ChairResponse>() {
            @Override
            public void onResponse(Call<ChairResponse> call, Response<ChairResponse> response) {

                ChairResponse trainResponse = response.body();

//                Toast.makeText(OrderTicketActivity.this, trainResponse.getMessage(),Toast.LENGTH_LONG).show();
                txtstatus.setText(trainResponse.getMessage());
                txtstatus.setVisibility(View.VISIBLE);
                if(trainResponse.getStatus()){
                    btn_order.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ChairResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan, silahkan kontak developer", Toast.LENGTH_LONG).show();

            }
        });
    }

}
