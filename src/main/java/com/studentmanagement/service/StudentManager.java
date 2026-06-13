package com.studentmanagement.service;

import com.studentmanagement.model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentManager {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Optional<Student> findById(String id) {
        return students.stream()
                .filter(student -> student.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public List<Student> findByName(String name) {
        return students.stream()
                .filter(student -> student.getFullName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Student> findByMajor(String major) {
        return students.stream()
                .filter(student -> student.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
    }

    public boolean updateStudent(String id, Student updatedStudent) {
        Optional<Student> optional = findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        Student existing = optional.get();
        existing.setFullName(updatedStudent.getFullName());
        existing.setDateOfBirth(updatedStudent.getDateOfBirth());
        existing.setGender(updatedStudent.getGender());
        existing.setMajor(updatedStudent.getMajor());
        existing.setGpa(updatedStudent.getGpa());
        existing.setStatus(updatedStudent.getStatus());
        return true;
    }

    public boolean removeStudent(String id) {
        return students.removeIf(student -> student.getId().equalsIgnoreCase(id));
    }

    public List<Student> sortById() {
        return students.stream()
                .sorted(Comparator.comparing(Student::getId))
                .collect(Collectors.toList());
    }

    public List<Student> sortByName() {
        return students.stream()
                .sorted(Comparator.comparing(Student::getFullName))
                .collect(Collectors.toList());
    }

    public List<Student> sortByGpa() {
        return students.stream()
                .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
                .collect(Collectors.toList());
    }

    public long countByMajor(String major) {
        return students.stream()
                .filter(student -> student.getMajor().equalsIgnoreCase(major))
                .count();
    }

    public List<Student> getHonorStudents(double minGpa) {
        return students.stream()
                .filter(student -> student.getGpa() >= minGpa)
                .collect(Collectors.toList());
    }

    public void seedData() {
        addStudent(new Student("SV001", "Nguyen Van A", LocalDate.of(2001, 3, 15), "Nam", "CNTT", 8.2, "Đang học"));
        addStudent(new Student("SV002", "Tran Thi B", LocalDate.of(2002, 7, 10), "Nữ", "Kinh tế", 7.3, "Đang học"));
        addStudent(new Student("SV003", "Le Van C", LocalDate.of(2000, 12, 1), "Nam", "CNTT", 6.8, "Đang học"));
    }
}
