package com.example.reaktionsspiel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter {

    private ArrayList<ScoreRow> row;
    private Fragment displayFragment;
    private int rowId;
    public ScoreAdapter(ArrayList<ScoreRow> scoreRows, Fragment fragment, int layoutId) {
        row = scoreRows;
        displayFragment = fragment;  //todo find out how to make lists with fragments
        rowId = layoutId;
    }


    @NonNull
    @Override
    public ViewHolderScore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View scoreEntry = LayoutInflater.from(parent.getContext()).inflate(viewType, parent);
        return new ViewHolderScore(scoreEntry);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderScore) {
            onBindViewHolderScore((ViewHolderScore) holder, position);
        }
    }

    public void onBindViewHolderScore(ViewHolderScore holderScore, int position) {
        holderScore.spot.setText(row.get(position).place);  //basically makes it so that the text becomes unique
        holderScore.score.setText("" +row.get(position).score);
        holderScore.playerName.setText(row.get(position).name);
//        holderScore.spot.setText(row.get(position).place);    make this to time
    }

    @Override
    public int getItemCount() {
        return row.size();
    }
}
