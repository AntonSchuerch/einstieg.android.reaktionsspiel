package com.example.reaktionsspiel.Sorters;

import com.example.reaktionsspiel.ScoreRow;

import java.util.Comparator;

public class SortByScore implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {

        ScoreRow row1 = (ScoreRow) o1;
        ScoreRow row2 = (ScoreRow) o2;

        if(row1.score > row2.score) {
            return 1;
        }
        if(row1.score < row2.score) {
            return -1;
        }

        return 0;
    }
}
