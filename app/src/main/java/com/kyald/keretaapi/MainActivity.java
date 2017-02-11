package com.kyald.keretaapi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.kyald.keretaapi.ui.CleaningActivity;
import com.kyald.keretaapi.ui.FoodActivity;
import com.kyald.keretaapi.ui.LoginActivity;
import com.kyald.keretaapi.ui.OrderTicketActivity;
import com.kyald.keretaapi.ui.PassangerActivity;
import com.kyald.keretaapi.ui.PassangerActivityAdmin;
import com.kyald.keretaapi.ui.StatusAdminActivity;
import com.kyald.keretaapi.ui.TicketActivity;
import com.kyald.keretaapi.utils.PreferenceUtils;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView idcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.TOKEN);
        String username = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.USERNAME);
        String email = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.EMAIL);
        String role = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.ROLE);
        initToolBar();
        TextView idnama = (TextView)findViewById(R.id.idnama);
        TextView idemail = (TextView)findViewById(R.id.idemail);
        TextView idtoken = (TextView)findViewById(R.id.idtoken);
        idcode = (TextView)findViewById(R.id.id_code);
        Button   buttonInfo = (Button) findViewById(R.id.btnInfo);
        Button   buttonLokasi = (Button) findViewById(R.id.btnLokasi);
        Button   btnFoodAdmin = (Button) findViewById(R.id.btnFoodAdmin);


        if(role.equals("user")){
//            buttonInfo.setVisibility(View.GONE);
//            buttonLokasi.setVisibility(View.GONE);
        } else {
            ((TextView)findViewById(R.id.id_code)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.textView7)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.btntiket)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.btnInfo_admin)).setVisibility(View.VISIBLE);
            ((Button)findViewById(R.id.btn_food)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.btn_clean)).setVisibility(View.VISIBLE);
            btnFoodAdmin.setVisibility(View.VISIBLE);
        }




        idnama.setText(username);
        idemail.setText(email);
        idtoken.setText(token);

//        try {
//
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }

        btnFoodAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StatusAdminActivity.class));

            }
        });

        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PassangerActivity.class));
            }
        });

        buttonLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LokasiActivity.class));
            }
        });

        ((Button)findViewById(R.id.btn_logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                PreferenceUtils.getInstance().deleteAll(MainActivity.this);
                finish();

            }
        });

        ((Button)findViewById(R.id.btnInfo_admin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, PassangerActivityAdmin.class));
//                PreferenceUtils.getInstance().deleteAll(MainActivity.this);
//                finish();

            }
        });

        ((Button)findViewById(R.id.btn_clean)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, CleaningActivity.class));
//                PreferenceUtils.getInstance().deleteAll(MainActivity.this);
//                finish();

            }
        });

        ((Button)findViewById(R.id.btntiket)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, OrderTicketActivity.class));

            }
        });

        ((Button)findViewById(R.id.btn_food)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, FoodActivity.class));

            }
        });

        initToolBar();

    }

    @Override
    public void onResume(){
        super.onResume();

        String code = PreferenceUtils.getInstance().loadDataString(getApplicationContext(), PreferenceUtils.CODE);
//        Toast.makeText(this, code, Toast.LENGTH_LONG).show();

        if(code == null || code.equals("")){
            ((Button)findViewById(R.id.btn_food)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.btnInfo)).setVisibility(View.GONE);
        } else {
            ((Button)findViewById(R.id.btn_food)).setVisibility(View.VISIBLE);
            ((Button)findViewById(R.id.btnInfo)).setVisibility(View.VISIBLE);
            idcode.setText("Nomor tiket aktif : "+code);
            idcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, TicketActivity.class));
//                    intent.putExtra()
                }
            });
        }

    }

    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Anda ingin keluar?");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1);



        builderSingle.setNegativeButton(
                "Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setPositiveButton(
                "Keluar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });


        builderSingle.show();
    }


    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("KAI Order");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

    }
}
