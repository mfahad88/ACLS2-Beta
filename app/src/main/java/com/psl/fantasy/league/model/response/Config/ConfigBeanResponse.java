package com.psl.fantasy.league.model.response.Config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConfigBeanResponse {

@SerializedName("ResponseCode")
@Expose
private String responseCode;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("data")
@Expose
private List<Datum> data = null;

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

public List<Datum> getData() {
return data;
}

public void setData(List<Datum> data) {
this.data = data;
}

}