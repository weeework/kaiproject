package com.kyald.keretaapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kiddie on 2/6/17.
 */

public class TrackUpdate {

    @SerializedName("train_id")
    @Expose
    private String trainId;
    @SerializedName("track_id")
    @Expose
    private String trackId;
    @SerializedName("status")
    @Expose
    private String status;

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
