package com.example.studentattendance.recycler_view_config;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentattendance.R;
import com.example.studentattendance.object.StudentInfomation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private StudentAdapter mStudentAdapter;

    public void setConfig(RecyclerView recyclerView,
                          Context context,
                          List<StudentInfomation> students,
                          List<String> keys){
        mContext = context;
        mStudentAdapter = new StudentAdapter(students,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mStudentAdapter);
    }


    class StudentItemView extends RecyclerView.ViewHolder{
        private TextView mFullName;
        private TextView mCode;
        private TextView mAbsent;
        private Button btnAbsent;

        private String key;

        public StudentItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.detail_student_item,parent,false));

            mFullName = itemView.findViewById(R.id.student_name_detail);
            mCode = itemView.findViewById(R.id.student_code_detail);
            mAbsent = itemView.findViewById(R.id.absent_detail);
            btnAbsent = itemView.findViewById(R.id.btn_absent);

        }

        public void bind(StudentInfomation student, String key){
            mFullName.setText(student.getFullName());
            mCode.setText(student.getStudentCode());
            mAbsent.setText("Số buổi vắng: " + student.getDateAbsent().size());
            this.key = key;
        }
    }
    class StudentAdapter extends RecyclerView.Adapter<StudentItemView>{
        private List<StudentInfomation> mStudentList;
        private List<String> mKeys;

        public StudentAdapter(List<StudentInfomation> mStudentList, List<String> mKeys) {
            this.mStudentList = mStudentList;
            this.mKeys = mKeys;
        }

        @Override
        public void onBindViewHolder(@NonNull StudentItemView holder, int position) {

            holder.bind(mStudentList.get(position), mKeys.get(position));
            holder.btnAbsent.setOnClickListener(v -> {
                String code = mStudentList.get(position).getStudentCode();
                butonClickToSubmitAbsence(code,mStudentList.get(position));
            });
        }

        void butonClickToSubmitAbsence(String code,StudentInfomation student){

            List<String> list = student.getDateAbsent();

            if (clickMoreThanOnce(list)){
                Toast.makeText(mContext.getApplicationContext(), code + " already updated", Toast.LENGTH_SHORT).show();
            } else {

                list.add(LocalDate.now().toString());
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("DatabaseTest").child(code)
                        .child("dateAbsent");
                myRef.setValue(list);

                Toast.makeText(mContext.getApplicationContext(), "Updated Successfully " + code, Toast.LENGTH_SHORT)
                        .show();
            }

        }

        private boolean clickMoreThanOnce(List<String> list){
            boolean status = false;
            for (String st: list
                 ) {
                if (st.equals(LocalDate.now().toString())){
                    status = true;
                    break;
                }
            }
            return status;
        }

        @NonNull
        @Override
        public StudentItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new StudentItemView(parent);
        }

        @Override
        public int getItemCount() {
            return mStudentList.size();
        }
    }
}
