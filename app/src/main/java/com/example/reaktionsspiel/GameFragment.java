package com.example.reaktionsspiel;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class GameFragment extends Fragment {

    int layoutWidth;
    int layoutHeight;
    Button button;
    Button startButton;
    ConstraintLayout layoutbase;
    TextView scoreView;
    int score;
    CountDownTimer countDown;
    int buttonStartHeight;
    int buttonStartWitdh;
    ViewGroup.LayoutParams params;
    Random rand;
    TextView highScore;
    TextView highScoreName;
    TextView timeView;
    int timeLeft;

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rand = new Random();
        timeLeft = 10;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonFound(v);
            }
        });
        startButton = view.findViewById(R.id.button2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(v);
            }
        });
        scoreView = view.findViewById(R.id.currentScore);
        layoutbase = (ConstraintLayout) getView();
        params = button.getLayoutParams();
        buttonStartWitdh = params.width;       //so that i can make the button big again, after starting a new run.
        buttonStartHeight = params.height;
        button.setVisibility(View.INVISIBLE);
        highScore = view.findViewById(R.id.highscore);
        timeView = view.findViewById(R.id.timeLeft);
        highScoreName = view.findViewById(R.id.highscorename);
    }

    public void backToStart() {
        countDown.cancel();
        timeLeft = 10;
        timeView.setText(timeLeft+"");
        startButton.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        score = 0;
        scoreView.setText("score: ");   //so that the past score doesn't get shown
    }

    public void startGame(View view) {
        // create a new score (show the view on top left
        if(countDown != null) {
            countDown.cancel();
        }
        view.setVisibility(View.GONE);  //entfernt den Startbutton
        layoutHeight = layoutbase.getHeight();
        layoutWidth = layoutbase.getWidth();
        params.width = buttonStartWitdh;
        params.height = buttonStartHeight;
        button.setLayoutParams(params);
        configureButton();
        //configureScore();
        timeLeft++;
        createCountDown(this, getContext());
    }

    public void buttonFound(View view) {
        score += 100;    //increase score
        configureScore();
        configureButton();
    }
    public void configureButton() {
        button.setVisibility(View.VISIBLE);
        int yPos = rand.nextInt(layoutHeight-button.getHeight() -70) +70;
        int xPos = rand.nextInt(layoutWidth-button.getWidth());
        button.setX(xPos);
        button.setY(yPos);
    }

    public void createCountDown(GameFragment gameFragment, Context gameContext) {
        countDown = new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                ViewGroup.LayoutParams params = button.getLayoutParams();
                params.width = (int) Math.round(params.width * 0.97);
                params.height = (int) Math.round(params.height * 0.97);
                button.setLayoutParams(params);
                timeLeft--;
                timeView.setText(timeLeft+"");
            }
            public void onFinish() {
                //spawn a fragment that asks you for a name for the score
                //then make it so that the list is updated and ge game is on the start screen again.
                FinishedDialog finish = new FinishedDialog(gameContext, score, gameFragment);   //since this doesnt' work here
                finish.showDialog(gameFragment);
                button.setVisibility(View.GONE);
                backToStart();
            }
        }.start();
    }

    public void addNewScore(long endScore, String username) {
        GameActivity activity = (GameActivity) getActivity();
        activity.addScoreList(endScore, username);
    }

    public void configureScore() {
        scoreView.setText("score: " + score);
    }

    @Override
    public void onDestroy() {
        if(countDown != null) {
            countDown.cancel();
        }
        super.onDestroy();
    }

    public void setHighScore(ScoreRow highest) {
        highScore.setText("Highscore:  " + highest.score);
        highScoreName.setText(highest.name);
        highScore.setVisibility(View.VISIBLE);
        highScoreName.setVisibility(View.VISIBLE);
    }
}