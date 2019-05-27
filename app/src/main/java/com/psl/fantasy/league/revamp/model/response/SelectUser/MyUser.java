
package com.psl.fantasy.league.revamp.model.response.SelectUser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyUser {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("pws")
    @Expose
    private String pws;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("sts")
    @Expose
    private Integer sts;
    @SerializedName("is_updated")
    @Expose
    private String isUpdated;
    @SerializedName("app_version")
    @Expose
    private String app_version;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPws() {
        return pws;
    }

    public void setPws(String pws) {
        this.pws = pws;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getSts() {
        return sts;
    }

    public void setSts(Integer sts) {
        this.sts = sts;
    }

    public String getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(String isUpdated) {
        this.isUpdated = isUpdated;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", pws='" + pws + '\'' +
                ", email='" + email + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", sts=" + sts +
                ", isUpdated='" + isUpdated + '\'' +
                ", app_version='" + app_version + '\'' +
                '}';
    }
}
