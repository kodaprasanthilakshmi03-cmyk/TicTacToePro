package com.example.tictactoepro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class StatisticsActivity extends AppCompatActivity {

    private TextView btnBack;

    private TextView txtTotalGames;
    private TextView txtWins;
    private TextView txtLosses;
    private TextView txtDraws;
    private TextView txtWinRate;

    private TextView txtEasyStats;
    private TextView txtMediumStats;
    private TextView txtHardStats;

    private TextView txtCurrentStreak;
    private TextView txtBestStreak;

    private AppCompatButton btnResetStats;
    private SoundManager soundManager;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        soundManager = SoundManager.getInstance(this);

        prefs = getSharedPreferences(
                "TicTacToeStats",
                MODE_PRIVATE
        );

        initializeViews();

        loadStatistics();

        btnBack.setOnClickListener(v ->
                finish()
        );

        btnResetStats.setOnClickListener(v -> {
            soundManager.playButton();

            prefs.edit().clear().apply();

            loadStatistics();

            Toast.makeText(
                    this,
                    "Statistics Reset",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }

    private void initializeViews() {

        btnBack = findViewById(R.id.btnBack);

        txtTotalGames = findViewById(R.id.txtTotalGames);
        txtWins = findViewById(R.id.txtWins);
        txtLosses = findViewById(R.id.txtLosses);
        txtDraws = findViewById(R.id.txtDraws);
        txtWinRate = findViewById(R.id.txtWinRate);

        txtEasyStats = findViewById(R.id.txtEasyStats);
        txtMediumStats = findViewById(R.id.txtMediumStats);
        txtHardStats = findViewById(R.id.txtHardStats);

        txtCurrentStreak =
                findViewById(R.id.txtCurrentStreak);

        txtBestStreak =
                findViewById(R.id.txtBestStreak);

        btnResetStats =
                findViewById(R.id.btnResetStats);
    }

    private void loadStatistics() {

        int totalGames =
                prefs.getInt("totalGames", 0);

        int totalWins =
                prefs.getInt("totalWins", 0);

        int totalLosses =
                prefs.getInt("totalLosses", 0);

        int totalDraws =
                prefs.getInt("totalDraws", 0);

        int currentStreak =
                prefs.getInt("currentStreak", 0);

        int bestStreak =
                prefs.getInt("bestStreak", 0);

        int easyWins =
                prefs.getInt("easyWins", 0);

        int easyLosses =
                prefs.getInt("easyLosses", 0);

        int easyDraws =
                prefs.getInt("easyDraws", 0);

        int mediumWins =
                prefs.getInt("mediumWins", 0);

        int mediumLosses =
                prefs.getInt("mediumLosses", 0);

        int mediumDraws =
                prefs.getInt("mediumDraws", 0);

        int hardWins =
                prefs.getInt("hardWins", 0);

        int hardLosses =
                prefs.getInt("hardLosses", 0);

        int hardDraws =
                prefs.getInt("hardDraws", 0);

        double winRate = 0;

        if (totalGames > 0) {

            winRate =
                    ((double) totalWins
                            / totalGames)
                            * 100;
        }

        txtTotalGames.setText(
                "Games Played : " + totalGames
        );

        txtWins.setText(
                "Wins : " + totalWins
        );

        txtLosses.setText(
                "Losses : " + totalLosses
        );

        txtDraws.setText(
                "Draws : " + totalDraws
        );

        txtWinRate.setText(
                "Win Rate : "
                        + String.format(
                        "%.1f",
                        winRate
                )
                        + "%"
        );

        txtEasyStats.setText(
                "Wins: "
                        + easyWins
                        + "   Losses: "
                        + easyLosses
                        + "   Draws: "
                        + easyDraws
        );

        txtMediumStats.setText(
                "Wins: "
                        + mediumWins
                        + "   Losses: "
                        + mediumLosses
                        + "   Draws: "
                        + mediumDraws
        );

        txtHardStats.setText(
                "Wins: "
                        + hardWins
                        + "   Losses: "
                        + hardLosses
                        + "   Draws: "
                        + hardDraws
        );

        txtCurrentStreak.setText(
                "Current Streak : "
                        + currentStreak
        );

        txtBestStreak.setText(
                "Best Streak : "
                        + bestStreak
        );
    }
}