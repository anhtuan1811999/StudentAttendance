package com.example.studentattendance.firebase_connection;

import androidx.annotation.NonNull;

import com.example.studentattendance.object.StudentInfomation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceStu;
    private List<StudentInfomation> students = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoad(List<StudentInfomation> students, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceStu = mDatabase.getReference("DatabaseTest");
    }

    public void readStudent(final DataStatus dataStatus){
        mReferenceStu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                students.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNodes: snapshot.getChildren()
                     ) {
                    keys.add(keyNodes.getKey());
                    StudentInfomation stu = keyNodes.getValue(StudentInfomation.class);
                    students.add(stu);
                }
                dataStatus.DataIsLoad(students,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
