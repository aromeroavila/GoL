package arao.gameoflife.view.custom;

import dagger.Component;

@Component (modules = {CustomModule.class})
interface CustomViewComponent {

    void resolveDependenciesFor(BoardView boardView);

}
