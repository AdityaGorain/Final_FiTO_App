package com.example.fito.helper;

public class UserHelperClass {

    String email,name,age,height,weight,target_calories;
    public UserHelperClass() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserHelperClass(String email, String name, String age, String height, String weight, String target_calories) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.target_calories = target_calories;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTarget_calories() {
        return target_calories;
    }

    public void setTarget_calories(String target_calories) {
        this.target_calories = target_calories;
    }
}
