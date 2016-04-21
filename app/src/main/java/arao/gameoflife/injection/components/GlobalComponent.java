package arao.gameoflife.injection.components;

import arao.gameoflife.controller.ControllerModule;
import arao.gameoflife.controller.activities.ActivityModule;
import arao.gameoflife.controller.activities.HomeActivity;
import arao.gameoflife.view.ui.ViewModule;
import arao.gameoflife.view.utils.UtilsModule;
import dagger.Component;

@Component(modules = {ControllerModule.class, ViewModule.class, ActivityModule.class, UtilsModule.class})
public interface GlobalComponent {

    void resolveDependenciesFor(HomeActivity activity);

}
