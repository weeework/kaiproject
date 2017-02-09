package com.kyald.keretaapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kiddie on 2/6/17.
 */

public class TrackStatus {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("track")
    @Expose
    private List<Track> track = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Track> getTrack() {
        return track;
    }

    public void setTrack(List<Track> track) {
        this.track = track;
    }

}
