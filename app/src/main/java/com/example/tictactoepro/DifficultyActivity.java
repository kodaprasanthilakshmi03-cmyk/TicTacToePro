package com.example.tictactoepro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;


public class DifficultyActivity extends AppCompatActivity {

    private MaterialCardView cardEasy;
    private MaterialCardView cardMedium;
    private MaterialCardView cardHard;
    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
        soundManager = SoundManager.getInstance(this);

        cardEasy = findViewById(R.id.cardEasy);
        cardMedium = findViewById(R.id.cardMedium);
        cardHard = findViewById(R.id.cardHard);

        cardEasy.setOnClickListener(v ->
                openGame("EASY"));

        cardMedium.setOnClickListener(v ->
                openGame("MEDIUM"));

        cardHard.setOnClickListener(v ->
                openGame("HARD"));
        findViewById(R.id.btnBack).setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            DifficultyActivity.this,
                            MainMenuActivity.class
                    );

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            finish();
        });
    }


    private void openGame(String difficulty) {
        soundManager.playButton();

        Intent intent =
                new Intent(
                        DifficultyActivity.this,
                        AIGameActivity.class
                );

        intent.putExtra(
                "DIFFICULTY",
                difficulty
        );

        startActivity(intent);
    }

}