package com.example.stressless;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class BreathingExerciseActivity extends AppCompatActivity {

    private View breathingCircle;
    private TextView breathingInstruction;
    private Button startBreathingButton;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathingexercise);

        breathingCircle = findViewById(R.id.breathingCircle);
        breathingInstruction = findViewById(R.id.breathingInstruction);
        startBreathingButton = findViewById(R.id.startBreathingButton);

        startBreathingButton.setOnClickListener(v -> startBreathingExercise());
    }

    private void startBreathingExercise() {
        animateBreathing(0);
    }

    private void animateBreathing(int step) {
        if (step == 0) {
            breathingInstruction.setText("Inhale");
            ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(breathingCircle, "scaleX", 2f);
            ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(breathingCircle, "scaleY", 2f);
            scaleUpX.setDuration(4000);
            scaleUpY.setDuration(4000);
            scaleUpX.setInterpolator(new AccelerateDecelerateInterpolator());
            scaleUpY.setInterpolator(new AccelerateDecelerateInterpolator());
            scaleUpX.start();
            scaleUpY.start();
            handler.postDelayed(() -> animateBreathing(1), 4000);
        } else if (step == 1) {
            breathingInstruction.setText("Hold");
            handler.postDelayed(() -> animateBreathing(2), 7000);
        } else if (step == 2) {
            breathingInstruction.setText("Exhale");
            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(breathingCircle, "scaleX", 1f);
            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(breathingCircle, "scaleY", 1f);
            scaleDownX.setDuration(4000);
            scaleDownY.setDuration(4000);
            scaleDownX.setInterpolator(new AccelerateDecelerateInterpolator());
            scaleDownY.setInterpolator(new AccelerateDecelerateInterpolator());
            scaleDownX.start();
            scaleDownY.start();
            handler.postDelayed(() -> animateBreathing(0), 4000);
        }
    }
}
