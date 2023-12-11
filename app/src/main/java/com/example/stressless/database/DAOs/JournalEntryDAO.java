// Package declaration
package com.example.stressless.database.DAOs;

// Import statements
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.stressless.database.entities.JournalEntry;
import java.util.List;

// Reference: I took inspiration for the following code from:
// Lab 6B - Demo (Lecture Notes)
// Reference complete
@Dao
public interface JournalEntryDAO {
    // Method to insert a journal entry
    @Insert
    void insert(JournalEntry journalEntry);

    // Method to delete a journal entry
    @Delete
    void delete(JournalEntry journalEntry);

    // Method to retrieve all journal entries ordered by timestamp
    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    List<JournalEntry> getAllEntries();
}

