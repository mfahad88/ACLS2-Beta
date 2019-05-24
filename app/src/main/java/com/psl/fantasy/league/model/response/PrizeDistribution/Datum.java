
package com.psl.fantasy.league.model.response.PrizeDistribution;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("contest_win_dist_id")
    @Expose
    private Integer contestWinDistId;
    @SerializedName("formation_id")
    @Expose
    private String formationId;
    @SerializedName("winning_category")
    @Expose
    private String winningCategory;
    @SerializedName("cat_desc")
    @Expose
    private String catDesc;
    @SerializedName("dist_value")
    @Expose
    private String distValue;
    @SerializedName("dist_percent")
    @Expose
    private String distPercent;
    @SerializedName("cd")
    @Expose
    private String cd;
    @SerializedName("md")
    @Expose
    private String md;

    public Integer getContestWinDistId() {
        return contestWinDistId;
    }

    public void setContestWinDistId(Integer contestWinDistId) {
        this.contestWinDistId = contestWinDistId;
    }

    public String getFormationId() {
        return formationId;
    }

    public void setFormationId(String formationId) {
        this.formationId = formationId;
    }

    public String getWinningCategory() {
        return winningCategory;
    }

    public void setWinningCategory(String winningCategory) {
        this.winningCategory = winningCategory;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public String getDistValue() {
        return distValue;
    }

    public void setDistValue(String distValue) {
        this.distValue = distValue;
    }

    public String getDistPercent() {
        return distPercent;
    }

    public void setDistPercent(String distPercent) {
        this.distPercent = distPercent;
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

}
