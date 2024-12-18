package com.example.reaktionsspiel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;
import java.util.Set;

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


    RecyclerView scoreList; //da fragmente zerstört werden und so nicht gespeichert werden.

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //macht wenig, das meiste passiert erst nach dem ersten klick.
        super.onCreate(savedInstanceState);
        rand = new Random();
        setContentView(R.layout.game_layout);
        scoreView = findViewById(R.id.currentScore);
        button = findViewById(R.id.button);
        startButton = findViewById(R.id.button2);
        layoutbase = findViewById(R.id.main);       //after the Contentview has been set.
        button.setVisibility(View.INVISIBLE);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        context = this;
    }

    public void backToStart() {
        startButton.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
    }

    public void startGame(View view) {
        // create a new score (show the view on top left
        // create a timer, that when runs out stops the program (find out how to do that)
//        Fragment button = Button.newInstance(layoutWidth, layoutHeight);
        view.setVisibility(View.GONE);  //entfernt den Startbutton
        layoutHeight = layoutbase.getHeight();
        layoutWidth = layoutbase.getWidth();
        configureButton();
        //         configureScore();
        new CountDownTimer(30000, 1000) {

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
                FinishedDialog finish = new FinishedDialog(context, startButton);   //since this doesnt' work here
                finish.showDialog((GameActivity) context);
                backToStart();
            }
        }.start();
    }

    public void buttonFound(View view) {
        //todo methode für score increase, wobei der score mit dem Timer verkleinert wird und der Timer beendet wird.
        score += 100;    //increase score
        configureScore();
        //always use those four together.
        FinishedDialog finish = new FinishedDialog(this, startButton);
        finish.showDialog(this);
        backToStart();


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
            backToStart();
        }
        if(item.getItemId() == idListButton) {

        }
        return super.onOptionsItemSelected(item);
    }

    public void configureScore() {
        scoreView.setText("score: " + score);
    }
    public void addScoreList() {

    }
}