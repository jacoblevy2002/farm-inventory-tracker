package com.example.farminginventorytracker;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class FontSingleton {

    private String myFont = "";

    public ArrayList<String> getMyFontsList() {
        return myFontsList;
    }

    public void setMyFontsList(ArrayList<String> myFontsList) {
        this.myFontsList = myFontsList;
    }

    private ArrayList<String> myFontsList;

    public String getMyFont() {
        return myFont;
    }

    public void setMyFont(String myFont) {
        this.myFont = "fonts/"+myFont;
    }

    // Getter/setter

    private static FontSingleton instance;

    public static FontSingleton getInstance() {
        if (instance == null)
            instance = new FontSingleton();
        return instance;
    }

    private FontSingleton() {
        myFont = "fonts/EBGaramond-Bold.ttf";
    }
}
