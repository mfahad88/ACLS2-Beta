package com.psl.fantasy.league.revamp.model.response.Redeem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamWiseRedeem {

    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("Message")
    @Expose
    private String message;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}