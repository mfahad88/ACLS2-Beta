package com.psl.fantasy.league.revamp.model.response.UpdateNotification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateNotificationBean {

@SerializedName("ResponseCode")
@Expose
private String responseCode;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("data")
@Expose
private String data;

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

public String getData() {
return data;
}

public void setData(String data) {
this.data = data;
}

}