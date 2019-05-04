
package com.psl.fantasy.league.model.response.Player;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("team_id")
    @Expose
    private Integer teamId;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("player_id")
    @Expose
    private Integer playerId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("game")
    @Expose
    private String game;
    @SerializedName("pic_url")
    @Expose
    private String picUrl;
    @SerializedName("plays_for")
    @Expose
    private String playsFor;
    @SerializedName("skill")
    @Expose
    private String skill;
    @SerializedName("style")
    @Expose
    private String style;
    @SerializedName("runs")
    @Expose
    private String runs;
    @SerializedName("avg")
    @Expose
    private String avg;
    @SerializedName("hundreds")
    @Expose
    private String hundreds;
    @SerializedName("fifties")
    @Expose
    private String fifties;
    @SerializedName("sr")
    @Expose
    private String sr;
    @SerializedName("wkt")
    @Expose
    private String wkt;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("cd")
    @Expose
    private String cd;
    @SerializedName("md")
    @Expose
    private String md;
    @SerializedName("method_Name")
    @Expose
    private String methodName;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPlaysFor() {
        return playsFor;
    }

    public void setPlaysFor(String playsFor) {
        this.playsFor = playsFor;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getHundreds() {
        return hundreds;
    }

    public void setHundreds(String hundreds) {
        this.hundreds = hundreds;
    }

    public String getFifties() {
        return fifties;
    }

    public void setFifties(String fifties) {
        this.fifties = fifties;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "Datum{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", playerId=" + playerId +
                ", name='" + name + '\'' +
                ", game='" + game + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", playsFor='" + playsFor + '\'' +
                ", skill='" + skill + '\'' +
                ", style='" + style + '\'' +
                ", runs='" + runs + '\'' +
                ", avg='" + avg + '\'' +
                ", hundreds='" + hundreds + '\'' +
                ", fifties='" + fifties + '\'' +
                ", sr='" + sr + '\'' +
                ", wkt='" + wkt + '\'' +
                ", price='" + price + '\'' +
                ", cd='" + cd + '\'' +
                ", md='" + md + '\'' +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}
