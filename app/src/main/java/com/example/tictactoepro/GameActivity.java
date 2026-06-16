package com.example.tictactoepro;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.widget.GridLayout;
import com.google.android.material.card.MaterialCardView;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity {

    private Button[] cells = new Button[9];

    private TextView txtTurn;
    private TextView txtXScore;
    private TextView txtOScore;
    private TextView txtRound;
    private GridLayout gameBoard;
    private MaterialCardView boardContainer;

    private AppCompatButton btnNewRound;
    private AppCompatButton btnReset;

    private String[] board = new String[9];

    private boolean playerXTurn = true;
    private boolean gameOver = false;
    private String nextStarter = "X";

    private int xScore = 0;
    private int oScore = 0;
    private int round = 1;
    private SoundManager soundManager;

    private final int[][] WIN_PATTERNS = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameBoard = findViewById(R.id.gameBoard);
        soundManager = SoundManager.getInstance(this);
        boardContainer = findViewById(R.id.boardContainer);
        findViewById(R.id.btnBack).setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            GameActivity.this,
                            MainMenuActivity.class
                    );

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            finish();
        });

        txtTurn = findViewById(R.id.txtTurn);
        boardContainer.setStrokeWidth(15);
        txtXScore = findViewById(R.id.txtXScore);
        txtOScore = findViewById(R.id.txtOScore);
        txtRound = findViewById(R.id.txtRound);

        btnNewRound = findViewById(R.id.btnNewRound);
        btnReset = findViewById(R.id.btnReset);

        initializeBoard();
        btnNewRound.setOnClickListener(v -> {
            soundManager.playButton();


            startNewRound();
        });
        btnReset.setOnClickListener(v -> {

            soundManager.playButton();

            resetEntireGame();
        });
        updateTurnText();
    }

    private void initializeBoard() {

        for (int i = 0; i < 9; i++) {

            int id = getResources().getIdentifier(
                    "cell" + i,
                    "id",
                    getPackageName()
            );

            cells[i] = findViewById(id);

            final int position = i;

            cells[i].setOnClickListener(
                    v -> handleMove(position)
            );
        }
    }

    private void handleMove(int position) {

        if (gameOver) return;

        if (board[position] != null) return;

        if (playerXTurn) {

            board[position] = "X";
            SoundManager.getInstance(this).playClick();

            cells[position].setText("✕");

            cells[position].setTextColor(
                    Color.parseColor("#00E5FF")
            );

            cells[position].setShadowLayer(
                    25f,
                    0f,
                    0f,
                    Color.parseColor("#00E5FF")
            );

        } else {

            board[position] = "O";
            SoundManager.getInstance(this).playClick();

            cells[position].setText("◯");

            cells[position].setTextColor(
                    Color.parseColor("#FF007F")
            );

            cells[position].setShadowLayer(
                    25f,
                    0f,
                    0f,
                    Color.parseColor("#FF007F")
            );
        }

        if (checkWinner()) {
            return;
        }

        if (isBoardFull()) {

            gameOver = true;

            txtTurn.setText("🤝 MATCH DRAW");
            SoundManager.getInstance(this).playDraw();

            Toast.makeText(
                    this,
                    "DRAW!",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        playerXTurn = !playerXTurn;

        updateTurnText();
    }

    private boolean checkWinner() {

        for (int[] pattern : WIN_PATTERNS) {

            int a = pattern[0];
            int b = pattern[1];
            int c = pattern[2];

            if (board[a] == null)
                continue;

            if (board[a].equals(board[b]) &&
                    board[a].equals(board[c])) {

                gameOver = true;

                highlightWinner(a, b, c);

                if (board[a].equals("X")) {
                    nextStarter = "X";

                    xScore++;

                    txtXScore.setText(
                            String.valueOf(xScore)
                    );

                    txtTurn.setText("👑 PLAYER X WINS");
                    SoundManager.getInstance(this).playWin();

                    Toast.makeText(
                            this,
                            "X WINS!",
                            Toast.LENGTH_SHORT
                    ).show();

                } else {

                    oScore++;
                    nextStarter = "O";

                    txtOScore.setText(
                            String.valueOf(oScore)
                    );

                    txtTurn.setText("👑 PLAYER O WINS");
                    SoundManager.getInstance(this).playWin();

                    Toast.makeText(
                            this,
                            "O WINS!",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                return true;
            }
        }

        return false;
    }

    private void highlightWinner(int a, int b, int c) {

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

    private void animateCell(
            Button button,
            int color
    ) {

        button.setBackgroundColor(color);

        button.animate()
                .scaleX(1.15f)
                .scaleY(1.15f)
                .setDuration(250)
                .withEndAction(() ->
                        button.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(250)
                );
    }

    private boolean isBoardFull() {

        for (String cell : board) {

            if (cell == null)
                return false;
        }

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

            txtTurn.setText("PLAYER O TURN");

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

            cell.setShadowLayer(
                    0f,
                    0f,
                    0f,
                    Color.TRANSPARENT
            );

            cell.setBackgroundResource(
                    R.drawable.game_cell
            );

            cell.setScaleX(1f);
            cell.setScaleY(1f);
        }

        playerXTurn = nextStarter.equals("X");
        gameOver = false;

        round++;

        txtRound.setText(
                String.valueOf(round)
        );

        updateTurnText();
    }

    private void resetEntireGame() {

        xScore = 0;
        oScore = 0;
        round = 0;
        nextStarter = "X";

        txtXScore.setText("0");
        txtOScore.setText("0");

        startNewRound();

        Toast.makeText(
                this,
                "Scores Reset",
                Toast.LENGTH_SHORT
        ).show();
    }

}