package com.example.lab5penzev;


import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private SeekBar textSizeSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        textSizeSeekBar = findViewById(R.id.textSizeSeekBar);

        findViewById(R.id.saveButton).setOnClickListener(v -> {
            int textSize = textSizeSeekBar.getProgress();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("textSize", textSize);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}