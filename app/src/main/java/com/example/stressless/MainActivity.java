package com.example.stressless;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonMeditation = findViewById(R.id.buttonMeditation);
        Button buttonBreathing = findViewById(R.id.buttonBreathing);
        Button buttonMindfulness = findViewById(R.id.buttonMindfulness);
        Button buttonVolumeTester = findViewById(R.id.buttonVolumeTester);

        buttonMeditation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(MainActivity.this, MeditationActivity.class));
                return true;
            }
        });

        buttonBreathing.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(MainActivity.this, BreathingActivity.class));
                return true;
            }
        });

        buttonMindfulness.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(MainActivity.this, MindfulnessActivity.class));
                return true;
            }
        });

        buttonVolumeTester.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(MainActivity.this, VolumeTesterActivity.class));
                return true;
            }
        });
    }
}

