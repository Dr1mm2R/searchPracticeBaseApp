package com.example.searchPracticeBase.Models;

import java.time.LocalDateTime;

public class RequestSubmitted {
    private Long id;

    private PracticeBase practiceBase;

    private Student student;

    private LocalDateTime submissionDateTime;

    private RequestStatus status;

    private boolean isCanceled;

    private String comment;

    public RequestSubmitted() {
    }

    public RequestSubmitted(Long id, PracticeBase practiceBase, Student student, LocalDateTime submissionDateTime, RequestStatus status, boolean isCanceled, String comment) {
        this.id = id;
        this.practiceBase = practiceBase;
        this.student = student;
        this.submissionDateTime = submissionDateTime;
        this.status = status;
        this.isCanceled = isCanceled;
        this.comment = comment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public PracticeBase getPracticeBase() {
        return practiceBase;
    }

    public void setPracticeBase(PracticeBase practiceBase) {
        this.practiceBase = practiceBase;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDateTime getSubmissionDateTime() {
        return submissionDateTime;
    }

    public void setSubmissionDateTime(LocalDateTime submissionDateTime) {
        this.submissionDateTime = submissionDateTime;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
