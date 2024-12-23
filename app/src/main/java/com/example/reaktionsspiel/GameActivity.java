package com.example.reaktionsspiel;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.reaktionsspiel.Sorters.SortByScore;
import com.example.reaktionsspiel.Sorters.SortByScoreDesc;

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
    FragmentManager manager;
    GameFragment gameFragment;
    ScoreFragment scoreFragment;
    ArrayList<ScoreRow> scoreList;  //da fragmente zerstört werden und so nicht gespeichert werden.
    ScoreRow newestScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {    //macht wenig, das meiste passiert erst nach dem ersten klick.
        super.onCreate(savedInstanceState);
        rand = new Random();
        setContentView(R.layout.game_layout);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        context = this;
        scoreList = new ArrayList<>();
        manager = getSupportFragmentManager();
        gameFragment = new GameFragment();
        scoreFragment = new ScoreFragment();
        if(manager.getFragments().isEmpty()) {
            manager.beginTransaction().setReorderingAllowed(true).add(R.id.gameFragmentContainerView, gameFragment).commit();
        }
        else {
            if (manager != null) {
                swapToGame();
            }
        }
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
        int idSearchButton = R.id.search;
        if(item.getItemId() == idSearchButton) {
            gameFragment.backToStart();
            SearchDialog searchByName = new SearchDialog(this, this);
            searchByName.showDialog();
        }
        if(item.getItemId() == idGameButton) {
            if(countDown != null) {
                countDown.cancel();
            }
            swapToGame();
        }
        if(item.getItemId() == idListButton) {
            if(countDown != null) {
                countDown.cancel();
                countDown.onFinish();
            }
            swapToScoreboard();
        }
        return super.onOptionsItemSelected(item);
    }


    public void addScoreList(long endScore, String username) {
        newestScore = new ScoreRow(endScore, username);
        scoreList.add(newestScore);
        SortByScoreDesc scoreSorterDesc = new SortByScoreDesc();
        Collections.sort(scoreList, scoreSorterDesc);
        //die rangliste wird nach dem neuen immer 1. nach hinten geschoben
        for(int i = scoreList.indexOf(newestScore); i < scoreList.size(); i++) {
            scoreList.get(i).place = i + 1;     //da index mit 0 anfängt und rang mit 1 kommt ein +1
        }
        if(newestScore == scoreList.get(0)) {
            gameFragment.setHighScore(newestScore);
        }
    }

    public void swapToGame() {
        manager.beginTransaction().remove(manager.findFragmentById(R.id.gameFragmentContainerView))
                .add(R.id.gameFragmentContainerView, gameFragment).commitNow(); //commitNow, damit es vor dem score stuff passiert
        if(scoreList != null){
            gameFragment.setHighScore(scoreList.get(0));
        }
    }
    public void swapToScoreboard() {
        Bundle args = new Bundle();
        args.putParcelableArrayList("rows", scoreList);
        manager.beginTransaction().remove(manager.findFragmentById(R.id.gameFragmentContainerView)).add(R.id.gameFragmentContainerView,
                ScoreFragment.class, args).commit();
    }

    public void pressedSearch(String username) {
        ArrayList<ScoreRow> personalized = new ArrayList<>();   //neue liste, für den spezifischen namen.
            for(ScoreRow scoreRow : scoreList) {
                if(scoreRow.name.contains(username)) {  //schaut ob die eingabe im text included ist.
                   personalized.add(scoreRow);
                }
            }
            SortByScoreDesc scoreSorterDesc = new SortByScoreDesc();
            Collections.sort(personalized, scoreSorterDesc);
            Bundle args = new Bundle();
            args.putParcelableArrayList("rows", personalized);
        manager.beginTransaction().remove(manager.findFragmentById(R.id.gameFragmentContainerView))
                .add(R.id.gameFragmentContainerView, ScoreFragment.class, args).commit();
    }
}