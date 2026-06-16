package com.example.tictactoepro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

import com.google.android.material.card.MaterialCardView;

public class MainMenuActivity extends AppCompatActivity {

    private MaterialCardView cardFriend;
    private MaterialCardView cardAI;
    private MaterialCardView cardStats;
    private MaterialCardView cardSettings;
    private int buttonSound;

    private ImageButton btnSound;

    private SoundManager soundManager;

    private ImageButton btnInfo;

    @Override
    protected void onResume() {
        super.onResume();

        boolean soundEnabled = soundManager.isSoundEnabled(this);

        if (soundEnabled) {
            btnSound.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
        } else {
            btnSound.setImageResource(android.R.drawable.ic_lock_silent_mode);
        }

        soundManager.updateSoundSetting(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        soundManager = SoundManager.getInstance(this);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {

        cardFriend = findViewById(R.id.cardFriend);
        cardAI = findViewById(R.id.cardAI);
        cardStats = findViewById(R.id.cardStats);
        cardSettings = findViewById(R.id.cardSettings);

        btnSound = findViewById(R.id.btnSound);
        btnInfo = findViewById(R.id.btnInfo);
    }

    private void setupListeners() {

        cardFriend.setOnClickListener(v -> {
           soundManager.playButton();

            animateCard(v);

            Intent intent =
                    new Intent(MainMenuActivity.this,
                            GameActivity.class);

            intent.putExtra("MODE", "FRIEND");

            startActivity(intent);
        });


        cardAI.setOnClickListener(v -> {
           soundManager.playButton();

            animateCard(v);

            Intent intent =
                    new Intent(
                            MainMenuActivity.this,
                            DifficultyActivity.class
                    );

            startActivity(intent);
        });

        cardStats.setOnClickListener(v -> {
           soundManager.playButton();

            animateCard(v);

            startActivity(
                    new Intent(
                            MainMenuActivity.this,
                            StatisticsActivity.class
                    )
            );
        });

        cardSettings.setOnClickListener(v -> {
           soundManager.playButton();

            animateCard(v);

            Intent intent =
                    new Intent(
                            MainMenuActivity.this,
                            SettingsActivity.class
                    );

            startActivity(intent);
        });

        btnSound.setOnClickListener(v -> {

            boolean current = soundManager.isSoundEnabled(this);
            boolean newValue = !current;

            soundManager.setSoundEnabled(this, newValue);

            soundManager.updateSoundSetting(this);

            if (newValue) {
                btnSound.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
                Toast.makeText(this, "Sound ON", Toast.LENGTH_SHORT).show();
            } else {
                btnSound.setImageResource(android.R.drawable.ic_lock_silent_mode);
                Toast.makeText(this, "Sound OFF", Toast.LENGTH_SHORT).show();
            }
        });

        btnInfo.setOnClickListener(v -> {
            soundManager.playButton();

            Intent intent =
                    new Intent(
                            MainMenuActivity.this,
                            SettingsActivity.class
                    );

            startActivity(intent);
        });
    }

    private void animateCard(View view) {

        view.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction(() ->
                        view.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(100)
                );
    }
}