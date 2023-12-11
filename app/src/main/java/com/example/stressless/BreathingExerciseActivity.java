// Package declaration
package com.example.stressless;

//Import Statements
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class BreathingExerciseActivity extends AppCompatActivity {
    // UI elements for visualizing the breathing exercise
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

        // Finding views by ID
        breathingCircle = findViewById(R.id.breathingCircle);
        breathingCircleMedium = findViewById(R.id.breathingCircleMedium);
        breathingCircleLarge = findViewById(R.id.breathingCircleLarge);
        breathingInstruction = findViewById(R.id.breathingInstruction);

        // Load custom timing settings
        loadCustomTimings();

        // Setting up navigation buttons
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        ImageButton nav_home = findViewById(R.id.nav_home);
        ImageButton nav_mindfulness = findViewById(R.id.nav_mindfulness);
        ImageButton nav_decibel = findViewById(R.id.nav_decibel);
        ImageButton nav_meditation = findViewById(R.id.nav_meditation);

        // Reference: I took inspiration for the setOnTouchListner code from:
        //https://developer.android.com/reference/android/view/View.OnTouchListener

        // Set touch listeners for navigation buttons, Intents to navigate to to different activities
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
        // Reference complete

        // Set a touch listener on the breathingCircle view
        breathingCircle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Check if the touch event is a 'down' action
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // If the exercise is not currently running, start it
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

    // Method to load custom timing settings for the breathing exercise
    private void loadCustomTimings() {
        // Reference: I took inspiration for the SharedPreferences code from:
        // https://developer.android.com/reference/android/content/SharedPreferences
        SharedPreferences preferences = getSharedPreferences("BreathingAppSettings", MODE_PRIVATE);
        // Reference Complete
        inhaleDuration = preferences.getLong("inhaleDuration", 4000);
        holdDuration = preferences.getLong("holdDuration", 7000);
        exhaleDuration = preferences.getLong("exhaleDuration", 4000);
    }

    // Method to start the breathing exercise
    private void startBreathingExercise() {
        animateBreathing(0);
    }

    // Method to stop the breathing exercise
    private void stopBreathingExercise() {
        // Remove any callbacks and messages in the handler to stop ongoing animations or delays
        handler.removeCallbacksAndMessages(null);
        isExerciseRunning = false;
        resetBreathingCircles();
    }

    // Method to reset the scale of breathing circles to their original size
    private void resetBreathingCircles() {
        breathingCircle.setScaleX(1f);
        breathingCircle.setScaleY(1f);
        breathingCircleMedium.setScaleX(1f);
        breathingCircleMedium.setScaleY(1f);
        breathingCircleLarge.setScaleX(1f);
        breathingCircleLarge.setScaleY(1f);
    }

    // Method to animate the breathing circles based on the breathing step
    private void animateBreathing(int step) {
        if (!isExerciseRunning) {
            return;
        }

        if (step == 0) { // Inhale step
            breathingInstruction.setText("Inhale");
            // Animate circles to enlarge
            startScalingAnimation(breathingCircle, 1.2f, inhaleDuration);
            startScalingAnimation(breathingCircleMedium, 1.4f, inhaleDuration);
            startScalingAnimation(breathingCircleLarge, 1.5f, inhaleDuration);
            // Schedule the next step after inhale duration
            handler.postDelayed(() -> animateBreathing(1), inhaleDuration);
        } else if (step == 1) { // Hold step
            breathingInstruction.setText("Hold");
            handler.postDelayed(() -> animateBreathing(2), holdDuration);
        } else if (step == 2) { // Exhale step
            breathingInstruction.setText("Exhale");
            // Animate circles to return to original size
            startScalingAnimation(breathingCircle, 1f, exhaleDuration);
            startScalingAnimation(breathingCircleMedium, 1f, exhaleDuration);
            startScalingAnimation(breathingCircleLarge, 1f, exhaleDuration);
            handler.postDelayed(() -> animateBreathing(0), exhaleDuration);
        }
    }

    // Reference: I took inspiration for the ObjectAnimator code from:
    // https://developer.android.com/reference/android/animation/ObjectAnimator

    // Method to start the scaling animation on a view
    private void startScalingAnimation(View view, float scaleFactor, long duration) {
        // Create and configure ObjectAnimators for scaling X and Y properties
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", scaleFactor);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", scaleFactor);
        scaleX.setDuration(duration);
        scaleY.setDuration(duration);
        // Start the animations
        scaleX.start();
        scaleY.start();
    }
    // Reference complete

    @Override
    protected void onResume() {
        super.onResume();
        // Reload custom timings when the activity resumes
        loadCustomTimings();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up by removing callbacks and messages in the handler
        handler.removeCallbacksAndMessages(null);
    }
}



