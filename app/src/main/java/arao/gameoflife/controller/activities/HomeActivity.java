package arao.gameoflife.controller.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import arao.gameoflife.controller.Generator;
import arao.gameoflife.controller.GeneratorImpl;
import arao.gameoflife.view.custom.BoardView;
import arao.gameoflife.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Generator mGenerator;
    private BoardView chessboard;
    private boolean taskCheck;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        chessboard = (BoardView) findViewById(R.id.board_view);
        boolean[][] emptyBoard = new boolean[60][60];
        chessboard.setCells(emptyBoard);
        mGenerator = new GeneratorImpl();
        handler = new Handler();
        handler.postDelayed(runnable, 500);
    }

    @Override
    public void onClick(View v) {
        taskCheck = !taskCheck;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (taskCheck) {
                chessboard.setCells(mGenerator.nextGeneration(chessboard.getCells()));
            }
            handler.postDelayed(runnable, 300);
        }
    };
}
