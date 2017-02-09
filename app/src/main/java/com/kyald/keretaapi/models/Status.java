package com.kyald.keretaapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Status {

    @SerializedName("ordernumb")
    @Expose
    private String ordernumb;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("gerbong")
    @Expose
    private Integer gerbong;
    @SerializedName("kursi")
    @Expose
    private Integer kursi;
    @SerializedName("dish")
    @Expose
    private String dish;
    @SerializedName("price")
    @Expose
    private Integer price;

    public String getOrdernumb() {
        return ordernumb;
    }

    public void setOrdernumb(String ordernumb) {
        this.ordernumb = ordernumb;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getGerbong() {
        return gerbong;
    }

    public void setGerbong(Integer gerbong) {
        this.gerbong = gerbong;
    }

    public Integer getKursi() {
        return kursi;
    }

    public void setKursi(Integer kursi) {
        this.kursi = kursi;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}


