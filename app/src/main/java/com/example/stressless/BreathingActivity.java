package com.example.stressless;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

public class BreathingActivity extends AppCompatActivity {
    private VideoView videoView1, videoView2, videoView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing);

        videoView1 = findViewById(R.id.videoView);
        videoView2 = findViewById(R.id.videoView2);
        videoView3 = findViewById(R.id.videoView3);

        String uriPath1 = "android.resource://" + getPackageName() + "/" + R.raw.exercise1;
        videoView1.setVideoURI(Uri.parse(uriPath1));

        String uriPath2 = "android.resource://" + getPackageName() + "/" + R.raw.exercise2;
        videoView2.setVideoURI(Uri.parse(uriPath2));

        String uriPath3 = "android.resource://" + getPackageName() + "/" + R.raw.exercise3;
        videoView3.setVideoURI(Uri.parse(uriPath3));

        setVideoViewListener(videoView1);
        setVideoViewListener(videoView2);
        setVideoViewListener(videoView3);

        Button navToBreathingExerciseButton = findViewById(R.id.navToBreathingExerciseButton);
        navToBreathingExerciseButton.setOnClickListener(v -> {
            Intent intent = new Intent(BreathingActivity.this, BreathingExerciseActivity.class);
            startActivity(intent);
        });
    }

    private void setVideoViewListener(VideoView videoView) {
        videoView.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
            } else {
                videoView.start();
            }
        });
    }
}

