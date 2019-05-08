
package com.psl.fantasy.league.model.response.MyLeaderboardTab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("my_user_team_id")
    @Expose
    private Integer myUserTeamId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("contest_id")
    @Expose
    private Integer contestId;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("rem_budget")
    @Expose
    private Integer remBudget;
    @SerializedName("cd")
    @Expose
    private String cd;
    @SerializedName("md")
    @Expose
    private String md;
    @SerializedName("total_point")
    @Expose
    private String totalPoint;

    public Integer getMyUserTeamId() {
        return myUserTeamId;
    }

    public void setMyUserTeamId(Integer myUserTeamId) {
        this.myUserTeamId = myUserTeamId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getRemBudget() {
        return remBudget;
    }

    public void setRemBudget(Integer remBudget) {
        this.remBudget = remBudget;
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

    public String getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(String totalPoint) {
        this.totalPoint = totalPoint;
    }

}
