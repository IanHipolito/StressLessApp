package com.example.stressless;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class VolumeTesterActivity extends AppCompatActivity {
    private static final int SAMPLE_RATE = 8000;
    private AudioRecord audioRecord;
    private boolean isRecording = false;
    private Thread recordingThread;
    private TextView decibelTextView;
    private TextView statusTextView;
    private Button startStopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_tester);

        decibelTextView = findViewById(R.id.decibelTextView);
        statusTextView = findViewById(R.id.statusTextView);
        startStopButton = findViewById(R.id.startStopButton);

        ImageButton nav_home = findViewById(R.id.nav_home);
        ImageButton nav_mindfulness = findViewById(R.id.nav_mindfulness);
        ImageButton nav_meditation = findViewById(R.id.nav_meditation);
        ImageButton nav_breathing = findViewById(R.id.nav_breathing);

        nav_home.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(VolumeTesterActivity.this, MainActivity.class);
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
                    Intent intent = new Intent(VolumeTesterActivity.this, MindfulnessActivity.class);
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
                    Intent intent = new Intent(VolumeTesterActivity.this, VolumeTesterActivity.class);
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
                    Intent intent = new Intent(VolumeTesterActivity.this, BreathingExerciseActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        startStopButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (isRecording) {
                        stopRecording();
                    } else {
                        startRecording();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void startRecording() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 0);
        }

        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT));

        audioRecord.startRecording();
        isRecording = true;
        statusTextView.setText("Recording...");
        startStopButton.setText("Stop");

        recordingThread = new Thread(new Runnable() {
            public void run() {
                calculateDecibelLevel();
            }
        }, "AudioRecorder Thread");
        recordingThread.start();
    }

    private void stopRecording() {
        if (audioRecord != null) {
            isRecording = false;
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
            recordingThread = null;
            statusTextView.setText("Stopped");
            startStopButton.setText("Start");
        }
    }

    private void calculateDecibelLevel() {
        short[] audioBuffer = new short[AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)];
        while (isRecording) {
            int numberOfShort = audioRecord.read(audioBuffer, 0, audioBuffer.length);
            double maxAmplitude = 0;
            for (int i = 0; i < numberOfShort; i++) {
                maxAmplitude = Math.max(maxAmplitude, Math.abs(audioBuffer[i]));
            }
            double amplitude = maxAmplitude / Short.MAX_VALUE;
            double refAmplitude = 0.00002;
            final double decibelLevel = 20 * Math.log10(amplitude / refAmplitude);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    decibelTextView.setText(String.format("Current Decibel Level: %.2f dB", decibelLevel));
                }
            });
        }
    }

}
