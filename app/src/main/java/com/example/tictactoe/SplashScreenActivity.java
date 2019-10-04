package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        ImageView icon = (ImageView) findViewById(R.id.splash_Screen_icon);
        TextView text = (TextView)findViewById(R.id.splash_screen_text);
        Animation fade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(getApplicationContext(),SymbolChooserActivity.class));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        icon.startAnimation(fade);
        text.startAnimation(fade);






    }
}
