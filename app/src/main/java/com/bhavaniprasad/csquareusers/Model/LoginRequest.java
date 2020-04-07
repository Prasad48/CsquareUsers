package com.bhavaniprasad.csquareusers.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginRequest {

    @SerializedName("token")
    private String token;
    @SerializedName("error")
    private String error;

    @NonNull
    @Override
    public String toString() {
        return token;
    }


    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }


}
