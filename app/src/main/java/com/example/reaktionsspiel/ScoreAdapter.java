package com.example.reaktionsspiel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class ScoreAdapter extends RecyclerView.Adapter {

    private ArrayList<ScoreRow> row;
    private Fragment displayFragment;
    private int rowId;

    public ScoreAdapter(ArrayList<ScoreRow> scoreRows, Fragment fragment, int layoutId) {
        row = scoreRows;
        displayFragment = fragment;
        rowId = layoutId;

    }


    @NonNull
    @Override
    public ViewHolderScore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View scoreEntry = LayoutInflater.from(parent.getContext()).inflate(rowId, parent, false);
        return new ViewHolderScore(scoreEntry);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderScore) {
            onBindViewHolderScore((ViewHolderScore) holder, position);
        }
    }

    public void onBindViewHolderScore(ViewHolderScore holderScore, int position) {
        //todo make it so that the time is not in timestamp but normal.

        holderScore.spot.setText(""+row.get(position).place);  //basically makes it so that the text becomes unique
        holderScore.score.setText("" +row.get(position).score);
        holderScore.playerName.setText(row.get(position).name);
        holderScore.recordTime.setText(configurateTime(row.get(position).time));   //converted es zu zeit, ohne Tag kürzel, Zeitzone und Jahr.
    }

    @Override
    public int getItemCount() {
        return row.size();
    }

    public String configurateTime(long rowTime) {
//        Date currentTime = Calendar.getInstance().getTime();
//        long timeStamp = currentTime.getTime();
        Date time = new Date(rowTime);
        String datum = time.toString().substring(4, 10);
        String zeit = time.toString().substring(11, 19);
        return zeit + "   " + datum;  //zum schön anzeigen
    }
}
