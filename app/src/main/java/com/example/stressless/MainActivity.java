package com.example.stressless;

import android.content.Intent;
import android.os.Bundle;
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
//        Button buttonSoundLevel = findViewById(R.id.buttonSoundLevel);

        buttonMeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MeditationActivity.class));
            }
        });

        buttonBreathing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BreathingActivity.class));
            }
        });

//        buttonMindfulness.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, MindfulnessActivity.class));
//            }
//        });

    }
}

