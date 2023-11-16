package com.example.stressless;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MeditationActivity extends AppCompatActivity{
    private MediaPlayer mediaPlayer;
    private Button playButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        mediaPlayer = MediaPlayer.create(this, R.raw.meditation_music);
        playButton = findViewById(R.id.playButton);
        stopButton = findViewById(R.id.stopButton);

        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    Toast.makeText(MeditationActivity.this, "Meditation Music Playing",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    Toast.makeText(MeditationActivity.this, "Meditation Music Stopped",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
