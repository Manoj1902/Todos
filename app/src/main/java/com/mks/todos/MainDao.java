package com.mks.todos;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MainDao {

//    Insert Query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

//    Delete Query
    @Delete
    void delete(MainData mainData);

//    Delete All Query
    @Delete
    void reset(List<MainData> mainDataList);

//    Update Query
    @Query("UPDATE tasks SET task_text = :sText WHERE ID = :sID")
    void update(int sID, String sText);

//    Get All Data Query
    @Query("SELECT * FROM tasks")
    List<MainData> getAll();

}

