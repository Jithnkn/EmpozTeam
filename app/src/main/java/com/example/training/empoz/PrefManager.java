package com.example.training.empoz;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.ValueEventListener;

public class PrefManager {


        Context context;

        public PrefManager(Context context) {
            this.context = context;
        }

    public void saveLoginDetails(String username, String department) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Username", username);
        editor.putString("Department", department);
        editor.commit();
    }
    public String getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Username", "");
    }
    public String getDepart() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Department", "");
    }
    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Username", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Department", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty;
    }
    }
