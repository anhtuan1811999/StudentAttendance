package com.example.studentattendance;

import static com.example.studentattendance.MainActivity.dbTest;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentattendance.object.StudentInfomation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText edFullName;
    private EditText edStudentCode;
    private EditText edEmail;
    private EditText edUsername;
    private EditText edPassword;
    private Button btnSubmit;

    private void createElement(){
        edFullName = findViewById(R.id.edit_text_fullname);
        edStudentCode = findViewById(R.id.edit_text_studentcode);
        edEmail = findViewById(R.id.edit_text_email);
        edUsername = findViewById(R.id.edit_text_username);
        edPassword = findViewById(R.id.edit_text_password);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createElement();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference(dbTest);

                StudentInfomation studentInfomation = new StudentInfomation();
                studentInfomation.setFullName(edFullName.getText().toString());
                studentInfomation.setStudentCode(edStudentCode.getText().toString());
                studentInfomation.setEmail(edEmail.getText().toString());
                studentInfomation.setUsername(edUsername.getText().toString());
                studentInfomation.setPassword(edPassword.getText().toString());

                ref.child(edFullName.getText().toString()).setValue(studentInfomation);

                Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }
}