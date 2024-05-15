package com.example.searchPracticeBase.Models;

public class ResumeStudent {

    private Long id;
    private String photoStudent;
    private String education;
    private String purposeInternship;
    private String personalQualities;
    private String professionalSkills;
    private String preferredLanguages;
    private String daysInternship;
    private String periodInternship;
    private Contact contact;

    public ResumeStudent() {
    }

    public ResumeStudent(Long id, String photoStudent, String education, String purposeInternship, String personalQualities, String professionalSkills, String preferredLanguages, String periodInternship, String daysInternship, Contact contact) {
        this.id = id;
        this.photoStudent = photoStudent;
        this.education = education;
        this.purposeInternship = purposeInternship;
        this.personalQualities = personalQualities;
        this.professionalSkills = professionalSkills;
        this.preferredLanguages = preferredLanguages;
        this.daysInternship = daysInternship;
        this.periodInternship = periodInternship;
        this.contact = contact;
    }

    public ResumeStudent(Long id, String photoStudent, String education, String purposeInternship, String personalQualities, String professionalSkills, String preferredLanguages, Contact contact) {
        this.id = id;
        this.photoStudent = photoStudent;
        this.education = education;
        this.purposeInternship = purposeInternship;
        this.personalQualities = personalQualities;
        this.professionalSkills = professionalSkills;
        this.preferredLanguages = preferredLanguages;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoStudent() {
        return photoStudent;
    }

    public void setPhotoStudent(String photoStudent) {
        this.photoStudent = photoStudent;
    }

    public String getPurposeInternship() {
        return purposeInternship;
    }

    public void setPurposeInternship(String purposeInternship) {
        this.purposeInternship = purposeInternship;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getPersonalQualities() {
        return personalQualities;
    }

    public void setPersonalQualities(String personalQualities) {
        this.personalQualities = personalQualities;
    }

    public String getProfessionalSkills() {
        return professionalSkills;
    }

    public void setProfessionalSkills(String professionalSkills) {
        this.professionalSkills = professionalSkills;
    }

    public String getPreferredLanguages() {
        return preferredLanguages;
    }

    public void setPreferredLanguages(String preferredLanguages) {
        this.preferredLanguages = preferredLanguages;
    }

    public String getDaysInternship() {
        return daysInternship;
    }

    public void setDaysInternship(String daysInternship) {
        this.daysInternship = daysInternship;
    }

    public String getPeriodInternship() {
        return periodInternship;
    }

    public void setPeriodInternship(String periodInternship) {
        this.periodInternship = periodInternship;
    }
}
