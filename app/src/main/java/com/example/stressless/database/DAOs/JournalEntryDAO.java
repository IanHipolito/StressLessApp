package com.example.stressless.database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.stressless.database.entities.JournalEntry;
import java.util.List;

@Dao
public interface JournalEntryDAO {
    @Insert
    void insert(JournalEntry journalEntry);

    @Delete
    void delete(JournalEntry journalEntry);

    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    List<JournalEntry> getAllEntries();
}
