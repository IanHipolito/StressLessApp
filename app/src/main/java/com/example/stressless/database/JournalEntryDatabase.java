package com.example.stressless.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.stressless.database.DAOs.JournalEntryDAO;
import com.example.stressless.database.entities.JournalEntry;

@Database(entities = {JournalEntry.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class JournalEntryDatabase extends RoomDatabase {
    public abstract JournalEntryDAO journalEntryDAO();

}
