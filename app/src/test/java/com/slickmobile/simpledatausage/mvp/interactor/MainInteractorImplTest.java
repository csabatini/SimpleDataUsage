package com.slickmobile.simpledatausage.mvp.interactor;

import com.slickmobile.simpledatausage.mvp.listener.MainInteractorListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class MainInteractorImplTest {

    @Mock
    MainInteractorListener listener;

    private MainInteractor interactor = new MainInteractorImpl();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void interactorWhenUserPlanChoiceTrueDoNothing() {
        interactor.handleUserPlanChoice(true, listener);
        verifyZeroInteractions(listener);
    }

    @Test
    public void interactorWhenUserPlanChoiceFalseInvokeCallback() {
        interactor.handleUserPlanChoice(false, listener);
        verify(listener).onUserPlanChoiceNotMade();
    }


}
