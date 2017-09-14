package com.example.android.lagosjavadevelopers;

/**
 * class to hold data retrieved from server
 * Created by OLANUBI J. A on 9/2/2017.
 */

public class SourceItem {
    private String name, url;
    private String avatar;
    //Constructor to initialize class
    public SourceItem(String name, String avatar, String url){
        this.name = name;
        this.avatar = avatar;
        this.url = url;
    }


    public String getName(){
        return name;
    }

    public String getAvatar(){
        return avatar;
    }

    public String getURL(){
        return url;
    }

}
