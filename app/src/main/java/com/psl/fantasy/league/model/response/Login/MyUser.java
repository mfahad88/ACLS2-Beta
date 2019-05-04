
package com.psl.fantasy.league.model.response.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyUser {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("pws")
    @Expose
    private String pws;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("app_version")
    @Expose
    private String appVersion;
    @SerializedName("os")
    @Expose
    private String os;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("pic_url")
    @Expose
    private String picUrl;
    @SerializedName("sts")
    @Expose
    private Integer sts;
    @SerializedName("cd")
    @Expose
    private String cd;
    @SerializedName("md")
    @Expose
    private String md;
    @SerializedName("is_updated")
    @Expose
    private String isUpdated;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getSts() {
        return sts;
    }

    public void setSts(Integer sts) {
        this.sts = sts;
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

    public String getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(String isUpdated) {
        this.isUpdated = isUpdated;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pws='" + pws + '\'' +
                ", email='" + email + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", os='" + os + '\'' +
                ", source='" + source + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", sts=" + sts +
                ", cd='" + cd + '\'' +
                ", md='" + md + '\'' +
                ", isUpdated='" + isUpdated + '\'' +
                '}';
    }
}
