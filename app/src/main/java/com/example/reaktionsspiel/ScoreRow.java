package com.example.reaktionsspiel;

public class ScoreRow {
    public long score;
    public String name;
    public int place;
    // public ... time
    public ScoreRow(long s, String n) {  //add one for Time when it was made
        score = s;
        name = n;
        place = 0;  //standart wert für place
        // time = ....
    }
}
