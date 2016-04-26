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
import static java.lang.System.arraycopy;

public class HomeActivity extends AppCompatActivity implements ActivityController, HomeController {

    private static final String GENERATION_BUNDLE_EXTRA = "generation_bundle_extra";
    private static final String SPEED_BUNDLE_EXTRA = "speed_bundle_extra";
    private static final String SIZE_BUNDLE_EXTRA = "size_bundle_extra";
    private static final String RUNNING_BUNDLE_EXTRA = "running_bundle_extra";
    private final static int MIN_BOARD_DIMENSION = 4;
    private final static int MIN_REPRODUCTION_SPEED = 1000;

    @Inject
    Generator mGenerator;
    @Inject
    HomeUi mHomeUi;
    @Inject
    Handler mHandler;

    private boolean[][] mCurrentGeneration;
    private int mGenerationSpeed;
    private int mBoardBaseSize;
    private boolean mRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resolveDependencies();
        initialiseParameters(savedInstanceState);
        mHomeUi.createView(this, this);
        mHandler.post(mGeneratorRunner);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(GENERATION_BUNDLE_EXTRA, mCurrentGeneration);
        outState.putInt(SPEED_BUNDLE_EXTRA, mGenerationSpeed);
        outState.putInt(SIZE_BUNDLE_EXTRA, mBoardBaseSize);
        outState.putBoolean(RUNNING_BUNDLE_EXTRA, mRunning);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCellClicked(Cell cell) {
        mCurrentGeneration[cell.getX()][cell.getY()] = cell.getValue();
        mHomeUi.setData(mCurrentGeneration);
    }

    @Override
    public void onRunClicked() {
        mRunning = !mRunning;
    }

    @Override
    public void onClearClicked() {
        int x = mCurrentGeneration.length;
        int y = mCurrentGeneration[0].length;
        mCurrentGeneration = null;
        mCurrentGeneration = createNewBoard(x, y);
        mHomeUi.setData(mCurrentGeneration);
    }

    @Override
    public void onBoardViewSized(int width, int height) {
        float ratio = (float) max(width, height) / min(width, height);
        int longSideSize = (int) (mBoardBaseSize * ratio);
        if (width < height) {
            mCurrentGeneration = createNewBoard(mBoardBaseSize, longSideSize);
        } else {
            mCurrentGeneration = createNewBoard(longSideSize, mBoardBaseSize);
        }

        mHomeUi.setData(mCurrentGeneration);
    }

    @Override
    public void onSpeedChanged(int newSpeed) {
        mGenerationSpeed = MIN_REPRODUCTION_SPEED - ((50 * newSpeed) - 1);
    }

    @Override
    public void onSizeChanged(int newSize) {
        mBoardBaseSize = MIN_BOARD_DIMENSION + (newSize + 1);
    }

    private void resolveDependencies() {
        GlobalComponent component = DaggerGlobalComponent.builder()
                .activityModule(new ActivityModule())
                .viewModule(new ViewModule())
                .controllerModule(new ControllerModule())
                .build();

        component.resolveDependenciesFor(this);
    }

    private void initialiseParameters(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mGenerationSpeed = savedInstanceState.getInt(SPEED_BUNDLE_EXTRA, MIN_REPRODUCTION_SPEED);
            mBoardBaseSize = savedInstanceState.getInt(SIZE_BUNDLE_EXTRA, MIN_BOARD_DIMENSION + 1);
            mCurrentGeneration = (boolean[][]) savedInstanceState.getSerializable(GENERATION_BUNDLE_EXTRA);
            mRunning = savedInstanceState.getBoolean(RUNNING_BUNDLE_EXTRA, false);
        } else {
            mGenerationSpeed = MIN_REPRODUCTION_SPEED;
            mBoardBaseSize = MIN_BOARD_DIMENSION + 1;
        }
    }

    private boolean[][] createNewBoard(int x, int y) {
        boolean[][] newGeneration = new boolean[x][y];
        if (mCurrentGeneration != null) {
            for (int i = 0; i < min(mCurrentGeneration.length, x); i++) {
                arraycopy(mCurrentGeneration[i], 0, newGeneration[i], 0, min(mCurrentGeneration[i].length, y));
            }
        }
        return newGeneration;
    }

    private Runnable mGeneratorRunner = new Runnable() {
        @Override
        public void run() {
            if (mRunning) {
                mCurrentGeneration = mGenerator.nextGeneration(mCurrentGeneration);
                mHomeUi.setData(mCurrentGeneration);
            }
            mHandler.postDelayed(mGeneratorRunner, mGenerationSpeed);
        }
    };
}
