package com.kyald.keretaapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by acer on 04/02/2017.
 */

public class DishKategory {
    @SerializedName("makanan")
    @Expose
    private ArrayList<Makanan> makanan = new ArrayList<>();
    @SerializedName("minuman")
    @Expose
    private ArrayList<Minuman> minuman = new ArrayList<>();

    public ArrayList<Makanan> getMakanan() {
        return makanan;
    }

    public void setMakanan(ArrayList<Makanan> makanan) {
        this.makanan = makanan;
    }

    public ArrayList<Minuman> getMinuman() {
        return minuman;
    }

    public void setMinuman(ArrayList<Minuman> minuman) {
        this.minuman = minuman;
    }
}
