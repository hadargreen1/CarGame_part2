package com.example.classone.Logic;

import com.example.classone.Model.Country;


import java.util.List;

public class GameManager {

    private  static final  int ANSWER_POINTS = 10;

    private  int score = 0;
    private int currentIndex = 0;
    private int wrongAnswers = 0;
    private int lifeCount;

    private List<Country> allCountries;

    public GameManager() {
        this(3);
        //lifeCount = 3;
        // allCountries = DataManager.getCountries();
    }

    public GameManager(int lifeCount) {
        this.lifeCount = lifeCount;
        allCountries = DataManager.getCountries();
    }

    public int getScore() {
        return score;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public int getLifeCount() {
        return lifeCount;
    }

    public List<Country> getAllCountries() {
        return allCountries;
    }

    public Country getCurrentCountry(){
        return this.allCountries.get(getCurrentIndex());
    }

    public boolean isGameEnded(){
        return getCurrentIndex() == allCountries.size();
    }

    public boolean isGameLost(){
        return getLifeCount() == getWrongAnswers();
    }

    public void checkAnswer(boolean answer){
        if(getCurrentCountry().isCanEnter() == answer)
            score += ANSWER_POINTS;
        else
            wrongAnswers++;
        currentIndex++;
    }
}
