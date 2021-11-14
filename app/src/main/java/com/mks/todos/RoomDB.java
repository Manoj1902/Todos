package com.mks.todos;



import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Add database entities
@Database(entities = {MainData.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

//    Create database Instance
    private static RoomDB database;

//    Define Database Name
    private static String DATABASE_NAME = "tasksDatabase";

    public synchronized static RoomDB getInstance(Context context){
//        Check Conditions

        if (database == null){
//            When database is null
//            Initialize database
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

//    Create DAO(Data Access Objects)

    public abstract MainDao mainDao();

}


