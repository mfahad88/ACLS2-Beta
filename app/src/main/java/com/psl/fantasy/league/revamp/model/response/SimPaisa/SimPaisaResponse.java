
package com.psl.fantasy.league.revamp.model.response.SimPaisa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SimPaisaResponse {

    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("Message")
    @Expose
    private String message;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
