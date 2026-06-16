package com.example.tictactoepro;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;

public class SoundManager {

    private static SoundManager instance;

    private SoundPool soundPool;
    private boolean soundEnabled;

    private int clickSound;
    private int winSound;
    private int drawSound;
    private int buttonSound;

    private SoundManager(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences("TicTacToeSettings", Context.MODE_PRIVATE);

        soundEnabled = prefs.getBoolean("sound_enabled", true);

        AudioAttributes audioAttributes =
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        clickSound = soundPool.load(context, R.raw.click, 1);
        winSound = soundPool.load(context, R.raw.win, 1);
        drawSound = soundPool.load(context, R.raw.draw, 1);
        buttonSound = soundPool.load(context, R.raw.button, 1);
    }

    public static SoundManager getInstance(Context context) {
        if (instance == null) {
            instance = new SoundManager(context.getApplicationContext());
        }
        return instance;
    }
    public boolean isSoundEnabled(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences("TicTacToeSettings", Context.MODE_PRIVATE);

        return prefs.getBoolean("sound_enabled", true);
    }

    public void setSoundEnabled(Context context, boolean enabled) {
        SharedPreferences prefs =
                context.getSharedPreferences("TicTacToeSettings", Context.MODE_PRIVATE);

        prefs.edit().putBoolean("sound_enabled", enabled).apply();
    }

    public void updateSoundSetting(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences("TicTacToeSettings", Context.MODE_PRIVATE);

        soundEnabled = prefs.getBoolean("sound_enabled", true);
    }

    private void play(int soundId) {
        if (!soundEnabled) return;
        if (soundPool == null) return;

        soundPool.play(soundId, 1f, 1f, 0, 0, 1f);
    }

    public void playClick() {
        play(clickSound);
    }

    public void playWin() {
        play(winSound);
    }

    public void playDraw() {
        play(drawSound);
    }

    public void playButton() {
        play(buttonSound);
    }

    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        instance = null;
    }
}