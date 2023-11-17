package com.example.stressless;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MindfulnessActivity extends AppCompatActivity {

    // UI elements
    private EditText journalEntry;
    private Button saveEntryButton;
    private TextView challengeOfTheDay;
    private TextView lastSavedTime;

    // Format for date and time
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfulnesschallenges);

        // Initialize UI elements
        journalEntry = findViewById(R.id.journalEntry);
        saveEntryButton = findViewById(R.id.saveEntryButton);
        challengeOfTheDay = findViewById(R.id.challengeOfTheDay);
        lastSavedTime = findViewById(R.id.lastSavedTime);

        // Set today's challenge
        String todayChallenge = getDailyChallenge();
        challengeOfTheDay.setText(todayChallenge);

        // Set save entry button click listener
        saveEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveJournalEntry();
            }
        });
    }

    private String getDailyChallenge() {
        // This method should retrieve a new challenge daily.
        // For simplicity, we'll just return a static string.
        return "Today's challenge: Spend 5 minutes in meditation focusing on your breath.";
    }

    private void saveJournalEntry() {
        String entryText = journalEntry.getText().toString();
        String currentTime = dateFormat.format(new Date());

        // TODO: Save the entry text and current time to persistent storage (like SharedPreferences or a database)

        lastSavedTime.setText("Last saved on: " + currentTime);
        journalEntry.setText(""); // Clear the entry after saving
    }
}
