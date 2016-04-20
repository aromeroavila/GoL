package arao.gameoflife.view.custom;

import android.graphics.Paint;
import android.graphics.Rect;

import dagger.Module;
import dagger.Provides;

@Module
class CustomModule {

    @Provides
    Rect rect() {
        return new Rect();
    }

    @Provides
    Paint paint() {
        return new Paint();
    }

}
