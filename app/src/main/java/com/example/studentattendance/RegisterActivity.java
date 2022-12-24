package com.example.studentattendance;

import static com.example.studentattendance.MainActivity.createRootReference;
import static com.example.studentattendance.MainActivity.dbTest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentattendance.object.StudentInfomation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        HashMap<String, StudentInfomation> studentInfos = MainActivity.studentInfosFromFireBase;

        /* Submit register*/

        btnSubmit.setOnClickListener(view -> {

            String fullName = edFullName.getText().toString();
            String code = edStudentCode.getText().toString();
            String email = edEmail.getText().toString();
            String username = edUsername.getText().toString();
            String password = edPassword.getText().toString();


            if (checkInfoInput(fullName,code,email,username,password)){
                Toast.makeText(getApplicationContext(),"Please fill out the information completely",Toast.LENGTH_SHORT).show();
            }else if (!checkEmailValid(email)){
                Toast.makeText(getApplicationContext(),"Email is invalid",Toast.LENGTH_SHORT).show();
            } else if (checkEmailDuplicated(email,studentInfos)){
                Toast.makeText(getApplicationContext(),"Email already used",Toast.LENGTH_SHORT).show();
            } else if (checkUsernameDuplicated(username,studentInfos)){
                Toast.makeText(getApplicationContext(),"Username already used",Toast.LENGTH_SHORT).show();
            } else if (checkCodeDuplicated(code,studentInfos)){
                Toast.makeText(getApplicationContext(),"Student code already used",Toast.LENGTH_SHORT).show();
            } else {
                DatabaseReference ref = createRootReference();
                StudentInfomation studentInfomation = new StudentInfomation();
                studentInfomation.setFullName(edFullName.getText().toString());
                studentInfomation.setStudentCode(edStudentCode.getText().toString());
                studentInfomation.setEmail(edEmail.getText().toString());
                studentInfomation.setUsername(edUsername.getText().toString());
                studentInfomation.setPassword(edPassword.getText().toString());
                ref.child(edFullName.getText().toString()+" "+edStudentCode.getText().toString()).setValue(studentInfomation);
                Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean checkInfoInput(String fullName, String code, String email, String username, String password){
        boolean status = false;
        if (fullName.equals("")||code.equals("")||email.equals("")||username.equals("")||password.equals("")){
            status = true;
        }
        return status;
    }

    public boolean checkEmailDuplicated(String email, Map<String,StudentInfomation> studentInfos){

        ArrayList<String> arrStu = new ArrayList<>();
        studentInfos.forEach((k,v)->{
            arrStu.add(v.getEmail());
        });
        boolean status = false;
        for (String element:arrStu
        ) {
            if (email.equals(element)){
                status =true;break;
            }
        }

        return status;
    }

    public boolean checkUsernameDuplicated(String username, Map<String,StudentInfomation> studentInfos){
        ArrayList<String> arrStu = new ArrayList<>();
        studentInfos.forEach((k,v)->{
            arrStu.add(v.getUsername());
        });
        boolean status = false;
        for (String element:arrStu
        ) {
            if (username.equals(element)){
                status =true;break;
            }
        }

        return status;
    }

    public boolean checkCodeDuplicated(String code, Map<String,StudentInfomation> studentInfos){
        ArrayList<String> arrStu = new ArrayList<>();
        studentInfos.forEach((k,v)->{
            arrStu.add(v.getStudentCode());
        });
        boolean status = false;
        for (String element:arrStu
        ) {
            if (code.equals(element)){
                status =true;break;
            }
        }

        return status;
    }

    public boolean checkEmailValid(String email){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}