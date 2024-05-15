package com.example.searchPracticeBase.Models;

public class RequestStatus {

    private Long id;

    private String statusName;

    private String statusColor;

    public RequestStatus() {
    }

    public RequestStatus(Long id, String statusName, String statusColor, boolean isCanceled) {
        this.id = id;
        this.statusName = statusName;
        this.statusColor = statusColor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }
}