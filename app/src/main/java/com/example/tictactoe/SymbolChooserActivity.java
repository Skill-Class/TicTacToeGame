package com.example.tictactoe;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SymbolChooserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symbol_chooser);

        findViewById(R.id.button_O).setOnClickListener(v -> onSymbolChosen("O"));
        findViewById(R.id.button_X).setOnClickListener(v -> onSymbolChosen("X"));
    }

    private void onSymbolChosen(final String symbol) {
        MainActivity.start(this, symbol);
    }
}
