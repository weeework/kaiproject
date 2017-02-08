package com.kyald.keretaapi.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kyald.keretaapi.MainActivity;
import com.kyald.keretaapi.utils.PreferenceUtils;
import com.kyald.keretaapi.R;
import com.kyald.keretaapi.endpoints.RestAPI;
import com.kyald.keretaapi.utils.Utils;
import com.kyald.keretaapi.models.Auth;
import com.kyald.keretaapi.responses.AuthResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        PreferenceUtils.getInstance().saveData(getApplicationContext(), PreferenceUtils.TOKEN, tpoken);

        final EditText edt_uname = (EditText)findViewById(R.id.input_uname);
        final EditText edt_email = (EditText)findViewById(R.id.input_email);
        final EditText edt_pass = (EditText)findViewById(R.id.input_password);


        initToolBar();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        ((TextView)findViewById(R.id.txtregis)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_email.getText().toString().isEmpty() || edt_email.getText().toString().equals("")){
                    edt_email.setError("Please insert email");
                    return;
                }
                if(edt_pass.getText().toString().isEmpty() || edt_email.getText().toString().equals("")){
                    edt_pass.setError("Please insert password");
                    return;
                }

                if(edt_uname.getText().toString().isEmpty() || edt_email.getText().toString().equals("")){
                    edt_uname.setError("Please insert username");
                    return;
                }

                if(edt_pass.getText().toString().length() < 6){
                    edt_pass.setError("Password cannot less than 6 charaters");
                    return;
                }


                progressDialog.show();

                String uname = edt_uname.getText().toString();
                String email = edt_email.getText().toString();
                String pass = edt_pass.getText().toString();

                RestAPI apiEndpoint = Utils.getClient().create(RestAPI.class);
                Call<AuthResponse> call = apiEndpoint.postLogin(uname,email,pass);

                call.enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                        progressDialog.dismiss();
                        AuthResponse authResponse = response.body();
//                        Toast.makeText(LoginActivity.this, authResponse.getMessage(),Toast.LENGTH_LONG).show();

                        if(authResponse.getStatus()){

                            Auth auth = authResponse.getData();

                            Toast.makeText(LoginActivity.this, "Berhasil",Toast.LENGTH_LONG).show();
//                            Toast.makeText(LoginActivity.this, auth.getToken(),Toast.LENGTH_LONG).show();
                            PreferenceUtils.getInstance().saveData(getApplicationContext(), PreferenceUtils.TOKEN, auth.getToken());
                            PreferenceUtils.getInstance().saveData(getApplicationContext(), PreferenceUtils.USERNAME, auth.getName());
                            PreferenceUtils.getInstance().saveData(getApplicationContext(), PreferenceUtils.EMAIL, auth.getEmail());
                            PreferenceUtils.getInstance().saveData(getApplicationContext(), PreferenceUtils.USERID, String.valueOf(auth.getUserId()));
                            PreferenceUtils.getInstance().saveData(getApplicationContext(), PreferenceUtils.ROLE, String.valueOf(auth.getRole()));


                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        } else {

                            Toast.makeText(LoginActivity.this, authResponse.getMessage(),Toast.LENGTH_LONG).show();
                        }



                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,  "Login error please check your input",Toast.LENGTH_LONG).show();

                    }
                });

                progressDialog.show();
            }
        });

    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("KAI Login");

        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

    }
}
