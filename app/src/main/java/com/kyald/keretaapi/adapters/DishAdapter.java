package com.kyald.keretaapi.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kyald.keretaapi.models.DishKategory;
import com.kyald.keretaapi.R;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.UserViewHolder> {
    private Context context ;
    private DishKategory dk;

    //------nerima data dari mai Aktivity----------------------
    //-------untuk ngelist di rcycle----------------------------
    public DishAdapter(Context context, DishKategory dk ){
        this.context = context ;
        this.dk=dk;

    }
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_makanandanminuman, parent, false);
        return new UserViewHolder(v);
    }


    //--------------------Proses button jika di click----------------------------
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(UserViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.Btn.setText("Makanan");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public Button Btn;

        public UserViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            Btn=(Button) mView.findViewById(R.id.id_button);


        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
