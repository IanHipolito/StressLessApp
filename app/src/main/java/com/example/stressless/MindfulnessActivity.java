package com.example.stressless;

import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.stressless.database.JournalEntryDatabase;
import com.example.stressless.database.entities.JournalEntry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MindfulnessActivity extends AppCompatActivity {
    private EditText journalEntry;
    private Button saveEntryButton;
    private TextView challengeOfTheDay;
    private TextView lastSavedTime;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private JournalEntryDatabase db;
    private List<String> quotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfulnesschallenges);

        journalEntry = findViewById(R.id.journalEntry);
        saveEntryButton = findViewById(R.id.saveEntryButton);
        lastSavedTime = findViewById(R.id.lastSavedTime);

        ImageButton nav_entries = findViewById(R.id.nav_entries);
        ImageButton nav_home = findViewById(R.id.nav_home);
        ImageButton nav_meditation = findViewById(R.id.nav_meditation);
        ImageButton nav_decibel = findViewById(R.id.nav_decibel);
        ImageButton nav_breathing = findViewById(R.id.nav_breathing);

        quotes = readQuotesFromCSV();
        TextView quoteTextView = findViewById(R.id.quoteTextView);
        quoteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String randomQuote = getRandomQuote();
                quoteTextView.setText(randomQuote);
            }
        });

        nav_breathing.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(MindfulnessActivity.this, BreathingExerciseActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        nav_home.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(MindfulnessActivity.this, MainActivity.class);
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
                    Intent intent = new Intent(MindfulnessActivity.this, MeditationActivity.class);
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
                    Intent intent = new Intent(MindfulnessActivity.this, VolumeTesterActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        nav_entries.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(MindfulnessActivity.this, JournalEntriesActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


        db = Room.databaseBuilder(getApplicationContext(),
                JournalEntryDatabase.class, "journal-entry-database").build();


        saveEntryButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    saveJournalEntry();
                    return true;
                }
                return false;
            }
        });

    }


    private void saveJournalEntry() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String entryText = journalEntry.getText().toString();
                Date currentTime = new Date();

                JournalEntry entry = new JournalEntry();
                entry.entryText = entryText;
                entry.timestamp = currentTime;

                db.journalEntryDAO().insert(entry);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lastSavedTime.setText("Last saved on: " + dateFormat.format(currentTime));
                        journalEntry.setText("");
                    }
                });
            }
        }).start();
    }

    private List<String> readQuotesFromCSV() {
        List<String> quotes = new ArrayList<>();
        try {
            InputStream is = getAssets().open("quotes.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                quotes.add(line);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quotes;
    }

    private String getRandomQuote() {
        Random random = new Random();
        return quotes.get(random.nextInt(quotes.size()));
    }

}
