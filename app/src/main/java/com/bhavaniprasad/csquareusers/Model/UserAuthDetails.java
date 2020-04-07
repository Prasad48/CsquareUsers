package com.bhavaniprasad.csquareusers.Model;


public class UserAuthDetails {

    private static UserAuthDetails single_instance = null;
    public String email,password;
    public int total_pages;

    public static UserAuthDetails getInstance()
    {
        if (single_instance == null)
            single_instance = new UserAuthDetails();

        return single_instance;
    }

}

