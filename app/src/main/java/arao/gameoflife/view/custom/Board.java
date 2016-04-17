package arao.gameoflife.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Board extends View {

    private boolean[][] mGeneration;
    private int mWidth;
    private int mHeight;
    private int mCellSize;
    private final Paint mCellColor;
    private Rect mRectangle;

    public Board(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        mCellColor = new Paint();
        mRectangle = new Rect();
    }

    public void setCells(boolean[][] cells) {
        mGeneration = cells;
        mWidth = cells.length;
        mHeight = cells[0].length;
        invalidate();
    }

    public boolean[][] getCells() {
        return mGeneration;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        if (mGeneration != null) {
            final int width = getWidth();
            final int height = getHeight();

            mCellSize = Math.min(getSquareSizeWidth(width), getSquareSizeHeight(height));

            for (int i = 0; i < mWidth; i++) {
                for (int j = 0; j < mHeight; j++) {
                    final int xCoord = getCellInitialX(i);
                    final int yCoord = getCellInitialY(j);

                    mRectangle.set(
                            xCoord + 1,              // left
                            yCoord + 1,              // top
                            xCoord + mCellSize - 1,  // right
                            yCoord + mCellSize - 1   // bottom
                    );

                    mCellColor.setColor(mGeneration[i][j] ? Color.BLUE : Color.WHITE);
                    canvas.drawRect(mRectangle, mCellColor);
                    mRectangle.setEmpty();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN ||
                event.getAction() == MotionEvent.ACTION_MOVE) {
            final int x = (int) event.getX();
            final int y = (int) event.getY();

            int xPos = 0;
            int yPos = 0;

            for (int i = 0; i < mWidth; i++) {
                if (x > mCellSize * i && x < mCellSize * (i + 1)) {
                    xPos = i;
                    break;
                }
            }

            for (int j = 0; j < mHeight; j++) {
                if (y > mCellSize * j && y < mCellSize * (j + 1)) {
                    yPos = j;
                    break;
                }
            }

            if (xPos != 0 && yPos != 0) {
                mGeneration[xPos][yPos] = !mGeneration[xPos][yPos];
                invalidate();

                return true;
            }
        }
        return false;
    }

    private int getSquareSizeWidth(final int width) {
        return (width / mWidth);
    }

    private int getSquareSizeHeight(final int height) {
        return (height / mHeight);
    }

    private int getCellInitialX(final int cellXPosition) {
        return mCellSize * cellXPosition;
    }

    private int getCellInitialY(final int cellYPosition) {
        return mCellSize * cellYPosition;
    }
}
