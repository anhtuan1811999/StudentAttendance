package com.example.studentattendance.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.studentattendance.R;
import com.example.studentattendance.object.StudentInfomation;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class StudentListViewAdapter extends BaseAdapter {

    private List<StudentInfomation> listStu;
    private Activity activity;

    public StudentListViewAdapter(Activity activity, List<StudentInfomation> listStu){
        this.activity = activity;
        this.listStu = listStu;
    }

    @Override
    public int getCount() {
        return listStu.size();
    }

    @Override
    public Object getItem(int position) {
        return listStu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.detail_student_item, null);

        TextView teName = convertView.findViewById(R.id.student_name_detail);
        TextView teCode = convertView.findViewById(R.id.student_code_detail);
        TextView teAbsent = convertView.findViewById(R.id.absent_detail);

        return convertView;
    }
}
