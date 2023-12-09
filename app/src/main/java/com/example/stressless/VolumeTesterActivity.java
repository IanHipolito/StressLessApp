package com.example.stressless;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    stopRecording();
                } else {
                    startRecording();
                }
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
