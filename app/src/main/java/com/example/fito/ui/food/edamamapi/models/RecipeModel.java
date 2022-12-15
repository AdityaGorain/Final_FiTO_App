package com.example.fito.ui.food.edamamapi.models;

import com.example.fito.ui.food.edamamapi.image_model.RootImageModel;
import com.example.fito.ui.food.edamamapi.nutrientModel.NutrientModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeModel {

    private String label;
    private String image;
    private String source;
    private float calories;
    private float yield;
    private float totalWeight;

    @SerializedName("images")
    @Expose
    private RootImageModel rootImageMode;

    @SerializedName("totalDaily")
    @Expose()
    private NutrientModel nutrientModel;

    public RecipeModel(){
        new RecipeModel(label, image, source, calories, yield, totalWeight, rootImageMode, nutrientModel);
    }

    public RecipeModel(String label, String image, String source, float calories, float yield, float totalWeight, RootImageModel rootImageMode, NutrientModel nutrientModel) {
        this.label = label;
        this.image = image;
        this.source = source;
        this.calories = calories;
        this.yield = yield;
        this.totalWeight = totalWeight;
        this.rootImageMode = rootImageMode;
        this.nutrientModel = nutrientModel;
    }

    public String getLabel() {
        return label;
    }

    public String getImage() {
        return image;
    }

    public String getSource() {
        return source;
    }

    public float getCalories() {
        return calories;
    }

    public float getYield() {
        return yield;
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    public RootImageModel getRootImageMode() {
        return rootImageMode;
    }

    public NutrientModel getNutrientModel() {
        return nutrientModel;
    }

}
