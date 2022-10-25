package com.example.ejercicio1_isabelserna;

import static androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FlagGame {

    private static String LEADERBOARD_KEY = "leaderboard" ;

    ArrayList<String> countryNames = new ArrayList<>(Arrays.asList("España", "Canada", "Suecia", "Reino Unido", "Argentina", "Italia", "Alemania", "Francia", "Hungría", "Bélgica", "China",
            "Dinamarca", "Finlandia", "Islandia", "Japón", "Gales", "México", "Madagascar", "Noruega", "Polonia", "Rusia", "Suiza", "Grecia"));

    ArrayList<Integer> countryFlags = new ArrayList<>(Arrays.asList(R.drawable.es, R.drawable.ca, R.drawable.se, R.drawable.gb, R.drawable.ar, R.drawable.it, R.drawable.de,
            R.drawable.fr, R.drawable.hu, R.drawable.be, R.drawable.cn , R.drawable.dk, R.drawable.fi, R.drawable.is, R.drawable.jp, R.drawable.gb_wls, R.drawable.mx,
            R.drawable.mg, R.drawable.no, R.drawable.pl, R.drawable.ru, R.drawable.ch, R.drawable.gr));

    private final Context context;

    int winnerNumber = (int) (Math.random() * 21);
    int loserNumber = (int) (Math.random() * 21);
    private String flagGuess;
    private int winnerFlag;
    private int loserFlag;
    private ImageView flag1;
    private ImageView flag2;
    private TextView mainText;
    boolean flag1selected;

    SharedPreferences sharedPref;

    int score = 0;

    public FlagGame(Context context, ImageView flag1, ImageView flag2, TextView mainText) {
        this.context = context;
        this.flag1 = flag1;
        this.flag2 = flag2;
        this.mainText = mainText;
        sharedPref = context.getSharedPreferences("sharedPreferences", context.MODE_PRIVATE);
    }

    void flagGame() {
        do {
            winnerNumber = (int) (Math.random() * countryFlags.size());     // Utilizamos la clase random para elegir dos banderas al azar, y hacemos un do-while para que no salga la misma bandera
            loserNumber = (int) (Math.random() * countryFlags.size());

        } while (winnerNumber == loserNumber);

        flagGuess = countryNames.get(winnerNumber);
        winnerFlag = countryFlags.get(winnerNumber);
        loserFlag = countryFlags.get(loserNumber);

        if (new Random().nextBoolean()) {
            flag1.setImageResource(winnerFlag);
            flag2.setImageResource(loserFlag);
            flag1selected = true;
        } else {
            flag1.setImageResource(loserFlag);
            flag2.setImageResource(winnerFlag);
            flag1selected = false;
        }

        mainText.setText(context.getString(R.string.maintext, flagGuess));

    }

    public void evaluateGame(boolean isFlag1) {

        if ((isFlag1 && flag1selected) || (!isFlag1 && !flag1selected)) {   //opcion de ganar
            flagGame();
            score++;
        } else {    //opcion de perder
            Toast.makeText(context, context.getString(R.string.game_lost, score), Toast.LENGTH_SHORT).show();
            int leaderboard = sharedPref.getInt(LEADERBOARD_KEY, 0);

            if (leaderboard < score){
                sharedPref.edit().putInt(LEADERBOARD_KEY, score).apply();
                String text = context.getString(R.string.leaderboard, score);
                mainText.setText(Html.fromHtml(text, FROM_HTML_MODE_LEGACY));
            }
        }
    }
}
