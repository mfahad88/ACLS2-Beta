
package com.psl.fantasy.league.model.response.Contest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("contest_id")
    @Expose
    private Integer contestId;
    @SerializedName("series_match_id")
    @Expose
    private Integer seriesMatchId;
    @SerializedName("contest_name")
    @Expose
    private String contestName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("multiple_allowed")
    @Expose
    private String multipleAllowed;
    @SerializedName("confirmed_winning")
    @Expose
    private String confirmedWinning;
    @SerializedName("entery_fee")
    @Expose
    private String enteryFee;
    @SerializedName("pool")
    @Expose
    private Integer pool;
    @SerializedName("contest_win_dist_id")
    @Expose
    private Integer contestWinDistId;
    @SerializedName("pool_consumed")
    @Expose
    private Integer poolConsumed;
    @SerializedName("winners")
    @Expose
    private String winners;
    @SerializedName("contest_type")
    @Expose
    private String contestType;
    @SerializedName("winning_points")
    @Expose
    private String winningPoints;
    @SerializedName("is_visible")
    @Expose
    private String isVisible;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("cd")
    @Expose
    private String cd;
    @SerializedName("md")
    @Expose
    private String md;

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Integer getSeriesMatchId() {
        return seriesMatchId;
    }

    public void setSeriesMatchId(Integer seriesMatchId) {
        this.seriesMatchId = seriesMatchId;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMultipleAllowed() {
        return multipleAllowed;
    }

    public void setMultipleAllowed(String multipleAllowed) {
        this.multipleAllowed = multipleAllowed;
    }

    public String getConfirmedWinning() {
        return confirmedWinning;
    }

    public void setConfirmedWinning(String confirmedWinning) {
        this.confirmedWinning = confirmedWinning;
    }

    public String getEnteryFee() {
        return enteryFee;
    }

    public void setEnteryFee(String enteryFee) {
        this.enteryFee = enteryFee;
    }

    public Integer getPool() {
        return pool;
    }

    public void setPool(Integer pool) {
        this.pool = pool;
    }

    public Integer getContestWinDistId() {
        return contestWinDistId;
    }

    public void setContestWinDistId(Integer contestWinDistId) {
        this.contestWinDistId = contestWinDistId;
    }

    public Integer getPoolConsumed() {
        return poolConsumed;
    }

    public void setPoolConsumed(Integer poolConsumed) {
        this.poolConsumed = poolConsumed;
    }

    public String getWinners() {
        return winners;
    }

    public void setWinners(String winners) {
        this.winners = winners;
    }

    public String getContestType() {
        return contestType;
    }

    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    public String getWinningPoints() {
        return winningPoints;
    }

    public void setWinningPoints(String winningPoints) {
        this.winningPoints = winningPoints;
    }

    public String getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(String isVisible) {
        this.isVisible = isVisible;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
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
