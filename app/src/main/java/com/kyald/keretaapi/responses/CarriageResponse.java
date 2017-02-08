package com.kyald.keretaapi.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kyald.keretaapi.models.Carriage;

/**
 * Created by gilangpramudya on 2/5/17.
 */

public class CarriageResponse {


    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Carriage data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Carriage getData() {
        return data;
    }

    public void setData(Carriage data) {
        this.data = data;
    }

}
