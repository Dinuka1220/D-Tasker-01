package com.example.todoapp;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class listAdapter extends ArrayAdapter<todoData> {

    private sharedPreferences sharedPrefManager;
    private ArrayList<todoData> dataArrayList;
    public listAdapter(@NonNull Context context, ArrayList<todoData> dataArrayList) {
        super(context, R.layout.list_item,dataArrayList);
        this.dataArrayList = dataArrayList;
        this.sharedPrefManager = new sharedPreferences(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        todoData todoData = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        TextView todoItem = view.findViewById(R.id.txt_todoname);
        ImageView edit = view.findViewById(R.id.img_edit);
        ImageView delete = view.findViewById(R.id.img_delete);

        todoItem.setText(todoData.todoItem);
        edit.setImageResource(todoData.edit);
        delete.setImageResource(todoData.delete);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditItemDialog(position, todoData);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdeletetemDialog(position, todoData);
            }
        });

        return view;
    }

    private void showEditItemDialog(int position, todoData todoData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        builder.setTitle("Edit "+todoData.todoItem);

        // Inflate the custom layout/view
        final View customLayout = LayoutInflater.from(getContext()).inflate(R.layout.additemdialog, null);
        builder.setView(customLayout);

        // Set the current text to the EditText
        EditText editTextItem = customLayout.findViewById(R.id.edit_text_item);
        editTextItem.setText(todoData.todoItem);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newItem = editTextItem.getText().toString();

            if (!newItem.isEmpty()) {
                // Update the item in SharedPreferences
                ArrayList<String> currentList = sharedPrefManager.getTodoList();
                currentList.set(currentList.indexOf(todoData.todoItem), newItem);
                todoData.todoItem = newItem;
                dataArrayList.set(position, todoData);
                sharedPrefManager.saveTodoList(currentList);

                // Notify the adapter that the data has changed
                notifyDataSetChanged();

                Toast.makeText(getContext().getApplicationContext(), "Item updated: " + newItem, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext().getApplicationContext(), "Item cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getResources().getColor(R.color.dialogTitleColor));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getResources().getColor(R.color.dialogTitleColor));

    }

    private void showdeletetemDialog(int position, todoData todoData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        builder.setTitle("Delete "+todoData.todoItem);
        builder.setMessage("Really want to delete "+todoData.todoItem+" ?");

        builder.setPositiveButton("Delete", (dialog, which) -> {
            dataArrayList.remove(position);

            // Remove the item from SharedPreferences
            ArrayList<String> currentList = sharedPrefManager.getTodoList();
            currentList.remove(todoData.todoItem);
            sharedPrefManager.saveTodoList(currentList);

            // Notify the adapter that the data has changed
            notifyDataSetChanged();

            Toast.makeText(getContext().getApplicationContext(), todoData.todoItem + " deleted", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getResources().getColor(R.color.dialogTitleColor));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getResources().getColor(R.color.dialogTitleColor));

    }
}
