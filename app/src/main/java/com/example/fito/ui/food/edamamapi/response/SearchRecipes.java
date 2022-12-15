package com.example.fito.ui.food.edamamapi.response;

import androidx.annotation.NonNull;

import com.example.fito.ui.food.edamamapi.models.RootObjectModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchRecipes {

    private Integer from;
    private Integer to;
    private Integer count;

    //we'll get the array of recipes here
    @SerializedName("hits")
    @Expose()
    private RootObjectModel[] foodRecipes;

    public Integer getFrom() {
        return from;
    }

    public Integer getTo() {
        return to;
    }

    public Integer getCount() {
        return count;
    }

    public RootObjectModel[] getFoodRecipes() {
        return foodRecipes;
    }
    //debugging
    @NonNull
    @Override
    public String toString(){
        return "Recipes{" + "Recipes\t" + foodRecipes + "from=\t" + from + "to=\t" + to + "}";
    }
}
