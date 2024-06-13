package com.example.todoapp;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class sharedPreferences {

    private static final String PREF_NAME = "TODO_APP";
    private static final String KEY_TODO_LIST = "TODO_LIST";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public sharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveTodoList(ArrayList<String> todoList) {
        Set<String> set = new HashSet<>(todoList);
        editor.putStringSet(KEY_TODO_LIST,set);
        editor.apply();
    }

    public ArrayList<String> getTodoList() {
        Set<String> set = sharedPreferences.getStringSet(KEY_TODO_LIST, new HashSet<>());
        return new ArrayList<>(set);
    }

}
