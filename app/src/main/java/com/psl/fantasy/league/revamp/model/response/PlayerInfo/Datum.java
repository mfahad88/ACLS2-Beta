
package com.psl.fantasy.league.revamp.model.response.PlayerInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("my_user_team_player_id")
    @Expose
    private Integer myUserTeamPlayerId;
    @SerializedName("my_user_team_id")
    @Expose
    private Integer myUserTeamId;
    @SerializedName("player_id")
    @Expose
    private Integer playerId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("is_captan")
    @Expose
    private String isCaptan;
    @SerializedName("is_vice_captan")
    @Expose
    private String isViceCaptan;
    @SerializedName("run")
    @Expose
    private Integer run;
    @SerializedName("four")
    @Expose
    private Integer four;
    @SerializedName("six")
    @Expose
    private Integer six;
    @SerializedName("fifty")
    @Expose
    private Integer fifty;
    @SerializedName("hundred")
    @Expose
    private Integer hundred;
    @SerializedName("duck")
    @Expose
    private Integer duck;
    @SerializedName("golden_duck")
    @Expose
    private Integer goldenDuck;
    @SerializedName("catches")
    @Expose
    private Integer catches;
    @SerializedName("stump")
    @Expose
    private Integer stump;
    @SerializedName("wicket")
    @Expose
    private Integer wicket;
    @SerializedName("four_wicket")
    @Expose
    private Integer fourWicket;
    @SerializedName("five_wicket")
    @Expose
    private Integer fiveWicket;
    @SerializedName("hatrick")
    @Expose
    private Integer hatrick;
    @SerializedName("maiden")
    @Expose
    private Integer maiden;
    @SerializedName("captan")
    @Expose
    private Integer captan;
    @SerializedName("vice_captan")
    @Expose
    private Integer viceCaptan;
    @SerializedName("user_point")
    @Expose
    private Integer userPoint;
    @SerializedName("player_point")
    @Expose
    private Integer playerPoint;
    @SerializedName("cd")
    @Expose
    private String cd;
    @SerializedName("md")
    @Expose
    private String md;
    @SerializedName("player_name")
    @Expose
    private String player_name;

    public Integer getMyUserTeamPlayerId() {
        return myUserTeamPlayerId;
    }

    public void setMyUserTeamPlayerId(Integer myUserTeamPlayerId) {
        this.myUserTeamPlayerId = myUserTeamPlayerId;
    }

    public Integer getMyUserTeamId() {
        return myUserTeamId;
    }

    public void setMyUserTeamId(Integer myUserTeamId) {
        this.myUserTeamId = myUserTeamId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIsCaptan() {
        return isCaptan;
    }

    public void setIsCaptan(String isCaptan) {
        this.isCaptan = isCaptan;
    }

    public String getIsViceCaptan() {
        return isViceCaptan;
    }

    public void setIsViceCaptan(String isViceCaptan) {
        this.isViceCaptan = isViceCaptan;
    }

    public Integer getRun() {
        return run;
    }

    public void setRun(Integer run) {
        this.run = run;
    }

    public Integer getFour() {
        return four;
    }

    public void setFour(Integer four) {
        this.four = four;
    }

    public Integer getSix() {
        return six;
    }

    public void setSix(Integer six) {
        this.six = six;
    }

    public Integer getFifty() {
        return fifty;
    }

    public void setFifty(Integer fifty) {
        this.fifty = fifty;
    }

    public Integer getHundred() {
        return hundred;
    }

    public void setHundred(Integer hundred) {
        this.hundred = hundred;
    }

    public Integer getDuck() {
        return duck;
    }

    public void setDuck(Integer duck) {
        this.duck = duck;
    }

    public Integer getGoldenDuck() {
        return goldenDuck;
    }

    public void setGoldenDuck(Integer goldenDuck) {
        this.goldenDuck = goldenDuck;
    }

    public Integer getCatches() {
        return catches;
    }

    public void setCatches(Integer catches) {
        this.catches = catches;
    }

    public Integer getStump() {
        return stump;
    }

    public void setStump(Integer stump) {
        this.stump = stump;
    }

    public Integer getWicket() {
        return wicket;
    }

    public void setWicket(Integer wicket) {
        this.wicket = wicket;
    }

    public Integer getFourWicket() {
        return fourWicket;
    }

    public void setFourWicket(Integer fourWicket) {
        this.fourWicket = fourWicket;
    }

    public Integer getFiveWicket() {
        return fiveWicket;
    }

    public void setFiveWicket(Integer fiveWicket) {
        this.fiveWicket = fiveWicket;
    }

    public Integer getHatrick() {
        return hatrick;
    }

    public void setHatrick(Integer hatrick) {
        this.hatrick = hatrick;
    }

    public Integer getMaiden() {
        return maiden;
    }

    public void setMaiden(Integer maiden) {
        this.maiden = maiden;
    }

    public Integer getCaptan() {
        return captan;
    }

    public void setCaptan(Integer captan) {
        this.captan = captan;
    }

    public Integer getViceCaptan() {
        return viceCaptan;
    }

    public void setViceCaptan(Integer viceCaptan) {
        this.viceCaptan = viceCaptan;
    }

    public Integer getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(Integer userPoint) {
        this.userPoint = userPoint;
    }

    public Integer getPlayerPoint() {
        return playerPoint;
    }

    public void setPlayerPoint(Integer playerPoint) {
        this.playerPoint = playerPoint;
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

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }
}
