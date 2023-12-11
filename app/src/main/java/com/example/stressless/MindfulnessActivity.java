// Package declaration
package com.example.stressless;

// Import statements
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
    private TextView lastSavedTime;
    // Format for displaying dates
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
            Locale.getDefault());
    private JournalEntryDatabase db;
    private List<String> quotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfulnesschallenges);

        // Initialize views
        journalEntry = findViewById(R.id.journalEntry);
        saveEntryButton = findViewById(R.id.saveEntryButton);
        lastSavedTime = findViewById(R.id.lastSavedTime);

        // Read quotes from a CSV file
        quotes = readQuotesFromCSV();
        // Set up other UI components and listeners
        TextView quoteTextView = findViewById(R.id.quoteTextView);
        quoteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String randomQuote = getRandomQuote();
                quoteTextView.setText(randomQuote);
            }
        });

        // Setting up navigation buttons
        ImageButton nav_entries = findViewById(R.id.nav_entries);
        ImageButton nav_home = findViewById(R.id.nav_home);
        ImageButton nav_meditation = findViewById(R.id.nav_meditation);
        ImageButton nav_decibel = findViewById(R.id.nav_decibel);
        ImageButton nav_breathing = findViewById(R.id.nav_breathing);

        // Set touch listeners for navigation buttons, Intents to navigate to to different activities
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

        // Initialize the Room database
        db = Room.databaseBuilder(getApplicationContext(),
                JournalEntryDatabase.class, "journal-entry-database").build();


        // Set a touch listener for the save entry button
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

    // Method to save a journal entry to the database
    private void saveJournalEntry() {
        // Start a new thread to perform database operations
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Get the text from the journal entry input field
                String entryText = journalEntry.getText().toString();
                // Capture the current time for the timestamp
                Date currentTime = new Date();

                // Create a new JournalEntry object and set its properties
                JournalEntry entry = new JournalEntry();
                entry.entryText = entryText;
                entry.timestamp = currentTime;

                // Insert the new entry into the database
                db.journalEntryDAO().insert(entry);

                // Update the UI on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Update the last saved time display
                        lastSavedTime.setText("Last saved on: " + dateFormat.format(currentTime));
                        // Clear the input field
                        journalEntry.setText("");
                    }
                });
            }
        }).start();
    }

    // Method to read quotes from a CSV file
    private List<String> readQuotesFromCSV() {
        // Initialize a list to store quotes
        List<String> quotes = new ArrayList<>();
        try {
            // Open the CSV file from assets
            InputStream is = getAssets().open("quotes.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            // Read each line (quote) from the CSV file
            while ((line = reader.readLine()) != null) {
                // Add the line to the quotes list
                quotes.add(line);
            }
            // Close the input stream
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quotes;
    }

    // Method to get a random quote from the list
    private String getRandomQuote() {
        Random random = new Random();
        // Return a random quote from the list
        return quotes.get(random.nextInt(quotes.size()));
    }
}
