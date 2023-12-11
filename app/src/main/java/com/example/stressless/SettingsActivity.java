// Package declaration
package com.example.stressless;

// Import statements
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private NumberPicker pickerInhale, pickerHold, pickerExhale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize the NumberPicker views
        pickerInhale = findViewById(R.id.numberPickerInhale);
        pickerHold = findViewById(R.id.numberPickerHold);
        pickerExhale = findViewById(R.id.numberPickerExhale);

        // Setup each NumberPicker with respective SharedPreferences key
        setupNumberPicker(pickerInhale, "inhaleDuration");
        setupNumberPicker(pickerHold, "holdDuration");
        setupNumberPicker(pickerExhale, "exhaleDuration");
    }

    // Method to setup a NumberPicker with values from SharedPreferences
    private void setupNumberPicker(NumberPicker numberPicker, String key) {
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(60);
        SharedPreferences preferences = getSharedPreferences("BreathingAppSettings", MODE_PRIVATE);
        // Set the current value of the picker based on saved preferences
        numberPicker.setValue((int) (preferences.getLong(key, 4) / 1000));
    }

    // Method called when the save button is clicked
    public void onSaveClicked(View view) {
        SharedPreferences preferences = getSharedPreferences("BreathingAppSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Save the settings for inhale, hold, and exhale durations
        editor.putLong("inhaleDuration", pickerInhale.getValue() * 1000L);
        editor.putLong("holdDuration", pickerHold.getValue() * 1000L);
        editor.putLong("exhaleDuration", pickerExhale.getValue() * 1000L);

        // Apply the changes to SharedPreferences
        editor.apply();
        // Close the activity
        finish();
    }
}
