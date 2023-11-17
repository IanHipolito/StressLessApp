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


    private EditText journalEntry;
    private Button saveEntryButton;
    private TextView challengeOfTheDay;
    private TextView lastSavedTime;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

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


        saveEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveJournalEntry();
            }
        });
    }

    private String getDailyChallenge() {

        return "Today's challenge: Spend 5 minutes in meditation focusing on your breath.";
    }

    private void saveJournalEntry() {
        String entryText = journalEntry.getText().toString();
        String currentTime = dateFormat.format(new Date());

        // TODO: Save the entry text and current time to persistent storage (like SharedPreferences or a database)

        lastSavedTime.setText("Last saved on: " + currentTime);
        journalEntry.setText("");
    }
}
