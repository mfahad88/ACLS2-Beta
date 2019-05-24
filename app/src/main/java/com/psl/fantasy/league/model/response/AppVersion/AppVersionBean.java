package com.psl.fantasy.league.model.response.AppVersion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppVersionBean {

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