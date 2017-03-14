package com.kigold.android.naijaprogrammers;

import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Kigold on 3/9/2017.
 */

public class Model {
    private String username;
    private String github_url;
    private String avatar;

    private View.OnClickListener requestBtnClickListener;
    private View.OnClickListener urlLuncherClickListener;
    //Constructor

    public Model(String username, String github_url, String avatar) {
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    //
    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    public View.OnClickListener getUrlLuncherClickListener() {return urlLuncherClickListener;
    }

    public void setUrlLuncherClickListener(View.OnClickListener urlLuncherClickListener) {
        this.urlLuncherClickListener = urlLuncherClickListener;
    }

    //seed data
    public static ArrayList<Model> getTestingList() {
        ArrayList<Model> models = new ArrayList<>();
        models.add(new Model("kingsley", "https://www.github.com/kigold", "R.mipmap.naruto"));
        models.add(new Model("Celestine", "https://www.github.com/ceo", "R.mipmap.naruto"));
        /*models.add(new Model("John", "https://www.github.com/john", R.mipmap.naruto));
        models.add(new Model("gift", "https://www.github.com/gifty", R.mipmap.naruto))*/;
        return models;
    }

    //convert JsonArray to Model item
    public static ArrayList<Model> getProgrammers(JSONArray list) {
        ArrayList<Model> models = new ArrayList<>();
        for(int i = 0 ; i < list.length() ; i++){
            try {
                models.add(new Model(
                        list.getJSONObject(i).getString("login"),//username
                        list.getJSONObject(i).getString("url"),//githuburl
                        list.getJSONObject(i).getString("avatar_url")//avatar
                    ));
            }
            catch (JSONException e){

            }
        }
        return models;
    }

}
