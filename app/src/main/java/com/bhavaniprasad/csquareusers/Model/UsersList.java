package com.bhavaniprasad.csquareusers.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.*;

public class UsersList {

    @SerializedName("data")
    private List<UsersList> data;
    @SerializedName("total_pages")
    private int total_pages;


    public int getTotal_pages() {
        return total_pages;
    }

    public List<UsersList> getData() {
        return data;
    }


    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("avatar")
    private String avatar;

    public String getAvatar() {
        return avatar;
    }



    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

}
