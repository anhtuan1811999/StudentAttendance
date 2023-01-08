package com.example.studentattendance.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentInfomation {

    private String fullName;
    private String studentCode;
    private String email;
    private String username;
    private String password;
    private String phone;
    private List<String> dateAbsent = new ArrayList<>();


    public StudentInfomation() {
    }

    public StudentInfomation(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public StudentInfomation(String fullName, String studentCode, String email) {
        this.fullName = fullName;
        this.studentCode = studentCode;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getDateAbsent() {
        return dateAbsent;
    }

    public void setDateAbsent(List<String> dateAbsent) {
        this.dateAbsent = dateAbsent;
    }
}
