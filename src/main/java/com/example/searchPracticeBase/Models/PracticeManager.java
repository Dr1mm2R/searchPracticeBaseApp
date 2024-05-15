package com.example.searchPracticeBase.Models;

public class PracticeManager {

    private Long id;

    private String postManager;

    private String firstName;

    private String secondName;

    private String middleName;

    private String managerLogin;

    private String workExperience;

    private String workDirection;

    private String role;

    private Contact contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostManager() {
        return postManager;
    }

    public void setPostManager(String postManager) {
        this.postManager = postManager;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getWorkDirection() {
        return workDirection;
    }

    public void setWorkDirection(String workDirection) {
        this.workDirection = workDirection;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public PracticeManager(Long id, String postManager, String workExperience, String workDirection, Contact contact, String login, String firstName, String secondName, String middleName, String role) {
        this.id = id;
        this.postManager = postManager;
        this.workExperience = workExperience;
        this.workDirection = workDirection;
        this.contact = contact;
        this.managerLogin = login;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.role = role;
    }

    public PracticeManager() {
    }

    public String getManagerLogin() {
        return managerLogin;
    }

    public void setManagerLogin(String managerLogin) {
        this.managerLogin = managerLogin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
