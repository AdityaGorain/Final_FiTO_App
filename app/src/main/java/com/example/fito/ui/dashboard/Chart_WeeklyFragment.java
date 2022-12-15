package com.example.fito.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fito.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
public class Chart_WeeklyFragment extends Fragment {

        String email;
        int card_pos;
        public Chart_WeeklyFragment(){}
        public Chart_WeeklyFragment(int pos) {
            this.card_pos = pos;
            // Required empty public constructor
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            return inflater.inflate(R.layout.fragment_chart_weekly, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            LineChart lineChart;
            lineChart = view.findViewById(R.id.chart_1);
Log.d("id inside chart weekly",Integer.toString(card_pos));
            //LineDataSet lineDataSet = new LineDataSet(dataValues1(),"Data Set 1");

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    for(DataSnapshot snapshot : datasnapshot.getChildren()){
                        String ma = snapshot.child("email").getValue(String.class);
                        if(Objects.equals(email, ma)){

                            //r.child("Steps").child((String) dateCharSequence);
                            ArrayList<Entry> dataVal1 = new ArrayList<Entry>();
                            for(int i=-6; i<1; i++){
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.get(Calendar.YEAR);
                                calendar1.get(Calendar.MONTH);
                                calendar1.get(Calendar.DATE) ;
                                calendar1.add(Calendar.DAY_OF_MONTH,i);
                                CharSequence dateCharSequence = android.text.format.DateFormat.format("dd-MM-yyyy", calendar1);
                                switch (card_pos){
                                    case 0:
                                        if(snapshot.child("Steps").child((String) dateCharSequence).exists()){
                                            int a = i+6;
                                            int b = Integer.parseInt(snapshot.child("Steps").child((String) dateCharSequence).child("step_count").getValue(String.class));
                                            Entry e = new Entry(a,b);
                                            dataVal1.add(e);


                                        }
                                        break;
                                    case 1:
                                        if(snapshot.child("Misc").child((String) dateCharSequence).child("Water_Intake").exists()){
                                            int a = i+6;
                                            int b = Integer.parseInt(snapshot.child("Misc").child((String) dateCharSequence).child("Water_Intake").getValue(String.class));
                                            Entry e = new Entry(a,b);
                                            dataVal1.add(e);


                                        }
                                        break;
                                    case 2:
                                        if(snapshot.child("Misc" +
                                                "").child((String) dateCharSequence).child("Sleep_Duration").exists()){
                                            int a = i+6;
                                            int b = Integer.parseInt(snapshot.child("Misc").child((String) dateCharSequence).child("Sleep_Duration").getValue(String.class));
                                            Entry e = new Entry(a,b);
                                            dataVal1.add(e);


                                        }
                                        break;
                                    case 3:
                                        if(snapshot.child("Misc").child((String) dateCharSequence).child("weight").exists()){
                                            int a = i+6;
                                            int b = Integer.parseInt(snapshot.child("Misc").child((String) dateCharSequence).child("weight").getValue(String.class));
                                            Entry e = new Entry(a,b);
                                            dataVal1.add(e);


                                        }
                                        break;
                                }
                            }
                            LineDataSet lineDataSet = new LineDataSet(dataVal1,"Data Set 1");
                            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                            dataSets.add(lineDataSet);

                            LineData data = new LineData(dataSets);
                            lineChart.setData(data);
                            lineChart.invalidate();

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
        private ArrayList<Entry> dataValues1(){
            ArrayList<Entry> dataVal = new ArrayList<Entry>();
//        int a[] ={1,2,3,4,5,6,7,8,9,10};
//        int b[] = {8,7,8,9,7,6,4,6,9,12};
//        for(int i=0;i<10;i++)
//        {
//            dataVal.add(new Entry(a[i],b[i]));
//            Log.d("datapoints",Integer.toString(a[i]));
//        }



            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    for(DataSnapshot snapshot : datasnapshot.getChildren()){
                        String ma = snapshot.child("email").getValue(String.class);
                        if(Objects.equals(email, ma)){

                            //r.child("Steps").child((String) dateCharSequence);
                            ArrayList<Entry> dataVal1 = new ArrayList<Entry>();
                            for(int i=-6; i<1; i++){
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.get(Calendar.YEAR);
                                calendar1.get(Calendar.MONTH);
                                calendar1.get(Calendar.DATE) ;
                                calendar1.add(Calendar.DAY_OF_MONTH,i);
                                CharSequence dateCharSequence = android.text.format.DateFormat.format("dd-MM-yyyy", calendar1);
                                if(snapshot.child("Steps").child((String) dateCharSequence).exists()){
                                    int a = i+6;
                                    int b = Integer.parseInt(snapshot.child("Steps").child((String) dateCharSequence).child("step_count").getValue(String.class));
                                    Entry e = new Entry(a,b);
                                    dataVal1.add(e);


                                }
                            }
                            System.out.println(dataVal1);

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

            return dataVal;
        }
    }
