package arao.gameoflife.controller.activities;

import arao.gameoflife.model.Cell;

public interface HomeController {

    void onCellClicked(Cell cell);

    void onRunClicked();

    void onClearClicked();

    void onBoardViewSized(int width, int height);

    void onSpeedChanged(int newSpeed);

    void onSizeChanged(int newSizeMultiplier);

}
