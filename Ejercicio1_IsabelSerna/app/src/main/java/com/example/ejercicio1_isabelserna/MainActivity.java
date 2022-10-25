package com.example.ejercicio1_isabelserna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ImageView flag1;
    ImageView flag2;
    TextView mainText;
    Button playAgain;

    FlagGame flagGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        flag1 = findViewById(R.id.flag1);
        flag2 = findViewById(R.id.flag2);
        mainText = findViewById(R.id.textView);
        playAgain = findViewById(R.id.play_again);

        flagGame = new FlagGame(this,flag1, flag2, mainText);

    }

    @Override
    protected void onStart() {
        super.onStart();

        flagGame.flagGame();

        flag1.setOnClickListener(view -> {
            flagGame.evaluateGame(true);

        });

        flag2.setOnClickListener(view -> {

            flagGame.evaluateGame(false);

        });

        playAgain.setOnClickListener(view -> {
            flagGame.score = 0;
            flagGame.flagGame();
        });
    }
}