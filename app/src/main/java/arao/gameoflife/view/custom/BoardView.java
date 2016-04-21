package arao.gameoflife.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import javax.inject.Inject;

import arao.gameoflife.model.Cell;

import static java.lang.Math.min;

public class BoardView extends View {

    private final static int CELL_SPACING_PIXELS = 1;

    @Inject
    Paint mCellColor;
    @Inject
    Rect mRectangle;

    private boolean[][] mGeneration;
    private int mColumns;
    private int mRows;
    private float mCellPixelSize;
    private OnCellClickListener mOnCellClickListener;

    public BoardView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        resolveDependencies();
        setFocusable(true);
    }

    private void resolveDependencies() {
        CustomViewComponent component = DaggerCustomViewComponent.builder()
                .customModule(new CustomModule())
                .build();

        component.resolveDependenciesFor(this);
    }

    public void setCells(boolean[][] cells) {
        mGeneration = cells;
        mColumns = cells.length;
        mRows = cells[0].length;
        invalidate();
    }

    public void setOnCellClickListener(OnCellClickListener listener) {
        mOnCellClickListener = listener;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        if (mGeneration != null) {
            final int width = getWidth();
            final int height = getHeight();

            mCellPixelSize = min(getCellWidth(width), getCellHeight(height));
            Cell cell = Cell.Builder.aCell().build();

            for (int i = 0; i < mColumns; i++) {
                for (int j = 0; j < mRows; j++) {
                    cell.setX(i);
                    cell.setY(j);
                    cell.setValue(mGeneration[i][j]);
                    drawCell(canvas, cell);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN
                || event.getAction() == MotionEvent.ACTION_MOVE) {
            int cellColumn = getCellColumn(x);
            int cellRow = getCellRow(y);

            if (cellColumn != -1 && cellRow != -1
                    && !mGeneration[cellColumn][cellRow]
                    && mOnCellClickListener != null) {
                Cell clickedCell = Cell.Builder.aCell()
                        .x(cellColumn)
                        .y(cellRow)
                        .value(true)
                        .build();
                mOnCellClickListener.onCellClick(clickedCell);
            }
            return true;
        }
        return false;
    }

    private void drawCell(Canvas canvas, Cell cell) {
        final float xPos = getCellInitialX(cell.getX());
        final float yPos = getCellInitialY(cell.getY());

        mRectangle.set(
                (int) xPos + CELL_SPACING_PIXELS,                   // left
                (int) yPos + CELL_SPACING_PIXELS,                   // top
                (int) (xPos + mCellPixelSize) - CELL_SPACING_PIXELS,  // right
                (int) (yPos + mCellPixelSize) - CELL_SPACING_PIXELS); // bottom

        mCellColor.setColor(cell.getValue() ? Color.BLUE : Color.WHITE);
        canvas.drawRect(mRectangle, mCellColor);
        mRectangle.setEmpty();
    }

    private int getCellRow(int y) {
        for (int j = 0; j < mRows; j++) {
            if (y > mCellPixelSize * j && y < mCellPixelSize * (j + 1)) {
                return j;
            }
        }
        return -1;
    }

    private int getCellColumn(int x) {
        for (int i = 0; i < mColumns; i++) {
            if (x > mCellPixelSize * i && x < mCellPixelSize * (i + 1)) {
                return i;
            }
        }
        return -1;
    }

    private float getCellWidth(final int totalWidth) {
        return (float) totalWidth / mColumns;
    }

    private float getCellHeight(final int totalHeight) {
        return (float) totalHeight / mRows;
    }

    private float getCellInitialX(final int cellXPosition) {
        return mCellPixelSize * cellXPosition;
    }

    private float getCellInitialY(final int cellYPosition) {
        return mCellPixelSize * cellYPosition;
    }
}
