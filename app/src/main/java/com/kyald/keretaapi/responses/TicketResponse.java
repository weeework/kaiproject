package com.kyald.keretaapi.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kyald.keretaapi.models.Ticket;
import com.kyald.keretaapi.models.Train;
import com.kyald.keretaapi.models.User;

/**
 * Created by gilangpramudya on 2/5/17.
 */

public class TicketResponse {


    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("ticket")
    @Expose
    private Ticket ticket;
    @SerializedName("train")
    @Expose
    private Train train;
    @SerializedName("location")
    @Expose
    private String location;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
