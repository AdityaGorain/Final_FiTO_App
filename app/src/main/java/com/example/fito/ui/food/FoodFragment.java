package com.example.fito.ui.food;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.fito.R;
import com.example.fito.databinding.FragmentFoodBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Objects;

public class FoodFragment extends Fragment {
    private FragmentFoodBinding binding;
    private TextView cf;
    private TextView cc;
    private TextView cp;
    private TextView dis;
    private String email;
    ProgressBar progressBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    binding = FragmentFoodBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    progressBar = root.findViewById(R.id.progressBar2);
        return root;
}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);
        CardView bfast,lunch,dinner;
        bfast = view.findViewById(R.id.card_bfast);
        lunch = view.findViewById(R.id.card_lunch);
        dinner = view.findViewById(R.id.card_dinner);

        cf = view.findViewById(R.id.circle_fats);
        cc = view.findViewById(R.id.circle_carbs);
        cp = view.findViewById(R.id.circle_proteins);
        dis = view.findViewById(R.id.display);
        //CardView.OnClickListener meal_click = view1 -> navController.navigate(R.id.activity_meal);



        View.OnClickListener meal_click = view1 ->{
          Intent intent = new Intent(getActivity(),MealActivity.class);
          switch (view1.getId())
          {
              case R.id.card_bfast:
                  intent.putExtra(MealActivity.EXTRA_TYPE,"Breakfast");
                  //Log.v(MealActivity.EXTRA_TYPE, "foodfrag");
                  break;
              case R.id.card_lunch:
              intent.putExtra(MealActivity.EXTRA_TYPE,"Lunch");
              break;
              case R.id.card_dinner:
                  intent.putExtra(MealActivity.EXTRA_TYPE,"Dinner");
                  break;
          }

          intent.putExtra(MealActivity.EXTRA_EMAIL,email);
          startActivity(intent);
        };
        bfast.setOnClickListener(meal_click);
        lunch.setOnClickListener(meal_click);
        dinner.setOnClickListener(meal_click);
        //changed
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String ma = snapshot.child("email").getValue(String.class);
                    if(Objects.equals(email, ma)){
                        DatabaseReference r = snapshot.getRef();
                        String e = snapshot.child("calorie_goal").getValue(String.class);
                        fill_circles(r,e);


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
        //changed
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String ma = snapshot.child("email").getValue(String.class);
                    if(Objects.equals(email, ma)){
                        DatabaseReference r = snapshot.getRef();
                        String e = snapshot.child("calorie_goal").getValue(String.class);
                        fill_circles(r,e);


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

    private void fill_circles(DatabaseReference r,String e) {
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.YEAR);
        calendar.get(Calendar.MONTH);
        calendar.get(Calendar.DATE);
        CharSequence dateCharSequence = DateFormat.format("dd-MM-yyyy", calendar);

        int t[] ={0};
        r.child("Food_Intake").child((String) dateCharSequence).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                int fats = 0, carbs = 0,proteins = 0, tc = 0;
                while (items.hasNext()) {
                    DataSnapshot item = items.next();

                    String a = item.child("fats").getValue().toString();
                    String b = item.child("carbs").getValue().toString();
                    String c = item.child("protein").getValue().toString();
                    String d = item.child("item_calorie").getValue().toString();

                    if( a.isEmpty()) a = "0";
                    if( b.isEmpty()) b = "0";
                    if( c.isEmpty()) c = "0";
                    if( d.isEmpty()) d = "0";

                        fats = fats + (int) Float.parseFloat(a);
                        carbs = carbs + (int) Float.parseFloat(b);
                        proteins = proteins + (int) Float.parseFloat(c);
                        tc = tc + (int) Float.parseFloat(d);

                }
                cf.setText(String.valueOf(fats)+"g");
                cc.setText(String.valueOf(carbs)+"g");
                cp.setText(String.valueOf(proteins)+"g");
                t[0] = tc;
                dis.setText(String.valueOf(tc+"/"+e+"Kcal"));
                String _a = e;
                if( _a == null){
                    _a = "0";
                    Log.v("setting progress2", "progress");
                    Log.v("setting progress", "progress");
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                }
                else if(tc > Integer.parseInt(_a)){
                    Log.v("setting progress1", "progress");
                    progressBar.setProgress(100);
                    progressBar.setMax(100);
                }
                else{
                    Log.v("setting progress", "progress");
                    Log.v("value",String.valueOf(t[0]));
                    Log.v("value",String.valueOf(Integer.valueOf(_a)));
                    progressBar.setProgress(t[0]);
                    progressBar.setMax(Integer.valueOf(_a));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

//        r.child("Activity").child((String) dateCharSequence).addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
//               int cals = 0;
//                while (items.hasNext()) {
//                    DataSnapshot item = items.next();
//                    cals = cals + (int) Float.parseFloat(item.child("calories").getValue().toString());
//
//
//                }
//                int a = (Integer.parseInt(e) + cals);
//                //dis.setText(String.valueOf(t[0]+"/"+String.valueOf(a)+"Kcal"));
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}