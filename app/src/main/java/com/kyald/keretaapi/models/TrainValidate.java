package com.kyald.keretaapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gilangpramudya on 2/5/17.
 */

public class TrainValidate {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("train_id")
    @Expose
    private Integer trainId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("total_chair")
    @Expose
    private Integer totalChair;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalChair() {
        return totalChair;
    }

    public void setTotalChair(Integer totalChair) {
        this.totalChair = totalChair;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

}
