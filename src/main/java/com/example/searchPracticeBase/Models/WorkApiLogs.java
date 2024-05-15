package com.example.searchPracticeBase.Models;

public class WorkApiLogs {

    private Long id;

    private int allCountRequests;

    private int errorCountRequests;

    private int successfulCountRequests;

    public WorkApiLogs() {
    }

    public WorkApiLogs(Long id, int allCountRequests, int errorCountRequests, int successfulCountRequests) {
        this.id = id;
        this.allCountRequests = allCountRequests;
        this.errorCountRequests = errorCountRequests;
        this.successfulCountRequests = successfulCountRequests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAllCountRequests() {
        return allCountRequests;
    }

    public void setAllCountRequests(int allCountRequests) {
        this.allCountRequests = allCountRequests;
    }

    public int getErrorCountRequests() {
        return errorCountRequests;
    }

    public void setErrorCountRequests(int errorCountRequests) {
        this.errorCountRequests = errorCountRequests;
    }

    public int getSuccessfulCountRequests() {
        return successfulCountRequests;
    }

    public void setSuccessfulCountRequests(int successfulCountRequests) {
        this.successfulCountRequests = successfulCountRequests;
    }
}
