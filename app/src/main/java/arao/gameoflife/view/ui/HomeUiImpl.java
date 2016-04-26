package arao.gameoflife.view.ui;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import javax.inject.Inject;

import arao.gameoflife.R;
import arao.gameoflife.controller.activities.ActivityController;
import arao.gameoflife.controller.activities.HomeController;
import arao.gameoflife.model.Cell;
import arao.gameoflife.view.custom.BoardView;
import arao.gameoflife.view.custom.OnCellClickListener;
import arao.gameoflife.view.utils.ColorParser;

class HomeUiImpl implements HomeUi, View.OnClickListener, OnCellClickListener, SeekBar.OnSeekBarChangeListener {

    private static final int SIZE_BAR_INITIAL_VALUE = 15;
    private static final int SIZE_BAR_MAX_VALUE = 40;
    private static final int SPEED_BAR_INITIAL_VALUE = 10;
    private static final int SPEED_BAR_MAX_VALUE = 20;
    private static final int COLOR_BAR_INITIAL_VALUE = 0;
    private static final int COLOR_BAR_MAX_VALUE = 256 * 7 - 1;

    private final ColorParser mColorParser;

    private HomeController mHomeController;
    private BoardView mBoardView;
    private TextView mSizeBarIndicator;
    private TextView mSpeedBarIndicator;

    @Inject
    HomeUiImpl(ColorParser colorParser) {
        mColorParser = colorParser;
    }

    @Override
    public void createView(ActivityController activityController, HomeController homeController) {
        mHomeController = homeController;
        activityController.setContentView(R.layout.home_activity);
        initViews(activityController);
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int id = seekBar.getId();
        switch (id) {
            case R.id.size_bar:
                mHomeController.onSizeChanged(progress);
                mSizeBarIndicator.setText(Integer.toString(progress + 5));
                if (fromUser) {
                    mHomeController.onBoardViewSized(mBoardView.getWidth(), mBoardView.getHeight());
                }
                break;
            case R.id.speed_bar:
                mSpeedBarIndicator.setText(Integer.toString(progress));
                mHomeController.onSpeedChanged(progress);
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

    private void initViews(ActivityController activityController) {
        activityController.findViewById(R.id.execute_button).setOnClickListener(this);
        activityController.findViewById(R.id.clear_button).setOnClickListener(this);
        mBoardView = (BoardView) activityController.findViewById(R.id.board_view);
        mBoardView.setOnCellClickListener(this);
        mSizeBarIndicator = (TextView) activityController.findViewById(R.id.size_bar_indicator);
        mSpeedBarIndicator = (TextView) activityController.findViewById(R.id.speed_bar_indicator);
        SeekBar sizeBar = (SeekBar) activityController.findViewById(R.id.size_bar);
        sizeBar.setOnSeekBarChangeListener(this);
        sizeBar.setMax(SIZE_BAR_MAX_VALUE);
        sizeBar.setProgress(SIZE_BAR_INITIAL_VALUE);
        SeekBar speedBar = (SeekBar) activityController.findViewById(R.id.speed_bar);
        speedBar.setOnSeekBarChangeListener(this);
        speedBar.setMax(SPEED_BAR_MAX_VALUE);
        speedBar.setProgress(SPEED_BAR_INITIAL_VALUE);
        SeekBar colorBar = (SeekBar) activityController.findViewById(R.id.color_bar);
        colorBar.setMax(COLOR_BAR_MAX_VALUE);
        onProgressChanged(colorBar, COLOR_BAR_INITIAL_VALUE, true);
        colorBar.setOnSeekBarChangeListener(this);

        mBoardView.post(new Runnable() {
            @Override
            public void run() {
                mHomeController.onBoardViewSized(mBoardView.getWidth(), mBoardView.getHeight());
            }
        });
    }

    private void setSeekBarColor(SeekBar seekBar, @ColorInt int newColor) {
        LayerDrawable ld = (LayerDrawable) seekBar.getProgressDrawable();
        ScaleDrawable d1 = (ScaleDrawable) ld.findDrawableByLayerId(android.R.id.progress);
        d1.setColorFilter(newColor, PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(newColor, PorterDuff.Mode.SRC_IN);
    }
}
