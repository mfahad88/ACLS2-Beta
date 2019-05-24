package com.psl.fantasy.league.revamp.model.response.Config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

@SerializedName("param_code")
@Expose
private String paramCode;
@SerializedName("param_type")
@Expose
private String paramType;
@SerializedName("desc")
@Expose
private String desc;
@SerializedName("config_val")
@Expose
private String configVal;
@SerializedName("userId")
@Expose
private Integer userId;
@SerializedName("cd")
@Expose
private String cd;
@SerializedName("md")
@Expose
private String md;

public String getParamCode() {
return paramCode;
}

public void setParamCode(String paramCode) {
this.paramCode = paramCode;
}

public String getParamType() {
return paramType;
}

public void setParamType(String paramType) {
this.paramType = paramType;
}

public String getDesc() {
return desc;
}

public void setDesc(String desc) {
this.desc = desc;
}

public String getConfigVal() {
return configVal;
}

public void setConfigVal(String configVal) {
this.configVal = configVal;
}

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
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

    @Override
    public String toString() {
        return "Datum{" +
                "paramCode='" + paramCode + '\'' +
                ", paramType='" + paramType + '\'' +
                ", desc='" + desc + '\'' +
                ", configVal='" + configVal + '\'' +
                ", userId=" + userId +
                ", cd='" + cd + '\'' +
                ", md='" + md + '\'' +
                '}';
    }
}