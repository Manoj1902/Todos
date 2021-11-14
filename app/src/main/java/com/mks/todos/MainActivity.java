package com.mks.todos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText txtTask;
    FloatingActionButton btnAdd;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;

    MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTask = findViewById(R.id.editTxt_task);
        btnAdd = findViewById(R.id.btnAddTask);
        recyclerView = findViewById(R.id.recyclerView);

        //Initialize database
        database = RoomDB.getInstance(this);

        //Store database value in data list
        dataList = database.mainDao().getAll();


        //Initialize Linear Layout Manager
        linearLayoutManager = new LinearLayoutManager(this);

        //Set layout Manager
        recyclerView.setLayoutManager(linearLayoutManager);

        //Initialize adapter
        adapter = new MainAdapter(dataList, MainActivity.this);

        //Set Adapter
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get string from txtTask
                String sText = txtTask.getText().toString().trim();

                //Check Conditions
                if (!sText.equals("")) {
                    //When text is not empty
                    //Initialize main data
                    MainData data = new MainData();

                    //Set text on main data
                    data.setTaskText(sText);

                    //Insert Text in database
                    database.mainDao().insert(data);

                    //Clear text box
                    txtTask.setText("");

                    //Notify when data is inserted
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }
}