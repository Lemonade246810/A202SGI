package com.example.cad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Patient_SessionManager {

    SharedPreferences sharedPreferences;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;


    private static final String PREF_NAME = "PLOGIN";
    private static final String LOGIN = "PIS_LOGIN";
    public static final String ID = "PID";
    public static final String PATIENT = "PNAME";


    public Patient_SessionManager(Context context){
        this._context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }
    public void createSession(String id, String patientName){
        editor.putBoolean(LOGIN, true);
        editor.putString(ID, id);
        editor.putString(PATIENT, patientName);
        editor.apply();
    }

    public boolean isloggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){
        if(!this.isloggin()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, PatientLogin.class);
            // Closing all the Activities
            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK |i.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);

        }

    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(PATIENT, sharedPreferences.getString(PATIENT, null));

        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, PatientLogin.class);
        // Closing all the Activities
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK |i.FLAG_ACTIVITY_NEW_TASK);

        // Add new Flag to start new Activity
        i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);



    }

}
