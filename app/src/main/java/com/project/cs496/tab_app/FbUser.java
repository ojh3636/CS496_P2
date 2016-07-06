package com.project.cs496.tab_app;

import com.strongloop.android.loopback.Model;

/**
 * Created by q on 2016-07-06.
 */
public class FbUser extends Model {
    private String Id;

    public String getFb_uid() {
        return fb_uid;
    }

    public void setFb_uid(String fb_uid) {
        this.fb_uid = fb_uid;
    }

    public String getGraph_uid() {
        return graph_uid;
    }

    public void setGraph_uid(String graph_uid) {
        this.graph_uid = graph_uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String fb_uid;
    private String graph_uid;
    private String token;
}
