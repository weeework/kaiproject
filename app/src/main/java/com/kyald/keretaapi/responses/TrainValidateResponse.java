package com.kyald.keretaapi.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kyald.keretaapi.models.TrainValidate;

import java.util.List;

/**
 * Created by gilangpramudya on 2/5/17.
 */

public class TrainValidateResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<TrainValidate> data = null;

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

    public List<TrainValidate> getData() {
        return data;
    }

    public void setData(List<TrainValidate> data) {
        this.data = data;
    }

}
