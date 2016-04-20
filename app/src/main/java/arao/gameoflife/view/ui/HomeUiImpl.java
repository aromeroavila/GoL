package arao.gameoflife.view.ui;

import android.view.View;

import arao.gameoflife.R;
import arao.gameoflife.controller.activities.ActivityController;
import arao.gameoflife.controller.activities.HomeController;
import arao.gameoflife.model.Cell;
import arao.gameoflife.view.custom.BoardView;
import arao.gameoflife.view.custom.OnCellClickListener;

class HomeUiImpl implements HomeUi, View.OnClickListener, OnCellClickListener {

    private BoardView mBoardView;
    private HomeController mHomeController;

    @Override
    public void createView(ActivityController activityController, HomeController homeController) {
        mHomeController = homeController;

        activityController.setContentView(R.layout.activity_board);
        activityController.findViewById(R.id.button).setOnClickListener(this);
        mBoardView = (BoardView) activityController.findViewById(R.id.board_view);
        mBoardView.setOnCellClickListener(this);
    }

    @Override
    public void setData(boolean[][] data) {
        mBoardView.setCells(data);
    }

    @Override
    public void onClick(View v) {
        mHomeController.onRunClicked();
    }

    @Override
    public void onCellClick(Cell cell) {
        mHomeController.onCellClicked(cell);
    }
}
