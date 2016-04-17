package arao.gameoflife.controller.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import arao.gameoflife.controller.Generator;
import arao.gameoflife.controller.GeneratorImpl;
import arao.gameoflife.view.custom.Board;
import arao.gameoflife.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Generator mGenerator;
    private Board chessboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        chessboard = (Board) findViewById(R.id.board_view);
        boolean[][] emptyBoard = new boolean[30][30];
        chessboard.setCells(emptyBoard);
        mGenerator = new GeneratorImpl();
    }

    @Override
    public void onClick(View v) {
        chessboard.setCells(mGenerator.nextGeneration(chessboard.getCells()));
    }
}
