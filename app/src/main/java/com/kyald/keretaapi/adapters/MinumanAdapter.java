package com.kyald.keretaapi.adapters;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kyald.keretaapi.models.Minuman;
import com.kyald.keretaapi.responses.PesananResponse;
import com.kyald.keretaapi.utils.PreferenceUtils;
import com.kyald.keretaapi.R;
import com.kyald.keretaapi.endpoints.RestAPI;
import com.kyald.keretaapi.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MinumanAdapter extends RecyclerView.Adapter<MinumanAdapter.UserViewHolder> {
    private Context context ;
    private ArrayList<Minuman> dk;

    //------nerima data dari mai Aktivity----------------------
    //-------untuk ngelist di rcycle----------------------------
    public MinumanAdapter(Context context, ArrayList<Minuman> dk ){
        this.context = context ;
        this.dk=dk;

    }
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_makanandanminuman, parent, false);
        return new UserViewHolder(v);
    }


    //--------------------Proses button jika di click----------------------------

    @Override
    public void onBindViewHolder(UserViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Minuman mnm=dk.get(position);
        holder.Btn.setText(mnm.getName() + " - "+mnm.getPrice());


        final String code = PreferenceUtils.getInstance().loadDataString(context, PreferenceUtils.CODE);
        final String token = PreferenceUtils.getInstance().loadDataString(context, PreferenceUtils.TOKEN);

        holder.Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
                builderSingle.setTitle("Anda yakin memesan ini?");
//                builderSingle.setMessage("Tiket berhasil dipesan \n Kode : "+ order.getCode());
                builderSingle.setPositiveButton(
                        "Pesan",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
//                                dialog.dismiss();

                                final ProgressDialog loading = ProgressDialog.show(context, "Fetching Data","Please wait..",false,false);


                RestAPI service = Utils.getClient().create(RestAPI.class);
                Call<PesananResponse> call = service. PostDataStatus(token,code,mnm.getName(), String.valueOf(mnm.getPrice()));
                call.enqueue(new Callback<PesananResponse>() { //Asyncronous Request
                                 @Override
                                 public void onResponse(Call<PesananResponse> call, Response<PesananResponse> response) {


                                     dialog.dismiss();
                                         loading.dismiss(); //progress dialog dihentikan


                                         //dapatkan hasil parsing dari method response.body()

                                         Toast.makeText(context,"Berhasil dipesan",Toast.LENGTH_LONG).show();


                                 }

                                 @Override
                                 public void onFailure(Call<PesananResponse> call, Throwable t) {

                                     dialog.dismiss();
                                     Toast.makeText(context,"Terjadi kesalahan, silahkan kontak developer", Toast.LENGTH_LONG).show();
                                     loading.dismiss(); //progress dialog dihentikan
                                 }

                             }
                                );
                            }
                        });

                builderSingle.setNegativeButton(
                        "Tidak",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });


                builderSingle.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return dk.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public Button Btn;

        public UserViewHolder(View itemView) {
            super(itemView);

            Btn=(Button) itemView.findViewById(R.id.id_button);
            mView = itemView;

        }
    }

//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//    }
}
