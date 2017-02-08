package com.kyald.keretaapi.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kyald.keretaapi.models.Order;

/**
 * Created by gilangpramudya on 2/5/17.
 */

public class OrderResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Order data;

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

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }


}
