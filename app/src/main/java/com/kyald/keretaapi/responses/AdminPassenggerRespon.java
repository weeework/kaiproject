package com.kyald.keretaapi.responses;

/**
 * Created by acer on 10/02/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminPassenggerRespon {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private listpassenggeradmin data;

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

    public listpassenggeradmin getData() {
        return data;
    }

    public void setData(listpassenggeradmin data) {
        this.data = data;
    }

}