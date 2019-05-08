
package com.psl.fantasy.league.model.response.Prizes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("cat_id")
    @Expose
    private Integer catId;
    @SerializedName("cat_Name")
    @Expose
    private String catName;
    @SerializedName("cat_desc")
    @Expose
    private String catDesc;
    @SerializedName("prize_id")
    @Expose
    private Integer prizeId;
    @SerializedName("prize_cat_id")
    @Expose
    private Integer prizeCatId;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("amt")
    @Expose
    private String amt;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("consume")
    @Expose
    private Integer consume;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("logo")
    @Expose
    private Object logo;
    @SerializedName("cd")
    @Expose
    private String cd;
    @SerializedName("md")
    @Expose
    private Object md;

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public Integer getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Integer prizeId) {
        this.prizeId = prizeId;
    }

    public Integer getPrizeCatId() {
        return prizeCatId;
    }

    public void setPrizeCatId(Integer prizeCatId) {
        this.prizeCatId = prizeCatId;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getConsume() {
        return consume;
    }

    public void setConsume(Integer consume) {
        this.consume = consume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getLogo() {
        return logo;
    }

    public void setLogo(Object logo) {
        this.logo = logo;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public Object getMd() {
        return md;
    }

    public void setMd(Object md) {
        this.md = md;
    }

}
