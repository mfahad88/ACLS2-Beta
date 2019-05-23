
package com.psl.fantasy.league.model.response.SelectUser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyUsermsc {

    @SerializedName("msc_id")
    @Expose
    private Integer mscId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("coins_balance")
    @Expose
    private Integer coinsBalance;
    @SerializedName("wallet_type")
    @Expose
    private Integer walletType;
    @SerializedName("txn_Sts")
    @Expose
    private Integer txnSts;
    @SerializedName("cd")
    @Expose
    private String cd;
    @SerializedName("md")
    @Expose
    private String md;
    @SerializedName("budget_point")
    @Expose
    private Integer budgetPoint;

    @SerializedName("point_balance")
    @Expose
    private Integer point_balance;
    public Integer getMscId() {
        return mscId;
    }

    public void setMscId(Integer mscId) {
        this.mscId = mscId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCoinsBalance() {
        return coinsBalance;
    }

    public void setCoinsBalance(Integer coinsBalance) {
        this.coinsBalance = coinsBalance;
    }

    public Integer getWalletType() {
        return walletType;
    }

    public void setWalletType(Integer walletType) {
        this.walletType = walletType;
    }

    public Integer getTxnSts() {
        return txnSts;
    }

    public void setTxnSts(Integer txnSts) {
        this.txnSts = txnSts;
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

    public Integer getBudgetPoint() {
        return budgetPoint;
    }

    public void setBudgetPoint(Integer budgetPoint) {
        this.budgetPoint = budgetPoint;
    }

    public Integer getPoint_balance() {
        return point_balance;
    }

    public void setPoint_balance(Integer point_balance) {
        this.point_balance = point_balance;
    }
}
