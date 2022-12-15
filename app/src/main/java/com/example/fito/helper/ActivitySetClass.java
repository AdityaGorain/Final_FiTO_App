package com.example.fito.helper;

public class ActivitySetClass {

    String activity,calories;

    public ActivitySetClass(String activity, String calories) {
        this.activity = activity;
        this.calories = calories;
    }


    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
