package com.kyald.keretaapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gilangpramudya on 2/5/17.
 */

public class Carriage {

    @SerializedName("total_chair")
    @Expose
    private Integer totalChair;

    public Integer getTotalChair() {
        return totalChair;
    }

    public void setTotalChair(Integer totalChair) {
        this.totalChair = totalChair;
    }


}
