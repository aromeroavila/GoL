package arao.gameoflife.view.ui;

import android.view.View;
import android.widget.ToggleButton;

import arao.gameoflife.R;
import arao.gameoflife.controller.activities.ActivityController;
import arao.gameoflife.controller.activities.HomeController;
import arao.gameoflife.model.Cell;
import arao.gameoflife.view.custom.BoardView;
import arao.gameoflife.view.custom.OnCellClickListener;

class HomeUiImpl implements HomeUi, View.OnClickListener, OnCellClickListener {

    private HomeController mHomeController;
    private BoardView mBoardView;

    @Override
    public void createView(ActivityController activityController, HomeController homeController) {
        mHomeController = homeController;

        activityController.setContentView(R.layout.home_activity);
        ToggleButton mButton = (ToggleButton) activityController.findViewById(R.id.execute_button);
        mButton.setOnClickListener(this);
        mBoardView = (BoardView) activityController.findViewById(R.id.board_view);
        mBoardView.setOnCellClickListener(this);

        mBoardView.post(new Runnable() {
            @Override
            public void run() {
                mHomeController.onBoardViewSized(mBoardView.getWidth(), mBoardView.getHeight());
            }
        });
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
