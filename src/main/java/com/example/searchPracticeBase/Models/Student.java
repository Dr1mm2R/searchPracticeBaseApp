package com.example.searchPracticeBase.Models;

import java.util.Objects;

public class Student {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private String studentLogin;

    private boolean isInternship;

    private PracticeBase practiceBase;

    private ResumeStudent resume;

    public Student() {
    }

    public Student(Long id, String lastName, String firstName, String middleName, String studentLogin, boolean isInternship, ResumeStudent resume, PracticeBase practiceBase) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.studentLogin = studentLogin;
        this.isInternship = isInternship;
        this.practiceBase = practiceBase;
        this.resume = resume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getStudentLogin() {
        return studentLogin;
    }

    public void setStudentLogin(String studentLogin) {
        this.studentLogin = studentLogin;
    }

    public boolean isInternship() {
        return isInternship;
    }

    public void setInternship(boolean internship) {
        isInternship = internship;
    }

    public ResumeStudent getResume() {
        return resume;
    }

    public void setResume(ResumeStudent resume) {
        this.resume = resume;
    }

    public PracticeBase getPracticeBase() {
        return practiceBase;
    }

    public void setPracticeBase(PracticeBase practiceBase) {
        this.practiceBase = practiceBase;
    }
}
