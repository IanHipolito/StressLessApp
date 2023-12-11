// Package declaration
package com.example.stressless;

// Import statements
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
import android.content.Intent;
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

        // Initialize MediaPlayer with the first song
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);

        // Finding views by their IDs
        playPauseButton = findViewById(R.id.playPauseButton);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        songTitleTextView = findViewById(R.id.songTitleTextView);
        songListView = findViewById(R.id.songListView);
        songProgressBar = findViewById(R.id.songProgressBar);

        // Create a new ArrayAdapter for the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songNames);
        // Set the adapter to the ListView
        songListView.setAdapter(adapter);
        // Update the song title text view to show the title of the current song
        updateSongTitle(currentSongIndex);

        // Setting up navigation buttons
        ImageButton nav_home = findViewById(R.id.nav_home);
        ImageButton nav_mindfulness = findViewById(R.id.nav_mindfulness);
        ImageButton nav_decibel = findViewById(R.id.nav_decibel);
        ImageButton nav_breathing = findViewById(R.id.nav_breathing);

        // Reference: I took inspiration for the setOnTouchListner code from:
        //https://developer.android.com/reference/android/view/View.OnTouchListener

        // Set touch listeners for navigation buttons, Intents to navigate to to different activities
        nav_home.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(MeditationActivity.this, MainActivity.class);
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
                    Intent intent = new Intent(MeditationActivity.this, MindfulnessActivity.class);
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
                    Intent intent = new Intent(MeditationActivity.this, VolumeTesterActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        nav_breathing.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(MeditationActivity.this, BreathingExerciseActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        // Reference complete

        // Set an OnTouchListener on the playPauseButton
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

        // Set an OnClickListener for the previousButton
        previousButton.setOnClickListener(view -> changeSong(false));

        // Set an OnClickListener for the nextButton
        nextButton.setOnClickListener(view -> changeSong(true));

        // Set an OnItemClickListener for the songListView
        songListView.setOnItemClickListener((parent, view, position, id) -> {
            // Update the current song index to the position clicked
            currentSongIndex = position;
            playSong(currentSongIndex);
        });

        songProgressBar.setMax(mediaPlayer.getDuration());

        // Reference: I took inspiration for the SeekBar code from:
        // https://developer.android.com/reference/android/widget/SeekBar.OnSeekBarChangeListener

        // Set up SeekBar listener for song progress interaction
        songProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // If the user changed the seek bar, seek to the specific position in the song
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Pause the song when the user starts interacting with the seek bar
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Resume playing the song when user interaction with seek bar stops
                mediaPlayer.start();
            }
        });
        // Initialize the song progress bar
        updateProgressBar();
    }
    // Reference Complete

    // Method to update the progress bar periodically
    private void updateProgressBar() {
        progressHandler.postDelayed(updateProgressRunnable, 100);
    }

    // Runnable for updating the progress bar
    private Runnable updateProgressRunnable = new Runnable() {
        public void run() {
            if (mediaPlayer != null) {
                // Update the progress bar to the current position of the song
                int mCurrentPosition = mediaPlayer.getCurrentPosition();
                songProgressBar.setProgress(mCurrentPosition);
            }
            // Schedule the next update after 100 milliseconds
            progressHandler.postDelayed(this, 100);
        }
    };

    // Method to update the displayed song title
    private void updateSongTitle(int currentSongIndex) {
        songTitleTextView.setText(songNames[currentSongIndex]);
    }

    // Reference: I took inspiration for the MediaPlayer code from:
    //https://developer.android.com/reference/android/media/MediaPlayer

    // Method to play a song
    private void playSong(int songIndex) {
        // Release the current MediaPlayer resource
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // Create a new MediaPlayer for the selected song
        mediaPlayer = MediaPlayer.create(this, songs[songIndex]);
        mediaPlayer.start();
        updateSongTitle(songIndex);
        playPauseButton.setImageResource(R.drawable.baseline_pause_24);
        songProgressBar.setMax(mediaPlayer.getDuration());
        updateProgressBar();
    }
    // Reference complete

    // Method to change the song
    private void changeSong(boolean next) {
        // Calculate the index of the next or previous song
        if (next) {
            currentSongIndex = (currentSongIndex + 1) % songs.length;
        } else {
            currentSongIndex = (currentSongIndex - 1 + songs.length) % songs.length;
        }
        playSong(currentSongIndex); // Play the selected song
    }

    // Reference: I took inspiration for the MediaPlayer code from:
    //https://developer.android.com/reference/android/media/MediaPlayer
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources and remove callbacks when the activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        progressHandler.removeCallbacks(updateProgressRunnable);
    }
    // Reference complete
}

