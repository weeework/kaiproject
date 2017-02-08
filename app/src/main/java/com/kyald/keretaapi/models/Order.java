package com.kyald.keretaapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gilangpramudya on 2/5/17.
 */

public class Order {

    @SerializedName("code")
    @Expose
    private Long code;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("train_id")
    @Expose
    private String trainId;
    @SerializedName("coach_id")
    @Expose
    private String coachId;
    @SerializedName("chair")
    @Expose
    private String chair;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getChair() {
        return chair;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
