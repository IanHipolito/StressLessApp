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
    private Button previousButton;
    private Button nextButton;
    private int[] songs = {R.raw.meditation_music, R.raw.meditation_music2, R.raw.meditation_music3, R.raw.meditation_music4};
    private int currentSongIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        mediaPlayer = MediaPlayer.create(this, R.raw.meditation_music);
        playButton = findViewById(R.id.playButton);
        stopButton = findViewById(R.id.stopButton);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);

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

        previousButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(currentSongIndex > 0){
                    currentSongIndex--;
                }
                else{
                    currentSongIndex = songs.length -1;
                }
                playSong(currentSongIndex);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(currentSongIndex < songs.length - 1){
                    currentSongIndex++;
                }
                else{
                    currentSongIndex = 0;
                }
                playSong(currentSongIndex);
            }
        });

    }

    private void playSong(int SongIndex){
        if(mediaPlayer !=null){
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, songs[SongIndex]);
        mediaPlayer.start();
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
