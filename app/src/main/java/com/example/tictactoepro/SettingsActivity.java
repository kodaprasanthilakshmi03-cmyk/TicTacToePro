package com.example.tictactoepro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.google.android.material.card.MaterialCardView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private TextView btnBack;
    private ImageView imgSound;
    private Switch switchSound;
    private MaterialCardView cardHelp;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnBack = findViewById(R.id.btnBack);
        imgSound = findViewById(R.id.imgSound);
        switchSound = findViewById(R.id.switchSound);
        cardHelp = findViewById(R.id.cardHelp);
        cardHelp.setOnClickListener(v -> {

            SoundManager.getInstance(this).playButton();

            Intent intent =
                    new Intent(
                            SettingsActivity.this,
                            HelpActivity.class
                    );

            startActivity(intent);
        });

        preferences = getSharedPreferences("TicTacToeSettings", MODE_PRIVATE);

        boolean soundEnabled = preferences.getBoolean("sound_enabled", true);

        switchSound.setChecked(soundEnabled);
        updateSoundIcon(soundEnabled);

        btnBack.setOnClickListener(v -> finish());

        switchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {

            // 1. Save preference
            preferences.edit()
                    .putBoolean("sound_enabled", isChecked)
                    .apply();

            // 2. Update icon immediately
            updateSoundIcon(isChecked);

            // 3. IMPORTANT: sync SoundManager instantly
            SoundManager.getInstance(this).updateSoundSetting(this);
        });
    }

    private void updateSoundIcon(boolean enabled) {

        if (enabled) {
            imgSound.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
        } else {
            imgSound.setImageResource(android.R.drawable.ic_lock_silent_mode);
        }
    }
}