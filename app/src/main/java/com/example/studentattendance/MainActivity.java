package com.example.studentattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public static final String dbTest = "DatabaseTest";

    private EditText edUsername;
    private EditText edPassword;
    private Button btnLogin;
    private TextView teNewStudent;

    private void createElement(){
        edUsername = (EditText) findViewById(R.id.edit_text_username_login);
        edPassword = (EditText) findViewById(R.id.edit_text_password_login);
        btnLogin = (Button) findViewById(R.id.btn_login);
        teNewStudent = (TextView) findViewById(R.id.text_view_new_student);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(dbTest);



    }


}