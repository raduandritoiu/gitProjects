package com.bigct.appint.model;

/**
 * Created by sdragon on 1/20/2016.
 */
public class MenuItem {
    String id;
    String name;
    String image;
    String marker;

    public MenuItem(String id, String name, String image, String marker) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.marker = marker;
    }

    public String getId() {return id;}

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }
}
