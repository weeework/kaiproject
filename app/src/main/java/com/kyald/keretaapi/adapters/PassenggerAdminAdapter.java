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
import android.widget.TextView;
import android.widget.Toast;

import com.kyald.keretaapi.R;
import com.kyald.keretaapi.endpoints.RestAPI;
import com.kyald.keretaapi.models.PassenggerAdminModel;
import com.kyald.keretaapi.responses.AdminPassenggerRespon;
import com.kyald.keretaapi.ui.PassangerActivityAdmin;
import com.kyald.keretaapi.utils.PreferenceUtils;
import com.kyald.keretaapi.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PassenggerAdminAdapter extends  RecyclerView.Adapter<PassenggerAdminAdapter.UserViewHolder> {
    private Context context ;
    private List<PassenggerAdminModel> dk;

    //------nerima data dari mai Aktivity----------------------
    //-------untuk ngelist di rcycle----------------------------
    public PassenggerAdminAdapter(Context context, List<PassenggerAdminModel> dk ){
        this.context = context ;
        this.dk=dk;

    }
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_admin_passengger, parent, false);
        return new UserViewHolder(v);
    }


    //--------------------Proses button jika di click----------------------------

    @Override
    public void onBindViewHolder(UserViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final PassenggerAdminModel sts=dk.get(position);
        holder.id_tiket.setText("Tiket : "+sts.getTicket() );
        holder.id_status.setText("Status : "+sts.getStatus() );
        holder.id_name.setText("Name : "+String.valueOf(sts.getName()) );
        holder.id_coach.setText("Gerbong : "+String.valueOf(sts.getCoach()) );
        holder.id_chair.setText("Kursi : "+String.valueOf(sts.getChair()) );
        holder.id_train.setText("Kereta : "+String.valueOf(sts.getTrain()) );


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(sts.getStatus().equals("complete")){
                        Toast.makeText(context,"Sudah diverifikasi",Toast.LENGTH_SHORT).show();
                    } else {
                                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
                                builderSingle.setTitle("Anda ingin memverifikasi penumpang?");
//                builderSingle.setMessage("Tiket berhasil dipesan \n Kode : "+ order.getCode());
                                builderSingle.setPositiveButton(
                                        "Verifikasi",
                                        new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
//                                dialog.dismiss();

                                        final ProgressDialog loading = ProgressDialog.show(context, "Fetching Data", "Please wait..", false, false);

                                        String code = PreferenceUtils.getInstance().loadDataString(context, PreferenceUtils.CODE);
                                        String token = PreferenceUtils.getInstance().loadDataString(context, PreferenceUtils.TOKEN);

                                        RestAPI service = Utils.getClient().create(RestAPI.class);
                                        Call<AdminPassenggerRespon> call = service.updatePasaanger(token, sts.getTicket());
                                        call.enqueue(new Callback<AdminPassenggerRespon>() { //Asyncronous Request
                                                         @Override
                                                         public void onResponse(Call<AdminPassenggerRespon> call, Response<AdminPassenggerRespon> response) {


                                                             dialog.dismiss();
                                                             loading.dismiss(); //progress dialog dihentikan
                                                             notifyDataSetChanged();
                                                             ((PassangerActivityAdmin) context).getData();

                                                             //dapatkan hasil parsing dari method response.body()

                                                             Toast.makeText(context, "Berhasil diverifikasi", Toast.LENGTH_LONG).show();


                                                         }

                                                         @Override
                                                         public void onFailure(Call<AdminPassenggerRespon> call, Throwable t) {

                                                             dialog.dismiss();
                                                             Toast.makeText(context, "Terjadi kesalahan, silahkan kontak developer", Toast.LENGTH_LONG).show();
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

                }
            });


    }

    @Override
    public int getItemCount() {
        return dk.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public TextView id_tiket,id_status,id_name,id_coach,id_chair,id_train;

        public UserViewHolder(View itemView) {
            super(itemView);
            id_tiket=(TextView) itemView.findViewById((R.id.id_tiket));
            id_status=(TextView) itemView.findViewById((R.id.id_status));
            id_name=(TextView) itemView.findViewById((R.id.id_name));
            id_coach=(TextView) itemView.findViewById((R.id.id_coach));
            id_chair=(TextView) itemView.findViewById((R.id.id_chair));
            id_train=(TextView) itemView.findViewById((R.id.id_train));


            mView = itemView;

        }
    }

//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//    }
}
