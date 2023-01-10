package com.example.studentattendance.test_get_data;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentattendance.R;
import com.example.studentattendance.firebase_connection.FirebaseDatabaseHelper;
import com.example.studentattendance.object.StudentInfomation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestActivityGetData extends AppCompatActivity {

    private TextView textView;
    private DatabaseReference reference;

    public TestActivityGetData(){
        reference = FirebaseDatabase.getInstance().getReference("DatabaseTest");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_get_data);

        GenericTypeIndicator<Map<String,StudentInfomation>> t = new GenericTypeIndicator<Map<String, StudentInfomation>>() {
        };
        textView = findViewById(R.id.text_view);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> list = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()
                     ) {
                    list.add(data.getValue(StudentInfomation.class).getFullName());
                }
                textView.setText(list.get(0));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
