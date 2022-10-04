package com.usa.tripjungle.model;

public class MainModel {
    private final String title;
    private final String description;
    private final String image;
    private final String id;

    public MainModel(String id, String title, String description, String image){
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
