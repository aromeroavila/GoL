package arao.gameoflife.view.ui;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import javax.inject.Inject;

import arao.gameoflife.R;
import arao.gameoflife.controller.activities.ActivityController;
import arao.gameoflife.controller.activities.HomeController;
import arao.gameoflife.model.Cell;
import arao.gameoflife.view.custom.BoardView;
import arao.gameoflife.view.custom.OnCellClickListener;
import arao.gameoflife.view.utils.ColorParser;

class HomeUiImpl implements HomeUi, View.OnClickListener, OnCellClickListener, SeekBar.OnSeekBarChangeListener {

    private static final int MAX_SIZE_BAR_VALUE = 40;
    private static final int MAX_SPEED_BAR_VALUE = 20;
    private static final int MAX_COLOR_BAR_VALUE = 256 * 7 - 1;

    private final ColorParser mColorParser;

    private HomeController mHomeController;
    private BoardView mBoardView;

    @Inject
    HomeUiImpl(ColorParser colorParser) {
        mColorParser = colorParser;
    }

    @Override
    public void createView(ActivityController activityController, HomeController homeController) {
        mHomeController = homeController;

        activityController.setContentView(R.layout.home_activity);
        ToggleButton mButton = (ToggleButton) activityController.findViewById(R.id.execute_button);
        mButton.setOnClickListener(this);
        Button clearButton = (Button) activityController.findViewById(R.id.clear_button);
        clearButton.setOnClickListener(this);
        mBoardView = (BoardView) activityController.findViewById(R.id.board_view);
        mBoardView.setOnCellClickListener(this);
        SeekBar sizeBar = (SeekBar) activityController.findViewById(R.id.size_bar);
        sizeBar.setOnSeekBarChangeListener(this);
        sizeBar.setMax(MAX_SIZE_BAR_VALUE);
        SeekBar speedBar = (SeekBar) activityController.findViewById(R.id.speed_bar);
        speedBar.setOnSeekBarChangeListener(this);
        speedBar.setMax(MAX_SPEED_BAR_VALUE);
        SeekBar colorBar = (SeekBar) activityController.findViewById(R.id.color_bar);
        colorBar.setMax(MAX_COLOR_BAR_VALUE);
        onProgressChanged(colorBar, 0, true);
        colorBar.setOnSeekBarChangeListener(this);

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
        int viewId = v.getId();
        switch (viewId) {
            case R.id.execute_button:
                mHomeController.onRunClicked();
                break;
            case R.id.clear_button:
                mHomeController.onClearClicked();
                break;
        }
    }

    @Override
    public void onCellClick(Cell cell) {
        mHomeController.onCellClicked(cell);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int id = seekBar.getId();
        switch (id) {
            case R.id.size_bar:
                if (fromUser) {
                    mHomeController.onSizeChanged(progress);
                    mHomeController.onBoardViewSized(mBoardView.getWidth(), mBoardView.getHeight());
                }
                break;
            case R.id.speed_bar:
                if (fromUser) {
                    mHomeController.onSpeedChanged(progress);
                }
                break;
            case R.id.color_bar:
                int color = mColorParser.colorFrom(progress);
                setSeekBarColor(seekBar, color);
                mBoardView.setColor(color);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void setSeekBarColor(SeekBar seekBar, @ColorInt int newColor) {
        LayerDrawable ld = (LayerDrawable) seekBar.getProgressDrawable();
        ScaleDrawable d1 = (ScaleDrawable) ld.findDrawableByLayerId(android.R.id.progress);
        d1.setColorFilter(newColor, PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(newColor, PorterDuff.Mode.SRC_IN);
    }
}
