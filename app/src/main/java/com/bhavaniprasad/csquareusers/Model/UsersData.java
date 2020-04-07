package com.bhavaniprasad.csquareusers.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "RoomDB")
public class UsersData implements Serializable {
    @PrimaryKey
    @NonNull
    private Long id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
    private int total_pages;

    public int getTotal_pages() {
        return total_pages;
    }


    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

