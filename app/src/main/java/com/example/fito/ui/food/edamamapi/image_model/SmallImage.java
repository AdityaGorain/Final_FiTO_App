package com.example.fito.ui.food.edamamapi.image_model;

public class SmallImage {

    private String url;
    private int width;
    private int height;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SmallImage(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }
}
