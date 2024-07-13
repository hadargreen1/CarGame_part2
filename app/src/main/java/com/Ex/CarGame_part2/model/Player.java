package com.Ex.CarGame_part2.model;

public class Player {
    private String name;
    private double latitude;
    private double longitude;
    private int score;

    public Player() {
    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Player setLocation(double latitude,double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getScore() {
        return score;
    }

    public Player setScore(int score) {
        this.score = score;
        return this;
    }
}
