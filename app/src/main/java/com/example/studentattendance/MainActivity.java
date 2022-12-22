package com.example.studentattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentattendance.object.StudentInfomation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String dbTest = "DatabaseTest";
    static HashMap<String, StudentInfomation> studentInfos;

    public static final DatabaseReference createRootReference(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return  database.getReference(dbTest);
    }

    private EditText edUsername;
    private EditText edPassword;
    private Button btnLogin;
    private TextView teNewStudent;

    private void createElement(){
        edUsername = findViewById(R.id.edit_text_username_login);
        edPassword = findViewById(R.id.edit_text_password_login);
        btnLogin = findViewById(R.id.btn_login);
        teNewStudent = findViewById(R.id.text_view_new_student);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createElement();
        String username = edUsername.getText().toString();
        String password = edPassword.getText().toString();


        getAllDataFromDBRoot();

        btnLogin.setOnClickListener(v -> {
            System.out.println("this is username: "+username);
            System.out.println("this is password: "+password);
            boolean status = checkLogin(username, password, studentInfos);
            Toast.makeText(getApplicationContext(), status +" "+username+" "+password,Toast.LENGTH_SHORT)
                    .show();
        });
    }

    public static void  getAllDataFromDBRoot(){
        DatabaseReference myRef = createRootReference();
        GenericTypeIndicator<HashMap<String, StudentInfomation>> t = new GenericTypeIndicator<HashMap<String, StudentInfomation>>() {};
        studentInfos = new HashMap<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, StudentInfomation> studentInfosRetrieve = snapshot.getValue(t);
                studentInfos.putAll(studentInfosRetrieve);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ERROR", "Something Wrong: " + error.getMessage());
            }
        });
    }


    private boolean checkLogin(String username, String password,HashMap<String, StudentInfomation> students){
        boolean status = false;
        for (StudentInfomation element:students.values()) {
            if (username.equals(element.getUsername())&&password.equals(element.getPassword())){
                status = true;
                break;
            }
        }
        return status;
    }
}