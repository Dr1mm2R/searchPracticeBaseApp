package com.example.searchPracticeBase.Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PracticeBase {
    private Long id;
    private String nameBase;
    private String descriptionAboutBase;
    private String statusDialing;
    private String photoPlace;
    private PracticeManager manager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameBase() {
        return nameBase;
    }

    public void setNameBase(String nameBase) {
        this.nameBase = nameBase;
    }

    public String getDescriptionAboutBase() {
        return descriptionAboutBase;
    }

    public void setDescriptionAboutBase(String descriptionAboutBase) {
        this.descriptionAboutBase = descriptionAboutBase;
    }

    public String getStatusDialing() {
        return statusDialing;
    }

    public void setStatusDialing(String statusDialing) {
        this.statusDialing = statusDialing;
    }

    public String getPhotoPlace() {
        return photoPlace;
    }

    public void setPhotoPlace(String photoPlace) {
        this.photoPlace = photoPlace;
    }

    public PracticeManager getManager() {
        return manager;
    }

    public void setManager(PracticeManager manager) {
        this.manager = manager;
    }

    public PracticeBase(Long id, String nameBase, String descriptionAboutBase, String statusDialing, String photoPlace, PracticeManager manager) {
        this.id = id;
        this.nameBase = nameBase;
        this.descriptionAboutBase = descriptionAboutBase;
        this.statusDialing = statusDialing;
        this.photoPlace = photoPlace;
        this.manager = manager;
    }

    public PracticeBase() {
    }
}
