package com.example.classone.Model;

public class Country {
    private String name;
    private int flagImage;
    private boolean canEnter;

    public Country(){

    }

    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public int getFlagImage() {
        return flagImage;
    }

    public Country setFlagImage(int flagImage) {
        this.flagImage = flagImage;
        return this;
    }

    public boolean isCanEnter() {
        return canEnter;
    }

    public Country setCanEnter(boolean canEnter) {
        this.canEnter = canEnter;
        return this;
    }
}
