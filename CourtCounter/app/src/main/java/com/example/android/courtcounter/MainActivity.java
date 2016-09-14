package com.example.android.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
int scoreTeamA=0;
    int scoreTeamB=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
    }
    public void displayForTeamA(int score){
        TextView ScoreView = (TextView) findViewById(R.id.team_a_score);
        ScoreView.setText(String.valueOf(score));
    }

    public void threePointA(View view){
        scoreTeamA =scoreTeamA+3;
        displayForTeamA(scoreTeamA);
    }

    public void twoPointA(View view){
        scoreTeamA =scoreTeamA+2;
        displayForTeamA(scoreTeamA);
    }

    public void freeThrowA(View view){
        scoreTeamA =scoreTeamA+1;
        displayForTeamA(scoreTeamA);
    }

    //team B
    public void displayForTeamB(int score){
        TextView ScoreView = (TextView) findViewById(R.id.team_b_score);
        ScoreView.setText(String.valueOf(score));
    }

    public void threePointB(View view){
        scoreTeamB =scoreTeamB+3;
        displayForTeamB(scoreTeamB);
    }

    public void twoPointB(View view){
        scoreTeamB =scoreTeamB+2;
        displayForTeamB(scoreTeamB);
    }

    public void freeThrowB(View view){
        scoreTeamB =scoreTeamB+1;
        displayForTeamB(scoreTeamB);
    }

    public void reset(View view){
        scoreTeamA=0;
        scoreTeamB=0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
    }
}
