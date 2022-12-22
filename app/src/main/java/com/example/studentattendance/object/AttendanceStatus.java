package com.example.studentattendance.object;

public class AttendanceStatus {

    private String date;
    private String status;

    public AttendanceStatus() {
    }

    public AttendanceStatus(String date, String status) {
        this.date = date;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
