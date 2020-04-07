package com.bhavaniprasad.csquareusers.DAO;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.bhavaniprasad.csquareusers.Model.UsersData;

import java.util.List;

@Dao
public interface UsersDao {
    @Insert
    public void insert(UsersData items);

    @Query("SELECT * FROM RoomDB")
    LiveData<List<UsersData>> fetchAllTasks();

    @Query("SELECT COUNT(*) FROM RoomDB")
    LiveData<Long> getdbcolumncount();


    @Query("SELECT * FROM RoomDB WHERE id =:taskId")
    LiveData<UsersData> getTask(int taskId);



    @Update
    public void update(UsersData items);
    @Delete
    public void delete(UsersData item);


    @Query("DELETE FROM RoomDB")
    public void deleteeverything();
}
