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
import com.kyald.keretaapi.adapters.MakananAdapter;
import com.kyald.keretaapi.models.DishKategory;
import com.kyald.keretaapi.models.Makanan;
import com.kyald.keretaapi.responses.DishResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananActivity extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView rv;
//    private MakananAdapter dishAdapter;
    private RecyclerView.Adapter dishAdapter;
    private ArrayList<Makanan> makanen = new ArrayList<>();

    public static final String ROOT_URL = "http://180.250.18.92:81/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makanan);

        initToolBar();


        rv=(RecyclerView) findViewById(R.id.id_ac);
        getData();
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

    private void getData(){
        //ketika aplikasi sedang mengambil data kita akan melihat progress dialog muncul
        final ProgressDialog loading = ProgressDialog.show(this, "Fetching Data","Please wait..",false,false);

        final String token = PreferenceUtils.getInstance().loadDataString(MakananActivity.this, PreferenceUtils.TOKEN);


        RestAPI service = Utils.getClient().create(RestAPI.class);
        Call<DishResponse> call = service. GetDataDish(token);
        call.enqueue(new Callback<DishResponse>() { //Asyncronous Request
                         @Override
                         public void onResponse(Call<DishResponse> call, Response<DishResponse> response) {
                             try {
                                 loading.dismiss(); //progress dialog dihentikan


                                 //dapatkan hasil parsing dari method response.body()
                                 DishResponse dishResponse=response.body();
                                 DishKategory dishKategory=dishResponse.getData();
                                 ArrayList<Makanan> makananList=dishKategory.getMakanan();

//                                 rv.setHasFixedSize(true);

//                                 for(int i=0;i<dishResponse.getData().)
                                 for (int i=0; i<=makananList.size();i++){
//                                     Toast.makeText(getApplicationContext(), makananList.get(i).getName(),Toast.LENGTH_SHORT).show();
//                                     makananList.add(new Makanan())
                                     makanen.add(makananList.get(i));
//                                   makananList.add(new Makanan(1,"rdytrdy"));
                                     dishAdapter = new MakananAdapter(MakananActivity.this,makanen);

                                     RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MakananActivity.this);
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
                         public void onFailure(Call<DishResponse> call, Throwable t) {

                             Toast.makeText(getApplicationContext(),"Terjadi kesalahan, silahkan kontak developer", Toast.LENGTH_LONG).show();
                             loading.dismiss(); //progress dialog dihentikan
                         }

                     }
        );


    }
}

