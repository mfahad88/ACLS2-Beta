
package com.psl.fantasy.league.revamp.model.response.JoinedContest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("contest_name")
    @Expose
    private String contestName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("entry_fee")
    @Expose
    private Integer entryFee;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("total_point")
    @Expose
    private Integer totalPoint;
    @SerializedName("contest_id")
    @Expose
    private Integer contest_id;
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

    public Integer getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(Integer entryFee) {
        this.entryFee = entryFee;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Integer getContest_id() {
        return contest_id;
    }

    public void setContest_id(Integer contest_id) {
        this.contest_id = contest_id;
    }
}
