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
import com.kyald.keretaapi.models.Status;
import com.kyald.keretaapi.models.TrackUpdate;
import com.kyald.keretaapi.responses.OrderResponse;
import com.kyald.keretaapi.responses.TrackUpdateResponse;
import com.kyald.keretaapi.ui.StatusAdminActivity;
import com.kyald.keretaapi.utils.PreferenceUtils;
import com.kyald.keretaapi.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatusFoodAdminAdapter extends  RecyclerView.Adapter<StatusFoodAdminAdapter.UserViewHolder> {
    private Context context ;
    private List<Status> dk;

    //------nerima data dari mai Aktivity----------------------
    //-------untuk ngelist di rcycle----------------------------
    public StatusFoodAdminAdapter(Context context, List<Status> dk ){
        this.context = context ;
        this.dk=dk;

    }
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status, parent, false);
        return new UserViewHolder(v);
    }


    //--------------------Proses button jika di click----------------------------

    @Override
    public void onBindViewHolder(UserViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Status sts=dk.get(position);
        holder.nms.setText(sts.getDish() );
        holder.stts.setText(sts.getStatus() );
        holder.hrg.setText(String.valueOf(sts.getPrice()) );
        holder.pemesan.setText("Nama Pemesan : "+ sts.getNama());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,"hi",Toast.LENGTH_LONG).show();

                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
                builderSingle.setTitle("Konfimrasi pesanan diterima ?");
//                builderSingle.setMessage("Tiket berhasil dipesan \n Kode : "+ order.getCode());
                builderSingle.setPositiveButton(
                        "Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {

                                    String token = PreferenceUtils.getInstance().loadDataString(context, PreferenceUtils.TOKEN);

                                    RestAPI apiEndpoint = Utils.getClient().create(RestAPI.class);
                                    final Call<OrderResponse> call = apiEndpoint.postFoodStatus(token, sts.getOrdernumb());

                                    final ProgressDialog progressDialog;
                                    progressDialog = new ProgressDialog(context);
                                    progressDialog.setMessage("Please wait...");

                                    call.enqueue(new Callback<OrderResponse>() {
                                        @Override
                                        public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                                            progressDialog.dismiss();
                                            dialog.dismiss();
                    //                        TrackUpdateResponse orderResponse = response.body();
                    //                        TrackUpdate trackUpdate = orderResponse.getData();
                                            Toast.makeText(context, "Sukses mengupdate status",Toast.LENGTH_LONG).show();

                                            notifyDataSetChanged();
                                            ((StatusAdminActivity)context).getData();
//                                            context.getDa
//                                            notifyItemChanged(position);
                    //                        tx.setText(trackUpdate.getStatus());
                    //                        if(orderResponse.getStatus()){
                    //
                    //                            Track order = orderResponse.getData();
                    //                            PreferenceUtils.getInstance().saveData(context, PreferenceUtils.CODE, String.valueOf(order.getCode()));
                    //                            PreferenceUtils.getInstance().saveData(context, PreferenceUtils.TRAIN_ID, String.valueOf(order.getUserId()));
                    //                            Toast.makeText(OrderTicketActivity.this, "ORDER SUCCESS!",Toast.LENGTH_LONG).show();
                    //                            finish();
                    //
                    //                        } else {
                    //                            Toast.makeText(context, orderResponse.getMessage(),Toast.LENGTH_LONG).show();
                    //
                    //                        }



                                        }

                                        @Override
                                        public void onFailure(Call<OrderResponse> call, Throwable t){
                    //                    progressDialog.dismiss();
                                            Toast.makeText(context,"Terjadi kesalahan, silahkan kontak developer", Toast.LENGTH_LONG).show();

                                        }
                                    });
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

                notifyDataSetChanged();


            }
        });


    }

    @Override
    public int getItemCount() {
        return dk.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public TextView nms,stts,hrg,pemesan;

        public UserViewHolder(View itemView) {
            super(itemView);
            nms=(TextView) itemView.findViewById((R.id.id_nama_item));
            stts=(TextView) itemView.findViewById((R.id.id_status_item));
            hrg=(TextView) itemView.findViewById((R.id.id_harga_item));
            pemesan=(TextView) itemView.findViewById((R.id.id_pemesan));


            mView = itemView;

        }
    }

//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//    }
}
