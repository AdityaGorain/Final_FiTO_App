package com.example.fito.ui.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.fito.LoginActivity;
import com.example.fito.databinding.FragmentDashboardBinding;
import com.example.fito.R;
import com.example.fito.helper.StepSetHelper;
import com.example.fito.ui.Details2Activity;
import com.example.fito.ui.DetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Objects;

public class DashboardFragment extends Fragment implements SensorEventListener {

    private FragmentDashboardBinding binding;
    private TextView a;
    private TextView b;
    private TextView c;
    private TextView d;
    private TextView disp;
    private TextView name;
    private String email;
    private Button set_goal;
    private Button details;
    private AppCompatImageButton logoutBtn;
    String calories_burnt= "2700", duration="60", step_count="10000", distance="5.0";
    private static final int PHYISCAL_ACTIVITY  = 100;
    private SensorManager sensorManager;
    private Sensor mStepCounter;
    boolean running = false;
    boolean isCounterSensorPresent = false;
    float totalSteps = 0.0f;
    float initializedSteps = -1;
    float previousTotalSteps = 0.0f;
    ProgressBar progressBar;
    TextView count;
    public int currentSteps;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.ACTIVITY_RECOGNITION }, PHYISCAL_ACTIVITY);

        }
        progressBar = root.findViewById(R.id.progressBar);
        count = (TextView) root.findViewById(R.id.steps);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        logoutBtn = root.findViewById(R.id.detailsBtn);
        logoutBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                return true;
            }
        });
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            mStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;
        }else{
            count.setText("counter sensor not present");
            isCounterSensorPresent = false;
        }
        count.setText("0");
        resetSteps();
        loadData();


        //final TextView textView = binding.textView;
        //dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        a = view.findViewById(R.id.steps);
        b = view.findViewById(R.id.water);
        c = view.findViewById(R.id.sleep);
        d = view.findViewById(R.id.weight);
        disp = view.findViewById(R.id.textView6);
        set_goal = view.findViewById(R.id.button);
        details = view.findViewById(R.id.button2);
        name = getView().findViewById(R.id.UserNameText);


        final NavController navController = Navigation.findNavController(view);

        CardView steps,sleep,water_intake,weight;
        steps = view.findViewById(R.id.card_steps);
        sleep = view.findViewById(R.id.card_water_intake);
        water_intake = view.findViewById(R.id.card_sleep);
        weight = view.findViewById(R.id.card_weight_tracking);

        set_goal.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                startActivity(intent);
            }
        });

        details.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Details2Activity.class);
                startActivity(intent);
            }
        });

        OnClickListener card_click = view1 -> {
            CardView cardView = (CardView) view1;
            Bundle bundle = new Bundle();

            int pos=0;
            switch (cardView.getId())
            {
                case R.id.card_steps:pos=0; break;
                case R.id.card_water_intake: pos=1; break;
                case R.id.card_sleep:pos=2; break;
                case R.id.card_weight_tracking:pos=3; break;
            }
            bundle.putString("id", Integer.toString(pos));
            Log.d("id",Integer.toString(pos));
            navController.navigate(R.id.action_navigation_dashboard_to_stepsFragment,bundle);
        };
        steps.setOnClickListener(card_click);
        sleep.setOnClickListener(card_click);
        water_intake.setOnClickListener(card_click);
        weight.setOnClickListener(card_click);
        //navController.navigateUp();
        //navController.clearBackStack(R.id.nav);

        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.YEAR);
        calendar.get(Calendar.MONTH);
        calendar.get(Calendar.DATE);

        CharSequence dateCharSequence = android.text.format.DateFormat.format("dd-MM-yyyy", calendar);
        //System.out.println(dateCharSequence);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String ma = snapshot.child("email").getValue(String.class);
                    if(Objects.equals(email, ma)){
                        DatabaseReference r = snapshot.getRef();

                        String w = snapshot.child("weight").getValue(String.class);
                        String wa = snapshot.child("water").getValue(String.class);
                        String sl = snapshot.child("sleep").getValue(String.class);
                        //String stps = snapshot.child("steps").getValue(String.class);
                        String e = snapshot.child("calorie_burn_goal").getValue(String.class);

                        //r.child("Misc").child((String) dateCharSequence).child("Water_Intake").setValue("3");
                        //r.child("Misc").child((String) dateCharSequence).child("Sleep_Duration").setValue("8");


                        String wat = snapshot.child("Misc").child((String) dateCharSequence).child("Water_Intake").getValue(String.class);
                        b.setText(wat+ "/"+wa);
                        String sle = snapshot.child("Misc").child((String) dateCharSequence).child("Sleep_Duration").getValue(String.class);
                        c.setText(sle+"/"+sl);
                        String wgt = snapshot.child("weight").getValue(String.class);
                        d.setText(wgt+"/"+w);
                        put_cals(r,e);
                        //Toast.makeText(Dashboard.this,"Welcome",Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void put_cals(DatabaseReference r,String e) {

        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.YEAR);
        calendar.get(Calendar.MONTH);
        calendar.get(Calendar.DATE);


        CharSequence dateCharSequence = DateFormat.format("dd-MM-yyyy", calendar);
