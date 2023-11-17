package com.example.stressless;

import android.os.Bundle;
import android.widget.VideoView;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

public class BreathingActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing);

        videoView = findViewById(R.id.videoView);

        // Set the URI of the video to be played
        String uriPath = "android.resource://" + getPackageName() + "/" + R.raw.takeadeepbreath;
        videoView.setVideoURI(Uri.parse(uriPath));

        // Set an on click listener on the VideoView
        videoView.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
            } else {
                videoView.start();
            }
        });
    }
}
