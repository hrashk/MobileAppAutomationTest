package com.mytaxi.android_demo.utils;

import android.content.Context;

import com.mytaxi.android_demo.App;
import com.mytaxi.android_demo.dependencies.component.DaggerTestComponent;
import com.mytaxi.android_demo.dependencies.component.TestComponent;
import com.mytaxi.android_demo.dependencies.module.FakeBaseUrlModule;

import androidx.test.platform.app.InstrumentationRegistry;
import okhttp3.HttpUrl;

public class DependencyInjector {

    public static TestComponent injectApp(HttpUrl baseUrl) {
        TestComponent component = DaggerTestComponent.builder()
                .fakeBaseUrlModule(new FakeBaseUrlModule(baseUrl))
                .build();

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        App app = App.getApplicationContext(context);
        app.setComponent(component);
        return component;
    }

}
