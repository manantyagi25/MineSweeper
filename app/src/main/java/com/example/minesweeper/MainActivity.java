package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CellClickListener{

    ImageButton retryButton;
    Context context;
    RecyclerView mineField;
    AdapterForMineFieldRV adapter;
    MineSweeperGame game;
    TextView timerTV, minesCountTV, statusTV;
    CountDownTimer countDownTimer;

    int size = 10;
    int numberOfBombs = 10;
    int elapsed;
    boolean timerStarted;
    boolean isGameOver;
    int maxTime = 999000;
    int vibrateTime = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        retryButton = findViewById(R.id.retryButton);
        mineField = findViewById(R.id.mineField);
        timerTV = findViewById(R.id.timerTV);
        minesCountTV = findViewById(R.id.minesTV);
        statusTV = findViewById(R.id.statusTV);
        timerStarted = false;
        isGameOver = false;

        countDownTimer = new CountDownTimer(maxTime, 1000) {
            @Override
            public void onTick(long l) {
                elapsed++;
                timerTV.setText(String.format("%03d", elapsed));
            }

            @Override
            public void onFinish() {
                timerTV.setText(String.format("%03d", elapsed));
                game.timeOver();
                Toast.makeText(context, "Game Over! You ran out of time!", Toast.LENGTH_SHORT).show();
                game.getMineField().showBombs();
                adapter.setCells(game.getMineField().getCells());
            }
        };

        minesCountTV.setText(String.format("%03d", numberOfBombs));

        retryButton.setOnClickListener(retryListener);

        mineField.setLayoutManager(new GridLayoutManager(context, size));
        game = new MineSweeperGame(size, numberOfBombs);

        adapter = new AdapterForMineFieldRV(game.getMineField().getCells(), this, MainActivity.this);
        mineField.setAdapter(adapter);
    }

    View.OnClickListener retryListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            /*game = new MineSweeperGame(size, numberOfBombs);
            adapter.setCells(game.getMineField().getCells());
            timerStarted = false;
            countDownTimer.cancel();
            elapsed = 0;
            timerTV.setText(R.string.default_count);*/
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    };

    @Override
    public void onCellClick(Cell cell) {
        if(!isGameOver)
            game.cellClick(cell);

        if(!isGameOver && game.isWon()){
            statusTV.setVisibility(View.VISIBLE);
            statusTV.setText(context.getResources().getString(R.string.won));
            statusTV.setTextColor(ContextCompat.getColor(context, R.color.count1));
            countDownTimer.cancel();
            isGameOver = true;
            return;
        }

        if(!timerStarted) {
            countDownTimer.start();
            timerStarted = true;
        }

        if(game.isGameOver()) {
            statusTV.setVisibility(View.VISIBLE);
            statusTV.setText(context.getResources().getString(R.string.lost));
            statusTV.setTextColor(ContextCompat.getColor(context, R.color.count3));
            game.getMineField().showBombs();
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(vibrateTime);
            isGameOver = true;
            countDownTimer.cancel();
        }

        adapter.setCells(game.getMineField().getCells());
    }
}