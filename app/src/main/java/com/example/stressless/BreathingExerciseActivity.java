package com.example.stressless;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class BreathingExerciseActivity extends AppCompatActivity {

    private View breathingCircle, breathingCircleMedium, breathingCircleLarge;
    private TextView breathingInstruction, timerTextView;
    private Button startStopBreathingButton;
    private Handler handler = new Handler();

    private CountDownTimer countDownTimer;
    private long totalTime = 60000;
    private NumberPicker timePicker;

    private boolean isExerciseRunning = false;

    private ObjectAnimator currentScaleXOriginal, currentScaleYOriginal;
    private ObjectAnimator currentScaleXMedium, currentScaleYMedium;
    private ObjectAnimator currentScaleXLarge, currentScaleYLarge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathingexercise);

        breathingCircle = findViewById(R.id.breathingCircle);
        breathingCircleMedium = findViewById(R.id.breathingCircleMedium);
        breathingCircleLarge = findViewById(R.id.breathingCircleLarge);
        breathingInstruction = findViewById(R.id.breathingInstruction);
        startStopBreathingButton = findViewById(R.id.startBreathingButton);
        timerTextView = findViewById(R.id.timerTextView);
        timePicker = findViewById(R.id.timePicker);

        timePicker.setMinValue(1);
        timePicker.setMaxValue(300);
        timePicker.setValue(60);

        startStopBreathingButton.setOnClickListener(v -> {
            if (!isExerciseRunning) {
                isExerciseRunning = true;
                startStopBreathingButton.setText("Stop Exercise");
                long totalTime = timePicker.getValue() * 1000;
                startBreathingExercise();
                startTimer(totalTime);
            } else {
                stopBreathingExercise();
            }
        });
    }

    private void startBreathingExercise() {
        animateBreathing(0);
    }

    private void stopBreathingExercise() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        handler.removeCallbacksAndMessages(null);

        if (currentScaleXOriginal != null) currentScaleXOriginal.cancel();
        if (currentScaleYOriginal != null) currentScaleYOriginal.cancel();
        if (currentScaleXMedium != null) currentScaleXMedium.cancel();
        if (currentScaleYMedium != null) currentScaleYMedium.cancel();
        if (currentScaleXLarge != null) currentScaleXLarge.cancel();
        if (currentScaleYLarge != null) currentScaleYLarge.cancel();

        breathingCircle.setScaleX(1f);
        breathingCircle.setScaleY(1f);
        breathingCircleMedium.setScaleX(1f);
        breathingCircleMedium.setScaleY(1f);
        breathingCircleLarge.setScaleX(1f);
        breathingCircleLarge.setScaleY(1f);

        isExerciseRunning = false;
        startStopBreathingButton.setText("Start Exercise");
        timerTextView.setText("Exercise Stopped");
    }

    private void animateBreathing(int step) {
        if (!isExerciseRunning) {
            return;
        }

        float largestScaleFactor = 1.5f;
        float largeScaleFactor = 1.4f;
        float originalScaleFactor = 1.2f;

        long animationDuration = 4000;

        if (step == 0) { // Inhale step
            breathingInstruction.setText("Inhale");

            ObjectAnimator scaleXOriginal = ObjectAnimator.ofFloat(breathingCircle, "scaleX", originalScaleFactor);
            ObjectAnimator scaleYOriginal = ObjectAnimator.ofFloat(breathingCircle, "scaleY", originalScaleFactor);
            scaleXOriginal.setDuration(animationDuration);
            scaleYOriginal.setDuration(animationDuration);

            ObjectAnimator scaleXMedium = ObjectAnimator.ofFloat(breathingCircleMedium, "scaleX", largeScaleFactor);
            ObjectAnimator scaleYMedium = ObjectAnimator.ofFloat(breathingCircleMedium, "scaleY", largeScaleFactor);
            scaleXMedium.setDuration(animationDuration);
            scaleYMedium.setDuration(animationDuration);

            ObjectAnimator scaleXLarge = ObjectAnimator.ofFloat(breathingCircleLarge, "scaleX", largestScaleFactor);
            ObjectAnimator scaleYLarge = ObjectAnimator.ofFloat(breathingCircleLarge, "scaleY", largestScaleFactor);
            scaleXLarge.setDuration(animationDuration);
            scaleYLarge.setDuration(animationDuration);

            scaleXOriginal.start();
            scaleYOriginal.start();
            scaleXMedium.start();
            scaleYMedium.start();
            scaleXLarge.start();
            scaleYLarge.start();

            handler.postDelayed(() -> animateBreathing(1), animationDuration);

        } else if (step == 1) { // Hold step
            breathingInstruction.setText("Hold");

            handler.postDelayed(() -> animateBreathing(2), 7000);

        } else if (step == 2) { // Exhale step
            breathingInstruction.setText("Exhale");

            ObjectAnimator scaleXOriginal = ObjectAnimator.ofFloat(breathingCircle, "scaleX", 1f);
            ObjectAnimator scaleYOriginal = ObjectAnimator.ofFloat(breathingCircle, "scaleY", 1f);
            scaleXOriginal.setDuration(animationDuration);
            scaleYOriginal.setDuration(animationDuration);

            ObjectAnimator scaleXMedium = ObjectAnimator.ofFloat(breathingCircleMedium, "scaleX", 1f);
            ObjectAnimator scaleYMedium = ObjectAnimator.ofFloat(breathingCircleMedium, "scaleY", 1f);
            scaleXMedium.setDuration(animationDuration);
            scaleYMedium.setDuration(animationDuration);

            ObjectAnimator scaleXLarge = ObjectAnimator.ofFloat(breathingCircleLarge, "scaleX", 1f);
            ObjectAnimator scaleYLarge = ObjectAnimator.ofFloat(breathingCircleLarge, "scaleY", 1f);
            scaleXLarge.setDuration(animationDuration);
            scaleYLarge.setDuration(animationDuration);

            scaleXOriginal.start();
            scaleYOriginal.start();
            scaleXMedium.start();
            scaleYMedium.start();
            scaleXLarge.start();
            scaleYLarge.start();

            handler.postDelayed(() -> animateBreathing(0), animationDuration);
        }
    }


    private void startTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerTextView.setText("Done!");
                stopBreathingExercise();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        handler.removeCallbacksAndMessages(null);
    }
}



