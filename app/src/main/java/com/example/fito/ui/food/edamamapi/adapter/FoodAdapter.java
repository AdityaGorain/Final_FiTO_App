package com.example.fito.ui.food.edamamapi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fito.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{

    private Context context;
    private ArrayList<String> labelArr;
    private ArrayList<String> caloriesArr;
    private ArrayList<String> fatsArr;
    private ArrayList<String> carbsArr;
    private ArrayList<String> proteinArr;

    public FoodAdapter(Context context, ArrayList<String> labelArr, ArrayList<String> caloriesArr, ArrayList<String> fatsArr, ArrayList<String> carbsArr, ArrayList<String> proteinArr) {
        this.context = context;
        this.labelArr = labelArr;
        this.caloriesArr = caloriesArr;
        this.fatsArr = fatsArr;
        this.carbsArr = carbsArr;
        this.proteinArr = proteinArr;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_output, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {

        String s = Integer.toString(position+1);
        final DecimalFormat decimalFormat = new DecimalFormat("0.00");

        decimalFormat.format(Double.parseDouble(caloriesArr.get(position)));
        holder.serialNumber.setText(s+".");
        holder.label.setText(labelArr.get(position));
        holder.calories.setText(decimalFormat.format(Double.parseDouble(caloriesArr.get(position)))+" Kcal");
        holder.carbs.setText(decimalFormat.format(Double.parseDouble(carbsArr.get(position)))+" g");
        holder.fats.setText(decimalFormat.format(Double.parseDouble(fatsArr.get(position)))+" g");
        holder.protein.setText(decimalFormat.format(Double.parseDouble(proteinArr.get(position)))+" g");
        Log.v("i: "+position + labelArr.get(position),"Inside food apdapter");
//            holder.fats.setText("Fats:\t"+foods.get(i));
//            holder.calories.setText("Calories:\t"+foods.get(i));
//            holder.carbs.setText("Carbs:\t"+foods.get(i));
//            holder.protein.setText("Protein:\t"+foods.get(i));
//            holder.portion.setText("Portion:\t"+foods.get(i));
        }


    @Override
    public int getItemCount() {
        if(labelArr.size() != 0){
            return labelArr.size();
        }
        return 0;
    }

    class FoodViewHolder extends RecyclerView.ViewHolder{
        TextView label, calories, fats, protein, carbs, serialNumber;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            serialNumber = itemView.findViewById(R.id.text_serialNumber);
            label = itemView.findViewById(R.id.text_labelFood);
            calories = itemView.findViewById(R.id.text_caloriesFood);
            fats = itemView.findViewById(R.id.text_fatFood);
            protein = itemView.findViewById(R.id.text_proteinFood);
            carbs = itemView.findViewById(R.id.text_carbsFood);
        }
    }
}
