
package com.psl.fantasy.league.model.response.Login;

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
    @SerializedName("md")
    @Expose
    private String md;
    @SerializedName("point_balance")
    @Expose
    private String pointBalance;
    @SerializedName("budget_point")
    @Expose
    private Integer budgetPoint;

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

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String getPointBalance() {
        return pointBalance;
    }

    public void setPointBalance(String pointBalance) {
        this.pointBalance = pointBalance;
    }

    public Integer getBudgetPoint() {
        return budgetPoint;
    }

    public void setBudgetPoint(Integer budgetPoint) {
        this.budgetPoint = budgetPoint;
    }

}
