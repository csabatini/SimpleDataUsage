package com.slickmobile.simpledatausage.mvp.interactor;

import com.slickmobile.simpledatausage.mvp.listener.MainInteractorListener;

public interface MainInteractor {
    void handleUserPlanChoice(boolean choiceMade, MainInteractorListener listener);
}
