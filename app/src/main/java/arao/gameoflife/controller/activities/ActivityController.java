package arao.gameoflife.controller.activities;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;

public interface ActivityController {

    void setContentView(@LayoutRes int layoutResID);

    View findViewById(@IdRes int id);

}
