package com.example.fito.ui.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fito.R;
import com.example.fito.ui.food.edamamapi.adapter.RecipeAdapter;
import com.example.fito.ui.food.edamamapi.apis.APIClient;
import com.example.fito.ui.food.edamamapi.models.RootObjectModel;
import com.example.fito.ui.food.edamamapi.response.SearchRecipes;
import com.example.fito.ui.food.edamamapi.utils.APICredentials;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodSelectActivity extends AppCompatActivity {
    private static final String TAG = "\t"+ FoodSelectActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ArrayList<RootObjectModel> recipes;
    private RecipeAdapter adapter;
    private EditText searchView, portionInput;
    AppCompatButton buttonSubmit ;
    public float fats, protein, calories, carbs, portion;
    public String label;
    FoodSelectActivity mainActivity;
    ArrayList<FoodSelectActivity> clickedFoods;
    String email;
    String type;
    public static String EXTRA_EMAIL1 = "THIS IS THE EMAIL";
    public static String EXTRA_TYPE1 = "This is the label";
//    public void onClickFunction(){
//        Intent intent = new Intent(MainActivity.this, TestActivity.class);
//        startActivity(intent);
//    }

//    public FoodSelectActivity(){}
//
//    //    public MainActivity(ArrayList<MainActivity> arr){
////        clickedFoods = new ArrayList<MainActivity>();
////    }
//    public FoodSelectActivity(float fats, float protein, float calories, float carbs, float portion, String label){
//        this.fats = fats;
//        this.protein = protein;
//        this.calories = calories;
//        this.carbs = carbs;
//        this.portion = portion;
//        this.label = label;
//
//    }

    public FoodSelectActivity getMainActivity(){
        return mainActivity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_select);
        getSupportActionBar().setTitle("Select Food Items");
        recyclerView = findViewById(R.id.recycler_view);

        email = getIntent().getStringExtra(EXTRA_EMAIL1);
        type = getIntent().getStringExtra(EXTRA_TYPE1);
        Log.v("ok" +email,"IntentValue");

//        searchView.setQueryHint("Type here to search");
//        searchView.onActionViewCollapsed();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                searchRecipes(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        searchView = findViewById(R.id.searchView1);
        portionInput = findViewById(R.id.inputPortion);
        buttonSubmit = findViewById(R.id.foodSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchView == null ||portionInput == null){
                    Toast.makeText(FoodSelectActivity.this , "Please enter the query and portion",Toast.LENGTH_SHORT).show();
                }
                else{
                    String query = searchView.getText().toString();
                    portion = Float.valueOf(portionInput.getText().toString());
                    searchRecipes(query);
                }

            }
        });

    }
    //initialize the recipes and retrofit
    private void searchRecipes(String query){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APICredentials.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        APIClient apiClient = retrofit.create(APIClient.class);
        Call<SearchRecipes> searchRecipesCall = apiClient.searchRecipes(APICredentials.TYPE, query, APICredentials.APP_ID, APICredentials.API_KEY);
        searchRecipesCall.enqueue(new Callback<SearchRecipes>() {
            @Override
            public void onResponse(Call<SearchRecipes> call, Response<SearchRecipes> response) {
                if(response.isSuccessful() && response.body() != null){

                    recipes = new ArrayList<>(Arrays.asList(response.body().getFoodRecipes()));
                    //mainActivity = new MainActivity();

                    for(int i = 0 ; i < recipes.size() ; i++){
                        RootObjectModel rootObjectModel = recipes.get(i);

                        //causing error -> allocating more memory (out of bounds)
                        //recipes.add(rootObjectModel);
                        //dbg
                        Log.v("Tag"+TAG, "onResponse()"+rootObjectModel.getRecipeModel().getLabel());
                        Log.v("Tag"+TAG, "onResponse()"+rootObjectModel.getRecipeModel().getNutrientModel().getFats());
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(FoodSelectActivity.this));
                    recyclerView.setHasFixedSize(true);
                    Log.v("ok" +type,"FoodSelect");
                    adapter = new RecipeAdapter(FoodSelectActivity.this, recipes, email,type, portion);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<SearchRecipes> call, Throwable t) {
                Log.v("Tag"+"onFailure()",t.getMessage());
            }
        });
    }
}