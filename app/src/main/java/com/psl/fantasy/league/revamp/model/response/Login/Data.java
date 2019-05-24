
package com.psl.fantasy.league.revamp.model.response.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("myUser")
    @Expose
    private MyUser myUser;
    @SerializedName("myUsermsc")
    @Expose
    private MyUsermsc myUsermsc;

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    public MyUsermsc getMyUsermsc() {
        return myUsermsc;
    }

    public void setMyUsermsc(MyUsermsc myUsermsc) {
        this.myUsermsc = myUsermsc;
    }

}
