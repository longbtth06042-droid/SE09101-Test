package com.studentmanagement.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Student {
    private String id;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String major;
    private double gpa;
    private String status;

    public Student(String id, String fullName, LocalDate dateOfBirth, String gender, String major, double gpa, String status) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.major = major;
        this.gpa = gpa;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getMajor() {
        return major;
    }

    public double getGpa() {
        return gpa;
    }

    public String getStatus() {
        return status;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAcademicRank() {
        if (gpa >= 8.0) {
            return "Giỏi";
        } else if (gpa >= 6.5) {
            return "Khá";
        } else if (gpa >= 5.0) {
            return "Trung bình";
        }
        return "Yếu";
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("%s | %s | %s | %s | %s | %.2f | %s | %s",
                id, fullName, dateOfBirth.format(formatter), gender, major, gpa, status, getAcademicRank());
    }
}
