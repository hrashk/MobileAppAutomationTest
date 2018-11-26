package com.mytaxi.android_demo;

import android.content.Context;

import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.dependencies.component.DaggerTestComponent;
import com.mytaxi.android_demo.dependencies.component.TestComponent;
import com.mytaxi.android_demo.dependencies.module.FakeBaseUrlModule;
import com.mytaxi.android_demo.utils.OkHttpIdlingResourceRule;
import com.mytaxi.android_demo.utils.storage.Storage;

import org.junit.Rule;
import org.junit.rules.RuleChain;

import javax.inject.Inject;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.platform.app.InstrumentationRegistry;
import okhttp3.mockwebserver.MockWebServer;

public class BaseTests {

    TestComponent mScreens;
    @Inject
    Storage mStorage;
    @Inject
    OkHttpIdlingResourceRule mIdlingResource;

    final MockWebServer mServer = new MockWebServer();

    {
        mScreens = injectApp();
        mScreens.inject(this);
    }

    /**
     * The activity is not launched right away so that we have a chance to set things up
     */
    final IntentsTestRule<MainActivity> mActivityRule =
            new IntentsTestRule<>(MainActivity.class, false, false);

    @Rule
    public final RuleChain chain = RuleChain
            .outerRule(mServer)
            .around(mIdlingResource)
            .around(mActivityRule);

    private TestComponent injectApp() {
        TestComponent component = DaggerTestComponent.builder()
                .fakeBaseUrlModule(new FakeBaseUrlModule(mServer.url("/app")))
                .build();

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        App app = App.getApplicationContext(context);
        app.setComponent(component);
        return component;
    }

}
