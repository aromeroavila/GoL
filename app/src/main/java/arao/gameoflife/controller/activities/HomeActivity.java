package arao.gameoflife.controller.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import arao.gameoflife.controller.ControllerModule;
import arao.gameoflife.controller.Generator;
import arao.gameoflife.injection.components.DaggerGlobalComponent;
import arao.gameoflife.injection.components.GlobalComponent;
import arao.gameoflife.model.Cell;
import arao.gameoflife.view.ui.HomeUi;
import arao.gameoflife.view.ui.ViewModule;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class HomeActivity extends AppCompatActivity implements ActivityController, HomeController {

    private final static int INITIAL_BOARD_DIMENSION = 20;
    private final static int INITIAL_REPRODUCTION_SPEED = 500;

    @Inject
    Generator mGenerator;
    @Inject
    HomeUi mHomeUi;
    @Inject
    Handler mHandler;

    private boolean[][] mCurrentGeneration;
    private int mGenerationSpeed = INITIAL_REPRODUCTION_SPEED;
    private int mBoardSize = INITIAL_BOARD_DIMENSION;

    private boolean mActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resolveDependencies();

        mHomeUi.createView(this, this);

        mHandler.post(runnable);
    }

    private void resolveDependencies() {
        GlobalComponent component = DaggerGlobalComponent.builder()
                .activityModule(new ActivityModule())
                .viewModule(new ViewModule())
                .controllerModule(new ControllerModule())
                .build();

        component.resolveDependenciesFor(this);
    }

    @Override
    public void onCellClicked(Cell cell) {
        mCurrentGeneration[cell.getX()][cell.getY()] = cell.getValue();
        mHomeUi.setData(mCurrentGeneration);
    }

    @Override
    public void onRunClicked() {
        mActive = !mActive;
    }

    @Override
    public void onBoardViewSized(int width, int height) {
        double ratio = (double) max(width, height) / min(width, height);
        int bigDimension = (int) (mBoardSize * ratio);
        if (width < height) {
            mCurrentGeneration = new boolean[mBoardSize][bigDimension];
        } else {
            mCurrentGeneration = new boolean[bigDimension][mBoardSize];
        }

        mHomeUi.setData(mCurrentGeneration);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mActive) {
                mCurrentGeneration = mGenerator.nextGeneration(mCurrentGeneration);
                mHomeUi.setData(mCurrentGeneration);
            }
            mHandler.postDelayed(runnable, mGenerationSpeed);
        }
    };
}
