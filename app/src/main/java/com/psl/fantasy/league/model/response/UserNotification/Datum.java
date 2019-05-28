package com.psl.fantasy.league.model.response.UserNotification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

@SerializedName("user_notif_id")
@Expose
private Integer userNotifId;
@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("subj")
@Expose
private String subj;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("user_notif_sts")
@Expose
private String userNotifSts;
@SerializedName("cd")
@Expose
private String cd;
@SerializedName("md")
@Expose
private String md;
@SerializedName("method_name")
@Expose
private Object methodName;

public Integer getUserNotifId() {
return userNotifId;
}

public void setUserNotifId(Integer userNotifId) {
this.userNotifId = userNotifId;
}

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
}

public String getSubj() {
return subj;
}

public void setSubj(String subj) {
this.subj = subj;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public String getUserNotifSts() {
return userNotifSts;
}

public void setUserNotifSts(String userNotifSts) {
this.userNotifSts = userNotifSts;
}

public String getCd() {
return cd;
}

public void setCd(String cd) {
this.cd = cd;
}

public String getMd() {
return md;
}

public void setMd(String md) {
this.md = md;
}

public Object getMethodName() {
return methodName;
}

public void setMethodName(Object methodName) {
this.methodName = methodName;
}

}