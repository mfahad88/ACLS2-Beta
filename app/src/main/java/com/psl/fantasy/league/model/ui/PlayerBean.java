package com.psl.fantasy.league.model.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayerBean{
    private int Id;
    private String name;
    private String short_country;
    private String skill;
    private double points;
    private double credits;
    private boolean isChecked;
    private boolean isCaptain;
    private boolean isViceCaptain;



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_country() {
        return short_country;
    }

    public void setShort_country(String short_country) {
        this.short_country = short_country;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isCaptain() {
        return isCaptain;
    }

    public void setCaptain(boolean captain) {
        isCaptain = captain;
    }

    public boolean isViceCaptain() {
        return isViceCaptain;
    }

    public void setViceCaptain(boolean viceCaptain) {
        isViceCaptain = viceCaptain;
    }


    @Override
    public String toString() {
        return "PlayerBean{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", short_country='" + short_country + '\'' +
                ", skill='" + skill + '\'' +
                ", points=" + points +
                ", credits=" + credits +
                ", isChecked=" + isChecked +
                ", isCaptain=" + isCaptain +
                ", isViceCaptain=" + isViceCaptain +
                '}';
    }


}