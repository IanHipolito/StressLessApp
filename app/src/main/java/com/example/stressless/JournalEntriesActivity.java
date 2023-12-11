// Package declaration
package com.example.stressless;

// Import statements
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.stressless.database.JournalEntryDatabase;
import com.example.stressless.database.OnJournalEntryDeleteListener;
import com.example.stressless.database.entities.JournalEntriesAdapter;
import com.example.stressless.database.entities.JournalEntry;
import java.util.List;

// JournalEntriesActivity class for displaying and managing journal entries
public class JournalEntriesActivity extends AppCompatActivity implements OnJournalEntryDeleteListener {
    private RecyclerView recyclerView;
    private JournalEntriesAdapter adapter;
    private JournalEntryDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entries);

        // Setup the RecyclerView and its layout manager
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the Room database for journal entries
        db = Room.databaseBuilder(getApplicationContext(),
                JournalEntryDatabase.class, "journal-entry-database").build();

        // Load journal entries from the database
        loadJournalEntries();
    }

    // Method to load journal entries from the database
    private void loadJournalEntries() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<JournalEntry> entries = db.journalEntryDAO().getAllEntries();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Create adapter
                        adapter = new JournalEntriesAdapter(entries, JournalEntriesActivity.this);
                        // Set adapter for RecyclerView
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start(); // Start the thread for database operations
    }

    // Method to handle deletion of a journal entry
    @Override
    public void onDeleteJournalEntry(int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Get all entries
                List<JournalEntry> entries = db.journalEntryDAO().getAllEntries();
                // Get the entry to delete
                JournalEntry entryToDelete = entries.get(position);
                // Delete the entry from the database
                db.journalEntryDAO().delete(entryToDelete);
                // Remove the entry from the list
                entries.remove(position);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Reload journal entries to update UI
                        loadJournalEntries();
                    }
                });
            }
        }).start();
    }
}
