package com.example.reaktionsspiel;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderScore extends RecyclerView.ViewHolder {

    public TextView spot;
    public TextView score;
    public TextView playerName;
    public TextView recordTime;
    public ViewHolderScore(@NonNull View itemView) {
        super(itemView);
        spot = itemView.findViewById(R.id.spot);
        score = itemView.findViewById(R.id.scoreInList);
        playerName = itemView.findViewById(R.id.playerName);
        recordTime = itemView.findViewById(R.id.time);
    }
}
