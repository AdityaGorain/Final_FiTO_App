package com.example.fito.helper;

public class StepSetHelper {

    String calories_burnt, duration, step_count,distance;
    public StepSetHelper() {
    }

    public StepSetHelper(String calories_burnt, String duration, String step_count, String distance) {
        this.calories_burnt = calories_burnt;
        this.duration = duration;
        this.step_count = step_count;
        this.distance = distance;
    }

    public String getCalories_burnt() {
        return calories_burnt;
    }

    public void setCalories_burnt(String calories_burnt) {
        this.calories_burnt = calories_burnt;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStep_count() {
        return step_count;
    }

    public void setStep_count(String step_count) {
        this.step_count = step_count;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
