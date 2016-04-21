package arao.gameoflife.view.utils;

import android.graphics.Color;

class ColorParserImpl implements ColorParser {

    @Override
    public int colorFrom(int value) {
        int r = 0;
        int g = 0;
        int b = 0;

        if (value < 256) {
            b = value;
        } else if (value < 256 * 2) {
            g = value % 256;
            b = 256 - value % 256;
        } else if (value < 256 * 3) {
            g = 255;
            b = value % 256;
        } else if (value < 256 * 4) {
            r = value % 256;
            g = 256 - value % 256;
            b = 256 - value % 256;
        } else if (value < 256 * 5) {
            r = 255;
            g = 0;
            b = value % 256;
        } else if (value < 256 * 6) {
            r = 255;
            g = value % 256;
            b = 256 - value % 256;
        } else if (value < 256 * 7) {
            r = 255;
            g = 255;
            b = value % 256;
        }

        return Color.argb(255, r, g, b);
    }
}
