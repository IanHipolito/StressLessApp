package com.example.stressless;

import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import com.example.stressless.database.JournalEntryDatabase;
import com.example.stressless.database.entities.JournalEntry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;

public class MindfulnessActivity extends AppCompatActivity {
    private EditText journalEntry;
    private Button saveEntryButton;
    private TextView challengeOfTheDay;
    private TextView lastSavedTime;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private JournalEntryDatabase db;
    private Button viewEntriesButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfulnesschallenges);

        journalEntry = findViewById(R.id.journalEntry);
        saveEntryButton = findViewById(R.id.saveEntryButton);
        challengeOfTheDay = findViewById(R.id.challengeOfTheDay);
        lastSavedTime = findViewById(R.id.lastSavedTime);

        String todayChallenge = getDailyChallenge();
        challengeOfTheDay.setText(todayChallenge);

        viewEntriesButton = findViewById(R.id.viewEntriesButton);

        viewEntriesButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    navigateToJournalEntriesList();
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

    private String getDailyChallenge() {

        return "Today's challenge: Spend 5 minutes in meditation focusing on your breath.";
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

    private void navigateToJournalEntriesList() {
        Intent intent = new Intent(MindfulnessActivity.this, JournalEntriesActivity.class);
        startActivity(intent);
    }
}
