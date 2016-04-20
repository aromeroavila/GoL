package arao.gameoflife.view.ui;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModule {

//    @Provides
//    HomeUi homeUi(HomeUiImpl homeUi) {
//        return homeUi;
//    }

    @Provides
    HomeUi homeUi() {
        return new HomeUiImpl();
    }

}
