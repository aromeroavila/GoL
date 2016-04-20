package arao.gameoflife.controller;

import dagger.Module;
import dagger.Provides;

@Module
public class ControllerModule {

//    @Provides
//    Generator generator(GeneratorImpl generator) {
//        return generator;
//    }

    @Provides
    Generator generator() {
        return new GeneratorImpl();
    }
}
