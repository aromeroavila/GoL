package arao.gameoflife.controller.activities;

import android.os.Handler;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @Provides
    Handler handler() {
        return new Handler();
    }
}
