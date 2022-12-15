package com.example.fito.helper;

public class FoodAddClass {
    String food_name, protein, fats, carbs, item_calorie, type;
    public FoodAddClass(String food_name, String protein, String fats, String carbs, String item_calorie, String type) {
         this.food_name = food_name;
        this.protein = protein;
        this.fats = fats;
        this.carbs = carbs;
        this.item_calorie = item_calorie;
        this.type = type;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getItem_calorie() {
        return item_calorie;
    }

    public void setItem_calorie(String item_calorie) {
        this.item_calorie = item_calorie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
