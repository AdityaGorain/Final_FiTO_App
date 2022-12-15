package com.example.fito.ui.diary;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.fito.R;
import com.example.fito.databinding.FragmentDiaryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Objects;

public class DiaryFragment extends Fragment {

    private FragmentDiaryBinding binding;

    public static DiaryFragment newInstance() {
        return new DiaryFragment();
    }
    String email;
    Calendar calendar1;
    CharSequence dateCharSequence;
    private TextView w1;
    private TextView sd1;
    private TextView wi1;
    private TextView sc1;
    private TextView f;
    private TextView g;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentDiaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        w1 = root.findViewById(R.id.diaryWeightValue);
        sc1 = root.findViewById(R.id.diaryStepsValue);
        sd1 = root.findViewById(R.id.diarySleepValue);
        wi1 = root.findViewById(R.id.diaryWaterValue);
        f = root.findViewById(R.id.calorie_card);
        g = root.findViewById(R.id.activity_card);
        handleDate();

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void handleDate() {
        Calendar calendar = Calendar.getInstance();
        int YEAR=calendar.get(Calendar.YEAR);
        int MONTH=calendar.get(Calendar.MONTH);
        int DATE=calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog= new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                // Here is your date selected by the users in the parameters

                calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DATE,date);

                dateCharSequence = android.text.format.DateFormat.format("EEEE, dd MMM yyyy", calendar1);
                TextView display = (TextView)getView().findViewById(R.id.date_display);
                display.setText(dateCharSequence);


                dateCharSequence = android.text.format.DateFormat.format("dd-MM-yyyy", calendar1);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                            String ma = snapshot.child("email").getValue(String.class);
                            if (Objects.equals(email, ma)) {
                                String w = null, sd = null, wi = null, sc = null;
                                System.out.println((String) dateCharSequence);
                                System.out.println(snapshot.child("Misc").child((String) dateCharSequence));

                                w = snapshot.child("Misc").child((String) dateCharSequence).child("weight").getValue(String.class);
                                sd = snapshot.child("Misc").child((String) dateCharSequence).child("Sleep_Duration").getValue(String.class);
                                wi = snapshot.child("Misc").child((String) dateCharSequence).child("Water_Intake").getValue(String.class);
                                sc = snapshot.child("Steps").child((String) dateCharSequence).child("step_count").getValue(String.class);

                                if (w != null) w1.setText(w);
                                if (sd != null) sd1.setText(sd);
                                if (wi != null) wi1.setText(wi);
                                if (sc != null) sc1.setText(sc);
                                //Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                                final String[] food = {""};
                                DatabaseReference ref = snapshot.getRef();
                                ref.child("Food_Intake").child((String) dateCharSequence).addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();

                                        while (items.hasNext()) {
                                            DataSnapshot item = items.next();

                                            String a = item.child("food_name").getValue().toString();
                                            String b = item.child("item_calorie").getValue().toString();

                                            food[0] = food[0] + (a + " " + b + " " + "Kcal" + "\n\n");

                                        }
                                        f.setText(food[0]);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });

                                final String activity[] = {""};
                                DatabaseReference ref1 = snapshot.getRef();
                                ref1.child("Activity").child((String) dateCharSequence).addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();

                                        while (items.hasNext()) {
                                            DataSnapshot item = items.next();

                                            String a = item.child("activity").getValue().toString();
                                            String b = item.child("calories").getValue().toString();

                                            activity[0] = activity[0] + (a + " " + b + " " + "Kcal" + "\n\n");

                                        }
                                        g.setText(activity[0]);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }, YEAR, MONTH, DATE);
        datePickerDialog.show();
    }

    @Override
    public void onResume(){
        super.onResume();

    }

}