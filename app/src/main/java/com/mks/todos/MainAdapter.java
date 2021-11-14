package com.mks.todos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

//    Initialize Variables
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

//    Create Constructor
    public MainAdapter(List<MainData> dataList, Activity context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Initialize View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        Initialize main data
        MainData data = dataList.get(position);
//        Initialize Database
        database = RoomDB.getInstance(context);
//        Set text on txtTask
        holder.txtTask.setText(data.getTaskText());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                Initialize Main Data
                MainData data1 = dataList.get(holder.getAdapterPosition());
//                Get id
                int sID = data1.getID();
//                Gwt Text
                String sText = data1.getTaskText();

//                Create Dialog
                Dialog dialog = new Dialog(context);
//                Set Content View
                dialog.setContentView(R.layout.dialog_update);
//                Initialize Width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
//                Initialize Height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //Set Layout
                dialog.getWindow().setLayout(width, height);
                //Set dialog
                dialog.show();

                //Initialize and Assign variable

                final EditText txtUpdate = dialog.findViewById(R.id.editTxt_update);
                Button btnUpdate = dialog.findViewById(R.id.btn_update);

                //Set text on taskText
                txtUpdate.setText(sText);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Dismiss dialog
                        dialog.dismiss();
                        String updateText = txtUpdate.getText().toString().trim();

                        //Update text in database
                        database.mainDao().update(sID, updateText);

                        //Notify when data is updated
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();


                    }
                });

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete Task?")
                        .setMessage("Are you sure you want to delete this task?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation

                                //Initialize main data
                                MainData d = dataList.get(holder.getAdapterPosition());

                                //Delete text from database
                                database.mainDao().delete(d);

                                //Notify when data is deleted
                                int adapterPosition = holder.getAdapterPosition();
                                dataList.remove(adapterPosition);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(adapterPosition, dataList.size());
                                Toast.makeText(context, "Successfully Deleted !!", Toast.LENGTH_SHORT).show();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "Task deletion not success !!", Toast.LENGTH_SHORT).show();
                            }
                        })
//                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        Initialize Variable
        TextView txtTask;
        ImageView btnEdit;
        ImageView btnDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            Assign variable
            txtTask = itemView.findViewById(R.id.txt_taskName);
            btnEdit= itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);


        }
    }
}
