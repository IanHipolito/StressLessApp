// Pack age Declaration
package com.example.stressless;

// Import Statements
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding ImageView elements by their IDs
        ImageView ImageViewMeditation = findViewById(R.id.ImageViewMeditation);
        ImageView ImageViewBreathingExercise = findViewById(R.id.ImageViewBreathingExercise);
        ImageView ImageViewMindfulness = findViewById(R.id.ImageViewMindfulness);
        ImageView ImageViewVolumeTester = findViewById(R.id.ImageViewVolumeTester);

        // Reference: I took inspiration for the setOnTouchListner code from:
        //https://developer.android.com/reference/android/view/View.OnTouchListener

        // Setting touch listeners on the ImageViews and start activities when ImageViews are touched
        ImageViewMeditation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(MainActivity.this, MeditationActivity.class));
                return true;
            }
        });

        ImageViewBreathingExercise.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(MainActivity.this, BreathingExerciseActivity.class));
                return true;
            }
        });

        ImageViewMindfulness.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(MainActivity.this, MindfulnessActivity.class));
                return true;
            }
        });

        ImageViewVolumeTester.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(MainActivity.this, VolumeTesterActivity.class));
                return true;
            }
        });
        // Reference complete
    }
}

