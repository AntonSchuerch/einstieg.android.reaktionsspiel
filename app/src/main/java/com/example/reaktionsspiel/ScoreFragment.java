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

import java.util.ArrayList;

public class ScoreFragment extends Fragment {

    //private static ArrayList<ScoreRow> rows;
    private ArrayList<ScoreRow> scoreBoard;
    private RecyclerView.Adapter scoreAdapter;

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
    }
}