package com.example.fito.ui.food.edamamapi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fito.R;
import com.example.fito.helper.FoodAddClass;
import com.example.fito.ui.food.FoodSelectActivity;
import com.example.fito.ui.food.edamamapi.models.RootObjectModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

//portion size


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.FoodViewHolder>{


    private Context context;
    private ArrayList<RootObjectModel> recipes;
    public float fats1;
    public float protein1;
    public float calories1;
    public float carbs1;
    public float portion1;
    public float weight1;
    String label1;

    String email;
    String Type;


    //    MainActivity mainActivity;
    ArrayList<FoodSelectActivity> clickedFoods;
    //private MainActivity mainActivity;
    public RecipeAdapter(Context context, ArrayList<RootObjectModel> recipes, String email, String type, float portion1) {
        this.context = context;
        this.recipes = recipes;
        this.email = email;
        this.Type = type;
        this.portion1 = portion1;
        Log.v("ok" +Type,"Constructor");
//        this.fats1 = fats1;
//        this.protein1 = protein1;
//        this.calories1 = calories1;
//        this.carbs1 = carbs1;
//        this.mainActivity = mainActivity;
//        this.clickedFoods = clickedFoods;

    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_entries, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {

        final DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Glide.with(holder.itemView.getContext()).load(recipes.get(position).getRecipeModel().getImage()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        //holder.carbs.setText("Total Carbs\t"+recipes.get(position).getRecipeModel().getNutrientModel().getCarbohydrates().getQuantity());
        //holder.fats.setText("Fats\t"+recipes.get(position).getRecipeModel().getNutrientModel().getFats().getQuantity());
        float val = recipes.get(position).getRecipeModel().getNutrientModel().getProtein().getQuantity();
        Float f = new Float(val);
        Double d1 = f.doubleValue();
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        holder.protein.setText("Protein:\t"+ decimalFormat.format(d1)+"g");
        holder.label.setText("Label:\t"+recipes.get(position).getRecipeModel().getLabel());
        //holder.source.setText("Source\t"+recipes.get(position).getRecipeModel().getSource());
        //holder.yield.setText("Yield\t"+recipes.get(position).getRecipeModel().getYield());
        val = recipes.get(position).getRecipeModel().getCalories();
        Float f1 = new Float(val);
        Double d2 = f1.doubleValue();
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        holder.calories.setText("Calories:\t"+ decimalFormat.format(d2)+"Kcal");
        //holder.weight.setText("Weight\t"+recipes.get(position).getRecipeModel().getTotalWeight());
//        label1 = recipes.get(position).getRecipeModel().getLabel();
//        calories1 = recipes.get(position).getRecipeModel().getCalories();
//        carbs1 = recipes.get(position).getRecipeModel().getNutrientModel().getCarbohydrates().getQuantity();
//        fats1 = recipes.get(position).getRecipeModel().getNutrientModel().getFats().getQuantity();
//        protein1 = recipes.get(position).getRecipeModel().getNutrientModel().getProtein().getQuantity();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "hii", Toast.LENGTH_SHORT).show();
                weight1 = recipes.get(holder.getAdapterPosition()).getRecipeModel().getTotalWeight();
                float fraction = portion1/weight1;

                label1 = recipes.get(holder.getAdapterPosition()).getRecipeModel().getLabel();
                calories1 = recipes.get(holder.getAdapterPosition()).getRecipeModel().getCalories();
                carbs1 = recipes.get(holder.getAdapterPosition()).getRecipeModel().getNutrientModel().getCarbohydrates().getQuantity();
                fats1 = recipes.get(holder.getAdapterPosition()).getRecipeModel().getNutrientModel().getFats().getQuantity();
                protein1 = recipes.get(holder.getAdapterPosition()).getRecipeModel().getNutrientModel().getProtein().getQuantity();
                calories1 = fraction*calories1;
                carbs1 = fraction*carbs1;
                fats1 = fraction*fats1;
                protein1 = fraction*protein1;
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for(DataSnapshot snapshot : datasnapshot.getChildren()){
                            String ma = snapshot.child("email").getValue(String.class);
                            if(Objects.equals(email, ma)){
                                DatabaseReference r = snapshot.getRef();
                                storeFood(r);
                                //Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
//                Intent intent = new Intent(context, MealActivity.class);
//                context.startActivity(intent);
                Toast.makeText(context, "Food Added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        if(recipes != null){  //means it has content
            return recipes.size();
        }
        return 0;
    }

    private void storeFood(DatabaseReference ref) {
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.YEAR);
        calendar.get(Calendar.MONTH);
        calendar.get(Calendar.DATE);

        CharSequence dateCharSequence = android.text.format.DateFormat.format("dd-MM-yyyy", calendar);
        FoodAddClass addFood = new FoodAddClass(label1,String.valueOf(protein1),String.valueOf(fats1),String.valueOf(carbs1),String.valueOf(calories1),Type);
        ref.child("Food_Intake").child((String) dateCharSequence).push().setValue(addFood);
        ref.child("Total_Calorie_Table").child((String) dateCharSequence).child(Type).push().setValue(calories1);
    }


    class FoodViewHolder extends RecyclerView.ViewHolder{

        TextView label, source, yield, calories, weight, fats, protein, carbs, portion;
        ImageView imageView;
        AppCompatButton btn;

        public FoodViewHolder(@NonNull View itemView){

            super(itemView);
            label = itemView.findViewById(R.id.text_label);
//            source = itemView.findViewById(R.id.text_source);
//            yield = itemView.findViewById(R.id.text_yield);
            calories = itemView.findViewById(R.id.text_calories);
//            weight = itemView.findViewById(R.id.text_weight);
            imageView = itemView.findViewById(R.id.recipeImg);
//            fats = itemView.findViewById(R.id.text_fat);
            protein = itemView.findViewById(R.id.text_protein);
//            carbs = itemView.findViewById(R.id.text_carbs);

        }
    }
}
