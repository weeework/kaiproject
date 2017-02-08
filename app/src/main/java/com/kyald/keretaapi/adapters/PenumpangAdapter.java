package com.kyald.keretaapi.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kyald.keretaapi.R;
import com.kyald.keretaapi.models.Passengger;
import com.kyald.keretaapi.models.Penumpang;
import com.kyald.keretaapi.models.Status;

import java.util.List;


public class PenumpangAdapter extends  RecyclerView.Adapter<PenumpangAdapter.UserViewHolder> {
    private Context context ;
    private List<Passengger> dk;

    //------nerima data dari mai Aktivity----------------------
    //-------untuk ngelist di rcycle----------------------------
    public PenumpangAdapter(Context context, List<Passengger> dk ){
        this.context = context ;
        this.dk=dk;

    }
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penumpang, parent, false);
        return new UserViewHolder(v);
    }


    //--------------------Proses button jika di click----------------------------

    @Override
    public void onBindViewHolder(UserViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Passengger sts=dk.get(position);
        holder.id_tiket.setText("Tiket : "+sts.getTicket() );
        holder.id_status.setText("Status : "+sts.getStatus() );
        holder.id_name.setText("Name : "+String.valueOf(sts.getName()) );
        holder.id_coach.setText("Gerbong : "+String.valueOf(sts.getCoach()) );
        holder.id_chair.setText("Kursi : "+String.valueOf(sts.getChair()) );
        holder.id_train.setText("Kereta : "+String.valueOf(sts.getTrain()) );
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
