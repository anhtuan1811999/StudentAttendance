package com.example.studentattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentattendance.object.StudentInfomation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String dbTest = "DatabaseTest";
    public static HashMap<String, StudentInfomation> studentInfosFromFireBase;

    public static final DatabaseReference createRootReference(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return  database.getReference(dbTest);
    }

    private EditText edUsername;
    private EditText edPassword;
    private Button btnLogin;
    private TextView teNewStudent;
    private RadioButton rbtnStudent;
    private RadioButton rbtnTeacher;
    private TextView teSignal;

    private void createElement(){
        edUsername = findViewById(R.id.username);
        edPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.loginbtn);
        teNewStudent = findViewById(R.id.new_student);
        rbtnStudent =  findViewById(R.id.rbtn_student);
        rbtnTeacher = findViewById(R.id.rbtn_teacher);
        teSignal = findViewById(R.id.txt_network);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createElement();

        getAllDataFromDBRoot();


        /* Change icon internet connection */

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType((NetworkCapabilities.TRANSPORT_CELLULAR))
                .build();

        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() ==
                NetworkInfo.State.DISCONNECTED){
                    runOnUiThread(() -> {
                        teSignal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_4g_plus_mobiledata_24,0,0,0);
                        teSignal.setText("Mobile Data");
                    });
                } else {
                    runOnUiThread(() -> {
                        teSignal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_wifi_24,0,0,0);
                        teSignal.setText("Wifi Connected");
                    });
                }
            }
            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                runOnUiThread(() -> {
                    teSignal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_signal_wifi_statusbar_connected_no_internet_4_24,0,0,0);
                    teSignal.setText("No Internet Connection");
                });
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                Toast.makeText(getApplicationContext(), "onUnavailable", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
            }
        };

        connectivityManager.requestNetwork(networkRequest, networkCallback);
        /*Login Feature*/

        btnLogin.setOnClickListener(v -> {
            String username = edUsername.getText().toString();
            String password = edPassword.getText().toString();
            boolean status = checkLoginStudent(username, password, studentInfosFromFireBase);

            if (noConnection()){
                Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
            else if (status){
                if (rbtnStudent.isChecked()){
                    Toast.makeText(getApplicationContext(), "Login Success",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainPageStudentActivity.class);
                    intent.putExtra("usernameLogin", username);
                    startActivity(intent);
                } else if (rbtnTeacher.isChecked()){
                    Toast.makeText(getApplicationContext(),"Your role is not teacher",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Please check your role",Toast.LENGTH_SHORT).show();
                }
            } else  if (checkLoginTeacher(username,password)&&rbtnTeacher.isChecked()){
                    Toast.makeText(getApplicationContext(), "Teacher Login Successfully", Toast.LENGTH_SHORT)
                            .show();
                Intent intent = new Intent(this, MainPageTeacherActivity.class);
                startActivity(intent);
            } else {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
            }
        });

        /* Register Feature */

        teNewStudent.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }

    /* get data from firebase*/
    public static void  getAllDataFromDBRoot(){
        DatabaseReference myRef = createRootReference();
        GenericTypeIndicator<HashMap<String, StudentInfomation>> t = new GenericTypeIndicator<HashMap<String, StudentInfomation>>() {};
        studentInfosFromFireBase = new HashMap<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                HashMap<String, StudentInfomation> studentInfosRetrieve = snapshot.getValue(t);

                if (studentInfosRetrieve==null){
                    return;
                } else {
                    studentInfosFromFireBase.putAll(studentInfosRetrieve);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ERROR", "Something Wrong: " + error.getMessage());
            }
        });
    }

    /* Check login student */
    private boolean checkLoginStudent(String username, String password, HashMap<String, StudentInfomation> students){
        boolean status = false;
        for (StudentInfomation element:students.values()) {
            if (username.equals(element.getUsername())&&password.equals(element.getPassword())){
                status = true;
                break;
            }
        }
        return status;
    }

    /* Check login teacher */
    private boolean checkLoginTeacher(String username, String password){
        String teacherUsername = "teacher";
        String teacherPassword = "teacher";
        boolean status = false;
        if (username.equals(teacherUsername)&&password.equals(teacherPassword)){
            status = true;
        }

        return status;
    }

    /* Check internet connection */
    private boolean checkInternetConnection(){
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

            return connected;
    }

    /* Check wifi connection */
    private boolean checkWifiConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    /* Check mobile data */
    private boolean checkMobileDataConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED;
    }

    /* Check no connection */
    private boolean noConnection(){
        return !checkInternetConnection();
    }
}