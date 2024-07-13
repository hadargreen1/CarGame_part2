package com.example.classone.model;

import java.util.ArrayList;

public class Records {
    private ArrayList<Player> players = new ArrayList<>();

    public Records() {
    }

    public ArrayList<Player> getRecords() {
        return players;
    }


    public void sortList() {
        players.sort((p1,p2) -> p2.getScore()-p1.getScore());
        if (players.size() > 10){
            players.remove(10);
        }
    }
}
