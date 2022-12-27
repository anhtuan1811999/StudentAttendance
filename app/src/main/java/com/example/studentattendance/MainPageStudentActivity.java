package com.example.studentattendance;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentattendance.firebase_connection.FirebaseDatabaseHelper;
import com.example.studentattendance.object.StudentInfomation;

import java.util.ArrayList;
import java.util.List;

public class MainPageStudentActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextView txtFullName;
    private TextView txtCode;
    private TextView txtAbsence;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_student);

        String usernameFromLogin = getIntent().getStringExtra("usernameLogin");

        linearLayout = findViewById(R.id.layout_student_main_page);
        txtCode = findViewById(R.id.student_code_main_page_student);
        txtFullName = findViewById(R.id.full_name_main_page_student);
        txtAbsence = findViewById(R.id.absent_main_page_student);

        new FirebaseDatabaseHelper().readStudent(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoad(List<StudentInfomation> students, List<String> keys) {
                linearLayout.clearAnimation();
                List<StudentInfomation> stulist = new ArrayList<>();
                stulist.addAll(students);
                for (StudentInfomation stu : students
                     ) {
                    if (stu.getUsername().equals(usernameFromLogin)){
                        txtCode.setText("MSSV: "+stu.getStudentCode());
                        txtFullName.setText("Họ và tên: "+stu.getFullName());
                        txtAbsence.setText("Số buổi vắng:  " + stu.getDateAbsent().size());
                        List<String> absenceList = stu.getDateAbsent();
                        for (String str: absenceList
                             ) {
                            TextView txt = new TextView(MainPageStudentActivity.this);
                            txt.setText(str);
                            txt.setTextSize(15);
                            linearLayout.addView(txt);
                        }
                        break;
                    }
                }
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
