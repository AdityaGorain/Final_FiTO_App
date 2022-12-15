package com.example.fito.ui.food.edamamapi.nutrientModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NutrientModel {

    @SerializedName("CHOCDF")
    @Expose()
    private Carbohydrates carbohydrates;

    @SerializedName("PROCNT")
    @Expose()
    private Protein protein;

    @SerializedName("FAT")
    @Expose()
    private Fats fats;

    public NutrientModel(Carbohydrates carbohydrates, Protein protein, Fats fats) {
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.fats = fats;
    }

    public Carbohydrates getCarbohydrates() {
        return carbohydrates;
    }

    public Protein getProtein() {
        return protein;
    }

    public Fats getFats() {
        return fats;
    }

    public void setCarbohydrates(Carbohydrates carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    public void setFats(Fats fats) {
        this.fats = fats;
    }
}
