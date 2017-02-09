package com.kyald.keretaapi;

import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;


import com.kyald.keretaapi.adapters.TrackAdapter;
import com.kyald.keretaapi.endpoints.RestAPI;
import com.kyald.keretaapi.models.Track;
import com.kyald.keretaapi.models.TrackStatus;
import com.kyald.keretaapi.models.Train;
import com.kyald.keretaapi.responses.TrackResponse;
import com.kyald.keretaapi.responses.TrackUpdateResponse;
import com.kyald.keretaapi.responses.TrainResponse;
import com.kyald.keretaapi.ui.OrderTicketActivity;
import com.kyald.keretaapi.utils.PreferenceUtils;
import com.kyald.keretaapi.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LokasiActivity extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView rv;
    //    private MakananAdapter dishAdapter;
    private RecyclerView.Adapter dishAdapter;
    private List<Track> tracks = new ArrayList<>();
    String token,role;
    TextView status;
    int kereta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi);

        token = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.TOKEN);
        role = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.ROLE);
        status = (TextView)findViewById(R.id.status_lokasi);
        initToolBar();


        rv=(RecyclerView) findViewById(R.id.recycleLokasi);
        rv.setVisibility(View.GONE);
//        getData();


        ((TextView)findViewById(R.id.txtTraing)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTrain();
            }
        });

    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("KAI Lokasi");
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

    private void dialogTrain() {

        rv.setVisibility(View.GONE);

        RestAPI apiEndpoint = Utils.getClient().create(RestAPI.class);
        Call<TrainResponse> call = apiEndpoint.getTrain(token);
//        btn_order.setVisibility(View.GONE);

        call.enqueue(new Callback<TrainResponse>() {
            @Override
            public void onResponse(Call<TrainResponse> call, Response<TrainResponse> response) {

                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(LokasiActivity.this);
                builderSingle.setTitle("Pilih kereta");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        LokasiActivity.this,
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

                                kereta = trains.get(which).getId();
                                ((TextView)findViewById(R.id.txtTraing)).setText(trains.get(which).getName() + " | "+ trains.get(which).getLocStart()
                                        + "-"+trains.get(which).getLocEnd()+" ("+trains.get(which).getSchStart()+"-"+trains.get(which).getSchEnd()+")" );
//                                edtCountryCode.setTag("+" + countryCodeEvent.getData().get(which).getPhoneCode());
                                getData();
//                                getDataLokasi();
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

//    private void getDataLokasi() {
//
//        RestAPI apiEndpoint = Utils.getClient().create(RestAPI.class);
//        final Call<TrackUpdateResponse> call = apiEndpoint.postTrack(token, train_id, track_id,status);
//
//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Please wait...");
//
//        call.enqueue(new Callback<TrackUpdateResponse>() {
//            @Override
//            public void onResponse(Call<TrackUpdateResponse> call, Response<TrackUpdateResponse> response) {
//                progressDialog.dismiss();
//
//                TrackUpdateResponse orderResponse = response.body();
//                TrackUpdate trackUpdate = orderResponse.getData();
//                Toast.makeText(context, "Status updated",Toast.LENGTH_LONG).show();
//
//                tx.setText(trackUpdate.getStatus());
////                        if(orderResponse.getStatus()){
////
////                            Track order = orderResponse.getData();
////                            PreferenceUtils.getInstance().saveData(context, PreferenceUtils.CODE, String.valueOf(order.getCode()));
////                            PreferenceUtils.getInstance().saveData(context, PreferenceUtils.TRAIN_ID, String.valueOf(order.getUserId()));
////                            Toast.makeText(OrderTicketActivity.this, "ORDER SUCCESS!",Toast.LENGTH_LONG).show();
////                            finish();
////
////                        } else {
////                            Toast.makeText(context, orderResponse.getMessage(),Toast.LENGTH_LONG).show();
////
////                        }
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<TrackUpdateResponse> call, Throwable t){
////                    progressDialog.dismiss();
//                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//    }

    private void getData(){
        //ketika aplikasi sedang mengambil data kita akan melihat progress dialog muncul
        final ProgressDialog loading = ProgressDialog.show(this, "Fetching Data","Please wait..",false,false);

        RestAPI apiEndpoint = Utils.getClient().create(RestAPI.class);
//        RestAPI service = apiEndpoint.create(RestAPI.class);
        Call<TrackResponse> call = apiEndpoint. getTrack(token,kereta);
        call.enqueue(new Callback<TrackResponse>() { //Asyncronous Request
                         @Override
                         public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                             try {
                                 loading.dismiss(); //progress dialog dihentikan


                                 //dapatkan hasil parsing dari method response.body()
                                 TrackResponse dishResponse=response.body();
                                 TrackStatus trackstatus=dishResponse.getData();
                                 List<Track> makananList=trackstatus.getTrack();


                                 status.setText(trackstatus.getStatus());
                                 if(role.equals("user")){
                                     rv.setVisibility(View.GONE);
                                 } else {
                                     rv.setVisibility(View.VISIBLE);
                                 }

//                                 rv.setHasFixedSize(true);

//                                 for(int i=0;i<dishResponse.getData().)
                                 for (int i=0; i<=makananList.size();i++){
//                                     Toast.makeText(getApplicationContext(), String.valueOf(i),Toast.LENGTH_SHORT).show();
//                                     makananList.add(new Makanan())
                                     tracks.add(makananList.get(i));
//                                   makananList.add(new Makanan(1,"rdytrdy"));
                                     dishAdapter = new TrackAdapter(LokasiActivity.this,tracks,status);

                                     RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LokasiActivity.this);
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
                         public void onFailure(Call<TrackResponse> call, Throwable t) {

                             Toast.makeText(getApplicationContext(),"Terjadi kesalahan, silahkan kontak developer", Toast.LENGTH_LONG).show();
                             loading.dismiss(); //progress dialog dihentikan
                         }

                     }
        );


    }
}

