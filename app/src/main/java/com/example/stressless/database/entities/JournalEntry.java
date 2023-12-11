// Package declaration
package com.example.stressless.database.entities;

// Import statements
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

// Reference: I took inspiration for the following code from:
// Lab 6B - Demo (Lecture Notes)
// Reference complete

// Annotation to define a Room Entity with a table name
@Entity(tableName = "journal_entries")
public class JournalEntry {
    // Annotation for the primary key with auto-generation
    @PrimaryKey(autoGenerate = true)

    // Instance Variables
    public int id;
    public String entryText;
    public Date entryDate;
    public Date timestamp;

    // Default constructor
    public JournalEntry() {
    }

    // Getter and setter methods for the fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntryText() {
        return entryText;
    }
}
