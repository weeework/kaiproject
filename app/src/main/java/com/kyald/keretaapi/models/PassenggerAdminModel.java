package com.kyald.keretaapi.models;

/**
 * Created by acer on 10/02/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassenggerAdminModel {

    @SerializedName("ticket")
    @Expose
    private String ticket;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("train")
    @Expose
    private String train;
    @SerializedName("coach")
    @Expose
    private String coach;
    @SerializedName("chair")
    @Expose
    private Integer chair;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public Integer getChair() {
        return chair;
    }

    public void setChair(Integer chair) {
        this.chair = chair;
    }

}