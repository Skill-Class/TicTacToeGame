package com.example.tictactoe;

import android.app.Activity;
import android.content.Intent;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.DrawableRes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.CountDownTimer;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_PLAYER_1_SYMBOL = "Player1Symbol";

    public static void start(final Activity activity, final String player1Symbol) {
        final Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(EXTRA_PLAYER_1_SYMBOL, player1Symbol);
        activity.startActivity(intent);
    }

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    //  private Timer timer;
    //   private int time = 0;

    //private CountDownTimer countDownTimer;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView timerView;
    //   private Chronometer timerr;
    private Animation flip, clear;
    private PopupWindow mPopupWindow;
    private Button addPlayer1Name, addPlayer2Name, okButton;
    private EditText playerNameEditText;
    String playerName = "";
    String player1 = "Player One", player2 = "Player Two";


    private String player1Symbol = "X";
    private String player2Symbol = "O";


    private SoundPool soundPool;
    private int victoryStreamId;
    private int drawStreamId;
  
    private String player1Colour = "#79CADC";   //lighter blue
    private String player2Colour = "#3A98D4"; //darker blue


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ThemeInitializer.getInstance().isNightModeEnabled(getApplicationContext())) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        flip = AnimationUtils.loadAnimation(this, R.anim.rotate);
        clear = AnimationUtils.loadAnimation(this, R.anim.clear);
        setContentView(R.layout.activity_main);

        final String chosenPlayer1Symbol = getIntent().getStringExtra(EXTRA_PLAYER_1_SYMBOL);
        if ("O".equals(chosenPlayer1Symbol)) {
            player1Symbol = "O";
            player2Symbol = "X";
        }

        textViewPlayer1 = findViewById(R.id.textView);
        textViewPlayer2 = findViewById(R.id.textView2);
        addPlayer1Name = findViewById(R.id.addName);
        addPlayer2Name = findViewById(R.id.addPlayer2Name);
       // updatePointsText();

        //   timerr = findViewById(R.id.timerID);
        timerView = findViewById(R.id.timerID);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                // buttons[i][j].setOnClickListener((View.OnClickListener) this);

                buttons[i][j].setOnClickListener(this::buttonPressed);
            }
        }

        ImageButton buttonReset = findViewById(R.id.image_button);
        buttonReset.setOnClickListener(v -> resetGame());
        addPlayer1Name.setOnClickListener(view -> {
            getPlayerName(1);
        });
        addPlayer2Name.setOnClickListener(view -> {
            getPlayerName(2);
        });

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        victoryStreamId = soundPool.load(this, R.raw.victory, 0);
        drawStreamId = soundPool.load(this, R.raw.draw, 0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_exit) {
            finish();
            moveTaskToBack(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Pops up a window with editText to enter the name and hitting close will update the name
     */
    private void getPlayerName(int i) {
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View customView = inflater.inflate(R.layout.add_player_name, null);
        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                true
        );
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }

        //  timerr.start();

        ImageButton closeButton = customView.findViewById(R.id.ib_close);
        playerNameEditText = customView.findViewById(R.id.playerName);

        closeButton.setOnClickListener(view1 -> {
            // Dismiss the popup window
            if (playerNameEditText != null) {
                playerName = playerNameEditText.getText().toString();
            }
            if (!playerName.equals("")) {
                if (i == 1) {
                    player1 = playerName;
                    updatePointsText();
                } else {
                    player2 = playerName;
                    updatePointsText();
                }
            }
            mPopupWindow.dismiss();
        });


        mPopupWindow.showAtLocation(addPlayer1Name, Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //countDownTimer.cancel();
        soundPool.release();
    }

    private void buttonPressed(View view) {


        if (!(((Button) view).getText().toString().equals(""))) {
            return;
        }
        view.startAnimation(flip);
        if (player1Turn) {
            ((Button) view).setText(player1Symbol);
            // ((Button) view).setTextColor(R.color.XColor);
            ((Button) view).setTextColor(Color.parseColor(player1Colour));
        } else {
            ((Button) view).setText(player2Symbol);
            // ((Button) view).setTextColor(R.color.OColor);
            ((Button) view).setTextColor(Color.parseColor(player2Colour));

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


    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
        //countDownTimer.cancel();
        //countDownTimer.start();


        //  timerr.setBase(SystemClock.elapsedRealtime());

        //  timer.cancel();
        //  timer.purge();
        //  time = 0;


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

        return field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("");
    }

    private void player1Wins() {
        player1Points++;
        showToastMessage("Player one won", R.drawable.thumbs_up);

        updatePointsText();
        resetBoard();
        //  countDownTimer.cancel();
        // countDownTimer.start();

        onPlayerVictory();

        //  timer.cancel();
        //  timer.purge();
        //  time = 0;
        //  textViewTimer.setText(String.valueOf(time));
    }

    private void player2Wins() {
        player2Points++;
        showToastMessage("Player two won", R.drawable.thumbs_up);

        updatePointsText();
        resetBoard();
        //  countDownTimer.cancel();
        //  countDownTimer.start();

        onPlayerVictory();

        //   timer.cancel();
        //   timer.purge();
        //  time = 0;
        // textViewTimer.setText(String.valueOf(time));
    }

    private void onPlayerVictory() {
        soundPool.play(victoryStreamId, 1f, 1f, 0, 0, 0f);
        updatePointsText();
        resetBoard();
        //countDownTimer.cancel();
        //countDownTimer.start();
    }

    private void draw() {
        soundPool.play(drawStreamId, 1f, 1f, 0, 0, 0f);
        showToastMessage("Draw", R.drawable.draw);
        resetBoard();
        //countDownTimer.cancel();
        //countDownTimer.start();
        //   timer.cancel();
        //   timer.purge();
    }

    private void updatePointsText() {
        textViewPlayer1.setText(player1 + " - " + player1Points);
        textViewPlayer2.setText(player2 + " - " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].startAnimation(clear);
            }
        }

        roundCount = 0;
        player1Turn = true;
    }


    private void showToastMessage(String message, @DrawableRes int icon) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        ImageView image = layout.findViewById(R.id.image);
        image.setImageResource(icon);
        TextView text = layout.findViewById(R.id.text);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


}
