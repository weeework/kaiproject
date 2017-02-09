package com.kyald.keretaapi.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kyald.keretaapi.models.Status;
import com.kyald.keretaapi.R;

import java.util.List;


public class StatusAdapter extends  RecyclerView.Adapter<StatusAdapter.UserViewHolder> {
    private Context context ;
    private List<Status> dk;

    //------nerima data dari mai Aktivity----------------------
    //-------untuk ngelist di rcycle----------------------------
    public StatusAdapter(Context context, List<Status> dk ){
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
        Status sts=dk.get(position);
        holder.nms.setText(sts.getDish() );
        holder.stts.setText(sts.getStatus() );
        holder.hrg.setText(String.valueOf(sts.getPrice()) );
        holder.pemesan.setText(sts.getNama());
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
