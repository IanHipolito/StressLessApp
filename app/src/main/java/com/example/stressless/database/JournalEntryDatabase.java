// Package declaration
package com.example.stressless.database;

// Import statements
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.stressless.database.DAOs.JournalEntryDAO;
import com.example.stressless.database.entities.JournalEntry;

// Annotation to define a Room Database
@Database(entities = {JournalEntry.class}, version = 1)
// Annotation to include type converters
@TypeConverters({DateConverter.class})
public abstract class JournalEntryDatabase extends RoomDatabase {
    // Abstract method to get the JournalEntryDAO
    public abstract JournalEntryDAO journalEntryDAO();

}
