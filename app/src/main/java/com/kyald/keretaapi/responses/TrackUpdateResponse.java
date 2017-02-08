package com.kyald.keretaapi.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kyald.keretaapi.models.Track;
import com.kyald.keretaapi.models.TrackUpdate;

import java.util.List;

/**
 * Created by kiddie on 2/6/17.
 */

public class TrackUpdateResponse {


    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private TrackUpdate data;

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

    public TrackUpdate getData() {
        return data;
    }

    public void setData(TrackUpdate data) {
        this.data = data;
    }

}
