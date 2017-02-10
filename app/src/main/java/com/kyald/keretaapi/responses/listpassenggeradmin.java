package com.kyald.keretaapi.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kyald.keretaapi.models.PassenggerAdminModel;

import java.util.List;

/**
 * Created by acer on 10/02/2017.
 */

public class listpassenggeradmin {


        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("list")
        @Expose
        private List<PassenggerAdminModel> list = null;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<PassenggerAdminModel> getList() {
            return list;
        }

        public void setList(List<PassenggerAdminModel> list) {
            this.list = list;
        }

    }