//        r.child("Food_Intake").child((String) dateCharSequence).addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
//                int sum = 0;
//                while (items.hasNext()) {
//                    DataSnapshot item = items.next();
//                    sum = sum + (int) Float.parseFloat(item.child("item_calorie").getValue().toString());
//                }
//
//                s[0] = sum;
//                //disp.setText(String.valueOf(sum)+"/"+e+" Kcal");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });

        r.child("Activity").child((String) dateCharSequence).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                int cals = 0;
                while (items.hasNext()) {
                    DataSnapshot item = items.next();
                    String s  =item.child("calories").getValue().toString();

                    if( s.isEmpty()) s = "0";
                    cals = cals + (int) Float.parseFloat(s);


                }
                int a = ( cals + (int)(currentSteps * 0.045));
                disp.setText(String.valueOf(String.valueOf(a)+"/"+e+" Kcal"));
                String _a = "";
                _a=e;

                if( _a == null){
                    _a = "0";
                    Log.v("setting progress dashboard", "0");
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                }
                else if(a > Integer.parseInt(_a)){
                    Log.v("setting progress dashboard", "100");
                    progressBar.setProgress(100);
                    progressBar.setMax(100);
                }
                else{
                    Log.v("setting progress dashboard", String.valueOf(a));
                    progressBar.setProgress(a);
                    progressBar.setMax(Integer.valueOf(_a));
//                    progressBar.setProgress(100);
//                    progressBar.setMax(100);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /////////////////////////////Step counter code
    private void resetSteps(){

        previousTotalSteps = totalSteps;
        //count.setText("0");
        saveData();
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("Key1", previousTotalSteps);
        editor.apply();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        float savedNumber = sharedPreferences.getFloat("key1", 0.0f);
        previousTotalSteps = savedNumber;
    }

    @Override
    public void onResume() {
        super.onResume();
        //changed
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.YEAR);
        calendar.get(Calendar.MONTH);
        calendar.get(Calendar.DATE);

        CharSequence dateCharSequence = android.text.format.DateFormat.format("dd-MM-yyyy", calendar);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String ma = snapshot.child("email").getValue(String.class);
                    if(Objects.equals(email, ma)){
                        DatabaseReference r = snapshot.getRef();

                        String w = snapshot.child("weight").getValue(String.class);
                        String wa = snapshot.child("water").getValue(String.class);
                        String sl = snapshot.child("sleep").getValue(String.class);
                        //String stps = snapshot.child("steps").getValue(String.class);
                        String e = snapshot.child("calorie_burn_goal").getValue(String.class);

                        //r.child("Misc").child((String) dateCharSequence).child("Water_Intake").setValue("3");
                        //r.child("Misc").child((String) dateCharSequence).child("Sleep_Duration").setValue("8");


                        String wat = snapshot.child("Misc").child((String) dateCharSequence).child("Water_Intake").getValue(String.class);
                        b.setText(wat+ "/"+wa);
                        String sle = snapshot.child("Misc").child((String) dateCharSequence).child("Sleep_Duration").getValue(String.class);
                        c.setText(sle+"/"+sl);
                        String wgt = snapshot.child("weight").getValue(String.class);
                        d.setText(wgt+"/"+w);
                        put_cals(r,e);
                        //Toast.makeText(Dashboard.this,"Welcome",Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        //changed
        running = true;
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            sensorManager.registerListener(this,mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            sensorManager.unregisterListener(this, mStepCounter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PHYISCAL_ACTIVITY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == mStepCounter && running){

            if(initializedSteps == -1){  //means we don't know the initial value of steps
                initializedSteps = event.values[0];
            }
            totalSteps =  event.values[0];
            //resetSteps();   //if database steps is 0
            currentSteps = (int) totalSteps - (int) previousTotalSteps;
            set_steps(currentSteps);
            Log.v("currentSteps"+totalSteps,"onSensorChanged");
            Log.v("previousTotalSteps"+previousTotalSteps,"onSensorChanged");
            Log.v("currentSteps"+currentSteps,"onSensorChanged");
            count.setText(String.valueOf(currentSteps));
        }
    }

    private void set_steps(int currentSteps) {

        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.YEAR);
        calendar.get(Calendar.MONTH);
        calendar.get(Calendar.DATE);

        CharSequence dateCharSequence = android.text.format.DateFormat.format("dd-MM-yyyy", calendar);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String ma = snapshot.child("email").getValue(String.class);
                    if(Objects.equals(email, ma)){
                        name.setText("Hi, " + snapshot.child("name").getValue(String.class));
                        DatabaseReference r = snapshot.getRef();


                        StepSetHelper putSteps = new StepSetHelper(String.valueOf(currentSteps * 0.045), duration, String.valueOf(currentSteps), distance);
                        r.child("Steps").child((String) dateCharSequence).setValue(putSteps);
                        //String stp = snapshot.child("Steps").child((String) dateCharSequence).getValue(String.class);
                        //a.setText(stp);
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
   /* public void card_steps(View view){
        Intent intent = new Intent(this,StepsActivity.class);
        startActivity(intent);

    }

    public void card_sleep(View view){
        Intent intent = new Intent(this,WaterActivity.class);
        startActivity(intent);

    }

    public void card_water_intake(View view){
        Intent intent = new Intent(this,WaterActivity.class);
        startActivity(intent);

    }

    public void card_weight_tracking(View view){
        Intent intent = new Intent(this,WaterActivity.class);
        startActivity(intent);

    }

    */
}