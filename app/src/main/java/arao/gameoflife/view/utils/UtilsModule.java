package arao.gameoflife.view.utils;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    ColorParser colorParser() {
        return new ColorParserImpl();
    }

}
