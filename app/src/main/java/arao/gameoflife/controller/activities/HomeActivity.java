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

    private final static int INITIAL_BOARD_DIMENSION = 4;
    private final static int INITIAL_REPRODUCTION_SPEED = 2000;

    @Inject
    Generator mGenerator;
    @Inject
    HomeUi mHomeUi;
    @Inject
    Handler mHandler;

    private boolean[][] mCurrentGeneration;
    private int mGenerationSpeed = INITIAL_REPRODUCTION_SPEED;
    private int mBoardBaseSize = INITIAL_BOARD_DIMENSION + 1;

    private boolean mActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resolveDependencies();

        mHomeUi.createView(this, this);
        mHandler.post(runnable);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        outState.putSerializable("test", mCurrentGeneration);
//        super.onSaveInstanceState(outState, outPersistentState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        mCurrentGeneration = (boolean[][]) savedInstanceState.getSerializable("test");
//    }

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
        float ratio = (float) max(width, height) / min(width, height);
        int longSideSize = (int) (mBoardBaseSize * ratio);
        if (width < height) {
            createNewBoard(mBoardBaseSize, longSideSize);
        } else {
            createNewBoard(longSideSize, mBoardBaseSize);
        }

        mHomeUi.setData(mCurrentGeneration);
    }

    private void createNewBoard(int x, int y) {
        boolean[][] newGeneration = new boolean[x][y];
        if (mCurrentGeneration != null) {
            for (int i = 0; i < min(mCurrentGeneration.length, x); i++) {
                arraycopy(mCurrentGeneration[i], 0, newGeneration[i], 0, min(mCurrentGeneration[i].length, y));
            }
        }
        mCurrentGeneration = newGeneration;
    }

    @Override
    public void onSpeedChanged(int newSpeed) {
        mGenerationSpeed = INITIAL_REPRODUCTION_SPEED - ((100 * newSpeed) - 1);
    }

    @Override
    public void onSizeChanged(int newSizeMultiplier) {
        mBoardBaseSize = INITIAL_BOARD_DIMENSION + (newSizeMultiplier + 1);
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
