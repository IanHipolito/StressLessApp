package com.example.stressless;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MeditationActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private ImageButton playPauseButton;
    private ImageButton previousButton;
    private ImageButton nextButton;
    private TextView songTitleTextView;
    private ListView songListView;
    private SeekBar songProgressBar;
    private ArrayAdapter<String> adapter;
    private Handler progressHandler = new Handler();
    private String[] songNames = {"Meditation Music 1", "Meditation Music 2", "Meditation Music 3", "Meditation Music 4", "Meditation Music 5"};
    private int[] songs = {R.raw.meditation_music, R.raw.meditation_music1, R.raw.meditation_music2, R.raw.meditation_music3, R.raw.meditation_music4};
    private int currentSongIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);
        playPauseButton = findViewById(R.id.playPauseButton);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        songTitleTextView = findViewById(R.id.songTitleTextView);
        songListView = findViewById(R.id.songListView);
        songProgressBar = findViewById(R.id.songProgressBar);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songNames);
        songListView.setAdapter(adapter);
        updateSongTitle(currentSongIndex);

        playPauseButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        playPauseButton.setImageResource(R.drawable.baseline_play_arrow_24);
                    } else {
                        mediaPlayer.start();
                        playPauseButton.setImageResource(R.drawable.baseline_pause_24);
                    }
                    return true;
                }
                return false;
            }
        });


        previousButton.setOnClickListener(view -> changeSong(false));
        nextButton.setOnClickListener(view -> changeSong(true));

        songListView.setOnItemClickListener((parent, view, position, id) -> {
            currentSongIndex = position;
            playSong(currentSongIndex);
        });

        songProgressBar.setMax(mediaPlayer.getDuration());
        songProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }
        });
        updateProgressBar();
    }

    private void updateProgressBar() {
        progressHandler.postDelayed(updateProgressRunnable, 100);
    }

    private Runnable updateProgressRunnable = new Runnable() {
        public void run() {
            if (mediaPlayer != null) {
                int mCurrentPosition = mediaPlayer.getCurrentPosition();
                songProgressBar.setProgress(mCurrentPosition);
            }
            progressHandler.postDelayed(this, 100);
        }
    };

    private void updateSongTitle(int currentSongIndex) {
        songTitleTextView.setText(songNames[currentSongIndex]);
    }

    private void playSong(int songIndex) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, songs[songIndex]);
        mediaPlayer.start();
        updateSongTitle(songIndex);
        playPauseButton.setImageResource(R.drawable.baseline_pause_24);
        songProgressBar.setMax(mediaPlayer.getDuration());
        updateProgressBar();
    }

    private void changeSong(boolean next) {
        if (next) {
            currentSongIndex = (currentSongIndex + 1) % songs.length;
        } else {
            currentSongIndex = (currentSongIndex - 1 + songs.length) % songs.length;
        }
        playSong(currentSongIndex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        progressHandler.removeCallbacks(updateProgressRunnable);
    }
}

