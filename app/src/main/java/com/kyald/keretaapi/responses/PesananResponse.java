package com.kyald.keretaapi.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kyald.keretaapi.models.DishKategory;

/**
 * Created by acer on 04/02/2017.
 */

public class PesananResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;

    private DishKategory data;

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

}