package com.haris.food_app;

import android.content.Context;
import android.content.SharedPreferences;

public class storage {
    private SharedPreferences.Editor editor;
    private Context _context;
    public SharedPreferences pref;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "_store";

    public storage(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setToken(String Token) {
        editor.putString("Token", Token);
        System.out.println("token saved" + " " + Token);
        editor.commit();
    }
    public String getToken() {
        return pref.getString("Token", "");
    }
}
