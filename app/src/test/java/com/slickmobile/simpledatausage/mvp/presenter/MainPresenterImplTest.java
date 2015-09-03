package com.slickmobile.simpledatausage.mvp.presenter;

import com.slickmobile.simpledatausage.mvp.interactor.MainInteractor;
import com.slickmobile.simpledatausage.mvp.interactor.MainInteractorImpl;
import com.slickmobile.simpledatausage.mvp.listener.MainInteractorListener;
import com.slickmobile.simpledatausage.mvp.view.MainView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class MainPresenterImplTest {

    @Mock
    MainView view;

    private MainPresenterImpl presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MainInteractor interactor = new MainInteractorImpl();
        presenter = new MainPresenterImpl(view, interactor);
    }

    @Test
    public void presenterWhenViewUserPlanChoiceFalseShowDialog() {
        when(view.getUserPlanChoice()).thenReturn(false);

        presenter.create();
        verify(view).getUserPlanChoice();
        verify(view).showUserChoiceDialog();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void presenterWhenViewUserPlanChoiceTrueShowNothing() {
        when(view.getUserPlanChoice()).thenReturn(true);

        presenter.create();
        verify(view).getUserPlanChoice();
        verifyNoMoreInteractions(view);
    }
}