package com.example.firebasedatabasetutorial;

public class Car {

    private String model;
    private String color;
    private int speed;
    private long views;
    private String description;
    private String key;

    public Car() {

    }

    public Car(String model, String color, int topSpeed, long views, String description, String key) {
        this.model = model;
        this.color = color;
        this.speed = topSpeed;
        this.views = views;
        this.description = description;
        this.key = key;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
