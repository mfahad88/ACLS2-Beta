
package com.psl.fantasy.league.model.response.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("myUser")
    @Expose
    private MyUser myUser;
    @SerializedName("myUsermsc")
    @Expose
    private Object myUsermsc;

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    public Object getMyUsermsc() {
        return myUsermsc;
    }

    public void setMyUsermsc(Object myUsermsc) {
        this.myUsermsc = myUsermsc;
    }

}
