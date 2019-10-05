package com.example.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class ThemeInitializer {
    public static final String NIGHT_MODE = "NIGHT_MODE";
    private boolean isNightModeEnabled = false;

    private static ThemeInitializer instance = null;

    public static ThemeInitializer getInstance() {
        if (instance == null) {
            instance = new ThemeInitializer();
        }
        return instance;
    }

    public boolean isNightModeEnabled(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.isNightModeEnabled = preferences.getBoolean(NIGHT_MODE, false);
        return isNightModeEnabled;
    }

    public void setIsNightModeEnabled(boolean isNightModeEnabled, Context context) {
        this.isNightModeEnabled = isNightModeEnabled;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(NIGHT_MODE, isNightModeEnabled);
        editor.apply();
    }
}
