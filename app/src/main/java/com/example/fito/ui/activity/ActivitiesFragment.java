package com.example.fito.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fito.R;
import com.example.fito.databinding.FragmentActivitiesBinding;
import com.example.fito.helper.ActivitySetClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Objects;

public class ActivitiesFragment extends Fragment {

    private FragmentActivitiesBinding binding;
    String email;
    private TextView act_name;
    private TextView cal_burnt;
    private Button save;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        binding = FragmentActivitiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        act_name = getView().findViewById(R.id.act_name);
        cal_burnt = getView().findViewById(R.id.cal_burnt);
        save = getView().findViewById(R.id.save_button);

        DatabaseReference[] r = {null};
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String ma = snapshot.child("email").getValue(String.class);
                    if(Objects.equals(email, ma)){
                        r[0] = snapshot.getRef();
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

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                storeActivity(r[0]);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void storeActivity(DatabaseReference ref){

        String a = act_name.getText().toString();
        String c = cal_burnt.getText().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.YEAR);
        calendar.get(Calendar.MONTH);
        calendar.get(Calendar.DATE);

        CharSequence dateCharSequence = android.text.format.DateFormat.format("dd-MM-yyyy", calendar);
        ActivitySetClass addNewActivity = new ActivitySetClass(a,c);
        ref.child("Activity").child((String) dateCharSequence).push().setValue(addNewActivity);
    }
}