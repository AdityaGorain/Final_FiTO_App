package com.example.fito.ui.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import com.example.fito.R;
import com.example.fito.ui.food.edamamapi.adapter.FoodAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Objects;

public class MealActivity extends AppCompatActivity {
    AppCompatButton addBtn;
    private static final String TAG = "\t"+MealActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    ArrayList<String> labelArr = new ArrayList<>();
    FoodSelectActivity mainActivity;
    public static final String EXTRASTR = "This is the array";
    ArrayList<FoodSelectActivity> listItems;
    String email;
    String type;


    ArrayList<String> caloriesArr = new ArrayList<>();
    ArrayList<String> fatsArr = new ArrayList<>();
    ArrayList<String> proteinArr = new ArrayList<>();
    ArrayList<String> carbsArr = new ArrayList<>();

    public static String EXTRA_EMAIL = "THIS IS THE EMAIL";
    public static String EXTRA_TYPE = "This is the label";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        recyclerView = findViewById(R.id.recycler_view_food);
        getSupportActionBar().setTitle("Food Intake");
        addBtn = findViewById(R.id.addbutton);
        email = getIntent().getStringExtra(EXTRA_EMAIL);
        type = getIntent().getStringExtra(EXTRA_TYPE);
//        type = getIntent().getStringExtras("type");
        Log.v("Email: "+email, "MealAct");
        //mainActivity = mainActivity.getMainActivity();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MealActivity.this, FoodSelectActivity.class);
                intent.putExtra(FoodSelectActivity.EXTRA_EMAIL1, email);
                intent.putExtra(FoodSelectActivity.EXTRA_TYPE1, type);
                startActivity(intent);
            }
        });





    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("outside onDataChange", String.valueOf(labelArr));
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String ma = snapshot.child("email").getValue(String.class);
                    if(Objects.equals(email, ma)){
                        DatabaseReference r = snapshot.getRef();
                        Log.v("inside onDataChange before clear", String.valueOf(labelArr));
                        labelArr.clear();

                        fill_food_list(r);


                        //String stp = snapshot.child("Steps").child((String) dateCharSequence).getValue(String.class);
                        //Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });




    }


    private void fill_food_list(DatabaseReference ref){
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.YEAR);
        calendar.get(Calendar.MONTH);
        calendar.get(Calendar.DATE);

        CharSequence dateCharSequence = DateFormat.format("dd-MM-yyyy", calendar);
        ref.child("Food_Intake").child((String) dateCharSequence).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {
                    Log.v("Inside fill_food_list", String.valueOf(labelArr));
                    DataSnapshot item = items.next();
                    if(Objects.equals(type, item.child("type").getValue().toString())){
                        Log.v(item.child("food_name").getValue().toString(),"Data1");
                        labelArr.add(item.child("food_name").getValue().toString());
                        Log.v(item.child("fats").getValue().toString(),"Data1");
                        fatsArr.add(item.child("fats").getValue().toString());
                        Log.v(item.child("carbs").getValue().toString(),"Data1");
                        carbsArr.add(item.child("carbs").getValue().toString());
                        Log.v(item.child("protein").getValue().toString(),"Data1");
                        proteinArr.add(item.child("protein").getValue().toString());
                        Log.v(item.child("item_calorie").getValue().toString(),"Data1");
                        caloriesArr.add(item.child("item_calorie").getValue().toString());

                    }

                }
                Log.v("after fill_food_list", String.valueOf(labelArr));

                Log.v("before Set Adapter", String.valueOf(labelArr));
                if(labelArr.size() != 0 && carbsArr.size() != 0){
                    Log.v("Inside Set Adapter", String.valueOf(labelArr));
                    recyclerView.setLayoutManager(new LinearLayoutManager(MealActivity.this));
                    recyclerView.setHasFixedSize(true);
                    foodAdapter = new FoodAdapter(MealActivity.this, labelArr, caloriesArr, fatsArr, carbsArr, proteinArr);
                    recyclerView.setAdapter(foodAdapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    }