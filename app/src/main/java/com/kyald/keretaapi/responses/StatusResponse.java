package com.kyald.keretaapi.responses;

/**
 * Created by acer on 05/02/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kyald.keretaapi.models.Status;

import java.util.List;

public class StatusResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Status> data = null;

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

    public List<Status> getData() {
        return data;
    }

    public void setData(List<Status> data) {
        this.data = data;
    }

}