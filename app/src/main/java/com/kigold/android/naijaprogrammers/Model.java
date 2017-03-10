package com.kigold.android.naijaprogrammers;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by Kigold on 3/9/2017.
 */

public class Model {
    private String username;
    private String github_url;
    private Integer avatar;

    private View.OnClickListener requestBtnClickListener;
    //Constructor

    public Model(String username, String github_url, Integer avatar) {
        this.username = username;
        this.github_url = github_url;
        this.avatar = avatar;
    }

    //Getters and Setters


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGithub_url() {
        return github_url;
    }

    public void setGithub_url(String github_url) {
        this.github_url = github_url;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    //
    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    //seed data
    public static ArrayList<Model> getTestingList() {
        ArrayList<Model> models = new ArrayList<>();
        models.add(new Model("gift", "https://www.github.com/gifty", R.mipmap.naruto));
        models.add(new Model("kingsley", "https://www.github.com/kigold", R.mipmap.naruto));
        models.add(new Model("Celestine", "https://www.github.com/ceo", R.mipmap.naruto));
        models.add(new Model("John", "https://www.github.com/john", R.mipmap.naruto));
        return models;

    }
}
