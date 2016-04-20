package arao.gameoflife.view.ui;

import arao.gameoflife.controller.activities.ActivityController;
import arao.gameoflife.controller.activities.HomeController;

public interface HomeUi {

    void createView(ActivityController activityController, HomeController homeController);

    void setData(boolean[][] data);

}
