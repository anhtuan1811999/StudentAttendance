package com.example.studentattendance;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentattendance.firebase_connection.FirebaseDatabaseHelper;
import com.example.studentattendance.object.StudentInfomation;
import com.example.studentattendance.recycler_view_config.RecyclerView_Config;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainPageTeacherActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_teacher);
        mRecyclerView = findViewById(R.id.recyclerreview_student);
        new FirebaseDatabaseHelper().readStudent(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoad(List<StudentInfomation> students, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, MainPageTeacherActivity.this,
                        students, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
}
