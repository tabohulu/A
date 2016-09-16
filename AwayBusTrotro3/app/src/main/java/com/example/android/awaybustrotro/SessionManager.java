package com.example.android.awaybustrotro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Bohulu on 13/09/2016.
 */
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;



    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AwayBusInitialSetup";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_INITIALIZED = "IsLoggedIn";


    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "user";

    // Password (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";

    // Car number (make variable public to access from outside)
    public static final String KEY_CAR_NUMBER = "number";

    // Start station (make variable public to access from outside)
    public static final String KEY_START_STATION = "start";

    // End Station (make variable public to access from outside)
    public static final String KEY_END_STATION = "end";

    //Station name (make variable public to access from outside)
    public static final String KEY_STATION_NAME="station";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String password, String plate_number, String stationName, String start, String end){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putBoolean(IS_INITIALIZED, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing password in pref
        editor.putString(KEY_PASSWORD, password);
        Log.i("SessionManager",KEY_PASSWORD+" entered");

        // Storing car number in pref
        editor.putString(KEY_CAR_NUMBER, plate_number);

        // Storing station name in pref
        editor.putString(KEY_STATION_NAME, stationName);

        // Storing start station in pref
        editor.putString(KEY_START_STATION, start);

        // Storing end station in pref
        editor.putString(KEY_END_STATION, end);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
   public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainLoginScreen.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

             // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }else{
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Main2Activity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

        }

    }

    public void checkInstantiated(){
        // Check instantiated status
        if(!this.isInitialized()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user password
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        // user licence plate number
        user.put(KEY_CAR_NUMBER, pref.getString(KEY_CAR_NUMBER, null));

        // user station name
        user.put(KEY_STATION_NAME, pref.getString(KEY_STATION_NAME, null));

        // user start station
        user.put(KEY_START_STATION, pref.getString(KEY_START_STATION, null));

        // user end station
        user.put(KEY_END_STATION, pref.getString(KEY_END_STATION, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, MainLoginScreen.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isInitialized(){
        return pref.getBoolean(IS_INITIALIZED, false);
    }

}
