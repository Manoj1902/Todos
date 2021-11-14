package com.mks.todos;

import androidx.annotation.ColorLong;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


//Define Table
@Entity (tableName = "tasks")
public class MainData implements Serializable {

//    Create Id Column
    @PrimaryKey(autoGenerate = true)
    private int ID;

//    Create Text Column
    @ColumnInfo(name = "task_text")
    private String taskText;

//    Generating Getter and Setter

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }
}
