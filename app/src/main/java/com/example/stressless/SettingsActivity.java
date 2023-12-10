package com.example.stressless;

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

        pickerInhale = findViewById(R.id.numberPickerInhale);
        pickerHold = findViewById(R.id.numberPickerHold);
        pickerExhale = findViewById(R.id.numberPickerExhale);

        setupNumberPicker(pickerInhale, "inhaleDuration");
        setupNumberPicker(pickerHold, "holdDuration");
        setupNumberPicker(pickerExhale, "exhaleDuration");
    }

    private void setupNumberPicker(NumberPicker numberPicker, String key) {
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(60);
        SharedPreferences preferences = getSharedPreferences("BreathingAppSettings", MODE_PRIVATE);
        numberPicker.setValue((int) (preferences.getLong(key, 4) / 1000));
    }

    public void onSaveClicked(View view) {
        SharedPreferences preferences = getSharedPreferences("BreathingAppSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong("inhaleDuration", pickerInhale.getValue() * 1000L);
        editor.putLong("holdDuration", pickerHold.getValue() * 1000L);
        editor.putLong("exhaleDuration", pickerExhale.getValue() * 1000L);

        editor.apply();
        finish();
    }
}
