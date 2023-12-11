// Package declaration
package com.example.stressless.database;

// Import statements
import androidx.room.TypeConverter;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    // Method to convert a timestamp to a Date
    public static Date fromTimestamp(Long value) {
        // Returns a Date object from a Long timestamp
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    // Method to convert a Date to a timestamp
    public static Long dateToTimestamp(Date date) {
        // Returns the timestamp from a Date object
        return date == null ? null : date.getTime();
    }
}
