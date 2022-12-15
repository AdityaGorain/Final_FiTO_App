package com.example.fito.ui.food.edamamapi.apis;

import com.example.fito.ui.food.edamamapi.response.SearchRecipes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIClient {

    @GET("/api/recipes/v2")
    Call<SearchRecipes> searchRecipes(@Query("type") String type,
                                      @Query("q") String query,
                                      @Query("app_id") String idApp,
                                      @Query("app_key") String keyApp);
}
