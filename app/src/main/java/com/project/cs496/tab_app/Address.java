package com.project.cs496.tab_app;

import com.strongloop.android.loopback.Model;

import java.util.Date;

/**
 * Created by q on 2016-07-06.
 */
public class Address extends Model {
    private String user_id;
    private String name;
    private String picture;



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
