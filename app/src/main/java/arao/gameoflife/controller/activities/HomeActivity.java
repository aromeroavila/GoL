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

public class HomeActivity extends AppCompatActivity implements ActivityController, HomeController {

    @Inject
    Generator mGenerator;
    @Inject
    HomeUi homeUi;
    @Inject
    Handler handler;

    private boolean taskCheck;
    private boolean[][] mCurrentGeneration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resolveDependencies();

        homeUi.createView(this, this);
        initaliseBoard();

        handler.postDelayed(runnable, 500);
    }

    private void resolveDependencies() {
        GlobalComponent component = DaggerGlobalComponent.builder()
                .activityModule(new ActivityModule())
                .viewModule(new ViewModule())
                .controllerModule(new ControllerModule())
                .build();

        component.resolveDependenciesFor(this);
    }

    private void initaliseBoard() {
        mCurrentGeneration = new boolean[10][10];
        homeUi.setData(mCurrentGeneration);
    }

    @Override
    public void onCellClicked(Cell cell) {
        mCurrentGeneration[cell.getX()][cell.getY()] = cell.getValue();
        homeUi.setData(mCurrentGeneration);
    }

    @Override
    public void onRunClicked() {
        taskCheck = !taskCheck;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (taskCheck) {
                mCurrentGeneration = mGenerator.nextGeneration(mCurrentGeneration);
                homeUi.setData(mCurrentGeneration);
            }
            handler.postDelayed(runnable, 300);
        }
    };
}
