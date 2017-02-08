package com.kyald.keretaapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Penumpang {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("list")
    @Expose
    private List<Passengger> list = null;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Passengger> getList() {
        return list;
    }

    public void setList(List<Passengger> list) {
        this.list = list;
    }
}