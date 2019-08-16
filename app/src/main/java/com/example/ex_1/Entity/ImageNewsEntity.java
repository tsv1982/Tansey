package com.example.ex_1.Entity;

public class ImageNewsEntity {
    String URL;

    public ImageNewsEntity() {
    }

    public ImageNewsEntity(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "ImageNewsEntity{" +
                "URL='" + URL + '\'' +
                '}';
    }

}
