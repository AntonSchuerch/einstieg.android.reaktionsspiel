package com.example.reaktionsspiel;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends AppCompatActivity {


    int layoutWidth;
    int layoutHeight;
    Button button;
    Button startButton;
    ConstraintLayout layoutbase;
    TextView scoreView;
    int score;
    Random rand;
    Context context;
    CountDownTimer countDown;
    int buttonStartHeight;
    int buttonStartWitdh;
    ViewGroup.LayoutParams params;
    ArrayList<ScoreRow> scoreList;  //da fragmente zerstört werden und so nicht gespeichert werden.

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //macht wenig, das meiste passiert erst nach dem ersten klick.
        super.onCreate(savedInstanceState);
        rand = new Random();
        setContentView(R.layout.game_layout);
        scoreView = findViewById(R.id.currentScore);
        button = findViewById(R.id.button);
        params = button.getLayoutParams();
        buttonStartWitdh = params.width;       //so that i can make the button big again, after starting a new run.
        buttonStartHeight = params.height;
        startButton = findViewById(R.id.button2);
        layoutbase = findViewById(R.id.main);       //after the Contentview has been set.
        button.setVisibility(View.INVISIBLE);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        context = this;
        scoreList = new ArrayList<>();
//        scoreList.add(new ScoreRow(100, "Anton"));
    }

    public void backToStart() {
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
        //         configureScore();
        createCountDown(this);
    }

    public void buttonFound(View view) {
        //todo methode für score increase, wobei der score mit dem Timer verkleinert wird und der Timer beendet wird.
        score += 100;    //increase score
        configureScore();
        //always use those four together.
        FinishedDialog finish = new FinishedDialog(this, score, this);
        finish.showDialog(this);
        button.setVisibility(View.INVISIBLE);   //hide button
        configureButton();
        Snackbar.make(button, button.getWidth()+ "", Snackbar.LENGTH_INDEFINITE);
        //todo make a second version where you have a timer after each one it gets smaller
//        try {
//            Thread.sleep(rand.nextInt(190) + 10);   //es ist immerhin ein reaktionsspiel
//        }
//        catch(Exception e) {
//            Thread.currentThread().interrupt();
//        }
        //modeSwap()    //hier wird der Timer zurückgesetzt, button angezeigt und screencolor verändert
        //change background color
    }
    public void configureButton() {
        button.setVisibility(View.VISIBLE);
        int yPos = rand.nextInt(layoutHeight-button.getHeight() -70) +70;
        int xPos = rand.nextInt(layoutWidth-button.getWidth());
        button.setX(xPos);
        button.setY(yPos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // todo make that the buttons on the menu do something
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idGameButton = R.id.playing;
        int idListButton = R.id.score;
        if(item.getItemId() == idGameButton) {
            if(countDown != null) {
                countDown.cancel();
            }
            backToStart();
        }
        if(item.getItemId() == idListButton) {
            if(countDown != null) {
                countDown.cancel();
                countDown.onFinish();
            }
            Bundle args = new Bundle();
            args.putParcelableArrayList("rows", scoreList);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentContainerView, ScoreFragment.class, args)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void configureScore() {
        scoreView.setText("score: " + score);
    }
    public void addScoreList(long endScore, String username) {
        ScoreRow newestScore = new ScoreRow(endScore, username);
        scoreList.add(newestScore);
        SortByScore scoreSorter = new SortByScore();
        Collections.sort(scoreList, scoreSorter);
        //die rangliste wird nach dem neuen immer 1. nach hinten geschoben
        for(int i = scoreList.indexOf(newestScore); i < scoreList.size(); i++) {
            scoreList.get(i).place = i + 1;     //da index mit 0 anfängt und rang mit 1 kommt ein +1
        }
    }

    public void createCountDown(GameActivity gameActivity) {
        countDown = new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                ViewGroup.LayoutParams params = button.getLayoutParams();
                params.width = (int) Math.round(params.width * 0.97);
                params.height = (int) Math.round(params.height * 0.97);
                button.setLayoutParams(params);
            }

            public void onFinish() {
                //spawn a fragment that asks you for a name for the score
                //then make it so that the list is updated and ge game is on the start screen again.
                FinishedDialog finish = new FinishedDialog(context, score, gameActivity);   //since this doesnt' work here
                finish.showDialog((GameActivity) context);
                backToStart();
            }
        }.start();
    }
}