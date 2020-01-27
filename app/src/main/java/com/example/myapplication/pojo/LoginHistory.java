package com.example.myapplication.pojo;

import com.google.gson.annotations.SerializedName;

public class LoginHistory {
    @SerializedName("id")
    private String id;
    @SerializedName("customerEmailId")
    private String customerEmailId;
    @SerializedName("timeStamp")
    private String timeStamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerEmailId() {
        return customerEmailId;
    }

    public void setCustomerEmailId(String customerEmailId) {
        this.customerEmailId = customerEmailId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
