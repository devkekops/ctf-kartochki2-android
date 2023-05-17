package ru.ctf.kartochki2;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;

public class PreferencesHelper {
    protected static final String PREF_NAME = "PREF_COOKIES";
    protected static final String KEY = "KEY";

    public static HashSet<String> getCookies(Context context) {
        return getStringPreference(context, PREF_NAME, KEY);
    }

    public static void setCookies(Context context, HashSet<String> cookies) {
        putStringPreference(context, PREF_NAME, KEY, cookies);
    }

    protected static HashSet<String> getStringPreference(Context context, String prefsName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(prefsName, context.MODE_PRIVATE);
        HashSet<String> value = (HashSet<String>)preferences.getStringSet(key, new HashSet<String>());
        return value;
    }

    protected static void putStringPreference(Context context, String prefsName, String key, HashSet<String> value) {
        SharedPreferences preferences = context.getSharedPreferences(prefsName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putStringSet(key, value);
        editor.commit();
    }

}
