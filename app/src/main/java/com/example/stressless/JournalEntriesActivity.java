package com.example.stressless;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.stressless.database.JournalEntryDatabase;
import com.example.stressless.database.entities.JournalEntriesAdapter;
import com.example.stressless.database.entities.JournalEntry;
import java.util.List;

public class JournalEntriesActivity extends AppCompatActivity implements OnJournalEntryDeleteListener {
    private RecyclerView recyclerView;
    private JournalEntriesAdapter adapter;
    private JournalEntryDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entries);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = Room.databaseBuilder(getApplicationContext(),
                JournalEntryDatabase.class, "journal-entry-database").build();

        loadJournalEntries();
    }

    private void loadJournalEntries() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<JournalEntry> entries = db.journalEntryDAO().getAllEntries();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new JournalEntriesAdapter(entries, JournalEntriesActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onDeleteJournalEntry(int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<JournalEntry> entries = db.journalEntryDAO().getAllEntries();
                JournalEntry entryToDelete = entries.get(position);
                db.journalEntryDAO().delete(entryToDelete);
                entries.remove(position);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemRemoved(position);
                    }
                });
            }
        }).start();
    }
}
