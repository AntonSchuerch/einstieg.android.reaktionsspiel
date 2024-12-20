package com.example.reaktionsspiel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reaktionsspiel.Sorters.SortByName;
import com.example.reaktionsspiel.Sorters.SortByNameDesc;
import com.example.reaktionsspiel.Sorters.SortByScore;
import com.example.reaktionsspiel.Sorters.SortByScoreDesc;
import com.example.reaktionsspiel.Sorters.SortByTime;
import com.example.reaktionsspiel.Sorters.SortByTimeDesc;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreFragment extends Fragment {

    //private static ArrayList<ScoreRow> rows;
    private ArrayList<ScoreRow> scoreBoard;
    private RecyclerView.Adapter scoreAdapter;
    //if they are true it means it is sorted asc, else desc, except for score that one is reversed
    private boolean sortScore;
    private boolean sortName;
    private boolean sortTime;
    private Fragment fragment = this;

    public ScoreFragment() {
        // Required empty public constructor
    }

    public static ScoreFragment newInstance(ArrayList<ScoreRow> scoreRows) {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("rows", scoreRows);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        scoreBoard = args.getParcelableArrayList("rows");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scoreAdapter = new ScoreAdapter(scoreBoard, this, R.layout.score_row);
        RecyclerView recyclerList = view.findViewById(R.id.scoreboard);
        recyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerList.setAdapter(scoreAdapter);
        //so that i can code it programmatically
        //score and place sorting both do the same thing
        view.findViewById(R.id.placeTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sortScore) {
                    SortByScoreDesc scoreSorterDesc = new SortByScoreDesc();
                    Collections.sort(scoreBoard, scoreSorterDesc);
                    sortScore = false;
                }
                else {
                    SortByScore scoreSorter = new SortByScore();
                    Collections.sort(scoreBoard, scoreSorter);
                    sortScore = true;
                }
                scoreAdapter.notifyDataSetChanged();
            }
        });
        view.findViewById(R.id.nameTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sortName) {
                    SortByName nameSorter = new SortByName();
                    Collections.sort(scoreBoard, nameSorter);
                    sortName = false;
                }
                else {
                    SortByNameDesc nameSorterDesc = new SortByNameDesc();
                    Collections.sort(scoreBoard, nameSorterDesc);
                    sortName = true;
                }
                scoreAdapter.notifyDataSetChanged();
            }
        });
        view.findViewById(R.id.scoreTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sortScore) {
                    SortByScoreDesc scoreSorterDesc = new SortByScoreDesc();
                    Collections.sort(scoreBoard, scoreSorterDesc);
                    sortScore = false;
                }
                else {
                    SortByScore scoreSorter = new SortByScore();
                    Collections.sort(scoreBoard, scoreSorter);
                    sortScore = true;
                }
                scoreAdapter.notifyDataSetChanged();
            }
        });
        view.findViewById(R.id.timeTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sortTime) {
                    SortByTime timeSorter = new SortByTime();
                    Collections.sort(scoreBoard, timeSorter);
                    sortTime = false;
                }
                else {
                    SortByTimeDesc timeSorterDesc = new SortByTimeDesc();
                    Collections.sort(scoreBoard, timeSorterDesc);
                    sortTime = true;
                }
                scoreAdapter.notifyDataSetChanged();
            }
        });
    }
}