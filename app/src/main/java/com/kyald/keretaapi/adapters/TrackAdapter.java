package com.kyald.keretaapi.adapters;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kyald.keretaapi.endpoints.RestAPI;
import com.kyald.keretaapi.models.Order;
import com.kyald.keretaapi.models.Status;
import com.kyald.keretaapi.R;
import com.kyald.keretaapi.models.Track;
import com.kyald.keretaapi.models.TrackUpdate;
import com.kyald.keretaapi.responses.OrderResponse;
import com.kyald.keretaapi.responses.TrackResponse;
import com.kyald.keretaapi.responses.TrackUpdateResponse;
import com.kyald.keretaapi.ui.OrderTicketActivity;
import com.kyald.keretaapi.utils.PreferenceUtils;
import com.kyald.keretaapi.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrackAdapter extends  RecyclerView.Adapter<TrackAdapter.UserViewHolder> {
    private Context context ;
    private List<Track> dk;
    private TextView tx;

    //------nerima data dari mai Aktivity----------------------
    //-------untuk ngelist di rcycle----------------------------
    public TrackAdapter(Context context, List<Track> dk , TextView tx){
        this.context = context ;
        this.dk=dk;
        this.tx = tx;

    }
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lokasi, parent, false);
        return new UserViewHolder(v);
    }


    //--------------------Proses button jika di click----------------------------

    @Override
    public void onBindViewHolder(UserViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Track sts=dk.get(position);
        holder.lct.setText(sts.getLocation() );
        holder.lct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String token = PreferenceUtils.getInstance().loadDataString(context, PreferenceUtils.TOKEN);
                int train_id = sts.getTrainId();
                final int track_id = sts.getId();
                final String status = "Posisi sekarang : "+sts.getLocation();

                RestAPI apiEndpoint = Utils.getClient().create(RestAPI.class);
                final Call<TrackUpdateResponse> call = apiEndpoint.postTrack(token, train_id, track_id,status);

                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please wait...");

                call.enqueue(new Callback<TrackUpdateResponse>() {
                    @Override
                    public void onResponse(Call<TrackUpdateResponse> call, Response<TrackUpdateResponse> response) {
                        progressDialog.dismiss();

                        TrackUpdateResponse orderResponse = response.body();
                        TrackUpdate trackUpdate = orderResponse.getData();
                        Toast.makeText(context, "Status updated",Toast.LENGTH_LONG).show();

                        tx.setText(trackUpdate.getStatus());
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
                    public void onFailure(Call<TrackUpdateResponse> call, Throwable t){
//                    progressDialog.dismiss();
                        Toast.makeText(context,"Terjadi kesalahan, silahkan kontak developer", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return dk.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public Button lct;

        public UserViewHolder(View itemView) {
            super(itemView);
            lct=(Button) itemView.findViewById((R.id.btnItemLokasi));



            mView = itemView;

        }
    }

//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//    }
}
