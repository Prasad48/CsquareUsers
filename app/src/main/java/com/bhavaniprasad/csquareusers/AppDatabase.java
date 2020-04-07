package com.bhavaniprasad.csquareusers;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bhavaniprasad.csquareusers.DAO.UsersDao;
import com.bhavaniprasad.csquareusers.Model.UsersData;

@Database(entities = {UsersData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsersDao daoAccess();
}
