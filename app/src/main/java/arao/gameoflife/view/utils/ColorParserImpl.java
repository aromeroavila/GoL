package arao.gameoflife.view.utils;

import android.graphics.Color;

class ColorParserImpl implements ColorParser {

    public static final int RGB_MAX_VALUE = 256;

    @Override
    public int colorFrom(int value) {
        int r = 0;
        int g = 0;
        int b = 0;

        if (value < RGB_MAX_VALUE) {
            b = value;
        } else if (value < RGB_MAX_VALUE * 2) {
            g = value % RGB_MAX_VALUE;
            b = RGB_MAX_VALUE - value % RGB_MAX_VALUE;
        } else if (value < RGB_MAX_VALUE * 3) {
            g = 255;
            b = value % RGB_MAX_VALUE;
        } else if (value < RGB_MAX_VALUE * 4) {
            r = value % RGB_MAX_VALUE;
            g = RGB_MAX_VALUE - value % RGB_MAX_VALUE;
            b = RGB_MAX_VALUE - value % RGB_MAX_VALUE;
        } else if (value < RGB_MAX_VALUE * 5) {
            r = 255;
            g = 0;
            b = value % RGB_MAX_VALUE;
        } else if (value < RGB_MAX_VALUE * 6) {
            r = 255;
            g = value % RGB_MAX_VALUE;
            b = RGB_MAX_VALUE - value % RGB_MAX_VALUE;
        } else if (value < RGB_MAX_VALUE * 7) {
            r = 255;
            g = 255;
            b = value % RGB_MAX_VALUE;
        }

        return Color.argb(255, r, g, b);
    }
}
