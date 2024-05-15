package com.example.searchPracticeBase.Models;

import java.util.Date;

public class VisitLog {

    private Long id;

    private Date dateVisit;

    private String typeOfWork;

    private Student student;

    private PracticeBase practiceBase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit(Date dateVisit) {
        this.dateVisit = dateVisit;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }

    public void setTypeOfWork(String typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public VisitLog(Long id, Date dateVisit, String typeOfWork, Student student, PracticeBase practiceBase) {
        this.id = id;
        this.dateVisit = dateVisit;
        this.typeOfWork = typeOfWork;
        this.student = student;
        this.practiceBase = practiceBase;
    }

    public VisitLog() {
    }

    public PracticeBase getPracticeBase() {
        return practiceBase;
    }

    public void setPracticeBase(PracticeBase practiceBase) {
        this.practiceBase = practiceBase;
    }
}
