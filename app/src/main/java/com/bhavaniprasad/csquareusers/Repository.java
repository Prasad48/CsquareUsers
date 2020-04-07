package com.bhavaniprasad.csquareusers;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.bhavaniprasad.csquareusers.Model.UsersData;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    public String DB_NAME = "RoomDB";

    private AppDatabase noteDatabase;
    public Repository(Context context) {
        noteDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }


    public void insertTask(String first_name,
                           String last_name,
                           String email,String avatar) {

        UsersData note = new UsersData();
        note.setFirst_name(first_name);
        note.setLast_name(last_name);
        note.setEmail(email);
        note.setAvatar(avatar);

        insertTask(note);
    }

    public void insertTask(final UsersData note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().insert(note);
                return null;
            }
        }.execute();
    }

    public void deleteeverything(){

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().deleteeverything();
                return null;
            }
        }.execute();
    }

    public void updateTask(final UsersData note) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().update(note);
                return null;
            }
        }.execute();
    }

    public void deleteTask(final int id) {
        final LiveData<UsersData> task = getTask(id);
        if(task != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    noteDatabase.daoAccess().delete(task.getValue());
                    return null;
                }
            }.execute();
        }
    }

    public void deleteTask(final UsersData note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().delete(note);
                return null;
            }
        }.execute();
    }

    public LiveData<UsersData> getTask(int id) {
        return noteDatabase.daoAccess().getTask(id);
    }

    public LiveData<List<UsersData>> getTasks() {
        return noteDatabase.daoAccess().fetchAllTasks();
    }

    public LiveData<Long> getDbcolumncount(){return noteDatabase.daoAccess().getdbcolumncount();}
}
