package com.slickmobile.simpledatausage.ui;

import android.support.v7.app.ActionBarActivity;

import com.slickmobile.simpledatausage.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    @Test
    public void testActivityNotNull() {
        ActionBarActivity activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .get();
        assertNotNull(activity);
    }

}
