package com.kyald.keretaapi.ui;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.kyald.keretaapi.utils.PreferenceUtils;
import com.kyald.keretaapi.R;
import com.kyald.keretaapi.endpoints.RestAPI;
import com.kyald.keretaapi.utils.Utils;
import com.kyald.keretaapi.adapters.StatusAdapter;
import com.kyald.keretaapi.responses.StatusResponse;
import com.kyald.keretaapi.models.Status;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusActivity extends AppCompatActivity {
    private RecyclerView rv;
    //    private MakananAdapter dishAdapter;
    private RecyclerView.Adapter dishAdapter;
    private List<Status> Status_var = new ArrayList<>();
    Toolbar toolbar;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        code = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.CODE);

        rv=(RecyclerView) findViewById(R.id.id_ac);
        getData();
        initToolBar();
    }


    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("KAI Status");
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
    private void getData(){
        //ketika aplikasi sedang mengambil data kita akan melihat progress dialog muncul
        final ProgressDialog loading = ProgressDialog.show(this, "Fetching Data","Please wait..",false,false);
        final String token = PreferenceUtils.getInstance().loadDataString(StatusActivity.this, PreferenceUtils.TOKEN);

        RestAPI service = Utils.getClient().create(RestAPI.class);
        Call<StatusResponse> call = service.getFoodStatusUser(token,code);
        call.enqueue(new Callback<StatusResponse>() { //Asyncronous Request
                         @Override
                         public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                             try {
                                 loading.dismiss(); //progress dialog dihentikan


                                 //dapatkan hasil parsing dari method response.body()
                                 StatusResponse statusResponse=response.body();

                                 List<Status> statusList=statusResponse.getData();
//                                 Toast.makeText(getApplicationContext(), String.valueOf(statusList.size()),Toast.LENGTH_SHORT).show();
//
//                                 rv.setHasFixedSize(true);

//                                 for(int i=0;i<dishResponse.getData().)
                                 for (int i=0; i<statusList.size();i++){
//                                     Toast.makeText(getApplicationContext(), statusList.get(i).getDish(),Toast.LENGTH_SHORT).show();
//                                     makananList.add(new Makanan())
                                     Status_var.add(statusList.get(i));
//                                   makananList.add(new Makanan(1,"rdytrdy"));
                                     dishAdapter = new StatusAdapter(StatusActivity.this,Status_var);

                                     RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(StatusActivity.this);
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
                         public void onFailure(Call<StatusResponse> call, Throwable t) {

                             Toast.makeText(getApplicationContext(),"Terjadi kesalahan, silahkan kontak developer", Toast.LENGTH_LONG).show();
                             loading.dismiss(); //progress dialog dihentikan
                         }

                     }
        );
    }
}