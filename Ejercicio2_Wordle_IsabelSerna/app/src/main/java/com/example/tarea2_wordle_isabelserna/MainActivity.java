package com.example.tarea2_wordle_isabelserna;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public Button[] buttonsKeyboard;
    public Button[] buttonsWords;
    public LinearLayout linearLayout;

    public final ArrayList<Character> keyboardLetters = new ArrayList<>(Arrays.asList(
            'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p',
            'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'ñ',
            '⌫','z', 'x', 'c', 'v', 'b', 'n', 'm', '↩'
    ));

    private final int rowNumber = 6;
    private final int columnNumber = 5;
    private String randomWord;
    private ArrayList<String> word = new ArrayList<>();
    private ArrayList<Button> buttonsPressed = new ArrayList<>();

    private int currentLine = 0;
    private int attempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.layoutPrincipal);

        linearLayout.post(new Runnable() {
            @Override
            public void run() {
                createIU();
            }
        });
    }

    public void createIU() {

        currentLine = 0;
        attempts = 0;
        word.clear();

        generateWord();
        generatewordsAttemps();
        generateKeyboard();

    }

    private void generateWord() {


        try {
            InputStream is = getAssets().open("palabras5.txt", AssetManager.ACCESS_RANDOM);
            int randomWordPosition = new Random().nextInt(3784);
            randomWordPosition *= (columnNumber + 1);
            is.skip(randomWordPosition);
            byte b[] = new byte[5];
            is.read(b);
            randomWord = new String(b);
            System.out.println("Random word: " + randomWord);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generatewordsAttemps() {

        GridLayout miGrid = findViewById(R.id.miGrid);
        miGrid.removeAllViews();

        miGrid.setColumnCount(columnNumber);
        miGrid.setRowCount(rowNumber);
        buttonsWords = new Button[columnNumber * rowNumber];

        int height = miGrid.getHeight();
        int width = miGrid.getWidth();
        Log.d("RiberaDelTajo", "width:" + width + ";height" + height);

        for (int i = 0; i < columnNumber * rowNumber; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((width - (32 * columnNumber)) / columnNumber, (height - (32 * rowNumber)) / rowNumber);
            params.setMargins(16, 16, 16, 16);
            buttonsWords[i] = new Button(this);
            buttonsWords[i].setLayoutParams(params);
            buttonsWords[i].setText("");
            buttonsWords[i].setClickable(false);
            miGrid.addView(buttonsWords[i]);
        }
    }

    public void generateKeyboard() {
        GridLayout miGridKeyboard = findViewById(R.id.miGridKeyboard);
        miGridKeyboard.removeAllViews();

        int rowKeyboard = 3;
        int columnKeyboard = 10;

        buttonsKeyboard = new Button[rowKeyboard * columnKeyboard];

        miGridKeyboard.setColumnCount(columnKeyboard);
        miGridKeyboard.setRowCount(rowKeyboard);

        int heightKeyboard = (int) (miGridKeyboard.getHeight() * 1.7);
        int widthKeyboard = miGridKeyboard.getWidth();


        for (int i = 0; i < keyboardLetters.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((widthKeyboard - (16 * columnNumber)) / columnKeyboard, heightKeyboard / rowNumber);
            params.setMargins(4, 4, 4, 4);
            buttonsKeyboard[i] = new Button(this);

            buttonsKeyboard[i].setText(keyboardLetters.get(i).toString());

            buttonsKeyboard[i].setLayoutParams(params);

            if(buttonsKeyboard[i].getText().equals("↩")){
                buttonsKeyboard[i].setTextSize(18);
            }

            miGridKeyboard.addView(buttonsKeyboard[i]);

            int finalI = i;
            buttonsKeyboard[i].setOnClickListener(v -> {
                String letter = buttonsKeyboard[finalI].getText().toString();
                Button button = buttonsKeyboard[finalI];
                letterClicked(letter,button);
            });
        }

    }

    private void letterClicked(String letter, Button button) {
        switch (letter) {
            case "⌫":
                if (attempts > 0) {
                    attempts--;
                    word.remove(attempts);
                    buttonsPressed.remove(attempts);
                    buttonsWords[(currentLine * columnNumber) + attempts].setText("");
                } else {
                    Toast.makeText(this, "No puedes borrar", Toast.LENGTH_SHORT).show();
                }
                break;

            case "↩":
                if (word.size() == columnNumber) {
                    if (checkWord()) {
                        showWinDialog();
                        break;
                    } else {
                        checkLose();
                    }
                    word.clear();
                    buttonsPressed.clear();
                    currentLine++;
                    attempts = 0;
                } else {
                    Toast.makeText(this, "Completa la palabra", Toast.LENGTH_SHORT).show();
                }

                checkLose();
                break;

            default:
                if (word.size() < columnNumber) {
                    buttonsWords[(currentLine * columnNumber) + attempts].setText(letter);
                    word.add(letter);
                    buttonsPressed.add(button);
                    attempts++;
                } else {
                    Toast.makeText(this, "Valida la palabra", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void checkLose() {
        if (currentLine == rowNumber) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Perdiste. La palabra era " + randomWord)
                    .setTitle("Fin del juego")
                    .setPositiveButton("Jugar de nuevo", (dialog, which) -> {
                        createIU();
                        dialog.dismiss();
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void showWinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Has ganado")
                .setTitle("Fin del juego")
                .setPositiveButton("Jugar de nuevo", (dialog, which) -> {
                    createIU();
                    dialog.dismiss();
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean checkWord() {

        int lettersCorrected = 0;
        ArrayList<String> winnerWord = new ArrayList<>();

        for (int i = 0; i < randomWord.length(); i++) {
            winnerWord.add(randomWord.substring(i, i + 1));
        }

        for (int i = 0; i < columnNumber; i++) {
            if (word.get(i).equals(winnerWord.get(i))) {
                buttonsWords[(currentLine * columnNumber) + i].setBackgroundColor(getColor(R.color.green));
                lettersCorrected++;
            } else if (winnerWord.contains(word.get(i))) {
                buttonsWords[(currentLine * columnNumber) + i].setBackgroundColor(getColor(R.color.yellow));
            } else {
                buttonsWords[(currentLine * columnNumber) + i].setBackgroundColor(getColor(R.color.grey));
            }
            for ( Button button : buttonsPressed){
                button.setBackgroundColor(getColor(R.color.grey));
            }
        }

        return lettersCorrected == columnNumber;
    }
}