package com.example.tictactoepro;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.Intent;
import java.util.Arrays;
import java.util.Random;
import android.view.View;
import android.widget.GridLayout;
import com.google.android.material.card.MaterialCardView;

public class AIGameActivity extends AppCompatActivity {

    private Button[] cells = new Button[9];

    private TextView txtTurn;
    private TextView txtXScore;
    private TextView txtOScore;
    private TextView txtRound;
    private GridLayout gameBoard;
    private MaterialCardView boardContainer;

    private AppCompatButton btnNewRound;
    private AppCompatButton btnReset;
    private SoundManager soundManager;
    private String[] board = new String[9];

    private boolean playerXTurn = true;
    private boolean gameOver = false;
    private boolean playerCanPlay = true;
    private String nextStarter = "X";

    private int xScore = 0;
    private int oScore = 0;
    private int round = 1;

    private String difficulty = "EASY";

    private final Random random = new Random();
    private final Handler handler = new Handler();

    private final int[][] WIN_PATTERNS = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        soundManager = SoundManager.getInstance(this);

        txtTurn = findViewById(R.id.txtTurn);
        boardContainer = findViewById(R.id.boardContainer);
        boardContainer.setStrokeWidth(15);
        gameBoard = findViewById(R.id.gameBoard);
        txtXScore = findViewById(R.id.txtXScore);
        txtOScore = findViewById(R.id.txtOScore);
        txtRound = findViewById(R.id.txtRound);
        btnNewRound = findViewById(R.id.btnNewRound);
        btnReset = findViewById(R.id.btnReset);
        findViewById(R.id.btnBack).setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            AIGameActivity.this,
                            DifficultyActivity.class
                    );

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            finish();
        });

        String mode = getIntent().getStringExtra("DIFFICULTY");
        if (mode != null) difficulty = mode;

        btnNewRound.setOnClickListener(v -> {

            soundManager.playButton();

            startNewRound();
        });
        btnReset.setOnClickListener(v -> {

            soundManager.playButton();

            resetEntireGame();
        });

        initializeBoard();
        updateTurnText();
    }


    private void initializeBoard() {

        for (int i = 0; i < 9; i++) {

            int id = getResources().getIdentifier("cell" + i, "id", getPackageName());
            cells[i] = findViewById(id);

            final int pos = i;
            cells[i].setOnClickListener(v -> handleMove(pos));
        }
    }

    private void handleMove(int position) {

        if (gameOver) return;

        if (!playerCanPlay) return;

        if (board[position] != null) return;

        board[position] = "X";
        SoundManager.getInstance(this).playClick();

        cells[position].setText("✕");
        cells[position].setTextColor(Color.parseColor("#00E5FF"));
        cells[position].setShadowLayer(25f, 0f, 0f, Color.parseColor("#00E5FF"));

        if (checkWinner()) return;

        if (isBoardFull()) {
            gameOver = true;
            txtTurn.setText("DRAW");
            SoundManager.getInstance(this).playDraw();
            saveStats("DRAW");
            nextStarter = playerXTurn ? "X" : "O";
            return;
        }

        playerXTurn = false;
        playerCanPlay = false;
        updateTurnText();
        aiMove();
    }

    private void aiMove() {

        handler.postDelayed(() -> {

            if (gameOver) return;

            int move;

            if (difficulty.equals("EASY")) {

                move = getRandomMove();

            } else if (difficulty.equals("MEDIUM")) {

                move = getWinningMove("O");

                if (move == -1) move = getBlockingMove();

                if (move == -1) move = getRandomMove();

            } else {

                move = getWinningMove("O");

                if (move == -1) move = getBlockingMove();

                if (move == -1) move = getBestMove();

                if (move == -1) move = getRandomMove();
            }

            if (move != -1) playAI(move);

        }, 600);
    }

    private void playAI(int move) {

        board[move] = "O";
        SoundManager.getInstance(this).playClick();

        cells[move].setText("◯");
        cells[move].setTextColor(Color.parseColor("#FF007F"));
        cells[move].setShadowLayer(25f, 0f, 0f, Color.parseColor("#FF007F"));

        if (checkWinner()) return;

        if (isBoardFull()) {
            gameOver = true;
            txtTurn.setText("DRAW");
            SoundManager.getInstance(this).playDraw();
            nextStarter = playerXTurn ? "X" : "O";
            return;
        }

        playerXTurn = true;
        playerCanPlay = true;
        updateTurnText();
    }

    private int getRandomMove() {

        ArrayList<Integer> empty = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            if (board[i] == null) empty.add(i);
        }

        if (empty.isEmpty()) return -1;

        return empty.get(random.nextInt(empty.size()));
    }
    private void highlight(int a, int b, int c) {

        int blinkColor = board[a].equals("X")
                ? Color.parseColor("#00E5FF")
                : Color.parseColor("#FF007F");

        blinkWinner(cells[a], blinkColor, 3);
        blinkWinner(cells[b], blinkColor, 3);
        blinkWinner(cells[c], blinkColor, 3);
    }
    private void blinkWinner(Button button, int color, int times) {

        Handler handler = new Handler();

        Runnable blink = new Runnable() {

            int count = 0;
            boolean visible = true;

            @Override
            public void run() {

                if (count >= times * 2) {
                    button.setAlpha(1f);
                    button.setTextColor(color);
                    button.setScaleX(1f);
                    button.setScaleY(1f);
                    return;
                }

                if (visible) {
                    button.setAlpha(0.2f);
                    button.setScaleX(1.2f);
                    button.setScaleY(1.2f);
                } else {
                    button.setAlpha(1f);
                    button.setScaleX(1f);
                    button.setScaleY(1f);
                }

                visible = !visible;
                count++;

                handler.postDelayed(this, 250);
            }
        };

        handler.post(blink);
    }

    private int getWinningMove(String player) {

        for (int[] p : WIN_PATTERNS) {

            int a = p[0], b = p[1], c = p[2];

            if (player.equals(board[a]) && player.equals(board[b]) && board[c] == null)
                return c;

            if (player.equals(board[a]) && player.equals(board[c]) && board[b] == null)
                return b;

            if (player.equals(board[b]) && player.equals(board[c]) && board[a] == null)
                return a;
        }

        return -1;
    }

    private int getBlockingMove() {
        return getWinningMove("X");
    }

    private int getBestMove() {

        if (board[4] == null) return 4;

        int[] corners = {0,2,6,8};

        for (int c : corners) {
            if (board[c] == null) return c;
        }

        int[] sides = {1,3,5,7};

        for (int s : sides) {
            if (board[s] == null) return s;
        }

        return getRandomMove();
    }
    private boolean checkWinner() {

        for (int[] p : WIN_PATTERNS) {

            int a = p[0], b = p[1], c = p[2];

            if (board[a] == null) continue;

            if (board[a].equals(board[b]) && board[a].equals(board[c])) {

                gameOver = true;

                if (board[a].equals("X")) {
                    xScore++;
                    txtXScore.setText(String.valueOf(xScore));
                    txtTurn.setText("PLAYER X WINS");
                    SoundManager.getInstance(this).playWin();
                    saveStats("WIN");
                    nextStarter = "X";
                } else {
                    oScore++;
                    txtOScore.setText(String.valueOf(oScore));
                    txtTurn.setText("AI WINS");
                    SoundManager.getInstance(this).playWin();
                    saveStats("LOSS");
                    nextStarter = "O";
                }
                highlight(a, b, c);


                return true;
            }
        }

        return false;
    }

    private boolean isBoardFull() {
        for (String s : board) if (s == null) return false;
        return true;
    }

    private void updateTurnText() {

        if (playerXTurn) {

            txtTurn.setText("PLAYER X TURN");

            txtTurn.setTextColor(
                    Color.parseColor("#00E5FF")
            );
            gameBoard.setBackgroundResource(
                    R.drawable.board_x
            );
            boardContainer.setStrokeColor(
                    Color.parseColor("#FF007F")
            );


        } else {

            txtTurn.setText("AI THINKING...");

            txtTurn.setTextColor(
                    Color.parseColor("#FF007F")
            );
            gameBoard.setBackgroundResource(
                    R.drawable.board_o
            );
            boardContainer.setStrokeColor(
                    Color.parseColor("#00E5FF")
            );
        }
    }

    private void startNewRound() {

        Arrays.fill(board, null);

        for (Button cell : cells) {
            cell.setText("");
            cell.setTextColor(Color.WHITE);
            cell.setShadowLayer(0f,0f,0f,Color.TRANSPARENT);
        }

        playerXTurn = nextStarter.equals("X");

        gameOver = false;

        playerCanPlay = playerXTurn;
        round++;

        txtRound.setText(String.valueOf(round));
        if (!playerXTurn) {

            txtTurn.setText("AI STARTS");

            handler.postDelayed(() -> {

                if (!gameOver) {

                    aiMove();
                }

            }, 700);

        } else {

            updateTurnText();
        }
    }

    private void resetEntireGame() {

        xScore = 0;
        oScore = 0;
        round = 0;

        txtXScore.setText("0");
        txtOScore.setText("0");

        startNewRound();

        Toast.makeText(
                this,
                "Scores Reset",
                Toast.LENGTH_SHORT
        ).show();
    }
    private void saveStats(String result) {

        android.content.SharedPreferences prefs =
                getSharedPreferences(
                        "TicTacToeStats",
                        MODE_PRIVATE
                );

        android.content.SharedPreferences.Editor editor =
                prefs.edit();

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

        totalGames++;

        if (result.equals("WIN")) {

            totalWins++;
            currentStreak++;

            if (currentStreak > bestStreak) {
                bestStreak = currentStreak;
            }

        } else if (result.equals("LOSS")) {

            totalLosses++;
            currentStreak = 0;

        } else {

            totalDraws++;
        }

        editor.putInt("totalGames", totalGames);
        editor.putInt("totalWins", totalWins);
        editor.putInt("totalLosses", totalLosses);
        editor.putInt("totalDraws", totalDraws);

        editor.putInt("currentStreak", currentStreak);
        editor.putInt("bestStreak", bestStreak);

        saveDifficultyStats(editor, result);

        editor.apply();
    }
    private void saveDifficultyStats(
            android.content.SharedPreferences.Editor editor,
            String result
    ) {

        String prefix =
                difficulty.toLowerCase();

        if (result.equals("WIN")) {

            int wins =
                    getSharedPreferences(
                            "TicTacToeStats",
                            MODE_PRIVATE
                    ).getInt(
                            prefix + "Wins",
                            0
                    );

            editor.putInt(
                    prefix + "Wins",
                    wins + 1
            );

        } else if (result.equals("LOSS")) {

            int losses =
                    getSharedPreferences(
                            "TicTacToeStats",
                            MODE_PRIVATE
                    ).getInt(
                            prefix + "Losses",
                            0
                    );

            editor.putInt(
                    prefix + "Losses",
                    losses + 1
            );

        } else {

            int draws =
                    getSharedPreferences(
                            "TicTacToeStats",
                            MODE_PRIVATE
                    ).getInt(
                            prefix + "Draws",
                            0
                    );

            editor.putInt(
                    prefix + "Draws",
                    draws + 1
            );
        }
    }
}