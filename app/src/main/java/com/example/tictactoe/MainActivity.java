package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.textView);
        textViewPlayer2 = findViewById(R.id.textView2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
              //  buttons[i][j].setOnClickListener((View.OnClickListener) this);

                buttons[i][j].setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (!(((Button) view).getText().toString().equals(""))) {
                            return;
                        }

                        if (player1Turn) {
                            ((Button) view).setText("X");
                            //Typeface custom_font = Typeface.createFromAsset(getAssets(),  "font/tictactoefont.ttf");
                            //((Button) view).setTypeface(custom_font);
                           // ((Button) view).setTextColor(R.color.XColor);
                            ((Button) view).setTextColor(Color.parseColor("#490080"));
                        } else {
                            ((Button) view).setText("O");
                            //Typeface custom_font = Typeface.createFromAsset(getAssets(),  "font/tictactoefont.ttf");
                            //((Button) view).setTypeface(custom_font);
                           // ((Button) view).setTextColor(R.color.OColor);
                            ((Button) view).setTextColor(Color.parseColor("#240040"));
                        }

                        roundCount++;

                        if (checkForWin()) {
                            if (player1Turn) {
                                player1Wins();
                            } else {
                                player2Wins();
                            }
                        } else if (roundCount == 9) {
                            draw();
                        } else {
                            player1Turn = !player1Turn;
                        }
                    }
                });
            }
        }

       ImageButton buttonReset = findViewById(R.id.image_button);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            resetGame();
            }
        });
    }


    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }



    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player One wins :) ", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player Two wins :) ", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw :(", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText(player1Points + "");
        textViewPlayer2.setText(player2Points + "");
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }
}
