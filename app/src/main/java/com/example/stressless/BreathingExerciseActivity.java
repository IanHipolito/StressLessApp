package com.example.stressless;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class BreathingExerciseActivity extends AppCompatActivity {
    private View breathingCircle, breathingCircleMedium, breathingCircleLarge;
    private TextView breathingInstruction;
    private Handler handler = new Handler();
    private boolean isExerciseRunning = false;
    private long inhaleDuration = 4000;
    private long holdDuration = 7000;
    private long exhaleDuration = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathingexercise);

        breathingCircle = findViewById(R.id.breathingCircle);
        breathingCircleMedium = findViewById(R.id.breathingCircleMedium);
        breathingCircleLarge = findViewById(R.id.breathingCircleLarge);
        breathingInstruction = findViewById(R.id.breathingInstruction);

        loadCustomTimings();

        ImageButton settingsButton = findViewById(R.id.settingsButton);
        ImageButton nav_home = findViewById(R.id.nav_home);
        ImageButton nav_mindfulness = findViewById(R.id.nav_mindfulness);
        ImageButton nav_decibel = findViewById(R.id.nav_decibel);
        ImageButton nav_meditation = findViewById(R.id.nav_meditation);

        settingsButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(BreathingExerciseActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        nav_home.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(BreathingExerciseActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        nav_mindfulness.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(BreathingExerciseActivity.this, MindfulnessActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        nav_decibel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(BreathingExerciseActivity.this, VolumeTesterActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        nav_meditation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(BreathingExerciseActivity.this, MeditationActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


        breathingCircle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!isExerciseRunning) {
                        isExerciseRunning = true;
                        startBreathingExercise();
                    } else {
                        stopBreathingExercise();
                    }
                }
                return false;
            }
        });
    }

    private void loadCustomTimings() {
        SharedPreferences preferences = getSharedPreferences("BreathingAppSettings", MODE_PRIVATE);
        inhaleDuration = preferences.getLong("inhaleDuration", 4000);
        holdDuration = preferences.getLong("holdDuration", 7000);
        exhaleDuration = preferences.getLong("exhaleDuration", 4000);
    }

    private void startBreathingExercise() {
        animateBreathing(0);
    }

    private void stopBreathingExercise() {
        handler.removeCallbacksAndMessages(null);
        isExerciseRunning = false;
        resetBreathingCircles();
    }

    private void resetBreathingCircles() {
        breathingCircle.setScaleX(1f);
        breathingCircle.setScaleY(1f);
        breathingCircleMedium.setScaleX(1f);
        breathingCircleMedium.setScaleY(1f);
        breathingCircleLarge.setScaleX(1f);
        breathingCircleLarge.setScaleY(1f);
    }

    private void animateBreathing(int step) {
        if (!isExerciseRunning) {
            return;
        }

        if (step == 0) {
            breathingInstruction.setText("Inhale");
            startScalingAnimation(breathingCircle, 1.2f, inhaleDuration);
            startScalingAnimation(breathingCircleMedium, 1.4f, inhaleDuration);
            startScalingAnimation(breathingCircleLarge, 1.5f, inhaleDuration);
            handler.postDelayed(() -> animateBreathing(1), inhaleDuration);
        } else if (step == 1) {
            breathingInstruction.setText("Hold");
            handler.postDelayed(() -> animateBreathing(2), holdDuration);
        } else if (step == 2) {
            breathingInstruction.setText("Exhale");
            startScalingAnimation(breathingCircle, 1f, exhaleDuration);
            startScalingAnimation(breathingCircleMedium, 1f, exhaleDuration);
            startScalingAnimation(breathingCircleLarge, 1f, exhaleDuration);
            handler.postDelayed(() -> animateBreathing(0), exhaleDuration);
        }
    }

    private void startScalingAnimation(View view, float scaleFactor, long duration) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", scaleFactor);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", scaleFactor);
        scaleX.setDuration(duration);
        scaleY.setDuration(duration);
        scaleX.start();
        scaleY.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCustomTimings();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}



